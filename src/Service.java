
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import db.Shop;
import model.Distance;
import model.Graph;
import model.ProductReturnJson;
import model.ProductsInShop;
import path.DijkstraAlgorithm;
import path.TspAlgorithm;
import db.Place;
import db.Product;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@Path("/map")
public class Service {

	@Path("getShops")
	@GET
	@Produces("application/json")
	public Response getShops() throws JSONException {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		List<Shop> shops = session.createQuery("from Shop").list();
		for (int i = 0; i < shops.size(); i++) {
			if (shops.get(i) == null)
				continue;
			ProductsInShop.getInstance().addToShop(shops.get(i).getId(), shops.get(i));
		}

		session.close();

		return Response.status(200).header("Access-Control-Allow-Origin", "*").entity(new JSONArray(shops).toString())
				.build();
	}

	@Path("getShopProducts")
	@GET
	@Produces("application/json")
	public Response getShopProducts(@QueryParam("shopId") Integer shopId) throws JSONException {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		
		List<Place> places = session.createQuery("from Place where shopId = " + shopId).list();
		List<Product> products = session.createQuery("from Product where shopId = " + shopId).list();

		session.close();
		List<ProductReturnJson> returnList = new ArrayList<>();
		
		for (int i = 0; i < products.size(); i++) {
			ProductReturnJson returnProduct = new ProductReturnJson();
			for (int j = 0; j < places.size(); j++) {
				if (places.get(j).getId() == products.get(i).getPlaceId()) {
					returnProduct.setColumnNumber(places.get(j).getColumnNumber());
					returnProduct.setRowNumber(places.get(j).getRowNumber());
					break;
				}
			}
			returnProduct.setName(products.get(i).getProduct_name());
			returnProduct.setShopId(shopId);
			returnList.add(returnProduct);
		}

		ProductsInShop.getInstance().addToProducts(shopId, products);

		return Response.status(200).header("Access-Control-Allow-Origin", "*")
				.entity(new JSONArray(returnList).toString()).build();
	}

	@Path("addProductToShop")
	@GET
	@Produces("application/json")
	public Response addProductToShop(@QueryParam("shopId") Integer shopId, @QueryParam("name") String name, 
			@QueryParam("columnNumber") Integer columnNumber, @QueryParam("rowNumber") Integer rowNumber) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		List<Place> places = session.createQuery("from Place where shopId = " + shopId).list();
		Place place = new Place (columnNumber, rowNumber);
		int placeId = places.indexOf(place);
		if (placeId == -1) {
			session.close();
			return Response.status(500).build();
		}
		
		place = places.get(placeId);
		Product newProduct = new Product(shopId, place.getId(), name);

		session.save(newProduct);

		session.close();
		
		ProductReturnJson returnProduct = new ProductReturnJson();
		returnProduct.setName(name);
		returnProduct.setRowNumber(place.getRowNumber());
		returnProduct.setColumnNumber(place.getColumnNumber());
		returnProduct.setShopId(shopId);
		

		return Response.status(200).header("Access-Control-Allow-Origin", "*")
				.entity(new JSONObject(returnProduct).toString()).build();
	}

	@Path("deleteProduct")
	@GET
	@Produces("application/json")
	public Response deteleProduct(@QueryParam("productId") int productId) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		Query query = session.createQuery("delete Product where productId=" + productId);
		query.executeUpdate();

		session.close();
		return Response.status(200).header("Access-Control-Allow-Origin", "*").build();

	}

	@Path("addShop")
	@GET
	@Produces("application/json")
	public Response addShop(@QueryParam("name") String name, @QueryParam("columnCount") Integer columnCount,
			@QueryParam("rowCount") Integer rowCount) {
		Shop newShop = new Shop(name, columnCount * 2, rowCount * 4 + 1);

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		session.save(newShop);

		generatePlacesForShop(session, name, columnCount, rowCount);

		session.close();

		return Response.status(200).header("Access-Control-Allow-Origin", "*")
				.entity(new JSONObject(newShop).toString()).build();
	}

	private void generatePlacesForShop(Session session, String shopName, Integer columnCount, Integer rowCount) {
		List<Shop> shop = session.createQuery("from Shop where shopName='" + shopName + "'").list();
		Integer shopId = -1;
		if (shop.size() > 0) {
			shopId = shop.get(0).getId();
		}
		if (shopId == -1)
			return;
		ProductsInShop.getInstance().addToShop(shopId, shop.get(0));
		for (int i = 0; i < columnCount * 2; i++) {
			for (int j = 0; j < rowCount * 4 + 1; j++) {
				Place newPlace = new Place(shopId, i, j);
				session.save(newPlace);
			}
		}

		// Temporary solution for testing
		int itemCount = 0;
		List<Place> places = session.createQuery("from Place where shopId=" + shopId).list();
//		for (int i = 0; i < places.size(); i++) {
//			if (places.get(i).getRowNumber() % 4 != 0) {
//				Product newProduct = new Product(shopId, places.get(i).getId(), "Product_" + places.get(i).getRowNumber()
//						+ "_" + places.get(i).getColumnNumber());
//				session.save(newProduct);
//				itemCount++;
//				if (itemCount >= 200) break;
//			}
//		}
		for (int i = 0; i < places.size(); i++) {
			if (places.get(i).getRowNumber() % 4 != 0) {
				Product newProduct = new Product(shopId, places.get(i).getId(), "Product_" + (itemCount + 1));
				session.save(newProduct);
				itemCount++;
				if (itemCount >= 200) break;
			}
		}
	}

	@Path("getShortestPath")
	@GET
	@Produces("application/json")
	public Response getShortestPath(@QueryParam("shop") final Integer shopId,
			@QueryParam("productList") List<String> productList) throws JSONException {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		
		List<Shop> shops = session.createQuery("from Shop").list();
		for (int i = 0; i < shops.size(); i++) {
			if (shops.get(i) == null)
				continue;
			ProductsInShop.getInstance().addToShop(shops.get(i).getId(), shops.get(i));
		}

		List<Place> places = session.createQuery("from Place where shopId = " + shopId).list();
		List<Product> products = session.createQuery("from Product where shopId = " + shopId).list();
		ProductsInShop.getInstance().addToPlaces(shopId, places);
		ProductsInShop.getInstance().addToProducts(shopId, products);

		List<Place> requiredPlacesList = new ArrayList<>();
		for (String productName : productList) {
			if (productName == null)
				continue;
			int placeId = -1;
			for (int i = 0; i < products.size(); i++) {
				if (products.get(i).getProduct_name().equals(productName)) {
					placeId = products.get(i).getPlaceId();
					break;
				}
			}
			if (placeId == -1)
				continue;
			for (int i = 0; i < places.size(); i++) {
				if (places.get(i).getId() == placeId) {
					requiredPlacesList.add(places.get(i));
					break;
				}
			}
		}

		session.close();

		generateGraph(shopId);

		Place sourcePlace = new Place(0, 0);
		sourcePlace = places.get(places.indexOf(sourcePlace));
		requiredPlacesList.add(0, sourcePlace);

		DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(shopId);
		dijkstra.execute(sourcePlace);

		List<Distance> productDistanceList = getShortestPathBetweenProducts(dijkstra, requiredPlacesList);
		List<Integer> requiredPlacesIdOrder = new TspAlgorithm().execute(dijkstra.getIntegerDistanceMatrix(requiredPlacesList.size(), productDistanceList));

		LinkedList<Place> path = new LinkedList<>();
		for (int i = 0; i < requiredPlacesIdOrder.size() - 1; i++) {
			Place source = null, destination = null;
			for (int j = 0; j < ProductsInShop.getInstance().getPlaces().get(shopId).size(); j++) {
				if (ProductsInShop.getInstance().getPlaces().get(shopId).get(j).getId() == 
						requiredPlacesIdOrder.get(i)) {
					source = ProductsInShop.getInstance().getPlaces().get(shopId).get(j);
				}
				if (ProductsInShop.getInstance().getPlaces().get(shopId).get(j).getId() 
						== requiredPlacesIdOrder.get(i + 1)) {
					destination = ProductsInShop.getInstance().getPlaces().get(shopId).get(j);
				}
				if (source != null && destination != null) break;
			}
			
			dijkstra.execute(source);
			List<Place> pathBetweenProducts	= dijkstra.getPath(destination);
			for (int j = 0; j < pathBetweenProducts.size(); j++) {
				if (requiredPlacesIdOrder.contains(pathBetweenProducts.get(j).getId())) {
					pathBetweenProducts.get(j).setContainsRequiredProduct(true);
				}
			}
			path.addAll(pathBetweenProducts);
		}

		System.out.println("");
		for (int i = 0; i < path.size(); i++) {
			System.out.print(path.get(i).getId() + "\t");
		}

		System.out.println("");
		
		return Response.status(200).header("Access-Control-Allow-Origin", "*").entity(new JSONArray(path).toString()).build();
	}

	private void generateGraph(final Integer shopId) {
		Set<Distance> distanceSet = new HashSet<>();

		for (int i = 0; i < ProductsInShop.getInstance().getShops().get(shopId).getColumnCount(); i++) {
			for (int j = 0; j < ProductsInShop.getInstance().getShops().get(shopId).getRowCount(); j++) {

				// Add distances between rows in one column
				Place source = new Place(i, j);
				int placeId = ProductsInShop.getInstance().getPlaces().get(shopId).indexOf(source);
				if (placeId == -1)
					continue;
				Place sourcePlace = ProductsInShop.getInstance().getPlaces().get(shopId).get(placeId);

				Distance distance = addDistanceRowMore(shopId, i, j);
				if (distance != null) {
					distanceSet.add(distance);
				}
				distance = addDistanceRowLess(shopId, i, j);
				if (distance != null) {
					distanceSet.add(distance);
				}

				// Add distances between columns in one row
				if (j % 4 == 0) {
					distance = addDistanceColumnMore(shopId, i, j);
					if (distance != null) {
						distanceSet.add(distance);
					}
					distance = addDistanceColumnLess(shopId, i, j);
					if (distance != null) {
						distanceSet.add(distance);
					}
				} else {
					if (i % 2 == 0) {
						distance = addDistanceColumnLess(shopId, i, j);
						if (distance != null) {
							distanceSet.add(distance);
						}
					} else {
						distance = addDistanceColumnMore(shopId, i, j);
						if (distance != null) {
							distanceSet.add(distance);
						}
					}
				}

			}
		}

		Graph.getInstance().getDistances().put(shopId, new ArrayList<>(distanceSet));
		Graph.getInstance().getPlaces().put(shopId, ProductsInShop.getInstance().getPlaces().get(shopId));
	}

	private Distance addDistanceRowMore(int shopId, int column, int row) throws IndexOutOfBoundsException {
		Place sourcePlace = new Place(column, row);
		sourcePlace = ProductsInShop.getInstance().getPlaces().get(shopId)
				.get(ProductsInShop.getInstance().getPlaces().get(shopId).indexOf(sourcePlace));
		Place destinationPlace = new Place(column, row + 1);
		int destinationId = ProductsInShop.getInstance().getPlaces().get(shopId).indexOf(destinationPlace);
		if (destinationId == -1)
			return null;
		destinationPlace = ProductsInShop.getInstance().getPlaces().get(shopId).get(destinationId);

		return new Distance(sourcePlace, destinationPlace, 1);
	}

	private Distance addDistanceRowLess(int shopId, int column, int row) throws IndexOutOfBoundsException {
		Place sourcePlace = new Place(column, row);
		sourcePlace = ProductsInShop.getInstance().getPlaces().get(shopId)
				.get(ProductsInShop.getInstance().getPlaces().get(shopId).indexOf(sourcePlace));
		Place destinationPlace = new Place(column, row - 1);
		int destinationId = ProductsInShop.getInstance().getPlaces().get(shopId).indexOf(destinationPlace);
		if (destinationId == -1)
			return null;
		destinationPlace = ProductsInShop.getInstance().getPlaces().get(shopId).get(destinationId);

		return new Distance(sourcePlace, destinationPlace, 1);
	}

	private Distance addDistanceColumnMore(int shopId, int column, int row) throws IndexOutOfBoundsException {
		Place sourcePlace = new Place(column, row);
		sourcePlace = ProductsInShop.getInstance().getPlaces().get(shopId)
				.get(ProductsInShop.getInstance().getPlaces().get(shopId).indexOf(sourcePlace));
		Place destinationPlace = new Place(column + 1, row);
		int destinationId = ProductsInShop.getInstance().getPlaces().get(shopId).indexOf(destinationPlace);
		if (destinationId == -1)
			return null;
		destinationPlace = ProductsInShop.getInstance().getPlaces().get(shopId).get(destinationId);

		return new Distance(sourcePlace, destinationPlace, 1);
	}

	private Distance addDistanceColumnLess(int shopId, int column, int row) throws IndexOutOfBoundsException {
		Place sourcePlace = new Place(column, row);
		sourcePlace = ProductsInShop.getInstance().getPlaces().get(shopId)
				.get(ProductsInShop.getInstance().getPlaces().get(shopId).indexOf(sourcePlace));
		Place destinationPlace = new Place(column - 1, row);
		int destinationId = ProductsInShop.getInstance().getPlaces().get(shopId).indexOf(destinationPlace);
		if (destinationId == -1)
			return null;
		destinationPlace = ProductsInShop.getInstance().getPlaces().get(shopId).get(destinationId);

		return new Distance(sourcePlace, destinationPlace, 1);
	}

	private List<Distance> getShortestPathBetweenProducts(DijkstraAlgorithm dijkstra, List<Place> productList) {
		List<Distance> resultList = new ArrayList<>();
		for (int i = 0; i < productList.size(); i++) {
			for (int j = i + 1; j < productList.size(); j++) {
				int distance = 0;
				dijkstra.execute(productList.get(i));
				LinkedList<Place> path = dijkstra.getPath(productList.get(j));
				if (path == null)
					continue;
				for (int k = 0; k < path.size() - 1; k++) {
					distance = distance + dijkstra.getDistance(path.get(k), path.get(k + 1));
				}
				if (distance != 0) {
					resultList.add(new Distance(productList.get(i), productList.get(j), distance));
				}
			}
		}
		return resultList;
	}
}

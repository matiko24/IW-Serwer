
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import db.Shop;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.json.JSONException;
import org.json.JSONObject;

@Path("/map")
public class Service {

	@Path("products")
	@GET
	@Produces("application/json")
	public Response generateMap(@QueryParam("shop") String shopId, @QueryParam("ids") String products)
			throws JSONException {

		List<String> ids = Arrays.asList(products.split(";"));
		JSONObject jsonObject = new JSONObject();
		String path;

		MapGenerator generator = new MapGenerator();
		path = generator.generateMap(shopId, ids);

		jsonObject.put("Result", path);
		return Response.status(200).entity("a" + jsonObject).build();
	}

	@Path("shortestPath")
	@GET
	@Produces("application/json")
	public Response getShortestPath(@QueryParam("shop") String shopId, @QueryParam("productList") List<String> productList)
		throws JSONException {
		return Response.status(200).build();
	}
	@Path("getShops")
	@GET
	@Produces("application/json")
	public Response getShops() throws JSONException{
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		List<Shop> shops = session.createQuery("from Shop").list();

		session.close();

		return Response.status(200).entity(new JSONObject(shops)).build();
	}
}
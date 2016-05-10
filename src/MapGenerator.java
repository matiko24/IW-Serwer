import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import db.Location;
import db.Shop;

public class MapGenerator {

	Shop shop;
	Location[] productsLocation;
	String path = "";
	List<String> locationToSort = new ArrayList<String>();
	Session session = HibernateUtil.getSessionFactory().getCurrentSession();

	public void generateMap(String shopId, List<String> ids) {
		getShopAndProductsDetails(shopId, ids);

		sortLocationsByCoordinateY();

		navigate();
		System.out.println(path);

	}

	private void navigate() {

		int nr = productsLocation.length - 1;
		Position actualPosition = new Position(8, shop.getStart_y());
		Location nextProductLocation = productsLocation[nr];
		int nextProductCoordinateY = nextProductLocation.getY();

		if (nextProductCoordinateY < actualPosition.getY() - 1) {
			path += "L;";
			findY(actualPosition, nr);
		} else if (nextProductCoordinateY > actualPosition.getY() + 1) {
			path += "P;";
			findY(actualPosition, nr);
		} else {
			path += "S;";
			findProduct(actualPosition, nr);
		}

	}

	private void findProduct(Position actualPosition, int nr) {

		int nextProductCoordinateX = productsLocation[nr].getX();
		int nextProductCoordinateY = productsLocation[nr].getY();
		int direction = 0;

		if (actualPosition.getX() == 4) {
			if (actualPosition.getX() > nextProductCoordinateX) {
				direction = 1;
				if (nextProductCoordinateY < actualPosition.getY())
					locationToSort.add((4 - nextProductCoordinateX) + "L;");
				else
					locationToSort.add((4 - nextProductCoordinateX) + "P;");
			} else {
				direction = -1;
				if (nextProductCoordinateY < actualPosition.getY())
					locationToSort.add((nextProductCoordinateX - 4) + "P;");
				else
					locationToSort.add((nextProductCoordinateX - 4) + "L;");
			}
		} else if (actualPosition.getX() == 8) {
			direction = 1;
			if (nextProductCoordinateY < actualPosition.getY())
				locationToSort.add((8 - nextProductCoordinateX) + "L;");
			else
				locationToSort.add((8 - nextProductCoordinateX) + "P;");
		} else {
			direction = -1;
			if (nextProductCoordinateY < actualPosition.getY())
				locationToSort.add(nextProductCoordinateX + "P;");
			else
				locationToSort.add(nextProductCoordinateX + "L;");
		}

		if (nr == 0) {
			sort(actualPosition.getX());
			path += "M;";
		} else {

			if (actualPosition.getY() == (productsLocation[nr - 1].getY() + 1)
					|| actualPosition.getY() == (productsLocation[nr - 1].getY() - 1)) {
				System.out.println("aY = " + actualPosition.getY() + " aX = " + actualPosition.getX() + " pY= "
						+ productsLocation[nr].getY() + " X= " + productsLocation[nr].getX());
				findProduct(new Position(actualPosition.getX(), actualPosition.getY()), nr - 1);

			} else {
				sort(actualPosition.getX());
				findX(new Position(nextProductCoordinateX, actualPosition.getY()), direction, nr - 1);
			}

		}
	}

	private void sort(int actualPositionX) {

		String[] s = new String[locationToSort.size()];

		for (int i = 0; i < locationToSort.size(); i++) {
			s[i] = locationToSort.get(i);
		}

		if (actualPositionX == 8) {
			if (s.length > 1)
				sort1(s);
			for (String k : s) {
				path += k;
			}
		}

	}

	private void sort1(String[] s) {

		boolean swapped = true;
		int j = 0;
		String tmp;
		while (swapped) {
			swapped = false;
			j++;
			for (int i = 0; i < s.length - j; i++) {
				if (Integer.valueOf((s[i].substring(0, 1))) > Integer.valueOf(s[i + 1].substring(0, 1))) {
					tmp = s[i];
					s[i] = s[i + 1];
					s[i + 1] = tmp;
					swapped = true;
				}
			}
		}
	}

	private void findX(Position actualPosition, int direction, int nr) {

		if ((actualPosition.getX() == 7 || actualPosition.getX() == 6) && productsLocation[nr].getX() == 7) {
			actualPosition.setX(8);
			if (direction == 1)
				path += "ZP;";
			else
				path += "SP;";
		} else if ((actualPosition.getX() == 1 || actualPosition.getX() == 2) && productsLocation[nr].getX() == 1) {
			actualPosition.setX(0);
			if (direction == 1)
				path += "SL;";
			else
				path += "ZL;";
		} else if (actualPosition.getX() == 5 || actualPosition.getX() == 6 || actualPosition.getX() == 7) {
			actualPosition.setX(4);
			if (direction == 1)
				path += "SL;";
			else
				path += "ZL;";
		} else if (actualPosition.getX() == 1 || actualPosition.getX() == 2 || actualPosition.getX() == 3) {
			actualPosition.setX(4);
			if (direction == 1)
				path += "ZP;";
			else
				path += "SP;";
		}

		findY(actualPosition, nr);
	}

	private void findY(Position actualPosition, int nr) {

		int nextCoordinateY = 3 * ((int) ((productsLocation[nr].getY() + 1) / 3));
		int amountOfWays = (actualPosition.getY() - nextCoordinateY) / 3;

		System.out.println(
				actualPosition.getY() + " " + productsLocation[nr].getY() + " " + nextCoordinateY + " " + amountOfWays);
		if (productsLocation[nr].getX() > actualPosition.getX()) {
			path += amountOfWays + "L;";
		} else
			path += amountOfWays + "P;";
		findProduct(new Position(actualPosition.getX(), nextCoordinateY), nr);
	}

	private void sortLocationsByCoordinateY() {

		boolean swapped = true;
		int j = 0;
		Location tmp;
		while (swapped) {
			swapped = false;
			j++;
			for (int i = 0; i < productsLocation.length - j; i++) {
				if ((productsLocation[i].getY() > productsLocation[i + 1].getY())
						&& (productsLocation[i].getY() - productsLocation[i + 1].getY() != 2)) {
					tmp = productsLocation[i];
					productsLocation[i] = productsLocation[i + 1];
					productsLocation[i + 1] = tmp;
					swapped = true;
				}
			}
		}
	}

	public void getShopAndProductsDetails(String shopId, List<String> productsId) {

		session.beginTransaction();

		shop = (Shop) session.get(Shop.class, Integer.valueOf(shopId));
		productsLocation = new Location[productsId.size()];

		for (int i = 0; i < productsId.size(); i++) {
			productsLocation[i] = session.get(Location.class, Integer.valueOf(productsId.get(i)));
		}
	}

	private void findProducts(Position actualPosition, int nr) {

		List<Location> tmp = new ArrayList<Location>();
		for (Location l : productsLocation) {
			if (productsLocation[nr].getY() == l.getY() || (Math.abs(productsLocation[nr].getY() - l.getY()) == 2))
				tmp.add(l);
		}
		Location[] thisSameCoordinateYLocation = new Location[tmp.size()];

		for (int i = 0; i < tmp.size(); i++) {
			thisSameCoordinateYLocation[i] = tmp.get(i);
		}

		for (Location l : thisSameCoordinateYLocation) {
			findProduct(actualPosition, nr);
		}

	}

	private String[] sortLocationsByDistance(String[] locationToSort) {

		boolean swapped = true;
		int j = 0;
		String tmp;
		while (swapped) {
			swapped = false;
			j++;
			for (int i = 0; i < locationToSort.length - j; i++) {
				if (Integer.valueOf(locationToSort[i].substring(0, 1)) > Integer
						.valueOf(locationToSort[i + 1].substring(0, 1))) {
					tmp = locationToSort[i];
					locationToSort[i] = locationToSort[i + 1];
					locationToSort[i + 1] = tmp;
					swapped = true;
				}
			}
		}
		return locationToSort;

	}

}

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import db.Shop;

public class Test {

	public static void main(String[] Args) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		Shop shop1 = session.get(Shop.class, 1);

		MapGenerator g = new MapGenerator();

		List<String> s = new ArrayList<String>();
		s.add("115");
		s.add("95");
		s.add("35");
		s.add("11");
	

		g.generateMap(String.valueOf(shop1.getId()), s);

	}
}
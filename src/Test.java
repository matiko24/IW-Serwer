import java.util.ArrayList;
import java.util.List;

import db.Shop;
import org.hibernate.Session;

public class Test {

	public static void main(String[] Args) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		Shop shop1 = session.get(Shop.class, 1);

		MapGenerator g = new MapGenerator();

		List<String> s = new ArrayList<String>();
		s.add("76");
		s.add("75");
		s.add("55");
		s.add("95");
		s.add("115");
		s.add("35");
		s.add("73");
		
	

		g.generateMap(String.valueOf(shop1.getId()), s);

	}
}
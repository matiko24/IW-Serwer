import org.hibernate.Session;

public class Main {

	public static void main(String[] args) {

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		Shop shop = new Shop("biedronka", "narol");

		session.save(shop);

		session.getTransaction().commit();

		session.close();
	}

}

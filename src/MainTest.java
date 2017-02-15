import db.Shop;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

/**
 * Created by Belhaver on 13.02.2017.
 */
public class MainTest {

    public static void main(String[] args) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        List<Shop> shops = session.createQuery("from Shop").list();

        session.close();

        for (Shop st : shops) {
            System.out.println(st.getId() + ". " + st.getId() + " " + st.getName());
        }
    }
}

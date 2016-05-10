
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import db.*;

public class Main {

	public static void main(String[] args) {

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();

			Shop shop1 = new Shop("tesco", "krakow");
			shop1.setMap(new byte[][] {

					{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
							0 },
					{ 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
							0 },
					{ 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1,
							0 },
					{ 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1,
							0 },
					{ 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1,
							0 },
					{ 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
							0 },
					{ 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1,
							0 },
					{ 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1,
							0 },
					{ 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1,
							0 },
					{ 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
							0 },
					{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
							0 } });
			shop1.setStart_y(24);

			session.save(shop1);

			/*
			 * String products[] = new String[] { "chleb", "mleko", "woda",
			 * "jab³ko", "jogurt", "bu³ka", "musli", "banan", "jajka",
			 * "kie³basa", "makaron", "musztarda", "sok", "ryba", "m¹ka", "ser",
			 * "kefir", "orzeszki", "pomidor", "ogórek", "ziemniaki", "piwo",
			 * "kasza", "pieprz", "czekolada", "herbata", "kawa", "papryka",
			 * "mas³o", "œmietana", "twaróg", "m¹ka", "paluszki", "orzeszki",
			 * "baton", "lód", "szpinak", "frytki", "gumy", "czosnek",
			 * "œledzie", "majonez", "gazeta", "myd³o", "pianka do golenia",
			 * "ocet", "szampon", "chipsy", "chusteczki", "wino",
			 * "maszynki do golenia", "olej", "oliwa z oliwek" , "budyñ",
			 * "bita œmietana", "ry¿", "sos pomidorowy", "proszek",
			 * "worek na œmieci", "pampersy" };
			 * 
			 * for (String productName : products) { session.save(new
			 * Product(productName)); }
			 */

			int productId = 1;
			for (int i = 1; i <= 7; i++) {
				if (i == 4) {
					;
				} else {
					for (int j = 1; j <= 29; j++) {
						if (j % 3 == 0) {
							;
						} else {
							session.save(new Product("product(" + i + "," + j + ")"));
							session.save(new Location(1, productId, i, j));
							productId++;
						}
					}
				}
			}
			transaction.commit();
		} catch (HibernateException e) {
			if (transaction != null)
				transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

}

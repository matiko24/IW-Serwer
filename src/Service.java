
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.hibernate.Session;
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

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		Shop shop = (Shop) session.get(Shop.class, 1);

		jsonObject.put("ShopId", shop.getId());
		jsonObject.put("Result", shop.getShopname());

		return Response.status(200).entity("a" + jsonObject).build();
	}
}

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

import db.Shop;

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
}
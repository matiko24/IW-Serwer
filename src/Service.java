
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

import org.json.JSONException;
import org.json.JSONObject;

@Path("/ftocservice")
public class Service {

	@Path("products")
	@GET
	@Produces("application/json")
	public Response convertFtoCfromInput(@QueryParam("shop") String shopId, @QueryParam("ids") String products)
			throws JSONException {

		List<String> ids = Arrays.asList(products.split(";"));
		JSONObject jsonObject = new JSONObject();
		String result = "";
		for (String i : ids) {
			result += i;
		}
		jsonObject.put("Result ", result);
		jsonObject.put("ShopId ", shopId);

		Connection con = null;
		Statement statement = null;
		ResultSet resultSet = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/baza","root","moknij");
			
			statement = con.createStatement();
			resultSet = statement.executeQuery("select * from shops");

			while (resultSet.next()) {
				System.out.println(resultSet.getInt(1)+resultSet.getString(2)+resultSet.getString(3));
			}
			con.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return Response.status(200).entity("a" + jsonObject).build();
	}
}
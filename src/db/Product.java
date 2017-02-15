package db;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "product")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "productId")
	private int id;

	@Column(name = "shopId")
	private int shopId;
	
	@Column(name = "placeId")
	private int placeId;

	@Column(name = "name")
	private String name;

//	@Column(name = "latitude")
//	private Double latitude;
//	
//	@Column(name = "longitude")
//	private Double longitude;

	public Product() {
		super();
		this.name = "";
		this.shopId = 0;
		this.placeId = 0;
	}

	public Product(Integer shopId, Integer placeId, String product_name) {
		super();
		this.name = product_name;
		this.shopId = shopId;
		this.placeId = placeId;
	}

	public int getId() {
		return id;
	}
	
	public int getShopId() {
		return shopId;
	}
	
	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}
	
	public int getPlaceId() {
		return placeId;
	}
	
	public void setPlaceId(Integer placeId) {
		this.placeId = placeId;
	}

	public String getProduct_name() {
		return name;
	}

//	public Double getLatitude() {
//		return latitude;
//	}
//	
//	public void setLatitude(Double latitude) {
//		this.latitude = latitude;
//	}
//
//	public Double getLongitude() {
//		return longitude;
//	}
//	
//	public void setLongitude(Double longitude) {
//		this.longitude = longitude;
//	}

}

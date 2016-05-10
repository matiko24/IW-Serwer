package db;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.Table;

@Entity
@Table(name = "shop")
public class Shop {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "shop_name")
	private String shop_name;

	@Column(name = "city")
	private String city;

	@Column(name = "street")
	private String street;

	@Column(name = "nr")
	private int nr;

	@Column(name = "map")
	private byte[][] map;

	@Column(name = "start_y")
	private int start_y;

	public Shop() {
		super();
		this.shop_name = "";
		this.city = "";
		this.street = "";
		this.nr = 0;
		this.map = new byte[][] { {} };
		this.start_y = 0;
	}

	public Shop(String shop_name, String city) {
		super();
		this.shop_name = shop_name;
		this.city = city;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getShop_name() {
		return shop_name;
	}

	public void setShop_name(String shop_name) {
		this.shop_name = shop_name;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public int getNr() {
		return nr;
	}

	public void setNr(int nr) {
		this.nr = nr;
	}

	public byte[][] getMap() {
		return map;
	}

	public void setMap(byte[][] map) {
		this.map = map;
	}

	public int getStart_y() {
		return start_y;
	}

	public void setStart_y(int start_y) {
		this.start_y = start_y;
	}

}

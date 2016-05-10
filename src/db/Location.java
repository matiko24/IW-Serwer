package db;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "location")
public class Location {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "shop_id")
	private int shop_id;

	@Column(name = "product_id")
	private int product_id;

	@Column(name = "x")
	private int x;

	@Column(name = "y")
	private int y;

	public Location() {
		super();
		this.shop_id = 0;
		this.product_id = 0;
		this.x = 0;
		this.y = 0;
	}

	public Location(int shop_id, int product_id, int x, int y) {
		super();
		this.shop_id = shop_id;
		this.product_id = product_id;
		this.x = x;
		this.y = y;
	}

	public int getShop_id() {
		return shop_id;
	}

	public void setShop_id(int shop_id) {
		this.shop_id = shop_id;
	}

	public int getProduct_id() {
		return product_id;
	}

	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

}

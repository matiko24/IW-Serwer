package model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.Place;
import db.Product;
import db.Shop;

public class ProductsInShop {
	
	private static ProductsInShop instance = new ProductsInShop();
	private static Map<Integer, List<Product>> products;
	private static Map<Integer, List<Place>> places;
	private static Map<Integer, Shop> shops;

	
	public static ProductsInShop getInstance() {
		return instance;
	}
	
	private ProductsInShop(){
		places = new HashMap<>();
		products = new HashMap<>();
		shops = new HashMap<>();
	}
	
	public void addToProducts(Integer shopId, List<Product> productList) {
		if (products.containsKey(shopId)) {
			products.get(shopId).clear();
			products.get(shopId).addAll(productList);
		} else {
			products.put(shopId, productList);
		}
	}
	
	public void addToPlaces(Integer shopId, List<Place> placeList) {
		if (places.containsKey(shopId)) {
			places.get(shopId).clear();
			places.get(shopId).addAll(placeList);
		} else {
			places.put(shopId, placeList);
		}
	}
	
	public void addToShop(Integer shopId, Shop shop) {
		if (shops.containsKey(shopId)) {
			shops.remove(shopId);
			shops.put(shopId, shop);
		} else {
			shops.put(shopId, shop);
		}
	}

	public static Map<Integer, List<Product>> getProducts() {
		return products;
	}

	public static Map<Integer, List<Place>> getPlaces() {
		return places;
	}

	public static Map<Integer, Shop> getShops() {
		return shops;
	}
}

package model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.Place;
import db.Product;

/**
 * Created by Belhaver on 11.02.2017.
 */
public class Graph {
	
	private static Graph instance = new Graph();
	private static Map<Integer, List<Place>> places;
	private static Map<Integer, List<Distance>> distances;
	
	public static Graph getInstance() {
		return instance;
	}

    private Graph() {
		places = new HashMap<>();
		distances = new HashMap<>();
	}

	public static Map<Integer, List<Place>> getPlaces() {
		return places;
	}

	public static Map<Integer, List<Distance>> getDistances() {
		return distances;
	}

	
}

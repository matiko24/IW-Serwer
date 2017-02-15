package path;

import java.util.*;

import db.Place;
import model.Distance;
import model.Graph;

/**
 * Created by Belhaver on 11.02.2017.
 */
public class DijkstraTestMain {

    private static List<Place> nodeList;
    private static List<Distance> distanceList;

    public static void main(String[] args) {

        nodeList = new ArrayList<>();
        distanceList = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
        	Place location = new Place(i, "Node_" + i);
            nodeList.add(location);
        }

        addLane(0, 1, 85);
        addLane(0, 2, 217);
        addLane(0, 3, 312);
        addLane(0, 5, 173);
        addLane(1, 4, 123);
        addLane(2, 5, 213);
        addLane(2, 6, 186);
        addLane(2, 8, 103);
        addLane(3, 7, 183);
        addLane(5, 8, 250);
        addLane(8, 9, 84);
        addLane(7, 9, 167);
        addLane(4, 9, 502);
        addLane(9, 10, 40);
        addLane(1, 10, 600);

        Graph graph = new Graph(nodeList, distanceList);
        DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph);
        dijkstra.execute(nodeList.get(0));

        List<Place> products = new ArrayList<>();
        products.add(nodeList.get(0));
        products.add(nodeList.get(2));
        products.add(nodeList.get(5));
        products.add(nodeList.get(8));
        products.add(nodeList.get(10));
        List<Distance> productDistanceList = getShortestPathBetweenProducts(dijkstra, products);

        new TspAlgorithm().execute(dijkstra.getIntegerDistanceMatrix(products.size(), productDistanceList));
    }

    private static void addLane(int sourceLocNo, int destLocNo,
                         int duration) {
        Distance lane = new Distance(nodeList.get(sourceLocNo), nodeList.get(destLocNo), duration );
        distanceList.add(lane);
    }

    private static List<Distance> getShortestPathBetweenProducts(DijkstraAlgorithm dijkstra, List<Place> productList) {
        List<Distance> resultList = new ArrayList<>();
        for (int i = 0; i < productList.size(); i++) {
            for (int j = i + 1; j < productList.size(); j++) {
                int distance = 0;
                dijkstra.execute(productList.get(i));
                LinkedList<Place> path = dijkstra.getPath(productList.get(j));
                if (path == null) continue;
                for (int k = 0; k < path.size() - 1; k++) {
                    distance = distance + dijkstra.getDistance(path.get(k), path.get(k + 1));
                }
                if (distance != 0) {
                    resultList.add(new Distance(productList.get(i), productList.get(j), distance));
                }
            }
        }
        return resultList;
    }
}

package path;

import java.util.*;

import db.Place;
import model.Distance;
import model.Graph;

/**
 * Created by Belhaver on 11.02.2017.
 */
public class DijkstraAlgorithm {

    private List<Place> nodeList;
    private List<Distance> distanceList;
    private Set<Place> settledNodes;
    private Set<Place> unSettledNodes;
    private Map<Place, Place> predecessors;
    private Map<Place, Integer> distanceMap;

    public DijkstraAlgorithm(int shopId) {
        nodeList = new ArrayList<>(Graph.getInstance().getPlaces().get(shopId));
        distanceList = new ArrayList<>(Graph.getInstance().getDistances().get(shopId));
    }

    public void execute(Place source) {
        settledNodes = new HashSet<>();
        unSettledNodes = new HashSet<>();
        distanceMap = new HashMap<>();
        predecessors = new HashMap<>();
        distanceMap.put(source, 0);
        unSettledNodes.add(source);
        while (unSettledNodes.size() > 0) {
        	Place node = getMinimum(unSettledNodes);
            settledNodes.add(node);
            unSettledNodes.remove(node);
            findMinimalDistances(node);
        }
    }

    private void findMinimalDistances(Place node) {
        List<Place> adjacentNodes = getNeighbors(node);
        for (Place target : adjacentNodes) {
            if (getShortestDistance(target) > getShortestDistance(node) + getDistance(node, target)) {
                distanceMap.put(target, getShortestDistance(node) + getDistance(node, target));
                predecessors.put(target, node);
                unSettledNodes.add(target);
            }
        }
    }

    public int getDistance(Place node, Place target) {
        for (Distance distance : distanceList) {
            if (distance.getSource().equals(node)
                    && distance.getDestination().equals(target)) {
                return distance.getDistance();
            }
        }
        throw new RuntimeException("Should not happen");
    }

    private List<Place> getNeighbors(Place node) {
        List<Place> neighbors = new ArrayList<>();
        for (Distance distance : distanceList) {
            if (distance.getSource().equals(node)
                    && !isSettled(distance.getDestination())) {
                neighbors.add(distance.getDestination());
            }
        }
        return neighbors;
    }

    private Place getMinimum(Set<Place> products) {
    	Place minimum = null;
        for (Place product : products) {
            if (minimum == null) {
                minimum = product;
            } else {
                if (getShortestDistance(product) < getShortestDistance(minimum)) {
                    minimum = product;
                }
            }
        }
        return minimum;
    }

    private boolean isSettled(Place product) {
        return settledNodes.contains(product);
    }

    private int getShortestDistance(Place destination) {
        Integer d = distanceMap.get(destination);
        if (d == null) {
            return Integer.MAX_VALUE;
        } else {
            return d;
        }
    }

    public LinkedList<Place> getPath(Place target) {
        LinkedList<Place> path = new LinkedList<>();

        Place step = target;

        if (predecessors.get(step) == null) {
            return null;
        }
        path.add(step);
        while (predecessors.get(step) != null) {
            step = predecessors.get(step);
            path.add(step);
        }

        Collections.reverse(path);
        return path;
    }


    public int[][] getIntegerDistanceMatrix(int matrixSize, List<Distance> productDistanceList) {
        List<Distance> distances = new ArrayList<>();
        for (int i = 0; i < productDistanceList.size(); i++) {
            distances.add(new Distance(productDistanceList.get(i).getSource(), productDistanceList.get(i).getDestination(),
                    productDistanceList.get(i).getDistance()));
            distances.add(new Distance(productDistanceList.get(i).getDestination(), productDistanceList.get(i).getSource(),
                    productDistanceList.get(i).getDistance()));
        }

        int[][] distanceMatrix = new int[matrixSize][matrixSize];
//        Distance[][] predecessorMatrix = new Distance[matrixSize][matrixSize];
        for (int i = 0; i < distanceMatrix.length; i++) {
            for (int j = 0; j < distanceMatrix[i].length; j++) {
                distanceMatrix[i][j] = i == j ? 0 : Integer.MAX_VALUE;
//                predecessorMatrix[i][j] = null;
            }
        }
        for (Distance distance : distances) {
            distanceMatrix[IdSubstitution.getInstance().getIdSubstitute(distance.getSource().getId(), IdSubstitution.NORMAL_TYPE)]
                    [IdSubstitution.getInstance().getIdSubstitute(distance.getDestination().getId(), IdSubstitution.NORMAL_TYPE)] = distance.getDistance();
//            predecessorMatrix[IdSubstitution.getInstance().getIdSubstitute(distance.getSource().getId(), IdSubstitution.NORMAL_TYPE)]
//                    [IdSubstitution.getInstance().getIdSubstitute(distance.getDestination().getId(), IdSubstitution.NORMAL_TYPE)] = distance;
        }

//        for (int k = 0; k < distanceMatrix.length; k++) {
//            for (int i = 0; i < distanceMatrix.length; i++) {
//                for (int j = 0; j < distanceMatrix.length; j++) {
//                    if (distanceMatrix[i][j] > distanceMatrix[i][k] + distanceMatrix[k][j]) {
//                        if (distanceMatrix[i][k] + distanceMatrix[k][j] < 0) continue;
//                        distanceMatrix[i][j] = distanceMatrix[i][k] + distanceMatrix[k][j];
//                        predecessorMatrix[i][j] = predecessorMatrix[k][j];
//                    }
//                }
//            }
//        }

        for (int i = 0; i < distanceMatrix.length; i++) {
            for (int j = 0; j < distanceMatrix[i].length; j++) {
                System.out.print((distanceMatrix[i][j] == Integer.MAX_VALUE ? "-" : distanceMatrix[i][j]) + "\t");
            }
            System.out.println("");
        }
        System.out.println("");

        System.out.println(IdSubstitution.getInstance().getIndexSubstituteMap().toString());
        System.out.println(IdSubstitution.getInstance().getRevertIndexSubstituteMap().toString());

        System.out.println("");

        return distanceMatrix;
    }

    public Place getProduct(int position) {
        return nodeList.get(position);
    }

    public Distance getEdge(int position) {
        return distanceList.get(position);
    }

    public List<Place> getNodeList() {
        return nodeList;
    }

    public List<Distance> getDistanceList() {
        return distanceList;
    }
}

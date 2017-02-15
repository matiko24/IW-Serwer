package path;

import java.util.*;

/**
 * Created by Belhaver on 11.02.2017.
 */
public class DijkstraAlgorithm {

    private List<Product> nodeList;
    private List<Distance> distanceList;
    private Set<Product> settledNodes;
    private Set<Product> unSettledNodes;
    private Map<Product, Product> predecessors;
    private Map<Product, Integer> distanceMap;

    public DijkstraAlgorithm(Graph graph) {
        nodeList = new ArrayList<>(graph.getProductList());
        distanceList = new ArrayList<>(graph.getDistanceList());
    }

    public void execute(Product source) {
        settledNodes = new HashSet<>();
        unSettledNodes = new HashSet<>();
        distanceMap = new HashMap<>();
        predecessors = new HashMap<>();
        distanceMap.put(source, 0);
        unSettledNodes.add(source);
        while (unSettledNodes.size() > 0) {
            Product node = getMinimum(unSettledNodes);
            settledNodes.add(node);
            unSettledNodes.remove(node);
            findMinimalDistances(node);
        }
    }

    private void findMinimalDistances(Product node) {
        List<Product> adjacentNodes = getNeighbors(node);
        for (Product target : adjacentNodes) {
            if (getShortestDistance(target) > getShortestDistance(node) + getDistance(node, target)) {
                distanceMap.put(target, getShortestDistance(node) + getDistance(node, target));
                predecessors.put(target, node);
                unSettledNodes.add(target);
            }
        }
    }

    public int getDistance(Product node, Product target) {
        for (Distance distance : distanceList) {
            if (distance.getSource().equals(node)
                    && distance.getDestination().equals(target)) {
                return distance.getDistance();
            }
        }
        throw new RuntimeException("Should not happen");
    }

    private List<Product> getNeighbors(Product node) {
        List<Product> neighbors = new ArrayList<>();
        for (Distance distance : distanceList) {
            if (distance.getSource().equals(node)
                    && !isSettled(distance.getDestination())) {
                neighbors.add(distance.getDestination());
            }
        }
        return neighbors;
    }

    private Product getMinimum(Set<Product> products) {
        Product minimum = null;
        for (Product product : products) {
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

    private boolean isSettled(Product product) {
        return settledNodes.contains(product);
    }

    private int getShortestDistance(Product destination) {
        Integer d = distanceMap.get(destination);
        if (d == null) {
            return Integer.MAX_VALUE;
        } else {
            return d;
        }
    }

    public LinkedList<Product> getPath(Product target) {
        LinkedList<Product> path = new LinkedList<>();

        Product step = target;

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

    public Product getProduct(int position) {
        return nodeList.get(position);
    }

    public Distance getEdge(int position) {
        return distanceList.get(position);
    }

    public List<Product> getNodeList() {
        return nodeList;
    }

    public List<Distance> getDistanceList() {
        return distanceList;
    }
}

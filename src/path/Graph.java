package path;

import java.util.List;

/**
 * Created by Belhaver on 11.02.2017.
 */
public class Graph {

    private List<Product> productList;
    private List<Distance> distanceList;

    public Graph(List<Product> productList, List<Distance> distanceList) {
        this.productList = productList;
        this.distanceList = distanceList;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public List<Distance> getDistanceList() {
        return distanceList;
    }
}

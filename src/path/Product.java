package path;

/**
 * Created by Belhaver on 11.02.2017.
 */
public class Product {

    String name;
    int id;

    public Product(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}

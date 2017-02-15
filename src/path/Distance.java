package path;

/**
 * Created by Belhaver on 11.02.2017.
 */
public class Distance {

    private Product source, destination;
    private int distance;

    public Distance(Product source, Product destination, int distance) {
        this.source = source;
        this.destination = destination;
        this.distance = distance;
    }

    public Product getSource() {
        return source;
    }

    public Product getDestination() {
        return destination;
    }

    public int getDistance() {
        return distance;
    }

    @Override
    public String toString() {
        return "Distance{" +
                "source=" + source +
                ", destination=" + destination +
                ", distance=" + distance +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Distance that = (Distance) o;

        if (!source.equals(that.source)) return false;
        return destination.equals(that.destination);
    }

    @Override
    public int hashCode() {
        int result = source.hashCode();
        result = 31 * result + destination.hashCode();
        return result;
    }
}

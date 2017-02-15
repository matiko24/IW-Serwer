package model;

import db.Place;
import path.Product;

/**
 * Created by Belhaver on 11.02.2017.
 */
public class Distance {

    private Place source, destination;
    private int distance;

    public Distance(Place source, Place destination, int distance) {
        this.source = source;
        this.destination = destination;
        this.distance = distance;
    }

    public Place getSource() {
        return source;
    }

    public Place getDestination() {
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

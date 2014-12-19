package kmeans;

import java.util.List;

/**
 * Created by OP on 18-12-14.
 */
public class DataPoint {

    private final List<Double> dataVector;

    public int getId() {
        return id;
    }

    private final int id;

    public DataPoint(int id, List<Double> dataVector) {
        this.id = id;
        this.dataVector = dataVector;
    }

    public List<Double> getDataVector() {
        return dataVector;
    }

    public int getDim() {
        return dataVector.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DataPoint dataPoint = (DataPoint) o;

        if (id != dataPoint.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "DataPoint{" +
                "id=" + id +
                ", dataVector=" + dataVector +
                '}';
    }
}

package kmeans;

import java.util.*;

/**
 * Created by OP on 18-12-14.
 */
public class Cluster {

    private final int id;
    private final int dim;
    private Map<Integer, DataPoint> points;
    private DataPoint currCentroid;

    @Override
    public String toString() {
        return "Cluster{" +
                "id="     + id +
                ", size=" + points.size() +
                ", currCentroid=" + currCentroid.getDataVector() +
                '}';
    }

    public Cluster(int id, int dim) {
        this.id = id;
        this.dim = dim;
        this.points = new HashMap<Integer,DataPoint>();
    }

    public DataPoint getCentroid() {
        // return centroid
        return currCentroid;
    }

    public void refreshCentroid() {
        double[] centroid = new double[dim];
        // accumulate points
        for (DataPoint p : points.values()) {
            List<Double> currPointValues = p.getDataVector();
            for (int d = 0; d < dim; d++) {
                centroid[d] += currPointValues.get(d);
            }
        }
        // calculate mean for each dim
        int pointNum = points.size();
        List<Double> centroidList = new ArrayList<Double>();
        for (int d = 0; d < dim; d++) {
            centroidList.add(centroid[d]/pointNum);
        }
        this.currCentroid = new DataPoint(-(id+1),centroidList);
    }

    public int getId() {
        return id;
    }

    public Iterable<DataPoint> getPoints(){
        return Collections.unmodifiableCollection(points.values());
    }

    public DataPoint getPoint(Integer pointID) {
        return points.get(pointID);
    }

    public void addPoint(DataPoint point) {
        points.put(point.getId(), point);
    }

    // remove by ID
    public void removePoint(Integer pointID) {
        points.remove(pointID);
    }

    //remove by object reference
    public void removePoint(DataPoint point) {
        points.values().remove(point);
    }

    public int getPointNum() { return points.size(); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cluster cluster = (Cluster) o;

        if (id != cluster.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id;
    }
}

package kmeans;

import java.util.List;

/**
 * Created by OP on 18-12-14.
 */
public interface ClusteringAlg {

    public List<Cluster> cluster(List<DataPoint> points, int k) throws IllegalArgumentException;

}

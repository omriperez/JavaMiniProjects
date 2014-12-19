package kmeans;

/**
 * Created by OP on 18-12-14.
 */
public interface DistanceCalculator {

    public double calculate(DataPoint point1, DataPoint point2);

}

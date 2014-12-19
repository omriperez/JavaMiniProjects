package kmeans;

import java.util.List;

/**
 * Created by OP on 18-12-14.
 */
public class DistanceCalculatorEuclidean implements DistanceCalculator {

    public double calculate(DataPoint point1, DataPoint point2) {
        List<Double> p1 = point1.getDataVector();
        List<Double> p2 = point2.getDataVector();
        double SumSquares = 0.0;
        int minLength = Math.min(p1.size(),p2.size());
        // Accumulate square distance in each dim
        for (int i = 0; i < minLength; i++) {
            SumSquares += Math.pow(p1.get(i)-p2.get(i),2);
        }
        return Math.sqrt(SumSquares);
    }

}

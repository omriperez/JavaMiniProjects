package equipoint; /**
 * Created by shay on 18-12-14.
 */

import java.util.ArrayList;
import java.util.List;

public class EquiPoint {
    private EquiPointStrategy equiPointStrategy;

    public EquiPoint(EquiPointStrategy equiPointStrategy) {
        this.equiPointStrategy = equiPointStrategy;
    }

    public int run(double[] values) {
        return equiPointStrategy.calculate(values);
    }

    public static void main(String[] args) {
        String inputStr = args[0];
        String[] inputStrsArr = inputStr.split(",");
        List<Double> inputDoubles = new ArrayList<Double>();
        for (String s : inputStrsArr) {
            try {
                double currDouble = Double.parseDouble(s);
                inputDoubles.add(currDouble);
            } catch (Exception e) {
            } // Ignore errors

        }
        // convert to primitive type
        double[] values = new double[inputDoubles.size()];
        for (int i = 0; i< values.length; i++) {
            values[i] = inputDoubles.get(i);
        }
        // get computation engine and run
        EquiPoint engine = new EquiPoint(new EquiPointLinearStrategy());
        int result = engine.run(values);
        System.out.println(result);


    }
}

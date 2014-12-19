package equipoint;

/**
 * Created by shay on 18-12-14.
 */
public class EquiPointLinearStrategy implements EquiPointStrategy {

    @Override
    public int calculate(double[] values) {

        // check input
        if (values == null || values.length == 0) {
            throw new IllegalArgumentException();
        }
        if (values.length == 1) {
            return 1;
        }

        // compute CUMSUM in place
        for (int i = 1; i < values.length; i++) {
            values[i] = values[i] + values[i - 1];
        }
        double sum = values[values.length - 1];

        // search for the equilibrium point

        for (int i = 1; i < values.length; i++) {
            if (values[i - 1] == sum - values[i]) {
                return i;
            }
        }

        return -1;
    }
}

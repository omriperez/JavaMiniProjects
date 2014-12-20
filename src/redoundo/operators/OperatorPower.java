package redoundo.operators;
import redoundo.Operator;

/**
 * Created by OP on 20-12-14.
 */
public class OperatorPower implements Operator {

    private final double operationVal;
    private double valueStateCache;

    public OperatorPower(double val) {
        operationVal = val;
    }

    public double perform(double currentValue) {
        valueStateCache = currentValue;
        return Math.pow(currentValue, operationVal);
    }

    public double revert(double currentValue) {
        return valueStateCache;
    }

}

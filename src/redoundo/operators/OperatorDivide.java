package redoundo.operators;
import redoundo.Operator;

/**
 * Created by OP on 20-12-14.
 */
public class OperatorDivide implements Operator {

    private final double operationVal;

    public OperatorDivide(double val) {
        operationVal = val;
    }

    public double perform(double currentValue) {
        return currentValue / operationVal;
    }

    public double revert(double currentValue) {
        return currentValue * operationVal;
    }

}

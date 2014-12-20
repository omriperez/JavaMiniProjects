package redoundo;

/**
 * Created by OP on 20-12-14.
 */
public interface Operator {

    public double perform(double currentValue);

    public double revert(double currentValue);

}

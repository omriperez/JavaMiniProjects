package redoundo;

import redoundo.operators.*;

/**
 * Created by OP on 21-12-14.
 */
public class OperatorFactory {

    public static Operator getOperator(char operatorCharacter, double value) throws IllegalArgumentException{
        Operator op;
        switch (operatorCharacter) {
            case '+':
                op = new OperatorAdd(value);
                break;
            case '-':
                op = new OperatorSubtract(value);
                break;
            case '*':
                op = new OperatorMultiply(value);
                break;
            case '/':
                op = new OperatorDivide(value);
                break;
            case '^':
                op = new OperatorPower(value);
                break;
            default: // invalid operator
                throw new IllegalArgumentException();
        }
        return op;
    }
}

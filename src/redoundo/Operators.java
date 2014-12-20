package redoundo;

import redoundo.operators.*;

/**
 * Created by OP on 20-12-14.
 */
public enum Operators {

    ADD {
        public Operator getOperator(double val) { return new OperatorAdd(val); }
    },
    SUBTRACT {
        public Operator getOperator(double val) { return new OperatorSubtract(val); }
    },
    MULTIPLY {
        public Operator getOperator(double val) { return new OperatorMultiply(val); }
    },
    DIVIDE {
        public Operator getOperator(double val) { return new OperatorDivide(val); }
    },
    POWER {
        public Operator getOperator(double val) { return new OperatorPower(val); }
    },
    ;

    public abstract Operator getOperator(double val);
}
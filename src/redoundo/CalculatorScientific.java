package redoundo;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by OP on 20-12-14.
 */
public class CalculatorScientific implements Calculator {

    private final int undoNumLimit;
    private double currentValue;
    Deque<Operator> undoStack;
    Deque<Operator> redoStack;

    public CalculatorScientific(int undoNumLimit) {
        if (undoNumLimit<1) {
            undoNumLimit =0;
        }
        this.undoNumLimit = undoNumLimit;
        // Initialize data structures
        undoStack = new ArrayDeque<Operator>();
        redoStack = new ArrayDeque<Operator>();
    }

    public void run() {
        // main loop
        Boolean exitFlag = false;
        while (!exitFlag) {
            // display current value
            System.out.println("Current value: " + currentValue);
            // get input
            String s = getInput();
            // parse input and decide what to do
            exitFlag = parseInputAndAct(s);
        }
        System.out.println("User requested to exit.");
    }

    private String getInput() {
        System.out.print(">> ");
        Scanner in = new Scanner(System.in);
        return in.nextLine();
    }

    private Boolean parseInputAndAct(String s) {
        s = s.trim(); //remove excess whitespaces

        // check for special conditions
        if (s.equalsIgnoreCase("exit"))   { return true; } // exit

        if (s.equalsIgnoreCase("u"))      {                // undo
            performUndo();
            return false;
        }
        else if (s.equalsIgnoreCase("r")) {                // redo
            performRedo();
            return false;
        }

        // parse expression. We expect an operator and a value.
        Pattern r = Pattern.compile("([*\\^/+-])\\s*(\\d*\\.?+\\d+)");
        Matcher m = r.matcher(s);
        if (m.find() && m.groupCount() == 2) {
            Operator op;
            double value = Double.parseDouble(m.group(2));

            // switch on the operator symbol
            switch (m.group(1).charAt(0)) {
                case '+':
                    op = Operators.ADD.getOperator(value);
                    break;
                case '-':
                    op = Operators.SUBTRACT.getOperator(value);
                    break;
                case '*':
                    op = Operators.MULTIPLY.getOperator(value);
                    break;
                case '/':
                    op = Operators.DIVIDE.getOperator(value);
                    break;
                case '^':
                    op = Operators.POWER.getOperator(value);
                    break;
                default: // invalid operator
                    return false;
            }
            // perform operation
            if (op!=null) {
                currentValue = op.perform(currentValue);
                // add op to undo stack
                addOperatorToUndo(op);
            }

        } else System.out.println("Unidentified expression");
        return false;
    }

    private void addOperatorToUndo(Operator op) {
        undoStack.push(op);
        if (undoStack.size() > undoNumLimit) {
            // if stack is overflowing - remove oldest operator
            // because we use ArrayDeque this is O(1)
            undoStack.removeLast();
        }
    }

    private void performUndo() {
        if (undoStack.size()>0) {
            Operator op = undoStack.pop();
            currentValue = op.revert(currentValue);
            redoStack.push(op);
        }
        else System.out.println("No undo available!");
    }

    private void performRedo() {
        if (redoStack.size()>0) {
            Operator op = redoStack.pop();
            currentValue = op.perform(currentValue);
            undoStack.push(op);
        }
        else System.out.println("No redo available!");
    }

    public static void main(String[] args) {
        System.out.println("The calculator knows the following operations: + - * / ^");
        System.out.println("\nTo exit type: exit");
        System.out.println("To undo type: u");
        System.out.println("To redo type: r\n");
        System.out.println("To start add a value to the current value (e.g. +5)");

        Calculator engine = new CalculatorScientific(5);
        engine.run();
    }

}

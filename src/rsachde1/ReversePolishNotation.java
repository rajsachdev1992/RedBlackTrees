/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rsachde1;

import java.math.BigInteger;
import java.util.Scanner;

/**
 *
 * @author Raj92
 */
public class ReversePolishNotation {

    private static final Scanner INPUT = new Scanner(System.in);
    private String[] expression;                //stores each element of the postfix expression 
    private final Stack operandStack;           //Object stack
    private final RedBlackTree variableTree;    //Tree which stores variables and their values

    public ReversePolishNotation() {
        operandStack = new Stack();
        variableTree = new RedBlackTree();
    }
    
    /**
     * Accepts input from the user and sets the expression[] array
     */
    public void getInputsAndSetExpression() {
        expression = INPUT.nextLine().split("\\s+");
    }
    
    /**
     * Evaluates the postfix expression.
     * @return BigInteger: Result of the expression.
     * Precondition: The expression should be a valid postfix expression.
     * Postcondition: A correct result to the expression will be returned.
     * Complexity: Theta(n)
     */
    public BigInteger evaluateExpression() {
        for (String x : expression) {
            if (x.matches("[+-/%~#=]") || x.equals("*")) {
                //operator
                switch (x) {
                    case "+":
                    case "-":
                    case "/":
                    case "*":
                    case "%":
                        handleBinaryOperator(x);
                        break;
                    case "~":
                        handleUnaryMinus();
                        break;
                    case "#":
                        handlePowerMod();
                        break;
                    case "=":
                        handleAssignmentOperator();
                        break;
                    default:
                }
            } else {
                //variable or digit
                if (isInteger(x)) {
                    operandStack.push(new BigInteger(x));
                } else {
                    operandStack.push(x);
                }
            }
        }

        Object result = operandStack.pop();
        BigInteger finalReturnValue;
        if (result instanceof BigInteger) {
            finalReturnValue = (BigInteger) result;
        } else {
            finalReturnValue = variableTree.lookup((String) result);
        }

        return finalReturnValue;
    }
    
    /**
     * Handles the assignment operations.
     * Complexity: Theta(1)
     */
    private void handleAssignmentOperator() {
        BigInteger v = (BigInteger) operandStack.pop();
        Object key = operandStack.pop();
        if(!(key instanceof String)) {
            throw new RuntimeException("error: "+key+" is not an lvalue");
        }
        String k = (String) key;
        variableTree.insert(new KeyValuePair(k, v));
        operandStack.push(k);
    }
    
    /**
     * Handles the PowerMod operations.
     * Complexity: Theta(1)
     */
    private void handlePowerMod() {
        Object o3 = operandStack.pop();
        Object o2 = operandStack.pop();
        Object o1 = operandStack.pop();
        BigInteger a;
        BigInteger b;
        BigInteger c;
        if (o1 instanceof String) {
            a = variableTree.lookup((String) o1);
        } else {
            a = (BigInteger) o1;
        }
        if (o2 instanceof String) {
            b = variableTree.lookup((String) o2);
        } else {
            b = (BigInteger) o2;
        }
        if (o3 instanceof String) {
            c = variableTree.lookup((String) o3);
        } else {
            c = (BigInteger) o3;
        }

        BigInteger result = a.modPow(b, c);
        operandStack.push(result);
    }
    
    /**
     * Handles the Unary minus operations.
     * Complexity: Theta(1)
     */
    private void handleUnaryMinus() {
        Object o = operandStack.pop();
        BigInteger a;
        if (o instanceof String) {
            a = variableTree.lookup((String) o);
        } else {
            a = (BigInteger) o;
        }
        a = a.negate();
        operandStack.push(a);
    }

    //
    /**
     * Handles the Binary operations.
     * precondition: operator should be +,-,*,/
     * Postcondition: A valid result will be computed and pushed into stack.
     * Complexity: Theta(1)
     */
    private void handleBinaryOperator(String operator) {
        Object o2 = operandStack.pop();
        Object o1 = operandStack.pop();
        BigInteger a;
        BigInteger b;
        if (o1 instanceof String) {
            a = variableTree.lookup((String) o1);
        } else {
            a = (BigInteger) o1;
        }
        if (o2 instanceof String) {
            b = variableTree.lookup((String) o2);
        } else {
            b = (BigInteger) o2;
        }
        BigInteger result = null;
        switch (operator) {
            case "+":
                result = a.add(b);
                break;
            case "-":
                result = a.subtract(b);
                break;
            case "*":
                result = a.multiply(b);
                break;
            case "/":
                result = a.divide(b);
                break;
            case "%":
                result = a.mod(b);
        }
        operandStack.push(result);
    }
    
    /**
     * Checks if the str is a valid BigInteger.
     * @param str
     * @return Boolean
     */
    private boolean isInteger(String str) {
        try {
            BigInteger d = new BigInteger(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
    
    /**
     * Driver method
     * @param args 
     */
    public static void main(String args[]) {
        ReversePolishNotation mainObj = new ReversePolishNotation();
        while (true) {
            mainObj.getInputsAndSetExpression();
            System.out.println(mainObj.evaluateExpression());
        }
    }

}

package rsachde1;

import java.math.BigInteger;

/**
 *Models a stack which stores objects.
 * @author Raj.Sachdev
 */
public class Stack {

    private int top;        //top of the stack  
    private Object[] s;     //array that stores stack elements
    private int size;       //size of the stack
    private static int thresholdSize;   //size reached after which stack is resized.

    public Stack() {
        thresholdSize = 6;
        top = -1;
        s = new Object[thresholdSize];
        size = 0;
    }
    
    /**
     * 
     * @param o : element to be pushed into the stack.
     * PreCondition: o should not be null.
     * PostCondition: The element o is pushed into the stack.
     * Complexity: Best Case: Theta(1)
     *            Worst Case: Theta(n): When array is resized.
     */
    public void push(Object o) {

        if (top == thresholdSize - 1) {
            this.doubleStackSize();
        }
        s[++top] = o;
        size++;
    }
    
    /**
     * Deletes the top element from the stack and returns it.
     * @return : Object
     * PreCondition: the stack should not be empty.
     * PostCondition: Top element is popped from the stack.
     * Complexity: Theta(1)
     */
    public Object pop() {
        if (isEmpty()) {
            throw new RuntimeException("error: stack underflow exception");
        }
        return s[top--];
    }

    public boolean isEmpty() {
        return top == -1;
    }

    public int getSize() {
        return this.size;
    }
    
    /**
     * Helper method for the push() method. Doubles the size of the stack array.
     * Complexity: Theta(n)
     */
    private void doubleStackSize() {
        thresholdSize *= 2;
        Object[] oldStack = this.s;
        s = new Object[thresholdSize];
        for (int i = 0; i <= top; i++) {
            s[i] = oldStack[i];
        }
    }
    
    /**
     * Driver class.
     * @param args 
     */
    public static void main(String args[]) {
        Stack s = new Stack();
        String str;
        BigInteger bi;
        /* pushing 10000 values into stack */
        for (int i = 0; i < 1000; i++) {
            if (i % 2 == 0) {
                str = String.valueOf(i);
                s.push(str);
            } else {
                bi = new BigInteger(String.valueOf(i));
                s.push(bi);
            }
        }

        /*popping values from stack */
        while (!s.isEmpty()) {
            System.out.println(s.pop());
        }
    }

}

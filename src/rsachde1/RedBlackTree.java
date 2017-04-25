package rsachde1;

import java.math.BigInteger;
import java.util.Random;

/**
 * 
 * @author Raj.Sachdev
 * This class defines a Red Black Tree.
 */
public class RedBlackTree extends Object {

    private RedBlackNode tree;          //root node of the tree
    private static RedBlackNode nil;    //nil acts as a sentinel node
    private int numberOfInsertions;     //number of nodes inserted so far in the tree
    private int recentCompares;         //number of compares in the last search in the tree

    public RedBlackTree() {
        //default constructor
        nil = new RedBlackNode(null, RedBlackNode.Color.BLACK, nil, nil, nil);
        tree = nil;
        numberOfInsertions = 0;
        recentCompares = 0;
    }
    
    /**
     * Inserts a key value pair in the Red Black Tree
     * @param insertPair 
     * Pre-Condition: insertPair should not be null
     * Post-Condition: the insertPair will be inserted in the Red Black Tree
     */
    public void insert(KeyValuePair insertPair) {
        RedBlackNode y = nil;
        RedBlackNode x = tree;
        RedBlackNode z = new RedBlackNode();
        z.setData(insertPair);
        while (x != nil) {
            y = x;
            if (insertPair.getKey().equals(x.getData().getKey())) {
                x.getData().setValue(insertPair.getValue());
                return;
            }
            if (insertPair.getKey().compareTo(x.getData().getKey()) < 0) {
                x = x.getLeft();
            } else {
                x = x.getRight();
            }
        }
        z.setParent(y);
        if (y == nil) {
            tree = z;
        } else {
            if (insertPair.getKey().compareTo(y.getData().getKey()) < 0) {
                y.setLeft(z);
            } else {
                y.setRight(z);
            }
        }
        z.setLeft(nil);
        z.setRight(nil);
        z.setColor(RedBlackNode.Color.RED);
        RBInsertFixup(z);
        this.numberOfInsertions++;
    }
    
    /**
     * Performs an inorder traversal in the red black tree
     */
    public void inOrderTraversal() {
        inOrderTraversal(tree);
    }
    
    /**
     * Helper method for inOrderTraversal() method. Recursively carries out inorder traversal 
     * in the red black tree.
     * @param t: Pointer to a node in the tree 
     */
    private void inOrderTraversal(RedBlackNode t) {
        if (t != nil) {
            if (t.getLeft() != nil) {
                inOrderTraversal(t.getLeft());
            }
            System.out.println(t.getData().getKey() + ": " + t.getData().getValue());
            if (t.getRight() != nil) {
                inOrderTraversal(t.getRight());
            }
        }
    }
    
    /**
     * Performs a reverse inorder traversal in the Red Black Tree.
     */
    public void reverseOrderTraversal() {
        reverseOrderTraversal(tree);
    }
    
    /**
     * Helper method for reverseOrderTraversal() method. Recursively 
     * carries out reverse inorder traversal in the red black tree.
     * @param t: Pointer to a node in the tree 
     */
    private void reverseOrderTraversal(RedBlackNode t) {
        if (t != nil) {
            if (t.getRight() != nil) {
                inOrderTraversal(t.getRight());
            }
            System.out.println(t.getData().getKey() + ": " + t.getData().getValue());
            if (t.getLeft() != nil) {
                inOrderTraversal(t.getLeft());
            }

        }
    }
    
    /**
     * Checks if the tree contains a node with the key 'v'.
     * @param v
     * @return boolean
     * Precondition: v should not be null.
     * PostCondition: if a node with key 'v' exists, then true is returned,
     * else false.
     * Complexity: Theta(log(n))
     */
    public boolean contains(String v) {
        this.recentCompares = 0;
        RedBlackNode cur = tree;
        boolean found = false;
        while (cur != nil) {
            this.recentCompares++;
            if (cur.getData().getKey().equals(v)) {
                found = true;
                break;
            }
            if (cur.getData().getKey().compareTo(v) < 0) {
                cur = cur.getLeft();
            } else {
                cur = cur.getRight();
            }
        }
        return found;
    }
    
    /**
     * Returns the number of comparisons made during the last search
     * in the red black tree.
     * @return integer
     * Complexity: Theta(1)
     */
    public int getRecentCompares() {
        return this.recentCompares;
    }
    
    /**
     * Returns the number of nodes in the RB Tree.
     * @return integer
     * Complexity: Theta(1)
     */
    public int getSize() {
        return numberOfInsertions;
    }
    
    /**
     * Returns the height of the RB Tree.
     * @return Integer
     */
    public int height() {
        return height(this.tree);
    }
    
    /**
     * Helper method for the height() method.
     * @param t: a node in the RB tree
     * @return Integer: height of the tree.
     */
    private int height(RedBlackNode t) {
        if (t == nil) {
            return 0;
        }
        int lHeight = 1;
        int rHeight = 1;
        if (t.getLeft() != nil) {
            lHeight = 1 + height(t.getLeft());
        }
        if (t.getRight() != nil) {
            rHeight = 1 + height(t.getRight());
        }
        if (lHeight > rHeight) {
            return lHeight;
        } else {
            return rHeight;
        }
    }
    
    /**
     * Returns the value stored against the entered key if present in the 
     * RB tree.
     * @param key
     * @return BigInteger.
     * PreCondition: A node with the entered key should exist in the RB Tree.
     * PostCondition: The value stored against the key will be returned.
     * Complexity: Theta(log(n))
     */
    public BigInteger lookup(String key) {
        RedBlackNode cur = tree;
        BigInteger value = null;
        while (cur != nil) {
            if (cur.getData().getKey().equals(key)) {
                value = cur.getData().getValue();
                break;
            }
            if (key.compareTo(cur.getData().getKey()) < 0) {
                cur = cur.getLeft();
            } else {
                cur = cur.getRight();
            }
        }
        if (value == null) {
            throw new RuntimeException("error: no variable " + key);
        }
        return value;
    }
    
    /**
     * Helper method for the insert() method. Fixes the RB Tree as per the 
     * rules of a RB Tree.
     * @param z : Node to be inserted.
     * PreCondition: z should not be nil
     * PostCondition: The tree will be fixed as per the rules of an RB tree.
     */
    private void RBInsertFixup(RedBlackNode z) {
        while (z.getParent().getColor() == RedBlackNode.Color.RED) {
            if (z.getParent() == z.getParent().getParent().getLeft()) {
                RedBlackNode y = z.getParent().getParent().getRight();
                if (y.getColor() == RedBlackNode.Color.RED) {
                    z.getParent().setColor(RedBlackNode.Color.BLACK);
                    y.setColor(RedBlackNode.Color.BLACK);
                    z.getParent().getParent().setColor(RedBlackNode.Color.RED);
                    z = z.getParent().getParent();
                } else {
                    if (z == z.getParent().getRight()) {
                        z = z.getParent();
                        leftRotate(z);
                        //LEFT-Rotate(z)
                    }
                    z.getParent().setColor(RedBlackNode.Color.BLACK);
                    z.getParent().getParent().setColor(RedBlackNode.Color.RED);
                    rightRotate(z.getParent().getParent());
                    //RIGHT-Rotate(z.getParent().getParent())
                }
            } else {
                RedBlackNode y = z.getParent().getParent().getLeft();
                if (y.getColor() == RedBlackNode.Color.RED) {
                    z.getParent().setColor(RedBlackNode.Color.BLACK);
                    y.setColor(RedBlackNode.Color.BLACK);
                    z.getParent().getParent().setColor(RedBlackNode.Color.RED);
                    z = z.getParent().getParent();
                } else {
                    if (z == z.getParent().getLeft()) {
                        z = z.getParent();
                        rightRotate(z);
                        //Right-Rotate(z);
                    }
                    z.getParent().setColor(RedBlackNode.Color.BLACK);
                    z.getParent().getParent().setColor(RedBlackNode.Color.RED);
                    leftRotate(z.getParent().getParent());
                    //LEFT-Rotate(z.getParent().getParent());
                }
            }
        }
        tree.setColor(RedBlackNode.Color.BLACK);
    }
    
    /**
     * Performs a left rotate on the node x in the Red Black Tree.
     * @param x 
     * PreCondition: x should not be nil
     * PostCondition: A left rotate is performed on the node x.
     */
    private void leftRotate(RedBlackNode x) {
        RedBlackNode y = x.getRight();
        x.setRight(y.getLeft());
        y.getLeft().setParent(x);
        y.setParent(x.getParent());

        if (x.getParent() == nil) {
            tree = y;
        } else {
            if (x == x.getParent().getLeft()) {
                x.getParent().setLeft(y);
            } else {
                x.getParent().setRight(y);
            }
        }
        y.setLeft(x);
        x.setParent(y);
    }
    
    /**
     * Performs a right rotate on the node x in the Red Black Tree.
     * @param x 
     * PreCondition: x should not be nil
     * PostCondition: A right rotate is performed on the node x.
     */
    private void rightRotate(RedBlackNode x) {
        RedBlackNode y = x.getLeft();
        x.setLeft(y.getRight());
        y.getRight().setParent(x);
        y.setParent(x.getParent());

        // if x is at root then y becomes new root
        if (x.getParent() == nil) {
            tree = y;
        } else {
            // if x is a left child then adjust x's parent's left child 
            if (x == x.getParent().getLeft()) {
                x.getParent().setLeft(y);
            } else {
                // adjust x's parent's right child
                x.getParent().setRight(y);
            }
        }
        y.setRight(x);
        x.setParent(y);
    }
    
    /**
     * Driver method
     * @param args 
     */
    public static void main(String args[]) {
        Random rnd = new Random();
        RedBlackTree rbt = new RedBlackTree();
        for (int i = 1; i <= 50; i++) {
            rbt.insert(new KeyValuePair(String.valueOf(i), new BigInteger(16, rnd)));
        }
        System.out.println("Inorder Traversal\n");
        rbt.inOrderTraversal();
        System.out.println("\n\n\nReverseOrder Traversal");
        rbt.reverseOrderTraversal();
        System.out.println("\n\n\nHeight: " + rbt.height());
        System.out.println("Size: "+rbt.getSize());
    }
}

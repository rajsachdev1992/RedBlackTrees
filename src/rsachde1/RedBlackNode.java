package rsachde1;

/**
 *
 * @author Raj.Sachdev This class models the node of a Red Black Tree.
 */
public class RedBlackNode {

    private KeyValuePair data;
    private Color color;            //Color can be RED or BLACK
    private RedBlackNode parent;    //points to the parent ofthe node
    private RedBlackNode left;      //points to the left child
    private RedBlackNode right;     //points to the right child

    //Color of the node
    public enum Color {
        RED, BLACK
    }

    public RedBlackNode(KeyValuePair data, Color color, RedBlackNode parent, RedBlackNode left, RedBlackNode right) {
        this.data = data;
        this.color = color;
        this.parent = parent;
        this.left = left;
        this.right = right;
    }

    public RedBlackNode() {
        //default constructor
    }

    /*Getters and Setters*/
    public KeyValuePair getData() {
        return data;
    }

    public void setData(KeyValuePair data) {
        this.data = data;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public RedBlackNode getParent() {
        return parent;
    }

    public void setParent(RedBlackNode parent) {
        this.parent = parent;
    }

    public RedBlackNode getLeft() {
        return left;
    }

    public void setLeft(RedBlackNode left) {
        this.left = left;
    }

    public RedBlackNode getRight() {
        return right;
    }

    public void setRight(RedBlackNode right) {
        this.right = right;
    }
}

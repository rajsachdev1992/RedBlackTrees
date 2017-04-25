package rsachde1;

import java.math.BigInteger;

/**
 * @author Raj.Sachdev
 * This class models a key-value pair which can be stored in the RedBlackTree
 */
public class KeyValuePair {
    private String key;
    private BigInteger value;

    public KeyValuePair(String key, BigInteger value) {
        this.key = key;
        this.value = value;
    }
    
    public KeyValuePair() {
        //default constructor
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public BigInteger getValue() {
        return value;
    }

    public void setValue(BigInteger value) {
        this.value = value;
    }
    
    
    
}

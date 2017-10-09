package col106.a3;

import java.util.List;

public interface DuplicateBTree<Key extends Comparable<Key>, Value> {
    public boolean isEmpty();  /* Returns true if the tree is empty. */

    public int size();  /* Returns the number of key-value pairs */

    public int height();  /* Returns the height of this B-tree */

    public List<Value> search(Key key) throws IllegalKeyException; /* Returns all values associated with a given key in a vector */

    public void insert(Key key, Value val);  /* Inserts the key-value pair */

    public void delete(Key key) throws IllegalKeyException;  /* Deletes all occurrences of key */

    public String toString(); /* Prints all the tree in the format listed below */
}
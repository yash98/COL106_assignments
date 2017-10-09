package col106.a3;

import java.util.List;

public class BTree<Key extends Comparable<Key>,Value> implements DuplicateBTree<Key,Value> {

    public BTree(int b) throws bNotEvenException {  /* Initializes an empty b-tree. Assume b is even. */
        throw new RuntimeException("Not Implemented");
    }

    @Override
    public boolean isEmpty() {
        throw new RuntimeException("Not Implemented");
    }

    @Override
    public int size() {
        throw new RuntimeException("Not Implemented");
    }

    @Override
    public int height() {
        throw new RuntimeException("Not Implemented");
    }

    @Override
    public List<Value> search(Key key) throws IllegalKeyException {
        throw new RuntimeException("Not Implemented");
    }

    @Override
    public void insert(Key key, Value val) {
        throw new RuntimeException("Not Implemented");
    }

    @Override
    public void delete(Key key) throws IllegalKeyException {
        throw new RuntimeException("Not Implemented");
    }
}

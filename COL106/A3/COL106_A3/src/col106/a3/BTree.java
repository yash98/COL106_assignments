package col106.a3;

import java.util.List;
import java.util.ArrayList;
import java.lang.StringBuilder;

public class BTree<Key extends Comparable<Key>,Value> implements DuplicateBTree<Key,Value> {

    public Node root;
    public int m;
    public int size;
    public int height;

    private class KeyVal {
        public Key key;
        public Value val;

        public KeyVal (Key key, Value val) {
            this.key = key;
            this.val = val;
        }

        @Override
        public String toString() {
            return "<"+key.toString()+", "+val.toString()+">";
        }
    }

    private class Node {
        public Node parent;
        public List<KeyVal> data;
        public List<Node> children;

        public Node(Node parent, List<KeyVal> data ,List<Node> children) {
            this.parent = parent;
            this.data = data;
            this.children = children;
        }

        public Node split(boolean isLeaf) {
            if (isLeaf) {
                int s = this.data.size();
                KeyVal push = this.data.get((s-1)/2);
                if (this.parent!=null){
                    int indexOfSelf = this.parent.children.indexOf(this);
                    Node leftChild = new Node(this.parent, this.data.subList(0, (s-1)/2), null);
                    Node rightChild = new Node(this.parent, this.data.subList((s+1)/2, s), null);
                    this.parent.data.add(indexOfSelf, push);
                    this.parent.children.add(indexOfSelf, leftChild);
                    this.parent.children.add(indexOfSelf, rightChild);
                    this.parent.children.remove(indexOfSelf + 2);
                    return this.parent;
                } else {
                    Node leftChild = new Node(null, this.data.subList(0, (s-1)/2),  null);
                    Node rightChild = new Node(null, this.data.subList((s+1)/2, s),null);
                    List<Node> kids = new ArrayList<>();
                    kids.add(leftChild);
                    kids.add(rightChild);
                    Node newRoot = new Node(null, new ArrayList<>() ,kids);
                    newRoot.data.add(push);
                    leftChild.parent = newRoot;
                    rightChild.parent = newRoot;
                    return newRoot;
                }
            } else {
                int s = this.data.size();
                KeyVal push = this.data.get((s-1)/2);
                int c = 0;
                if (this.parent!=null){
                    int indexOfSelf = this.parent.children.indexOf(this);
                    Node leftChild = new Node(this.parent, this.data.subList(0, (s-1)/2), this.children.subList(0, (s-1)/2));
                    Node rightChild = new Node(this.parent, this.data.subList((s+1)/2, s), this.children.subList((s-1)/2, s));
                    this.parent.data.add(indexOfSelf, push);
                    this.parent.children.add(indexOfSelf, leftChild);
                    this.parent.children.add(indexOfSelf, rightChild);
                    this.parent.children.remove(indexOfSelf + 2);
                    for (Node child : this.children) {
                        if (c < s / 2) {
                            child.parent = leftChild;
                        } else {
                            child.parent = rightChild;
                        }
                        c++;
                    }
                    return this.parent;
                } else {
                    Node leftChild = new Node(null, this.data.subList(0, (s-1)/2), this.children.subList(0, (s-1)/2));
                    Node rightChild = new Node(null,  this.data.subList((s+1)/2, s), this.children.subList((s-1)/2, s));
                    List<Node> kids = new ArrayList<>();
                    kids.add(leftChild);
                    kids.add(rightChild);
                    Node newRoot = new Node(null, new ArrayList<>() ,kids);
                    newRoot.data.add(push);
                    leftChild.parent = newRoot;
                    rightChild.parent = newRoot;
                    for (Node child : this.children) {
                        if (c < s / 2) {
                            child.parent = leftChild;
                        } else {
                            child.parent = rightChild;
                        }
                        c++;
                    }
                    return newRoot;
                }
            }
        }

        public String toString() {
//            System.out.println(this.data.toString());
            if (this.data.isEmpty()) {
                return "[]";
            } else if (this.children==null) {
                StringBuilder sb = new StringBuilder();
                sb.append("[");
                for (int i = 0; i < this.data.size(); i++) {
                    if (i > 0) {
                        sb.append(", ");
                    }
                    sb.append(this.data.get(i).key.toString());
                }
                sb.append("]");
                return sb.toString();
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append("[");
                for (int i = 0; i <= this.data.size(); i++) {
                    if (i<this.data.size()) {
                        sb.append(this.children.get(i).toString());
                        sb.append(", ");
                        sb.append(this.data.get(i).key.toString());
                        sb.append(", ");
                    } else {
                        sb.append(this.children.get(i).toString());
                    }
                }
                sb.append("]");
                return sb.toString();
            }
        }
    }

    public BTree(int b) throws bNotEvenException {  /* Initializes an empty b-tree. Assume b is even. */
        if (b%2 == 1 && b>2) {
            throw new bNotEvenException();
        } else {
            this.m = b;
            this.size = 0;
            this.height = 0;
            this.root = new Node(null, new ArrayList<>(), null);
        }
    }

    @Override
    public boolean isEmpty() {
        return(this.root.data.isEmpty());
    }

    @Override
    public int size() {
        return(this.size);
    }

    @Override
    public int height() {
        return(this.height);
    }

    @Override
    public List<Value> search(Key key) throws IllegalKeyException {
        List<Value> found = new ArrayList<>();
        List<Node> findIn = new ArrayList<>();
        boolean lastEquality = false;
        findIn.add(this.root);
        Node current;
        while (!findIn.isEmpty()) {
            current = findIn.get(0);
            findIn.remove(0);
            for(int i=0; i<=current.data.size(); i++) {
                if (lastEquality) {
                    if (!current.children.isEmpty()) {
                        findIn.add(current.children.get(i));
                    }
                    if (i < current.data.size()) {
                        if (key.compareTo(current.data.get(i).key) != 0) {
                            lastEquality = false;
                        } else {
                            found.add(current.data.get(i).val);
                        }
                    }
                } else {
                    if (i==0) {
                        if (key.compareTo(current.data.get(0).key)<0) {
                            if (!current.children.isEmpty()) {
                                findIn.add(current.children.get(0));
                            }
                        } else if (key.compareTo(current.data.get(0).key)==0) {
                            found.add(current.data.get(0).val);
                            lastEquality = true;
                            if (!current.children.isEmpty()) {
                                findIn.add(current.children.get(0));
                            }
                        }
                    } else if (i < current.data.size()) {
                        if (key.compareTo(current.data.get(i-1).key) > 0 & key.compareTo(current.data.get(i).key) < 0) {
                            if (!current.children.isEmpty()) {
                                findIn.add(current.children.get(i));
                            }
                        } else if (key.compareTo(current.data.get(i).key)==0) {
                            found.add(current.data.get(i).val);
                            lastEquality = true;
                            if (!current.children.isEmpty()) {
                                findIn.add(current.children.get(i));
                            }
                        }
                    } else {
                        if (key.compareTo(current.data.get(i-1).key)>0) {
                            if (!current.children.isEmpty()) {
                                findIn.add(current.children.get(i));
                            }
                        }
                    }
                }
            }
        }
        if (found.isEmpty()) {
            throw new IllegalKeyException();
        } else {
            return found;
        }
    }

    @Override
    public void insert(Key key, Value val) {
        int s;
        this.size += 1;
        Node current = this.root;
        if (current.data.isEmpty()) {
            current.data.add(new KeyVal(key, val));
            this.height = 1;
        } else {
            while (current.children != null) {
                for (int i = 0; i <= current.data.size(); i++) {
                    if (i == 0) {
                        if (key.compareTo(current.data.get(0).key) <= 0) {
                            current = current.children.get(0);
                            break;
                        }
                    } else if (i < current.data.size()) {
                        if (key.compareTo(current.data.get(i - 1).key) > 0 & key.compareTo(current.data.get(i).key) <= 0) {
                            current = current.children.get(i);
                            break;
                        }
                    } else {
                        if (key.compareTo(current.data.get(i - 1).key) > 0) {
                            current = current.children.get(i);
                            break;
                        }
                    }
                }
                if (current.parent != null && current.parent.data.size() == this.m - 1) {
                    if (current.parent == root) {
                        this.height += 1;
                        this.root = current.parent.split(false);
                    } else {
                        current.parent.split(false);
                    }
                }
            }
            for (int i = 0; i <= current.data.size(); i++) {
                if (i == 0) {
                    if (key.compareTo(current.data.get(0).key) <= 0) {
                        current.data.add(0, new KeyVal(key, val));
                        break;
                    }
                } else if (i < current.data.size()) {
                    if (key.compareTo(current.data.get(i - 1).key) > 0 & key.compareTo(current.data.get(i).key) <= 0) {
                        current.data.add(i, new KeyVal(key, val));
                        break;
                    }
                } else {
                    if (key.compareTo(current.data.get(i - 1).key) > 0) {
                        current.data.add(i, new KeyVal(key, val));
                        break;
                    }
                }
            }
//            System.out.println(this.toString());
            if (current.data.size() == this.m - 1) {
                if (current == root) {
                    this.height += 1;
                    this.root = current.split(true);
                } else {
                    current.split(true);
                }
            }
        }
//        System.out.println(this.toString());
    }


    @Override
    public void delete(Key key) throws IllegalKeyException {
        throw new RuntimeException("Not Implemented");
    }

    @Override
    public String toString() {
        return this.root.toString();
    }
}

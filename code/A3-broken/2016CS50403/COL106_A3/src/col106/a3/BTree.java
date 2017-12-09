package col106.a3;

import java.util.Vector;
import java.util.List;
import java.lang.StringBuilder;
import java.util.ListIterator;
import java.util.ConcurrentModificationException;


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
        public int keysNum;
        public List<Node> children;

        public Node(Node parent, List<KeyVal> data , int keysNum, List<Node> children) {
            this.parent = parent;
            this.data = data;
            this.keysNum = keysNum;
            this.children = children;
        }

        public boolean equals (Object comp) {
            return this==comp;
        }

        public void merge() {

        }

        public Node insertAtLeaf(Node current, Key key, Value val) {
            int s = -1;
            int maxsize = current.keysNum;
            for (int i = 0; i <= maxsize; i++) {
                if (i == 0) {
                    if (key.compareTo(current.data.get(0).key) <= 0) {
                        s = 0;
                        //current.data.add(0, new KeyVal(key, val));
                        break;
                    }
                } else if (i < maxsize) {
                    if (key.compareTo(current.data.get(i - 1).key) > 0 & key.compareTo(current.data.get(i).key) <= 0) {
                        s = i;
                        //current.data.add(i, new KeyVal(key, val));
                        break;
                    }
                } else {
                    if (key.compareTo(current.data.get(i - 1).key) > 0) {
                        s = i;
                        //current.data.add(i, new KeyVal(key, val));
                        break;
                    }
                }
            }
//            System.out.println(s);
            current.data.add(s, new KeyVal(key, val));
            current.keysNum += 1;
            return current;
        }

        public Node split(boolean isLeaf) {
            if (isLeaf) {
                int s = this.keysNum;
                KeyVal push = this.data.get((s-1)/2);
                if (this.parent!=null){
                    // is leaf and not root
                    int indexOfSelf = this.parent.children.indexOf(this);
                    Node leftChild = new Node(this.parent, this.data.subList(0, (s-1)/2), (s-1)/2, null);
                    Node rightChild = new Node(this.parent, this.data.subList((s+1)/2, s), (s-1)/2,null);
                    this.parent.data.add(indexOfSelf, push);
                    this.parent.keysNum += 1;
                    this.parent.children.add(indexOfSelf, rightChild);
                    this.parent.children.add(indexOfSelf, leftChild);
                    this.parent.children.remove(indexOfSelf + 2);
                    return this.parent;
                } else {
                    // leaf and root
                    Node leftChild = new Node(null, this.data.subList(0, (s-1)/2), (s-1)/2,null);
                    Node rightChild = new Node(null, this.data.subList((s+1)/2, s), (s-1)/2,null);
                    List<Node> kids = new Vector<>();
                    kids.add(leftChild);
                    kids.add(rightChild);
                    Node newRoot = new Node(null, new Vector<>(), 1,kids);
                    newRoot.data.add(push);
                    leftChild.parent = newRoot;
                    rightChild.parent = newRoot;
                    return newRoot;
                }
            } else {
                // parent of current node
                // not leaf
                int s = this.keysNum;
                KeyVal push = this.data.get((s-1)/2);
                int c = 0;
                if (this.parent!=null){
                    int indexOfSelf = this.parent.children.indexOf(this);
                    Node leftChild = new Node(this.parent, this.data.subList(0, (s-1)/2), (s-1)/2, this.children.subList(0, (s-1)/2));
                    Node rightChild = new Node(this.parent, this.data.subList((s+1)/2, s), (s-1)/2, this.children.subList((s-1)/2, s));
                    this.parent.data.add(indexOfSelf, push);
                    this.parent.children.add(indexOfSelf, rightChild);
                    this.parent.children.add(indexOfSelf, leftChild);
                    this.parent.children.remove(indexOfSelf + 2);
                    this.parent.keysNum += 1;
                    for (Node child : this.children) {
                        if (c <= (s-1) / 2) {
                            child.parent = leftChild;
                        } else {
                            child.parent = rightChild;
                        }
                        c++;
                    }
                    return this.parent;
                } else {
                    // not leaf but is root
                    Node leftChild = new Node(null, this.data.subList(0, (s-1)/2), (s-1)/2, this.children.subList(0, (s-1)/2));
                    Node rightChild = new Node(null,  this.data.subList((s+1)/2, s), (s-1)/2,this.children.subList((s-1)/2, s));
                    List<Node> kids = new Vector<>();
                    kids.add(leftChild);
                    kids.add(rightChild);
                    Node newRoot = new Node(null, new Vector<>(), 1,kids);
                    newRoot.data.add(push);
                    leftChild.parent = newRoot;
                    rightChild.parent = newRoot;
                    for (Node child : this.children) {
                        if (c <= (s-1) / 2) {
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
            if (this.keysNum==0) {
                // if empty root comes
                return "[]";
            } else if (this.children==null) {
                // if leaf comes
                StringBuilder sb = new StringBuilder();
                sb.append("[");
                for (int i = 0; i < this.keysNum; i++) {
                    if (i > 0) {
                        sb.append(", ");
                    }
                    sb.append(this.data.get(i).key.toString());
                }
                sb.append("]");
                return sb.toString();
            } else {
                // if internal node comes
                StringBuilder sb = new StringBuilder();
                sb.append("[");
                for (int i = 0; i <= this.keysNum; i++) {
                    if (i<this.keysNum) {
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
            this.root = new Node(null, new Vector<>(), 0, null);
        }
    }

    @Override
    public boolean isEmpty() {
        return(this.root.keysNum == 0);
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
        List<Value> found = new Vector<>();
        List<Node> findIn = new Vector<>();
        boolean lastEquality = false;
        findIn.add(this.root);
        Node current;
        while (!findIn.isEmpty()) {
            current = findIn.get(0);
            findIn.remove(0);
            for(int i=0; i<=current.keysNum; i++) {
                if (lastEquality) {
                    if (!current.children.isEmpty()) {
                        findIn.add(current.children.get(i));
                    }
                    if (i < current.keysNum) {
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
                    } else if (i < current.keysNum) {
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
        int maxsize;
        int s=-1;
        this.size += 1;
        Node current = this.root;
        if (current.data.isEmpty()) {
            // root is empty
            current.data.add(new KeyVal(key, val));
            this.height = 1;
            current.keysNum = 1;
        } else {
            // non empty root
            while (current.children != null) {
                // transverse until you reach the correct leaf
                for (int i = 0; i <= current.keysNum; i++) {
                    if (i == 0) {
                        if (key.compareTo(current.data.get(0).key) <= 0) {
                            current = current.children.get(0);
                            break;
                        }
                    } else if (i < current.keysNum) {
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
                if (current.parent != null && current.parent.keysNum == this.m - 1) {
                    // top down approach
                    // split parent if it would be overflowed on 1 more key
                    if (current.parent == root) {
                        // split returns parent (false)
                        this.height += 1;
                        this.root = current.parent.split(false);
                    } else {
                        current.parent.split(false);
                    }
                }
            }

            current = current.insertAtLeaf(current, key, val);
//            System.out.println(this.toString());
            maxsize = current.keysNum;
            if (maxsize == this.m - 1) {
                // top down approach
                // split if it would be overflowed on 1 more key
                if (current == root) {
                    // split returns current (true)
                    this.height += 1;
                    this.root = current.split(true);
                } else {
                    current.split(true);
                }
            }
//            System.out.println(this.toString());
        }
    }

    public boolean deleteOne(Key key) {
        Node current = this.root;
        int indexToDel = 0;
        Node delFrom = current;
        boolean notPresent = false;
        boolean notfound = true;
        // find first topmost element to delete
        while (notfound & !notPresent) {
            for (int i = 0; i <= current.keysNum; i++) {
                if (i == 0) {
                    if (key.compareTo(current.data.get(i).key) < 0) {
                        // into 1st children
                        if (current.children != null) {
                            current = current.children.get(i);
                            break;
                        } else {
                            notPresent = true;
                        }
                    } else if (key.compareTo(current.data.get(i).key) == 0) {
                        // found
                        indexToDel = i;
                        delFrom = current;
                        notfound = false;
                        break;
                    }
                } else if (i < current.keysNum) {
                    if (key.compareTo(current.data.get(i - 1).key) > 0 & key.compareTo(current.data.get(i).key) < 0) {
                        if (current.children != null) {
                            current = current.children.get(i);
                            break;
                        } else {
                            notPresent = true;
                        }
                    } else if (key.compareTo(current.data.get(i).key) == 0) {
                        indexToDel = i;
                        delFrom = current;
                        notfound = false;
                        break;
                    }
                } else {
                    if (key.compareTo(current.data.get(i - 1).key) > 0) {
                        if (current.children != null) {
                            current = current.children.get(i);
                            break;
                        } else {
                            notPresent = true;
                        }
                    }
                }
            }
        }


        Node toSwap = current;
        boolean sameplace = true;
        if (!notPresent) {
            if (current.children != null) {
                current = current.children.get(current.keysNum + 1);
                while (current.children != null) {
                    current = current.children.get(0);
                }
                toSwap = current;
                sameplace = false;
            }

            toSwap.merge();
            KeyVal temp;
            if (!sameplace) {
                temp = toSwap.data.get(0);
                delFrom.data.set(indexToDel, temp);
                toSwap.data.remove(0);
            } else {
                toSwap.data.remove(indexToDel);
            }
        }
        return notPresent;
    }


    @Override
    public void delete(Key key) throws IllegalKeyException {
        int c = this.search(key).size();
        for (int i = 0; i < c; i++) {
            if(this.deleteOne(key)) {
                throw new IllegalKeyException();
            }
        }
    }


    @Override
    public String toString() {
        return this.root.toString();
    }
}

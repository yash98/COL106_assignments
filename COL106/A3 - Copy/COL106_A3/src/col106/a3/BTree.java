package col106.a3;

import java.util.*;

public class BTree<Key extends Comparable<Key>,Value> implements DuplicateBTree<Key,Value> {
    private class KeyVal{
        private Key key;
        private Value val;
        KeyVal(Key key, Value val){
            this.key = key;
            this.val = val;
        }
        // public int compare(KeyVal that){
         /*   return this.key.compareTo(that.key);
        }*/
    }
    private class Node{
        public Vector<KeyVal> data = new Vector<KeyVal>();
        public Vector<BTree<Key, Value>> children = new Vector<>();
        public BTree<Key, Value> parent;
        public Node(BTree<Key, Value> par){
            /*this.data = null;
            this.children = null;*/
            this.parent = par;
        }
    }
    // private BTree<Key, Value> parent;
    private int mysize;
    private Node root;
    private int m;
    private int myheight;
    //private BTree<Key, Value> head ;
    public BTree(int b) throws bNotEvenException {  /* Initializes an empty b-tree. Assume b is even. */
        this(b, null);
        //throw new RuntimeException("Not Implemented");
    }
    private BTree(int b, BTree<Key, Value> parent)throws bNotEvenException {
        if (b%2 == 1) throw new bNotEvenException();
        root = new Node(parent);
        m = b;
        mysize = 0;
        myheight = -1;
        //this.parent = parent;

    }
    /*private int compareTo(Key a, Key b){
        if ()
    }*/
    private int insertnew(KeyVal k, Vector<KeyVal> v){
        int i;
        for (i=0; i<v.size();i++){
            if (v.elementAt(i).key.compareTo(k.key)>0){
                v.add(i, k);
                return i;
            }
        }
        if (i==v.size())v.add(k);
        return i;
    }
    private int indexgo(KeyVal k, Vector<KeyVal> v){
        int i;
        for (i=0; i<v.size();i++){
            if (v.elementAt(i).key.compareTo(k.key)>0){
                return i;
            }
        }
        return i;
    }
    @Override
    public boolean isEmpty() {
        //throw new RuntimeException("Not Implemented");
        return root.data == null;
    }

    @Override
    public int size() {
        //throw new RuntimeException("Not Implemented");
        return mysize;
    }

    @Override
    public int height() {
        return myheight;
        //throw new RuntimeException("Not Implemented");
    }
    private List<Value> search(Key key, List<Value> out, BTree<Key, Value> b) throws IllegalKeyException{

        int i;
        for (i = 0; i<b.root.data.size(); i++){
            if (b.root.data.elementAt(i).key.compareTo(key)==0 && b.root.children.size()==0){
                out.add(b.root.data.elementAt(i).val);
            }
            else if(b.root.data.elementAt(i).key.compareTo(key)>0 && b.root.children.size()==0){
                break;
            }
            else if (b.root.data.elementAt(i).key.compareTo(key)==0){
                out = search(key, out, b.root.children.elementAt(i));
                out.add(b.root.data.elementAt(i).val);
            }
            else if (b.root.data.elementAt(i).key.compareTo(key)>0){
                out = search(key, out, b.root.children.elementAt(i));
                break;
            }
        }
        if (i==b.root.data.size() && b.root.children.size()!=0){
            out = search(key, out, b.root.children.elementAt(i));
        }
        return out;
    }
    @Override
    public List<Value> search(Key key) throws IllegalKeyException {
        List<Value> out = new ArrayList<Value>() ;

        out = search(key, out, this);
        //if (out.size()==0) throw new IllegalKeyException();
        return out;
        //throw new RuntimeException("Not Implemented");
    }
    private void split(Node myroot){
        if (myroot.data.size()==m){

            BTree<Key, Value> temp1 = null;
            BTree<Key, Value> temp2 = null;
            try {
                temp1 = new BTree<Key, Value>(m, myroot.parent);
                temp2 = new BTree<Key, Value>(m, myroot.parent);
            } catch (bNotEvenException e) {
                e.printStackTrace();
            }
            /*Vector<KeyVal> data1 = new Vector<>();
            Vector<KeyVal> data2 = new Vector<>();*/
            /*Vector<BTree<Key, Value>> child1 = new Vector<>();
            Vector<BTree<Key, Value>> child2 = new Vector<>();*/
            for (int i=0; i<m/2; i++){
                temp1.root.data.add(myroot.data.elementAt(i));
                if (myroot.children.size()!=0){
                    myroot.children.elementAt(i).root.parent = temp1;
                    temp1.root.children.add(myroot.children.elementAt(i));
                }
            }
            if ( myroot.children.size()!=0){
                myroot.children.elementAt(m/2).root.parent = temp1;
                temp1.root.children.add(myroot.children.elementAt(m/2));
            }
            for (int i= m/2+1; i<m; i++){
                temp2.root.data.add(myroot.data.elementAt(i));
                if (myroot.children.size()!=0){
                    myroot.children.elementAt(i).root.parent = temp2;
                    temp2.root.children.add(myroot.children.elementAt(i));
                }
            }
            if ( myroot.children.size()!=0){
                myroot.children.elementAt(m).root.parent = temp2;
                temp2.root.children.add(myroot.children.elementAt(m));
            }
            KeyVal k = myroot.data.elementAt(m/2);
            assert temp1 != null;
            assert temp2 != null;
            /*temp1.root.data = data1;
            temp2.root.data = data2;
            temp1.root.children = child1;
            temp2.root.children = child2;*/
            if (myroot.parent!=null){
                int index = insertnew(k, myroot.parent.root.data);
                myroot = myroot.parent.root;
                myroot.children.add(index, temp1);
                myroot.children.add(index+1, temp2);
                myroot.children.remove(index+2);
                split(myroot);
            }
            else{
                BTree<Key, Value> b = null;
                try {
                    b = new BTree<Key, Value>(m);
                } catch (bNotEvenException e) {
                    e.printStackTrace();
                }
                assert b != null;
                b.root.data.add(k);
                b.root.children.add(temp1);
                b.root.children.add(temp2);
                temp1.root.parent = b;
                temp2.root.parent = b;
                this.myheight = this.myheight+1;
                this.root = b.root;
            }
        }
    }
    private void insert2(Key key, Value val, BTree<Key, Value> b){
        if (b.root.data.size()==m-1){
            BTree<Key , Value> temp1= null;
            BTree<Key , Value> temp2= null;
            try {
                temp1 = new BTree<Key, Value>(m, b.root.parent);
                temp2 = new BTree<Key, Value>(m, b.root.parent);

            } catch (bNotEvenException e) {
                e.printStackTrace();
            }
            for (int i=0; i<m/2-1; i++){
                temp1.root.data.add(b.root.data.elementAt(i));
                temp2.root.data.add(b.root.data.elementAt(i+m/2));
                if (b.root.children.size()!=0){
                    temp1.root.children.add(b.root.children.elementAt(i));
                    temp2.root.children.add(b.root.children.elementAt(i+m/2));
                    b.root.children.elementAt(i).root.parent = temp1;
                    b.root.children.elementAt(i+m/2).root.parent = temp2;
                }
            }
            if(b.root.children.size()!=0){
                temp1.root.children.add(b.root.children.elementAt(m/2-1));
                temp2.root.children.add(b.root.children.elementAt(m-1));
                b.root.children.elementAt(m/2-1).root.parent = temp1;
                b.root.children.elementAt(m-1).root.parent = temp2;
            }
            int index = insertnew(b.root.data.elementAt(m/2-1), b.root.parent.root.data);
            BTree<Key, Value> father = b.root.parent;
            father.root.children.add(index, temp1);
            father.root.children.add(index+1, temp2);
            father.root.children.remove(index+2);
            int j;
            for (j=0; j<father.root.data.size(); j++){
                if (father.root.data.elementAt(j).key.compareTo(key)>0){
                    insert2(key, val, father.root.children.elementAt(j));
                    break;
                }
            }
            if (j==father.root.data.size()){
                insert2(key, val, father.root.children.elementAt(j));
            }

        }
        else if(b.root.children.size()==0){
            insertnew(new KeyVal(key, val), b.root.data);
        }
        else{
            int j;
            for (j =0; j<b.root.data.size(); j++){
                if (b.root.data.elementAt(j).key.compareTo(key)>0){
                    insert2(key,val,b.root.children.elementAt(j));
                    break;
                }
            }
            if(j == b.root.data.size()){
                insert2(key,val,b.root.children.elementAt(j));
            }
        }
    }
    @Override
    public void insert(Key key, Value val)  {
        if (mysize==0) myheight++;
        mysize++;
        if (this.root.data.size()==m-1){
            BTree<Key, Value> temp1= null, temp2 = null, new1 = null;
            try {
                new1 = new BTree<Key, Value>(m);
                temp1 = new BTree<Key, Value>(m, new1);
                temp2 = new BTree<Key, Value>(m, new1);

            } catch (bNotEvenException e) {
                e.printStackTrace();
            }
            for (int i=0; i<m/2-1; i++){
                temp1.root.data.add(this.root.data.elementAt(i));
                temp2.root.data.add(this.root.data.elementAt(i+m/2));
                if (this.root.children.size()!=0){
                    temp1.root.children.add(this.root.children.elementAt(i));
                    temp2.root.children.add(this.root.children.elementAt(i+m/2));
                    this.root.children.elementAt(i).root.parent = temp1;
                    this.root.children.elementAt(i+m/2).root.parent = temp2;
                }
            }
            if(this.root.children.size()!=0){
                temp1.root.children.add(this.root.children.elementAt(m/2-1));
                temp2.root.children.add(this.root.children.elementAt(m-1));
                this.root.children.elementAt(m/2-1).root.parent = temp1;
                this.root.children.elementAt(m-1).root.parent = temp2;
            }
            new1.root.data.add(this.root.data.elementAt(m/2-1));
            new1.root.children.add(temp1);
            new1.root.children.add(temp2);
            this.root = new1.root;
            this.myheight++;
        }
        insert2(key, val, this );
        /*if (mysize==0)myheight=0;
        mysize++;
        Node myroot = this.root;
        Node myroot_temp = this.root;
        for (int i=0; i<height();i++){
            int j;
            for(j=0; j<myroot.data.size();j++){
                if (myroot.data.elementAt(j).key.compareTo(key)>0){
                    myroot_temp = myroot.children.elementAt(j).root;
                    break;
                }
            }
            if (j==myroot.data.size()){
                myroot_temp = myroot.children.elementAt(j).root;
            }
            myroot = myroot_temp;
        }
        int i;

        for (i = 0; i < myroot.data.size(); i++) {
            if (myroot.data.elementAt(i).key.compareTo(key) > 0) {
                break;
            }
        }
        myroot.data.add(i, new KeyVal(key, val));

        split(myroot);*/



    /*public void insert(Key key, Value val)  {
        if (mysize==0)myheight=0;
        mysize++;
        Node myroot = this.root;
        Node myroot_temp = this.root;
        for (int i=0; i<height();i++){
            int j;
            for(j=0; j<myroot.data.size();j++){
                if (myroot.data.elementAt(j).key.compareTo(key)>0){
                    myroot_temp = myroot.children.elementAt(j).root;
                    break;
                }
            }
            if (j==myroot.data.size()){
                myroot_temp = myroot.children.elementAt(j).root;
            }
            myroot = myroot_temp;
        }
        int i;


        for (i = 0; i < myroot.data.size(); i++) {
            if (myroot.data.elementAt(i).key.compareTo(key) > 0) {
                break;
            }
        }
        myroot.data.add(i, new KeyVal(key, val));

        while (myroot.data.size()==m){
            BTree<Key, Value> temp1 = null;
            BTree<Key, Value> temp2 = null;
            try {
                temp1 = new BTree<>(m,myroot.parent);
                temp2 = new BTree<>(m, myroot.parent);
            } catch (bNotEvenException e) {
                e.printStackTrace();
            }
           // Node temp2 = new Node(myroot.parent);
            Vector<KeyVal> tmp1 = new Vector<>();
            Vector<KeyVal> tmp2 = new Vector<>();
            Vector<BTree<Key, Value >> child1 = new Vector<BTree<Key, Value>>();
            Vector<BTree<Key, Value >> child2 = new Vector<BTree<Key, Value>>();
            for (int j=0; j<m/2; j++){
                tmp1.add(myroot.data.elementAt(j));
                if (myroot.children.size()!= 0){
                    myroot.children.elementAt(j).root.parent = temp1;
                    child1.add(myroot.children.elementAt(j));
                }
            }
            if (myroot.children.size() != 0){
                myroot.children.elementAt(m/2).root.parent = temp1;
                child1.add(myroot.children.elementAt(m/2));
            }
            for (int j=m/2+1;j<m; j++){
                tmp2.add(myroot.data.elementAt(j));
                if (myroot.children.size() != 0){
                    myroot.children.elementAt(j).root.parent = temp2;
                    child2.add(myroot.children.elementAt(j));
                }
            }
            if (myroot.children.size() != 0) {
                myroot.children.elementAt(m).root.parent = temp2;
                child2.add(myroot.children.elementAt(m));
            }

            temp1.root.data = tmp1;
            temp1.root.children = child1;
            temp2.root.children = child2;
            temp2.root.data = tmp2;
            KeyVal k = myroot.data.elementAt(m/2);
            if(myroot.parent!= null){
                int ind = insertnew(k, myroot.parent.root.data);
                myroot.parent.root.children.add(ind, temp1);
                myroot.parent.root.children.add(ind+1, temp2);
                myroot.parent.root.children.remove(ind+2);
                myroot = temp1.root.parent.root;
            }
            else {
                try {
                    BTree<Key, Value> r = new BTree<Key, Value>(m, null);
                    r.root.data.add(k);
                    temp1.root.parent = r;
                    temp2.root.parent = r;

                    r.root.children.add(temp1);
                    r.root.children.add(temp2);
                    this.root = r.root;

                } catch (bNotEvenException e) {
                    e.printStackTrace();
                }
                myheight++;
                break;

            }
        }
        //throw new RuntimeException("Not Implemented");*/
    }



    private String tostr(BTree<Key, Value> b, String out){
        out = out.concat("[");
        if (b.root.children.size()==0){
            for(int i = 0; i<b.root.data.size(); i++){
                out = out.concat(b.root.data.elementAt(i).key+"="+b.root.data.elementAt(i).val+", ");
            }
            out = out.substring(0, out.length()-2);
            out = out+"]";
        }
        else {
            out = tostr(b.root.children.elementAt(0), out);
            for (int i = 0; i < b.root.data.size(); i++) {
                out = out.concat(", " + b.root.data.elementAt(i).key + "=" + b.root.data.elementAt(i).val + ", ");
                out = tostr(b.root.children.elementAt(i + 1), out);
            }
            out = out + "]";
        }
        return out;
    }
    public String toString(){
        if (myheight==-1)return "[]";
        String out = "";

        out = tostr(this, out);
        return out;
    }


    //@Override
    @Override
    public void delete(Key key) throws IllegalKeyException {
        int p = this.search(key).size();
        if (p==0) throw new IllegalKeyException();
        for(int q= 0; q<p; q++) {
            /*if (this.root.children.size()==0){
                int j;
                for (j=0; j<this.root.data.size(); j++){
                    if (this.root.data.elementAt(j).key.compareTo(key)==0){
                        this.root.data.remove(j);
                        break;
                    }
                }
                break;
            }*/
            delete3(key, this);
        }
        mysize = mysize-p;
        if (mysize==0) myheight=-1;
    }
    private int child_index(BTree<Key , Value > b){
        int i;
        for (i=0; i<b.root.parent.root.children.size(); i++){
            if (b.root.parent.root.children.elementAt(i)==b) return i;
        }
        return 0;
    }
    private BTree<Key, Value> merge(int i, BTree<Key, Value> successor, BTree<Key, Value> sibling, BTree<Key, Value> father){
        if(sibling.root.data.size()>=m/2){
            successor.root.data.add(father.root.data.elementAt(i));
            father.root.data.add(i,sibling.root.data.elementAt(0));
            father.root.data.remove(i+1);
            sibling.root.data.remove(0);
            if (sibling.root.children.size()!=0){
                if (successor.root.children.size()!=0) {
                    successor.root.children.add(sibling.root.children.elementAt(0));
                    sibling.root.children.elementAt(0).root.parent = successor;
                    sibling.root.children.remove(0);
                }
            }
            return successor;
        }
        else {
            father.root.children.remove(i);
            sibling.root.data.add(0, father.root.data.elementAt(i));
            father.root.data.remove(i);
            int j;
            for (j = 0; j < successor.root.data.size(); j++) {
                sibling.root.data.add(0, successor.root.data.elementAt(successor.root.data.size() - 1 - j));
                if (successor.root.children.size() != 0) {
                    sibling.root.children.add(0, successor.root.children.elementAt(successor.root.children.size() - 1 - j));
                    successor.root.children.elementAt(j).root.parent = sibling;
                }
            }
            if (successor.root.children.size() != 0){
                sibling.root.children.add(0, successor.root.children.elementAt(successor.root.children.size() - 1 - j));
                successor.root.children.elementAt(j).root.parent = sibling;
            }
            if (father.root.data.size()==0){
                sibling.root.parent=null;
                this.root = sibling.root;
                this.myheight--;
            }
            return sibling;
        }
    }
    private BTree<Key, Value> mergeback(int i, BTree<Key, Value> successor, BTree<Key, Value> sibling, BTree<Key, Value> father) {
        if (sibling.root.data.size() >= m / 2) {
            successor.root.data.add(0,father.root.data.elementAt(i));
            father.root.data.add(i, sibling.root.data.elementAt(sibling.root.data.size()-1));
            father.root.data.remove(i + 1);
            sibling.root.data.remove(sibling.root.data.size()-1);
            if (sibling.root.children.size() != 0) {
                successor.root.children.add(0,sibling.root.children.elementAt(sibling.root.children.size()-1));
                sibling.root.children.elementAt(sibling.root.children.size()-1).root.parent = successor;
                sibling.root.children.remove(sibling.root.children.size()-1);
            }
            return successor;
        } else {
            father.root.children.remove(i+1);
            sibling.root.data.add( father.root.data.elementAt(i));
            father.root.data.remove(i);
            int j;
            for (j = 0; j < successor.root.data.size(); j++) {
                sibling.root.data.add( successor.root.data.elementAt(j));
                if (successor.root.children.size()!=0) {
                    sibling.root.children.add(successor.root.children.elementAt(j));
                    successor.root.children.elementAt(j).root.parent = sibling;
                }
            }
            if (successor.root.children.size()!=0) {
                sibling.root.children.add(successor.root.children.elementAt(j));
                successor.root.children.elementAt(j).root.parent = sibling;
            }
            if (father.root.data.size() == 0) {
                sibling.root.parent = null;
                this.root = sibling.root;
                this.myheight--;
            }
            return sibling;
        }
    }
    private void delete3(Key key, BTree<Key, Value> b) throws IllegalKeyException{
        int i;
        int max = b.root.data.size();
        for (i = 0; i<b.root.data.size(); i++){
            if (b.root.data.elementAt(i).key.compareTo(key)>0){
                BTree<Key, Value> successor = b.root.children.elementAt(i);
                if(successor.root.data.size()>m/2-1) {
                    delete3(key, b.root.children.elementAt(i));
                    break;
                }
                else{
                    BTree<Key, Value> sibling = b.root.children.elementAt(i+1);
                    b = merge(i, successor, sibling, b);
                    delete3(key , b);
                    break;
                }
            }
            else if (b.root.data.elementAt(i).key.compareTo(key)==0){
                if (b.root.children.size()==0){
                    b.root.data.remove(i);
                    break;
                }
                else {
                    BTree<Key, Value> successor = b.root.children.elementAt(i);
                    if(successor.root.data.size()==m/2-1) {
                        BTree<Key, Value> sibling = b.root.children.elementAt(i+1);
                        b = merge(i, successor, sibling, b);
                        delete3(key , b);
                        break;
                    }
                    else{
                        BTree<Key, Value> myhead = b;
                        b=myhead.root.children.elementAt(i);
                        while(b.root.children.size()!=0){
                            if(b.root.children.lastElement().root.data.size()==m/2-1){
                                int ind = b.root.data.size()-1;
                                b=mergeback(b.root.data.size()-1,b.root.children.lastElement(),b.root.children.elementAt(b.root.children.size()-2),b);
                            }
                            else{
                                b = b.root.children.lastElement();
                            }
                        }
                        myhead.root.data.add(i,b.root.data.lastElement());
                        myhead.root.data.remove(i+1);
                        b.root.data.remove(b.root.data.size()-1);
                        b=myhead;
                        break;

                    }
                }
            }
        }
        if (i==max){
            BTree<Key, Value> successor = b.root.children.elementAt(i);
            if(successor.root.data.size()>m/2-1) {
                delete3(key, b.root.children.elementAt(i));

            }
            else{
                BTree<Key, Value> sibling = b.root.children.elementAt(i-1);
                b = mergeback(i-1, successor, sibling, b);
                delete3(key , b);

            }
        }
    }
    public void delete2(Key key, BTree<Key, Value> b) throws IllegalKeyException {


        int i;
        int j = b.root.data.size();
        for (i= 0 ; i<b.root.data.size(); i++){
            if (b.root.data.elementAt(i).key.compareTo(key)>0){
                delete2(key, b.root.children.elementAt(i));
                break;
            }
            else if (b.root.data.elementAt(i).key.compareTo(key) == 0) {

                if(b.root.children.size()==0){
                    b.root.data.remove(i);

                }
                else {
                    BTree<Key, Value> k = b.root.children.elementAt(i+1);
                    while (k.root.children.size()!=0){
                        k= k.root.children.elementAt(0);
                    }
                    b.root.data.add(i, k.root.data.firstElement());
                    b.root.data.remove(i+1);
                    k.root.data.remove(0);
                    b = k;
                }
                if(b.root.data.size()<m/2-1) {
                    if (b.root.parent==null){
                        if (b.root.data.size()==0){
                            myheight=-1;
                        }
                        break;
                    }
                    while (b.root.data.size() < m/2-1 && b.root.parent!=null) {
                        BTree<Key, Value> father = b.root.parent;
                        // BTree<Key, Value> sibling = father.root.children.elementAt(1);
                        //assert father != null;
                        if (father.root.children.elementAt(0) == b) {
                            BTree<Key, Value> sibling = father.root.children.elementAt(1);
                            if (sibling.root.data.size() >= m / 2) {
                                b.root.data.add(father.root.data.elementAt(0));
                                father.root.data.add(0, sibling.root.data.elementAt(0));
                                father.root.data.remove(1);

                                sibling.root.data.remove(0);
                                if (b.root.children.size()!=0) {
                                    b.root.children.add(sibling.root.children.elementAt(0));
                                    sibling.root.children.elementAt(0).root.parent = b;
                                    sibling.root.children.remove(0);
                                }
                            }
                            else {
                                father.root.children.remove(0);
                                sibling.root.data.add(0,father.root.data.elementAt(0));
                                father.root.data.remove(0);
                                for (int x = 0; x<b.root.data.size(); x++){
                                    sibling.root.data.add(0,b.root.data.elementAt(b.root.data.size()-x-1));
                                    if (b.root.children.size()!=0){
                                        sibling.root.children.add(0,b.root.children.elementAt(b.root.data.size()-x));
                                        b.root.children.elementAt(x).root.parent = sibling;
                                    }
                                }
                                if (b.root.children.size()!=0){
                                    sibling.root.children.add(0,b.root.children.elementAt(0));
                                    b.root.children.elementAt(0).root.parent = sibling;
                                }
                                b = father;
                                if (b.root.parent==null && b.root.data.size()==0) {
                                    b = b.root.children.firstElement();
                                    b.root.parent=null;
                                    this.root = b.root;
                                    this.myheight = this.myheight-1;
                                }
                            }
                        }
                        else{
                            int index = father.root.children.indexOf(b);
                            BTree<Key, Value> sibling = father.root.children.elementAt(index-1);
                            if (sibling.root.data.size() >= m / 2) {
                                b.root.data.add(father.root.data.elementAt(index-1));
                                father.root.data.add(index-1, sibling.root.data.lastElement());
                                father.root.data.remove(index);
                                sibling.root.data.remove(sibling.root.data.size()-1);
                                if (b.root.children.size()!=0) {
                                    b.root.children.add(sibling.root.children.lastElement());
                                    sibling.root.children.lastElement().root.parent = b;
                                    sibling.root.children.remove(sibling.root.children.size()-1);
                                }
                            }
                            else {
                                father.root.children.remove(index);
                                sibling.root.data.add(father.root.data.elementAt(index-1));
                                father.root.data.remove(index-1);
                                for (int x=0; x<b.root.data.size(); x++){
                                    sibling.root.data.add(b.root.data.elementAt(x));
                                    if (b.root.children.size()!=0){
                                        sibling.root.children.add(b.root.children.elementAt(x));
                                        b.root.children.elementAt(x).root.parent = sibling;
                                    }
                                }
                                if (b.root.children.size()!=0){
                                    sibling.root.children.add(b.root.children.elementAt(b.root.children.size()-1));
                                    b.root.children.elementAt(b.root.children.size()-1).root.parent = sibling;
                                }
                                b = father;
                                if (b.root.parent==null && b.root.data.size()==0) {
                                    b = b.root.children.firstElement();
                                    b.root.parent=null;
                                    this.root = b.root;
                                    this.myheight = this.myheight-1;
                                }
                            }
                        }
                    }
                }
                break;
            }
        }

        if (i==b.root.data.size() && b.root.children.size()!=0){
            delete2(key,b.root.children.elementAt(i));
        }



        // throw new RuntimeException("Not Implemented");
    }
    public static void main(String args[]) throws bNotEvenException, IllegalKeyException {
        BTree<Integer, Integer> b = new BTree<Integer, Integer>(4);
        Random r = new Random();
        for (int j= 0; j<5 ; j++) {

            for (int i = 0; i < 5; i++) {
                b.insert(i, j*50);
            }
        }
        for (int i = 0; i<5; i++){
            System.out.println(b.search(i).size());
        }
        for (int i=4; i>0; i--){
            b.delete(i);
        }
        /*System.out.println(b.search(3));
        System.out.println(b);*/
       /* b.insert("a", "b");
        b.insert("a", "c");
        b.insert("b", "a");
        b.insert("b", "c");
        b.insert("b", "d");
        b.insert("b", "e");
        b.insert("b", "g");
        b.insert("c", "a");
        b.insert("c", "b");
        b.insert("c", "d");
        b.insert("d", "b");
        b.insert("d", "c");
        b.insert("d", "e");
        b.insert("d", "f");
        b.insert("e", "b");
        b.insert("e", "d");
        b.insert("e", "f");
        b.insert("e", "g");
        b.insert("e", "h");
        b.delete("e");
        b.delete("d");
        b.delete("c");
        b.delete("b");
        b.delete("a");*/
        System.out.println(b);

    }
}

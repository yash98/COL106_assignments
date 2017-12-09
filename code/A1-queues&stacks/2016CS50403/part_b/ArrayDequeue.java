public class ArrayDequeue implements DequeInterface {

  public Object[] arr = new Object[1];
  public int front = 0;
  public int rear = 0;

  public int size(){
    return ((arr.length + rear - front) % arr.length);
  }

  public boolean isEmpty(){
    return (front == rear);
  }

  private void growArray() {
    Object[] tempArr = new Object[(arr.length*2)];
    for (int i = 0; i < arr.length-1; i++) {
      tempArr[i] = arr[(front+i)%arr.length];
    }
    front = 0;
    rear = arr.length-1;
    arr = tempArr;
  }

  public void insertFirst(Object o){
    if (this.size() == arr.length-1) {
      this.growArray();
    }
    arr[(arr.length+front-1)%arr.length] = o;
    front = (arr.length+front-1)%arr.length;
  }

  public void insertLast(Object o){
    if (this.size() == arr.length-1) {
      this.growArray();
    }
    arr[rear] = o;
    rear = (rear+1)%arr.length;
  }


  public Object removeFirst() throws EmptyDequeException{
    if (this.isEmpty()) {
      throw new EmptyDequeException("Cant remove first from an empty Deque.");
    } else {
      front = (front+1)%arr.length;
      return arr[(arr.length+front-1)%arr.length];
    }
  }

  public Object removeLast() throws EmptyDequeException{
    if (this.isEmpty()) {
      throw new EmptyDequeException("Cant remove last from an empty Deque.");
    } else {
      rear = (arr.length+rear-1)% arr.length;
      return arr[rear];
    }
  }

  public Object first() throws EmptyDequeException{
    if (this.isEmpty()) {
      throw new EmptyDequeException("The Deque is empty, has no first.");
    } else {
      return arr[front];
    }
  }

  public Object last() throws EmptyDequeException{
    if (this.isEmpty()) {
      throw new EmptyDequeException("The Deque is empty, has no last.");
    } else {
      return arr[(arr.length+rear-1)%arr.length];
    }
  }

  public String toString(){
    if (this.isEmpty()) {
      return "[]";
    } else {
      String str = "[";
      for (int i = 0; i < this.size()-1; i++) {
        str += arr[(front+i)%arr.length].toString();
        str += ", ";
      }
        str += arr[(front+this.size()-1)%arr.length].toString();
        str += "]";
      return str;
    }
  }

  public static void main(String[] args){
    int  N = 10;
    DequeInterface myDeque = new ArrayDequeue();
    for(int i = 0; i < N; i++) {
      myDeque.insertFirst(i);
      myDeque.insertLast(-1*i);
    }

    int size1 = myDeque.size();
    System.out.println("Size: " + size1);
    System.out.println(myDeque.toString());

    if(size1 != 2*N){
      System.err.println("Incorrect size of the queue.");
    }

    //Test first() operation
    try{
      int first = (int)myDeque.first();
      int size2 = myDeque.size(); //Should be same as size1
      if(size1 != size2) {
        System.err.println("Error. Size modified after first()");
      }
    }
    catch (EmptyDequeException e){
      System.out.println("Empty queue");
    }

    //Remove first N elements
    for(int i = 0; i < N; i++) {
      try{
        int first = (Integer)myDeque.removeFirst();
      }
      catch (EmptyDequeException e) {
        System.out.println("Cant remove from empty queue");
      }

    }


    int size3 = myDeque.size();
    System.out.println("Size: " + myDeque.size());
    System.out.println(myDeque.toString());

    if(size3 != N){
      System.err.println("Incorrect size of the queue.");
    }

    try{
      int last = (int)myDeque.last();
      int size4 = myDeque.size(); //Should be same as size3
      if(size3 != size4) {
        System.err.println("Error. Size modified after last()");
      }
    }
    catch (EmptyDequeException e){
      System.out.println("Empty queue");
    }

    //empty the queue  - test removeLast() operation as well
    while(!myDeque.isEmpty()){
        try{
          int last = (int)myDeque.removeLast();
        }
        catch (EmptyDequeException e) {
          System.out.println("Cant remove from empty queue");
        }
      }

    int size5 = myDeque.size();
    if(size5 != 0){
      System.err.println("Incorrect size of the queue.");
    }

  }

}

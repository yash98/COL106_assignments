
public class ArrayDequeue implements DequeInterface {
  public void insertFirst(Object o){
    //you need to implement this
    throw new java.lang.UnsupportedOperationException("Not implemented yet.");
  }
  
  public void insertLast(Object o){
//you need to implement this
    throw new java.lang.UnsupportedOperationException("Not implemented yet.");
    
  }
  
  public Object removeFirst(){
    throw new java.lang.UnsupportedOperationException("Not implemented yet.");
  }
  
  public Object removeLast(){
    throw new  java.lang.UnsupportedOperationException("Not implemented yet.");
  }
  public Object first(){
    throw new  java.lang.UnsupportedOperationException("Not implemented yet.");
  }
  
  public Object last(){
    throw new java.lang.UnsupportedOperationException("Not implemented yet.");
  }
  
  public int size(){
    throw new java.lang.UnsupportedOperationException("Not implemented yet.");
  }
  public boolean isEmpty(){
    throw new java.lang.UnsupportedOperationException("Not implemented yet.");
  }
  public String toString(){
    throw new java.lang.UnsupportedOperationException("Not implemented yet.");
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
public interface DequeInterface {     
  public void insertFirst(Object o);     
  public void insertLast(Object o);     
  public Object removeFirst() throws EmptyDequeException;     
  public Object removeLast() throws EmptyDequeException;      
  public Object first() throws EmptyDequeException;     
  public Object last() throws EmptyDequeException;     
  public int size();     
  public boolean isEmpty();     
  public String toString();
  
  
  class EmptyDequeException extends Exception{  
    EmptyDequeException(String s){  
      super(s);  
    }  
  }
}


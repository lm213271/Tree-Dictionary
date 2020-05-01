import java.util.Iterator;

//
// Complete this class: 20 points
//
// Note:
// Do NOT copy from textbook, or any other sources
// Do NOT copy from code that we did in class (as it contains errors)
//
// Do READ CODE from textbook and in-class code and write it out as your own code
//
public class LinkedList<T> implements Iterable<T>
{
    public static void main(String [] args)
    {
        //Optional: test LinkedList here
        Double A[]={0.1,0.34,0.7,23.1,-0.75};
        LinkedList<Double> M = new LinkedList<Double>(A);
        System.out.println("Linked List: "+M);
    }

    /**
     * Contructor to create a linked list from a array
     * @param A: arguement for the constructor
     */
    public LinkedList(T [] A) //create a linked list from an array
    {
        for (T a: A) {
            insert(a);
        }
    }

    /**
     *  this method is responsible for inserting data into the linked list
     * @param data: data value to be inserted
     */
    public void insert(T data) //insert data into the linked list
    {
        /**
        * we make a new node for data and we check and see if the tail is not 
        * an empthy node or the new node is empthy then we insert the new node 
        * at the end of the list next to tail node.
        * if head is an empthy node or the new node is empthy node then we insert
        * it to the first node.
        */
        Node<T> node = new Node<>(data);
        node.next = null;
        node.prev = this.tail;
        if (this.tail != null || this.tail.data == null) tail.next = node;
        this.tail = node;
        if (this.head == null || this.head.data == null) this.head = node;
    }

    /**
     * this method delete the node from the linked list and also maintain the link between
     *  the node while deletion
     * @param n : node to be delete
     */
    private void delete(Node<T> n) //remove node n from the linked list
    {
        /**
         * if both head and tail are null then we return nothing because it is an empthy list.
         */
        if (this.head == null && this.tail == null) {
            return;
        }
        /**
         * if head is equal to node n then we shift head next to the node n.
         */
        if (this.head == n) {
            this.head = n.next;
        }
        /**
         * if the node next to the node n is empthy or null it means node n is the last node 
         * then tail will be the node before n.
         */
        if (n.next == null) {
            this.tail = n.prev;
        }
        if (n.next != null) {
            n.next.prev = n.prev;
        }
        if (n.prev != null) {
            n.prev.next = n.next;
        }
        return;
    }

    /**
     * this is method tell whethet the link list is empty or not
     * @return: returns true if list is empty else false
     */
    public boolean is_empty() //check if the string is empty
    {
      return this.head != null;
    }


    /**
     * this method is iterator method in order to support foreach or short for loop form
     * @return: return object of the Iterator class
     */
    public Iterator<T> iterator(){
      return new DllIterator<T>(this);
    }

    //Note: You will need to implement an iterator class using java.util.Iterator
    //      interface

    //TO DO: implement iterator here

    private class DllIterator<T> implements Iterator<T> {
        LinkedList<T> ts;
        LinkedList<T>.Node<T> curr;
        public DllIterator(LinkedList<T> ts) {
            this.ts = ts;
            curr = ts.head;
        }

        @Override
        public boolean hasNext() {
            return curr != null;
        }

        @Override
        public T next() {
            LinkedList<T>.Node<T> tmp = curr;
            this.curr = this.curr.next;
            return tmp.data;
        }
    }

    // ----------------------------------------------------------------------
    //
    // !!! READ but Do NOT Change anything after this line
    //
    // ----------------------------------------------------------------------

    private class Node<T>
    {
      Node(){}
      Node(T data){ this.data=data; }
      public T data;
      public Node<T> next;
      public Node<T> prev; //for doubly linked list
    }

    Node<T> head; //pointing to the location BEFORE the first element
    Node<T> tail; //for doubly linked list
                  //pointing to the location AFTER the last element

    public LinkedList() //constructor
    {
      head=new Node<T>();
      tail=new Node<T>();
      head.next=tail;
      tail.prev=head;
    }

    public T last()
    {
      //nothing to return
      if(head.next==tail) return null;
      return tail.prev.data;
    }

    public String toString()
    {
      String S="(";
      for(T t : this) S=S+t+", ";
      if(is_empty()==false) S=S.substring(0,S.length()-2);
      S=S+")";
      return S;
    }
}

import java.util.Iterator;

//
// Complete this class: 15 points
//
// Note:
// Do NOT copy from textbook, or any other sources
// Do NOT copy from code that we did in class (as it contains errors)
//
// Do READ CODE from textbook and in-class code and write it out as your own code
//
public class DynamicArray<T> implements Iterable<T>
{

    public static void main(String [] args)
    {
        //Optional: test DynamicArray here
        DynamicArray<Double> A = new DynamicArray<Double>();
        for(int i=0;i<20;i++) A.insert(Math.random()*100);
        for(Double v : A)
        {
          System.out.println(v);
        }
    }

    // Note: You can add any private data and methods here
    private T array[];
    private int size;
    private int count;
    @SuppressWarnings("unchecked")
    public DynamicArray() //constructor
    {
        array = (T[]) new Object[4];
        count = 0;
        size = 4;
    }

    /**
     * this method is responsible for inserting data into the array
     * also it double the size of the array if the arrat size gets full
     * @param data: data to be inserted
     */
    @SuppressWarnings("unchecked")
    public void insert(T data)
    {
        if (count==size) {
            increaseSize();
        }
        this.array[count] = data;
        this.count++;
    }

    /**
     * it double the size of the array and reinsert the data
     */
    @SuppressWarnings("unchecked")
    private void increaseSize() 
    {

      /**
       * we make a empthy temporary array 
       * we check to see if ount of the total data inserted the array is equal 
       * to the size of array then we copy all the elements of array into the 
       * temporary array, then we double the size of the array.
       */
        T tmp[] = null;
        if (this.count==this.size) 
        {
            tmp = (T[]) new Object[this.size * 2];
            for (int i=0; i < this.size; i++) 
            {
                tmp[i] = array[i];
            }
            array = tmp;
            this.size = this.size * 2;
        }
    }

    /**
     * fetch the value at index
     * @param index: integer value of the index at which we want the data
     * @return: data at the index location in the array
     */
    public T get(int index) //get value by index
    {
        if (index < count) {
            return this.array[index];
        }
      return null;
    }

    /**
     * count of the total data inserted the array
     * @return: count of the data inserted
     */
    public int size() //size of the dynamic array
    {
      return this.count;
    }

    /**
     * delete the last value of the array
     */
    void delete() //delete the last value in the array
   {
        /** 
         * Check if the count is greater than zero if yes then we remove 
         * the last element and then decrement the count.
         */
        if (this.count > 0) {
            this.array[count - 1] = null;
            this.count--;
        }
   }

    /**
     * delete the value at the location and reorder the array
     * @param loc
     */
   void delete(int loc) //delete element at index "loc"
   {
        /** 
        * Check if the given location or index is not out of bound
        * if not then shift the elements of the array by one, and once
        * you rich the value at the given location remove it from the 
        * array, then decrement the count.
        */
        if (loc < this.count && this.count > 0) {
            for (int i=loc; i < this.count-1; i++) {
                this.array[i] = this.array[i+1];
            }
            this.array[this.count-1] = null;
            this.count--;
        }
        return;
   }

    /**
     * provide boolean value whether the array is empty or not
     * @return: if empty then true; else false
     */
    boolean is_empty(){
        if (this.count==0)
            return true;
        return false;
    }

    /**
     * this method is iterator method in order to support foreach or short for loop form
     * @return: return object of the Iterator class
     */
    public Iterator<T> iterator(){
      return new DynamicArrayIterator<T>(this);
    }

    //Note: You will need to implement an iterator class using java.util.Iterator
    //      interface

    //TO DO: implement iterator here
    private class DynamicArrayIterator<T> implements Iterator<T> {
        DynamicArray<T> ts;
        int currPos;
        public DynamicArrayIterator(DynamicArray<T> ts) {
            this.ts = ts;
            this.currPos = 0;
        }

        @Override
        public boolean hasNext() {
            return this.currPos < this.ts.count;
        }

        @Override
        public T next() {
            T obj = this.ts.array[this.currPos];
            this.currPos++;
            return obj;
        }
    }
}

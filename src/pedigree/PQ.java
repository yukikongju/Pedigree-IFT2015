
package pedigree;

import java.util.ArrayList;


public class PQ <T extends Comparable<T>>{
    
    // Right now: ordinary PQ O(log n) insert and delete
    // TODO: priority queue with quick find 
    
    private ArrayList<T> heap; // heap[i] = ith element on pq
    private int size; // number of elements on pq
    
//    public PQ(int capacity){
//        heap = new ArrayList<>(capacity);
//    }

    public PQ() {
        heap = new ArrayList<>();
    }
    
    public boolean isEmpty(){
        return size() == 0;
    }
    
    public int size(){
        return heap.size();
    }
    
    private void swim(int k){ // to test
        int parent = (k-1)/2;
        while(k > 0 && less(k, parent)){
            swap(parent, k);
            k = parent;
            parent = (k - 1)/2;
        }
    }
    
    private void sink(int k){ // to test
        int heapSize = size();
        while(true){
            int left = 2 * k + 1; // Left  node
            int right = 2 * k + 2; // Right node
           
            int smallest = left; // Assume left is the smallest node of the two children
            if (right < heapSize && less(right, left)) smallest = right; // assign right child to smallest if it is smaller than left 

            if (left >= heapSize || less(k, smallest)) break; //  stop if we cannot sink k anymore

            swap(smallest, k);
            k = smallest;
        }
    }
    
    private boolean less(int i, int j){
        T element1 = heap.get(i);
        T element2 = heap.get(j);
        return element1.compareTo(element2) <= 0;
    }

    private void swap(int i, int j) { // to test
        T element1 = heap.get(i);
        T element2 = heap.get(i);
        heap.set(i, element1);
        heap.set(j, element2);

    }
    
    public T deleteMin(){ // to test
        if(isEmpty()) return null;
        
        int indexOfLast = size() - 1;
        T firstElement = heap.get(0);
        
        swap(0, indexOfLast);
        heap.remove(indexOfLast);
        
        if(0 == indexOfLast) return firstElement; // 
        
        // find the position of the initial last element
        T elem = heap.get(0);
        sink(0); // try sinking the item
        if(heap.get(0).equals(elem)) swim(0); // try swimming if sinking did not work
        
        return firstElement;
    }
    
   public void insert(T elem){ // to test
       if(elem == null) throw new IllegalArgumentException();
       heap.add(elem);
       int indexOfLast = size() -1;
       swim(indexOfLast);
   }

    @Override
    public String toString() {
        return heap.toString();
    }
    
   
    
}

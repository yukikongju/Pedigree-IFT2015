
package pedigree;

import java.util.Arrays;

public class PQ <T extends Comparable<T>>{
    
    T[] heap;
    int size = 0;
    public final int STARTING_CAPACITY = 2;

    public PQ() {
        heap = (T[]) new Comparable[STARTING_CAPACITY];
    }

    public int size(){
        return size;
    }
    
    /**
    * Copies old array in new Array with new capacity
    * 
    * @param newCapacity capacity the array should be resized to
    */
    private void resize(int newCapacity) {
        T[] temp = (T[]) new Comparable[newCapacity];
        for(int i = 0; i < size; i++){
            temp[i] = heap[i];
        }
        heap = temp;
    }
     
    public boolean isEmpty() {
        return size == 0;
    }
   
        private boolean more(int i, int j) {
       return heap[i].compareTo(heap[j]) > 0;
    }
    
    private boolean less(int i, int j){
        return heap[i].compareTo(heap[j]) < 0;
    }
    
    /**
     * Swap elements at index i and j
     * @param i
     * @param j 
     */
    private void swap(int i, int j) {
        T temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    @Override
    public String toString() {
        return Arrays.toString(heap);
    }
    
    public void insert(T elem){
        if(size() == heap.length){
            resize(heap.length * 2);
        }
        heap[size] = elem;
        swim(size);
        size++;
    }

    private void swim(int index) {
        int parent = getParentIndex(index);
        while(index >0 && less(index, parent)){
            swap(parent, index);
            index = parent;
            parent = getParentIndex(index);
        }
    }
    
    private int getParentIndex(int index){
        return (index -1)/2;
    }
    
}

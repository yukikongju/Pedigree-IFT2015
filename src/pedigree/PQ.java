
package pedigree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PQ <T extends Comparable<T>>{
    
    // Right now: ordinary PQ O(log n) insert and delete
    // TODO: priority queue with quick find 
    
    T[] heap;
    int size = 0; // length the user see
    public final int STARTING_CAPACITY = 2;
    
    public PQ(){
        heap =  (T[]) new Comparable[STARTING_CAPACITY];
    }
    
    public int size(){
        return size;
    }
    
    public boolean isEmpty(){
        return size() == 0;
    }
    
    public void insert(T elem){
        if(size() == heap.length){
            resize(heap.length * 2);
        }
        
        heap[size++] = elem;
        swim(size);
        
//        swim(elem, size);
//        size++;
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

//    private void swim(T elem, int index) {
//        int p = getIndexParent(index);
//        while(p != 0 && more(p, index)){
//            heap[index] = heap[p];
//            index = p;
//            p = getIndexParent(index);
//        }
//        heap[index] = elem;
//    }
    
    private void swim(int index){
        int p = getIndexParent(index);
        while(p != 0 && more(p, index)){
            swap(p, index);
            index = p;
            p = getIndexParent(index);
        }
    }

    private void sink(T elem, int index){
        int c = getIndexMinChild(index);
    }
    
    private int getIndexParent(int index) {
        return (index - 1)/2;
    }

    private boolean more(int i, int j) {
       return heap[i].compareTo(heap[j]) > 0;
    }
    
    private boolean less(int i, int j){
        return heap[i].compareTo(heap[j]) < 0;
    }

    private int getIndexMinChild(int index) {
//        int left = index *2 + 1;
//        int right = (index * 2) + 2;
//        int smallest = left;
//        if(smallest > size -1) return 0; // if child doesn't exist, return 0
//        if(right <= size -1 && less(right, smallest)) smallest = right; // check if right is smaller than left
//        return smallest;

int j = 0; //si aucun enfant retourner 0
        int c1 = (index * 2) + 1;
        int c2 = (index * 2) + 2; //calculer la position des enfants de idx
        if (c1 <= size - 1) { //si enfant c1 existe
            j = c1;
            if (c2 <= size - 1 && heap[c2].compareTo(heap[c1]) < 0) { //si enfant 2 existe et est plus petit que enfant 1
                j = c2;
            }
        }
        return j;
    }

    public T deleteMin(){ // TODO
        return null;
    }

    @Override
    public String toString() {
        return Arrays.toString(heap);
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
    
}

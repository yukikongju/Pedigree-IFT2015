package pedigree;

import java.util.Arrays;
import java.util.Random;

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
   
    protected boolean more(int i, int j) { // changed encapsulation to protected to allow Population to overrider compareTo
       return heap[i].compareTo(heap[j]) > 0;
    }
    
    protected boolean less(int i, int j){ // changed encapsulation to protected to allow Population to overrider compareTo
        return heap[i].compareTo(heap[j]) < 0;
    }
    
    protected void swap(int i, int j) {
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
        swim(size++);
    }

    // O(log n)
    protected void swim(int index) {
        int parent = getParentIndex(index);
        while(index > 0 && less(index, parent)){
            swap(parent, index);
            index = parent;
            parent = getParentIndex(index);
        }
    }
    
    protected int getParentIndex(int index){
        return (index - 1)/2;
    }
    
    // O(log n)
    protected void sink(int index){
        int child = getIndexMinChild(index);
        while(child != 0 && more(index, child)){
            swap(child, index);
            index = child;
            child = getIndexMinChild(index);
        }
    }

    protected int getIndexMinChild(int index) { // FIXED
        int left = index * 2 + 1;
        int right = (index * 2) + 2;
        int smallest = left;
        if(left >= size) return 0; // VERIFY: if child doesn't exist, return 0
        if(right < size() && less(right, smallest)) smallest = right; // check if right is smaller than left
        return smallest;
    }
    
    protected T deleteMin(){
        if(isEmpty()) return null;
        T topElement = heap[0];
        int indexOfLastElement = size() -1 ;
        if(size() > 0){ // TOFIX: redundant
            swap(indexOfLastElement, 0);
            heap[indexOfLastElement] = null;
            size--; 
            if(size == heap.length/4){ // resizing dynamic array when quarter full
                resize(heap.length/2);
            }
            sink(0);
        }
        return topElement;
    }
    
    // linear search O(n) -> not used
    public boolean contains(T elem){ // VERIFY: should we create population class for sim comparison instead?
        for(int i = 0; i< heap.length; i++){
            if(heap[i] == elem) return true;
        }
        return false;
    }
    
    public T getRandomElement(Random random){ 
        if(isEmpty()) throw new IllegalArgumentException("HEAP IS EMPTY"); // VERIFY: should never go in this because we verify that heap not empty before calling
        int index = random.nextInt(size()); 
        return heap[index];
    }
    
}

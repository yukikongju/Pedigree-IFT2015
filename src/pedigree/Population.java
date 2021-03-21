package pedigree;

import java.util.Arrays;

public class Population <T extends Comparable<T>> { // <T extends Comparable<T>> extends PQ<T>
    
    // maxHeap that compare Sim BirthTime
    
    T[] heap;
    int size = 0;
    public final int STARTING_CAPACITY = 2;
    private final SimBirthComparator comparator = new SimBirthComparator();
    
    public Population() {
        heap = (T[]) new Comparable[STARTING_CAPACITY];
    }
    
    public boolean isOnlyFondators(){ // TODO
        // check if population is only made of fondators
        
        return true;
    }

    public void insert(T elem){
        if(size() == heap.length){
            resize(heap.length * 2);
        }
        heap[size] = elem;
        swim(size++);
    }

    private void swim(int index) {
        int parent = getParentIndex(index);
        while(index > 0 && more(index, parent)){
            swap(parent, index);
            index = parent;
            parent = getParentIndex(index);
        }
    }

    private void sink(int index) {
        int child = getIndexMaxChild(index);
        while(child != 0 && less(index, child)){
            swap(child, index);
            index = child;
            child = getIndexMaxChild(index);
        }
    }
    
    private int getIndexMaxChild(int index) { // FIXED
        int left = index * 2 + 1;
        int right = (index * 2) + 2;
        int biggest = left;
        if(left >= size) return 0; // VERIFY: if child doesn't exist, return 0
        if(right < size() && more(right, biggest)) biggest = right; // check if right is smaller than left
        return biggest;
    }
    
    public T deleteMax(){ // TOCHANGE
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
    
    private int getParentIndex(int index){
        return (index - 1)/2;
    }
    
    private void swap(int i, int j) {
        T temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }
    
    private boolean more(int i, int j) { 
        return comparator.compare((Sim) heap[i], (Sim) heap[j]) > 0;
    }

    private boolean less(int i, int j) { 
        return comparator.compare((Sim) heap[i], (Sim) heap[j]) < 0;
    }
    
    @Override
    public String toString() {
        return Arrays.toString(heap);
    }
    
    // linear search O(n) -> not used
    public boolean contains(T elem){ // VERIFY: should we create population class for sim comparison instead?
        for(int i = 0; i< heap.length; i++){
            if(heap[i] == elem) return true;
            
        }
        return false;
    }
    
}

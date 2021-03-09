
package pedigree;

import java.util.Arrays;

public class PQ <T extends Comparable<T>>{
    
    T[] heap;
    int size;
    public final int STARTING_CAPACITY = 2;

    public PQ() {
        heap = (T[]) new Comparable[STARTING_CAPACITY];
        size = 1;
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
     
    void insert(T item) {
        if(size() == heap.length){
            resize(heap.length * 2);
        }
        swim(item, size);
        size++;
    }

    private void swim(T elem, int index) {
        int p = getIndexParent(index);
        while (p != 0 && heap[p].compareTo(elem) > 0) {// //tant que parent plus grand a cet indice, aller voir parent de parent
            heap[index] = heap[p];
            index = p;
            p = getIndexParent(index);
        }
        heap[index] = elem;
    }

    private void sink(T elem, int index) {
        int c = getIndexMinChild(index);
        while (c != 0 && heap[c].compareTo(elem) < 0) {
            heap[index] = heap[c];
            index = c;
            c = getIndexMinChild(c); // si pas de minChild retourne 0 et arret la boucle
        }
        heap[index] = elem;
    }

    public T deleteMin() { // REDO
        T r = heap[1];
        if (size > 1) {
            T v = heap[size - 1];
            heap[size - 1] = null;
            size--;
            sink(v, 1);
        }
        return r;
    }

    private int getIndexParent(int idx) {
        return Math.floorDiv(idx, 2);
    }

    /**
     * retourne l'indice de l'enfant le plus petit ou 0 s'il n'y a pas d'enfant.
     *
     * @param index
     * @return
     */
    private int getIndexMinChild(int index) {
        int left = index * 2;
        int right = (index * 2) + 1;
        int smallest = left;
        if(smallest > size -1) return 0; // if child doesn't exist, return 0
        if(right <= size -1 && less(right, smallest)) smallest = right; // check if right is smaller than left
        return smallest;
//        int j = 0; //si aucun enfant retourner 0
//        int c1 = (index * 2) + 1;
//        int c2 = (index * 2) + 2; //calculer la position des enfants de idx
//        if (c1 <= size - 1) { //si enfant c1 existe
//            j = c1;
//            if (c2 <= size - 1 && heap[c2].compareTo(heap[c1]) < 0) { //si enfant 2 existe et est plus petit que enfant 1
//                j = c2;
//            }
//        }
//        return j;
    }
   
    @Override
    public String toString() {
        return Arrays.toString(heap);
    }

    public boolean isEmpty() {
        return size == 1;
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
    
}

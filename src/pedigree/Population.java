package pedigree;

public class Population extends PQ { // <T extends Comparable<T>> extends PQ<T>
    
    private final SimBirthComparator comparator = new SimBirthComparator();
    
    public boolean contains(Sim sim){ // TODO
        return true;
    }
    
    public boolean isOnlyFondators(){ // TODO
        // check if population is only made of fondators
        
        return true;
    }

    @Override
    protected boolean more(int i, int j) { // VERIFY
        return comparator.compare((Sim) heap[i], (Sim) heap[j]) > 0;
    }

    @Override
    protected boolean less(int i, int j) { //VERIFY
        return comparator.compare((Sim) heap[i], (Sim) heap[j]) < 0;
    }
    
}

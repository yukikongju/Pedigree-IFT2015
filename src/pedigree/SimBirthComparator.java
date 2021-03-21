package pedigree;

import java.util.Comparator;

public class SimBirthComparator implements Comparator<Sim> {

    @Override
    public int compare(Sim t, Sim t1) {
        // see: https://www.baeldung.com/java-comparator-comparable
        return Double.compare(t.getBirthTime(), t1.getBirthTime());
    }

}

package pedigree;

import java.util.HashSet;
import java.util.TreeMap;

public class Coalescing {

    private TreeMap<Double, Integer> PA; // coalescing points for males ancestors
    private TreeMap<Double, Integer> MA; // coalescing points for females ancestors

    public void coalesce(PQ<Sim> survivors) { 
        // TreeMap ensure that the order is kept vs hashmap that doesn't
        PA = new TreeMap<>();
        MA = new TreeMap<>();

        // keeping track of ancestors identification
        HashSet<Integer> identification = new HashSet<>();

        Population<Sim> males = new Population<>();
        Population<Sim> females = new Population<>();

        // split population by male and female
        while (!survivors.isEmpty()) {
            Sim sim = survivors.deleteMin();
            if (sim.isFemale()) {
                females.insert(sim);
            } else { // sim is male
                males.insert(sim);
            }
        }

        // coalescence pour les aieules
        while (!females.isEmpty()) { 
            Sim sim = females.deleteMax(); // enlever le plus jeune
            Sim mother = sim.getMother();
            if (sim.isFounder() || identification.contains(mother.getID())) { 
                MA.put(sim.getBirthTime(), females.size());
                if (sim.isFounder()) break;
            } else {
                females.insert(mother);
                identification.add(mother.getID());
            }

        }
        
        // coalescence pour les aieux
        while (!males.isEmpty()) { 
            Sim sim = males.deleteMax(); // enlever le plus jeune
            Sim father = sim.getFather();
            if (sim.isFounder() || identification.contains(father.getID())) { 
                PA.put(sim.getBirthTime(), males.size());
                if (sim.isFounder()) break;
            } else {
                males.insert(father);
                identification.add(father.getID());
            }

        }

    }

    public TreeMap<Double, Integer> getAieules() {
        return MA;
    }

    public TreeMap<Double, Integer> getAieux() {
        return PA;
    }

}

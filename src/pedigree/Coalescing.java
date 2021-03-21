package pedigree;

import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeMap;

public class Coalescing {

    private TreeMap<Double, Integer> PA; // coalescing points for males ancestors
    private TreeMap<Double, Integer> MA; // coalescing points for females ancestors

    public void coalesce(PQ<Sim> survivors) { // TO FIX
        // VERIFY: how can we ensure that lookups are O(1) and not O(n)
        PA = new TreeMap<>();
        MA = new TreeMap<>();

        // HashMap females and males
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

        // aieules
        while (!females.isEmpty()) { // !females.isEmpty() // && !females.isOnlyFondators()
            Sim sim = females.deleteMax(); // enlever le plus jeune
            Sim mother = sim.getMother();
            if (mother.isFounder() || identification.contains(mother.getID())) { // !females.contains(mother)
                MA.put(sim.getBirthTime(), females.size());
                if (mother.isFounder()) break;
            } else {
                females.insert(mother);
                identification.add(mother.getID());
            }

        }
        
        // aieux
        while (!males.isEmpty()) { // !females.isEmpty() // && !females.isOnlyFondators()
            Sim sim = males.deleteMax(); // enlever le plus jeune
            Sim father = sim.getFather();
            if (father.isFounder() || identification.contains(father.getID())) { // !females.contains(mother)
                PA.put(sim.getBirthTime(), males.size());
                if (father.isFounder()) break;
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

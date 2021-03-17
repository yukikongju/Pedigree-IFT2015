package pedigree;

import java.util.HashMap;

public class Coalescing {
    
    private HashMap<Double, Integer> aieux; // coalescing points for males ancestors
    private HashMap<Double, Integer> aieules; // coalescing points for females ancestors
    
    public void coalesce(PQ<Sim> population) { // TO FIX
        // VERIFY: how can we ensure that lookups are O(1) and not O(n)
        aieux = new HashMap<>(); 
        aieules = new HashMap<>(); 

        PQ<Sim> maleAncestors = new PQ<>();
        PQ<Sim> femaleAncestors = new PQ<>();
        
        // begin coalescing
        while(!population.isEmpty()){
            Sim sim = population.deleteMin();
            Sim dad = sim.getFather();
            Sim mom = sim.getMother();
            
            // VERIFY: why would we need sim_id? // why would we need to check if population is only founder if we only need coalescing points
            
            if(!maleAncestors.contains(dad) && dad != null){ 
                maleAncestors.insert(dad);
            } else {
                aieux.put(sim.getBirthTime(), population.size()); 
            }
            
            if(!femaleAncestors.contains(mom) && mom != null){ 
                femaleAncestors.insert(mom);
            } else {
                aieules.put(sim.getBirthTime(), population.size()); 
            }
            
        }
//        System.out.println(aieux.size());
//        System.out.println(aieules.size());
    }

    public HashMap<Double, Integer> getAieules() {
        return aieules;
    }

    public HashMap<Double, Integer> getAieux() {
        return aieux;
    }
    
}

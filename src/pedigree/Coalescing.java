package pedigree;

import java.util.HashMap;

public class Coalescing {
    
    private HashMap<Double, Integer> aieux; 
    private HashMap<Double, Integer> aieules;
        
    public void coalesce(PQ<Sim> population) { // TO FIX
        // VERIFY: how can we ensure that lookups are O(1) and not O(n)
        aieux = new HashMap<>(); 
        aieules = new HashMap<>(); 

        // begin coalescing
        while(!population.isEmpty()){
            Sim sim = population.deleteMin();
            Sim dad = sim.getFather();
            Sim mom = sim.getMother();
            
            // coalescing dad
            if(!aieux.containsKey(dad.getBirthTime())){ // TODO: fix logic
                aieux.put(dad.getBirthTime(), aieux.size());
//                System.out.println(dad + " " + aieux.size());
            }
            
            // coalescing mom
            if(!aieules.containsKey(mom.getBirthTime())){ // TODO: fix logic
                aieules.put(mom.getBirthTime(), aieules.size());
//                System.out.println(mom + " " + aieules.size());
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

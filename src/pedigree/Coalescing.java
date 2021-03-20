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
        
        Population popTest = new Population();
        
        // CHECKK: testing Population PQ
        while(!population.isEmpty()){
            Sim sim = population.deleteMin();
            popTest.insert(sim);
            System.out.println(popTest);
        }

        
        // split population left into males and females
//        while(!population.isEmpty()){
//            Sim sim = population.deleteMin();
//            if(sim.isFemale()){
//                femaleAncestors.insert(sim);
//            }
//            if(sim.isMale()){
//                maleAncestors.insert(sim);
//            }
//        }
        
        // begin coalescing
        
//        do{
//            Sim sim = maleAncestors.deleteMin();
//            Sim father = sim.getFather();
//            if(sim.isFounder()) break;
//            if(father != null && !maleAncestors.contains(father)){
//                maleAncestors.insert(father);
//            } 
//            aieux.put(sim.getBirthTime(), maleAncestors.size());
//        } while(!maleAncestors.isEmpty());
        
        // good stuffs

//        while(population.size() > 1){ //!population.isEmpty() -> pk le prof demande cette implementation
//            Sim sim = population.deleteMin();
//            Sim dad = sim.getFather();
//            Sim mom = sim.getMother();
//            if(sim.isFounder()) break;
//            // VERIFY: why would we need sim_id? // why would we need to check if population is only founder if we only need coalescing points
//            
//            if(!population.contains(dad) && dad != null){ 
//                population.insert(dad);
//            } else { // population contains dad
//                aieux.put(sim.getBirthTime(), population.size());
//                System.out.println("tt " + population.size());
//            }
//            
//            if(!population.contains(mom) && mom != null){ 
//                population.insert(mom);
//            } else {
//                aieules.put(sim.getBirthTime(), population.size()); 
//            }
//        }
            
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

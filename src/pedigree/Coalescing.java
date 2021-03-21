package pedigree;

import java.util.HashMap;

public class Coalescing {
    
    private HashMap<Double, Integer> aieux; // coalescing points for males ancestors
    private HashMap<Double, Integer> aieules; // coalescing points for females ancestors
    
    public void coalesce(PQ<Sim> survivors) { // TO FIX
        // VERIFY: how can we ensure that lookups are O(1) and not O(n)
        aieux = new HashMap<>(); 
        aieules = new HashMap<>(); 

//        PQ<Sim> maleAncestors = new PQ<>();
//        PQ<Sim> femaleAncestors = new PQ<>();
        
        Population<Sim> males = new Population<>();
        Population<Sim> females = new Population<>();
        
//        Population population = new Population();
        
        // split population by male and female
        while(!survivors.isEmpty()){
            Sim sim = survivors.deleteMin();
            if(sim.isFemale()){
                females.insert(sim);
//                System.out.println(females);
            } else { // sim is male
                males.insert(sim);
            }
        }
        
        while(!females.isEmpty()){
            females.deleteMax();
//            System.out.println(females);
        }
        
        // aieules
//        while(!females.isEmpty()){
////            System.out.println(females.deleteMax());
//            females.deleteMax();
//            System.out.println(females);
//        }
        
        // aieules
//        while(!females.isEmpty()){
//            Sim sim = females.deleteMax(); // enlever le plus jeune
//            Sim mother = sim.getMother();
//            if(sim.isFounder()) break;
//            if(mother != null && !females.contains(mother)){
//                females.insert(mother);
//            } else {
//                aieules.put(sim.getBirthTime(), females.size());
//            }
//        }
        
        
        
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

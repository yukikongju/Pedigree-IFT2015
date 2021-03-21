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
        while(!survivors.isEmpty()){
            Sim sim = survivors.deleteMin();
            if(sim.isFemale()){
                females.insert(sim);
            } else { // sim is male
                males.insert(sim);
            }
        }
        
        System.out.println(females.size());
        System.out.println(males.size());
        
        // aieules
        while(!females.isEmpty()){ // !females.isEmpty() // && !females.isOnlyFondators()
            Sim sim = females.deleteMax(); // enlever le plus jeune
            Sim mother = sim.getMother();
            if(sim.isFounder()) break;
            if(mother != null && !identification.contains(sim.getID())){ // !females.contains(mother)
                females.insert(mother);
                identification.add(sim.getID());
            } else {
                MA.put(sim.getBirthTime(), females.size());
//                System.out.println(sim.getBirthTime()+ "," +  females.size());
            }
            
        }
        
        // aieux
//        while(!males.isEmpty()){
//            Sim sim = males.deleteMax(); // enlever le plus jeune
//            Sim father = sim.getFather();
//            if(sim.isFounder()) break;
//            if(father != null && !males.contains(father)){
//                males.insert(father);
//            }
//            aieules.put(sim.getBirthTime(), males.size());
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

    public TreeMap<Double, Integer> getAieules() {
        return MA;
    }

    public TreeMap<Double, Integer> getAieux() {
        return PA;
    }
    
}

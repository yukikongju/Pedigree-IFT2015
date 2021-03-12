package pedigree;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Coalescing {
    
    private HashMap<Double, Integer> aieux; 
    private HashMap<Double, Integer> aieules;
    
    public void generateCSVFile(HashMap<Double, Integer> aieux, HashMap<Double, Integer> aieules) throws IOException{
        String fileName = "test.csv";
        String filePath = new File("").getAbsolutePath();
        filePath = filePath.concat("/data/" + fileName);
        System.out.println(filePath);
        
        File file = new File(filePath);
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write("time, size, sex\n");
        
        // https://stackoverflow.com/questions/1066589/iterate-through-a-hashmap
        
        // add aieules to file
        Iterator iterMoms = aieules.entrySet().iterator();
        while(iterMoms.hasNext()){
            Map.Entry pair = (Map.Entry) iterMoms.next();
            writer.write(pair.getKey() + "," + pair.getValue() + "," + "F\n");
            iterMoms.remove(); // avoids concerrentModificationException
        }
        
        // add aieux to file
        Iterator iterDads = aieux.entrySet().iterator();
        while(iterDads.hasNext()){
            Map.Entry pair = (Map.Entry) iterDads.next();
            writer.write(pair.getKey() + "," + pair.getValue() + "," + "M\n");
            iterDads.remove(); // avoids concerrentModificationException
        }
        
        writer.close();
    }
    
    public void coalesce(PQ<Sim> population) throws IOException { // TO FIX
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
//        generateCSVFile(aieux, aieules);
    }

    public HashMap<Double, Integer> getAieules() {
        return aieules;
    }

    public HashMap<Double, Integer> getAieux() {
        return aieux;
    }
    
}

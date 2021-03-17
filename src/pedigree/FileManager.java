package pedigree;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class FileManager {

    private final String directory; // directory where the files should be saved in

    public FileManager(String directory) {
        this.directory = directory;
    }

    public void generateCSVFileForCoalescingPoints(String fileName, HashMap<Double, Integer> aieux, HashMap<Double, Integer> aieules) throws IOException{
        File file = new File(directory + fileName);
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write("time,size,sex\n");

        // https://stackoverflow.com/questions/1066589/iterate-through-a-hashmap

        // add aieules to file
        Iterator iterMoms = aieules.entrySet().iterator();
        while(iterMoms.hasNext()){
            Map.Entry pair = (Map.Entry) iterMoms.next();
            writer.write(pair.getKey() + "," + pair.getValue() + "," + "F\n");
            iterMoms.remove(); // avoids concurrentModificationException
        }

        // add aieux to file
        Iterator iterDads = aieux.entrySet().iterator();
        while(iterDads.hasNext()){
            Map.Entry pair = (Map.Entry) iterDads.next();
            writer.write(pair.getKey() + "," + pair.getValue() + "," + "M\n");
            iterDads.remove(); // avoids concurrentModificationException
        }

        writer.close();
    }
    
    public void generateCSVFileForPopulationGrowth(String fileName, HashMap<Double, Integer> populationGrowth) throws IOException{
        File file = new File(directory + fileName);
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write("time,size\n");
        
        // add population growth to csv file
        Iterator iterPopulation = populationGrowth.entrySet().iterator();
        while(iterPopulation.hasNext()){
            Map.Entry pair = (Map.Entry) iterPopulation.next();
            writer.write(pair.getKey() + "," + pair.getValue() + "\n");
            iterPopulation.remove(); // avoids concurrentModificationException
        }
        
        writer.close();
    }

}
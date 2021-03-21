package pedigree;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class FileManager {

    private final String directory; // directory where the files should be saved in

    public FileManager(String directory) {
        this.directory = directory;
    }

    public void generateCSVFileForCoalescingPoints(String fileName, TreeMap<Double, Integer> PA, TreeMap<Double, Integer> MA) throws IOException{
        File file = new File(directory + fileName);
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write("time,size,sex\n");

        // add aieules to file
        Iterator iterMoms = MA.entrySet().iterator();
        while(iterMoms.hasNext()){
            Map.Entry pair = (Map.Entry) iterMoms.next();
            writer.write(pair.getKey() + "," + pair.getValue() + "," + "F\n");
            iterMoms.remove(); // avoids concurrentModificationException
        }

        // add aieux to file
        Iterator iterDads = PA.entrySet().iterator();
        while(iterDads.hasNext()){
            Map.Entry pair = (Map.Entry) iterDads.next();
            writer.write(pair.getKey() + "," + pair.getValue() + "," + "M\n");
            iterDads.remove(); // avoids concurrentModificationException
        }

        writer.close();
    }
    
    public void generateCSVFileForPopulationGrowth(String fileName, TreeMap<Double, Integer> populationGrowth) throws IOException{
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

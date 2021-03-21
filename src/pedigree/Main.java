package pedigree;

import java.io.File;
import java.io.IOException;
import java.util.TreeMap;

public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            System.out.println("Invalid numbers of arguments"); // java Simulation <n> <Tmax>
            System.exit(0);
        }
        
        int n = Integer.parseInt(args[0]);
        int Tmax = Integer.parseInt(args[1]);
        // Simulate
        Simulation simulation = new Simulation();
        simulation.simulate(1000, 20000); // n>=1000 ; Tmax>=10n
        PQ<Sim> population = simulation.getPopulation();

        System.out.println("Taille de population: " + population.size());

        // Coalescing
        Coalescing coalescing = new Coalescing();
        coalescing.coalesce(population);

        // Fetch HashMap for "Etude Empirique"
        TreeMap<Double, Integer> populationGrowth = simulation.getPopulationGrowth();
        TreeMap<Double, Integer> aieux = coalescing.getAieux();
        TreeMap<Double, Integer> aieules = coalescing.getAieules();

        System.out.println("Nombre de lignées paternelles: " + aieux.size());
        System.out.println("Nombre de lignées paternelles: " + aieules.size());

        // Generate CSV files from HashMaps
        String downloadPath = new File("").getAbsolutePath().concat("/data/");
        FileManager manager = new FileManager(downloadPath);
        manager.generateCSVFileForCoalescingPoints("coalescing.csv", aieux, aieules);
        manager.generateCSVFileForPopulationGrowth("population.csv", populationGrowth);

        // Generate plot from coalescing: check python script
    }

}

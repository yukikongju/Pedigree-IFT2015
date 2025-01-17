package pedigree;

import java.io.File;
import java.io.IOException;
import java.util.TreeMap;

public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
//        if (args.length != 2) {
//            System.out.println("Invalid numbers of arguments"); // java Simulation <n> <Tmax>
//            System.exit(0);
//        }
//        int n = Integer.parseInt(args[0]);
//        int Tmax = Integer.parseInt(args[1]);
        int n = 1000;
        int Tmax = 20000;
        // Simulate
        Simulation simulation = new Simulation();
        simulation.simulate(n, Tmax); // n>=1000 ; Tmax>=10n
        PQ<Sim> population = simulation.getPopulation();
        int populationSize = population.size();

        // Coalescing
        Coalescing coalescing = new Coalescing();
        coalescing.coalesce(population);

        // Fetch HashMap for "Etude Empirique"
        TreeMap<Double, Integer> populationGrowth = simulation.getPopulationGrowth();
        TreeMap<Double, Integer> aieux = coalescing.getAieux();
        TreeMap<Double, Integer> aieules = coalescing.getAieules();
        
        int aieuxSize = aieux.size();
        int aieulesSize = aieules.size();


        // Generate CSV files from HashMaps
        String downloadPath = new File("").getAbsolutePath().concat("/data/");
        FileManager manager = new FileManager(downloadPath);
        manager.generateCSVFileForPopulationGrowth("population.csv", populationGrowth);
        manager.generateCSVFileForCoalescingPoints("coalescing.csv", aieux, aieules);

        System.out.println("Taille de la Population:" + populationSize);
        System.out.println("Nombre de lignées paternelles: " + aieuxSize);
        System.out.println("Nombre de lignées paternelles: " + aieulesSize);
        
        // Generate plot from coalescing: check python script
    }

}

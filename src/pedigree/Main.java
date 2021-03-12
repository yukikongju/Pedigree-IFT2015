package pedigree;

import java.io.IOException;
import java.util.HashMap;

public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // Simulate
        Simulation simulation = new Simulation();
        simulation.simulate(1000, 2000); // n>=1000 ; Tmax>=10n
        PQ<Sim> population = simulation.getPopulation();
        System.out.println(population.size());
        
        // Coalescing
        Coalescing coalescing = new Coalescing();
        coalescing.coalesce(population);
        
        // Fetch HashMap for "Etude Empirique"
        HashMap<Double, Integer> populationGrowth = simulation.getPopulationGrowth();
        HashMap<Double, Integer> aieux = coalescing.getAieux();
        HashMap<Double, Integer> aieules = coalescing.getAieules();

        
        System.out.println(populationGrowth);

        // Generate CSV files from HashMaps
        
       // Generate plot from coalescing
    }

}

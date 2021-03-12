package pedigree;

import java.io.IOException;

public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // Simulate
        Simulation simulation = new Simulation();
        PQ<Sim> population = simulation.simulate(1000, 2000); // n>=1000 ; Tmax>=10n
        System.out.println(population.size());

        // Coalescing
        Coalescing coalescing = new Coalescing();
        coalescing.coalesce(population);
        
       // Generate plot from coalescing
    }

}

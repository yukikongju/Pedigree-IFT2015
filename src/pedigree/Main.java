package pedigree;

public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Simulate
        Simulation simulation = new Simulation();
        PQ<Sim> population = simulation.simulate(1000, 2000); 
        System.out.println(population.size());

        // Coalescing
        Coalescing coalescing = new Coalescing();
        coalescing.coalesce(population);
        
       // Generate plot from coalescing
    }

}

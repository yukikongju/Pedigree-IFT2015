
package pedigree;

import static java.lang.Math.random;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Random;
import pedigree.Sim.Sex;

public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        // 1. Simulate
        // 2. Coalescing
        // 3. Plot
        
        int n = 5; // nombre d'individus
        double Tmax = 1000; // durée de la simulation
        
        simulate(n, Tmax); // run simulation
        
    }

    private static void simulate(int n, double Tmax) { // TODO: create class?
        // TODO: test the simulation
        PQ<Sim> population = new PQ();
        PQ<Event> eventQ = new PQ(); // TODO: do we have to implement our own PQ?
        AgeModel ageModel = new AgeModel();
        Random random = new Random();
        // Generate First Gen of sims
        for(int i=0; i<n; i++){
            Sim fondateur = new Sim(generateSex()); 
            double deathTime = ageModel.randomAge(random);
//            System.out.println(deathTime);
            fondateur.setDeathTime(deathTime);
            Event E = new Birth(fondateur, 0); // la naissance des fondateurs se fait au temps 0
            eventQ.insert(E);
//            System.out.println(E);
        }
        // Begin Simulation
        while(!eventQ.isEmpty()){
            Event E = eventQ.deleteMin();
            if(E.getScheduledTime() > Tmax) break; // arrêter à Tmax 
            if(E.getSim().getDeathTime() > E.getScheduledTime()){ // si le sim n'est pas mort
                E.simulate();
            }
        }
        
    }

    /**
     * @Sex: the Sex of the sim (0 if woman, 1 if male)
     **/
    private static Sex generateSex() { // TODO: put generateSex() inside Sim class?
        Random random = new Random();
        int temp = random.nextInt(2); // generate 0 or 1
        if(temp == 0){
            return Sim.Sex.F;
        } 
        return Sim.Sex.M;
    }
    
    
}

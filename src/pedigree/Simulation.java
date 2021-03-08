/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pedigree;

import java.util.HashMap;
import java.util.Random;
import pedigree.Sim.Sex;

/**
 *
 * @author emuli
 */
public class Simulation {
    
    Random random; 
    
    PQ<Event> eventQ;
    AgeModel ageModel;

    //    add ancestors males and females to a hashmap to facilitate mating 
    //    HashMap<Sim> aieux;
    //    HashMap<Sim> aieules;
    //    HashMap<Sim> population;

    public Simulation() {
        eventQ = new PQ<>();
        ageModel = new AgeModel();
        random = new Random();
    }

    public void simulate(int n, double Tmax){
        // generate first generation
        for(int i = 0; i<n; i++){
            Sim fondateur = new Sim(null, null, 0); // founder Sim 
            Event E = new Event(fondateur, 0, Event.EventType.BIRTH);
            System.out.println(fondateur);
            eventQ.insert(E);
        }
        
        // Begin simulation
        while(!eventQ.isEmpty()){
            Event E = (Event) eventQ.deleteMin();
            if(E.getScheduledTime() > Tmax) break; // arrêter à Tmax 
            switch (E.getEventType()) { // NOTE: eventType should never be null, so we don't have to check if it's null
                case BIRTH:
                    birth(E);
                    break;
                case DEATH:
                    death(E);
                    break;
                case REPRODUCTION:
                    reprodution(E);
                    break;
                default:
                    break;
            }
            
//            System.out.println("YEAR : + " + E.getScheduledTime() + " EVENT TYPE " + E.getEventType().toString());
            
        }
    }
    
//    private Sim.Sex generateSex(Random random){
//       int temp = random.nextInt(2); // generate 0 or 1
//       return Sim.Sex.values()[temp];
//    }

    private void birth(Event E) {
//       double lifeLength = ageModel.randomAge(random);
    }

    private void death(Event E) {
    }

    private void reprodution(Event E) {
    }
}

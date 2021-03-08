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

    // add ancestors males and females to a hashmap to facilitate mating 
//    HashMap<Sim> aieux;
//    HashMap<Sim> aieules;

    public Simulation() {
        eventQ = new PQ<>();
        ageModel = new AgeModel();
        random = new Random();
    }

    public void simulate(int n, double Tmax){
        // generate first generation
        for(int i = 0; i<n; i++){
            Sim fondateur = new Sim(generateSex(random));
            Event E = new Event(fondateur, 0, Event.EventType.BIRTH);
            eventQ.insert(E);
        }
        
        // Begin simulation
        while(!eventQ.isEmpty()){
            Event E = (Event) eventQ.deleteMin();
            if(E.getScheduledTime() > Tmax) break; // arrêter à Tmax 
            //            if(E.getSim().getDeathTime() > E.getScheduledTime()){ // si le sim n'est pas mort} // no need to check
            switch (E.getEventType()) { // NOTE: eventType shoudl never be null, so we don't have to check if it's null
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
    
    private Sim.Sex generateSex(Random random){
       int temp = random.nextInt(2); // generate 0 or 1
       return Sim.Sex.values()[temp];
    }

    private void birth(Event E) {
    }

    private void death(Event E) {
    }

    private void reprodution(Event E) {
    }
}

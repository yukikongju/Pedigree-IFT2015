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
    }
    
    private Sim.Sex generateSex(Random random){
       int temp = random.nextInt(2); // generate 0 or 1
       return Sim.Sex.values()[temp];
    }
}

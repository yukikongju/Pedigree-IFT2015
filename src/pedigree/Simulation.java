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
    
   public final Random RND; 
   public final double REPRODUCTION_RATE;
    
    PQ<Event> eventQ;
    AgeModel ageModel;
    
    //    add ancestors males and females to a hashmap to facilitate mating 
    //    HashMap<Sim> aieux;
    //    HashMap<Sim> aieules;
    //    HashMap<Sim> population;

    public Simulation() {
        eventQ = new PQ<>();
        ageModel = new AgeModel();
        RND = new Random();
        REPRODUCTION_RATE = 2 / ageModel.expectedParenthoodSpan(Sim.MIN_MATING_AGE_F, Sim.MAX_MATING_AGE_F);
    }

    public void simulate(int n, double Tmax){
        // generate first generation
        for(int i = 0; i<n; i++){
            Sim fondateur = new Sim(generateSex(RND)); // TODO: refractor generateSex in SexModel or inside Sim class?
            Event E = new Event(fondateur, 0, Event.EventType.BIRTH);
            eventQ.insert(E);
        }
        
        // Begin simulation
        while(!eventQ.isEmpty()){
            Event E = (Event) eventQ.deleteMin();
            if(E.getScheduledTime() > Tmax) break; // arrêter à Tmax 
            if (E.getSim().getDeathTime() >= E.getScheduledTime()){ // NOTE: we don't want a strict inequality bc we won't be able to simulate death
                switch (E.getEventType()) { // NOTE: eventType should never be null, so we don't have to check if it's null
                    case BIRTH:
                        birth(E);
                        break;
                    case DEATH: // PROBLEME: on ne rentre jamais dans cette boucle (i think it's bc of the if statement)
                        death(E);
                        break;
                    case REPRODUCTION:
                        reprodution(E);
                        break;
                    default:
                        break;
                }
            }
            System.out.println("YEAR : + " + E.getScheduledTime() + " EVENT TYPE " + E.getEventType().toString());
            
        }
    }
    
    private Sim.Sex generateSex(Random random){
       int temp = random.nextInt(2); // generate 0 or 1
       return Sim.Sex.values()[temp];
    }

    private void birth(Event E) {
       // scheduling death
       double lifeLength = ageModel.randomAge(RND); // lifespan of a Sim
       E.getSim().setDeathTime(E.getScheduledTime() + lifeLength);
       Event death = new Event(E.getSim(), E.getScheduledTime() + lifeLength, Event.EventType.DEATH);
       eventQ.insert(death); 
       
       // scheduling reproduction
       if (E.getSim().getSex() == Sim.Sex.F){ // TODO: schedule reproduction if sim is a female (how many children does the woman birth)
//           double ageOfReproduction = AgeModel.randomWaitingTime(RND, REPRODUCTION_RATE);
//           System.out.println(ageOfReproduction);
       }
    }

    private void death(Event E) {
        // TODO: remove sim from population and add it to dead-people
        System.out.println("dead");
    }

    private void reprodution(Event E) {
        // TODO: 
    }
}

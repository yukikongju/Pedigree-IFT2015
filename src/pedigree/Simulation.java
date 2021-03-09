package pedigree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import pedigree.Sim.Sex;

public class Simulation {
    
   public final Random RND; 
   public final double REPRODUCTION_RATE;
    
    private PQ<Event> eventQ; // PQ<Event> is sorted cronologically by death 
    private PQ<Sim> population; // PQ<Sim> is sorted bronologically by death
    private AgeModel ageModel;
    
    private ArrayList<Sim> males; // TODO: change Data Structures?
    
    //    add ancestors males and females to a hashmap to facilitate mating 
    //    HashMap<Sim> aieux;
    //    HashMap<Sim> aieules;
    //    HashMap<Sim> population;

    public Simulation() {
        eventQ = new PQ<>();
        population = new PQ<>();
        ageModel = new AgeModel();
        males = new ArrayList<>();
        RND = new Random(); // TODO: Fix Reproduction rate
//        REPRODUCTION_RATE = 2 / ageModel.expectedParenthoodSpan(Sim.MIN_MATING_AGE_F, Sim.MAX_MATING_AGE_F);
        REPRODUCTION_RATE = ageModel.expectedParenthoodSpan(Sim.MIN_MATING_AGE_F, Sim.MAX_MATING_AGE_F);
//        System.out.println(REPRODUCTION_RATE);
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
            if (E.getSim().getDeathTime() >= E.getScheduledTime()){ // FIXED: we don't want a strict inequality bc we won't be able to simulate death
                switch (E.getEventType()) { // NOTE: eventType should never be null, so we don't have to check if it's null
                    case BIRTH:
                        birth(E);
                        break;
                    case DEATH: 
                        death(E);
                        break;
                    case REPRODUCTION:
                        reproduction(E);
                        break;
                    default:
                        break;
                }
            }
//            System.out.println("YEAR : + " + E.getScheduledTime() + " EVENT TYPE " + E.getEventType().toString());
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
       
//        System.out.println(E.getSim());
       
       // scheduling reproduction
       if (E.getSim().getSex() == Sim.Sex.F){ // TODO: schedule reproduction if sim is a female (how many children does the woman birth)
//           double ageOfReproduction = AgeModel.randomWaitingTime(RND, REPRODUCTION_RATE); // WRONG OUTPUT???
           double ageOfReproduction = 25; // TO CHANGE: calculate number of offspring and random ageOfReproduction
           Event reproduction = new Event(E.getSim(), E.getScheduledTime() + ageOfReproduction,
                   Event.EventType.REPRODUCTION);
           eventQ.insert(reproduction);
       }
       
       // adding sim to mating pool if male
       if(E.getSim().getSex() == Sim.Sex.M){
           males.add(E.getSim());
       }
       
       // adding Sim to population active
       population.insert(E.getSim());
    }

    private void death(Event E) {
        population.deleteMin();         // remove sim from population active
    }

    private void reproduction(Event E) { // TODO: schedule bebe every time mom gets pregnant?
        Sim mom = E.getSim();
        double birthdate = E.getScheduledTime();
        if(mom.isMatingAge(birthdate)){
            Sim dad = findFather(birthdate, mom); 
//            Sim dad = new Sim(mom.getMother(), mom.getFather(), mom.getBirthTime(), generateSex(RND)); //DUMMY
//            dad.setDeathTime(mom.getDeathTime()); // DUMMY
            Sim baby = new Sim(mom, dad, E.getScheduledTime(), generateSex(RND));
            Event naissance = new Event(baby, E.getScheduledTime(), Event.EventType.BIRTH);
            eventQ.insert(naissance);
            dad.setMate(mom);
            mom.setMate(dad);
        }
    }

    private Sim findFather(double time, Sim mom) {
        Sim father = null;
        if(!mom.isInARelationship(time) || RND.nextDouble()> Sim.FIDELITY){
            do{
                Sim potentialMate = getRandomMate(); 
                if(potentialMate.getSex() != mom.getSex() && potentialMate.isMatingAge(time)){
                    if(mom.isInARelationship(time) || !potentialMate.isInARelationship(time) || RND.nextDouble() > Sim.FIDELITY){
                        father = potentialMate;
                    }
                }
            } while(father == null);
        } else{
            father = mom.getMate();
        }
        return father;
    }

    private Sim getRandomMate() { // PROBLEM 1: we get caucht up in an infinite loop when there is no males in the population
        if(males.isEmpty()) System.out.println("NO MALES"); // PROBLEM 2: we still get caught up in an infinite loop from time to time
        int index = RND.nextInt(males.size()); 
//        System.out.println(index);
//        System.out.println("num males " + males.size());
        return (Sim) males.get(index);
    }
}

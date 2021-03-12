package pedigree;

import java.util.HashMap;
import java.util.Random;

public class Simulation {
    
    public final Random RND; 
    public final double REPRODUCTION_RATE;
    
    public static final double EPSILUM_HUNDRED_YEAR = 0.1; // error we are willing to accept when comparing time to hundred year
    
    private PQ<Event> eventQ; // PQ<Event> is sorted chronologically by death 
    private PQ<Sim> population; // PQ<Sim> is sorted chronologically by death
    private final AgeModel ageModel;
    
    private HashMap<Double, Integer> populationGrowth;
    
    public Simulation() { 
        
        ageModel = new AgeModel();
        RND = new Random();
        REPRODUCTION_RATE = 2 / ageModel.expectedParenthoodSpan(Sim.MIN_MATING_AGE_F, Sim.MAX_MATING_AGE_F); // probabilité d'avoir un bébé à chaque année
    }

    public void simulate(int n, double Tmax){
        // initialized PQ and hashMap inside simulate() instead of constructor in case we want to perform several simulations
        eventQ = new PQ<>();
        population = new PQ<>();
        populationGrowth = new HashMap<>();
        
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
       double lifeLength = generateLifeLength(); // lifespan of a Sim
       E.getSim().setDeathTime(E.getScheduledTime() + lifeLength); // TO FIX? should we set death to global deathtime or relative to sim
       Event death = new Event(E.getSim(), E.getScheduledTime() + lifeLength, Event.EventType.DEATH);
       eventQ.insert(death); 
       
       // scheduling reproduction
       if (E.getSim().isFemale()){
        Event reproduction = new Event(E.getSim(), E.getScheduledTime() + Sim.MIN_MATING_AGE_F + generateRandomWaitingTime(),
                Event.EventType.REPRODUCTION); // PROBLEM: HALT
//           System.out.println(E.getScheduledTime() + waitingTime);
        eventQ.insert(reproduction);
       }
       
       // adding Sim to population active
       population.insert(E.getSim());
       
       // add checkpoints every 100 years for population growth
       if(E.getScheduledTime() % 100 < EPSILUM_HUNDRED_YEAR){ // FIXED: bc time is a double, each year has to fall into an error rate
           populationGrowth.put(E.getScheduledTime(), population.size());
       }
    }

    private void death(Event E) {
        Sim sim =  population.deleteMin();         // remove sim from population active
    }

    private void reproduction(Event E) { 
        Sim mom = E.getSim();
        double birthdate = E.getScheduledTime();
        if(mom.isMatingAge(birthdate) && !population.isEmpty()){ // FIXED: doesn't try to find mate if there is no males left 
            Sim dad = findFather(birthdate, mom); 
            Sim baby = new Sim(mom, dad, birthdate, generateSex(RND));
            Event naissance = new Event(baby, E.getScheduledTime(), Event.EventType.BIRTH);
            eventQ.insert(naissance);
            dad.setMate(mom);
            mom.setMate(dad);
            
            // Schedule next reproduction
            Event reproduction = new Event(E.getSim(), E.getScheduledTime() + generateRandomWaitingTime(),
                Event.EventType.REPRODUCTION); 
            eventQ.insert(reproduction);
        }
    }
    
    private double generateRandomWaitingTime(){
        return AgeModel.randomWaitingTime(RND, REPRODUCTION_RATE);
    }
    
    private double generateLifeLength(){
        return ageModel.randomAge(RND); // generate lifespan of a Sim
    }
    
    private Sim findFather(double time, Sim mom) { // PROBLEM 2: get caught up in infinite loop from time to time
        Sim father = null;
        if(!mom.isInARelationship(time) || RND.nextDouble()> Sim.FIDELITY){ // if mom is single, has dead husband or mate cheated, find new partner
            do{
                Sim potentialMate = (Sim) population.getRandomElement(RND);
                if(potentialMate.getSex() != mom.getSex() && potentialMate.isMatingAge(time)){ // if mate is a fertile male
                    if(mom.isInARelationship(time) || !potentialMate.isInARelationship(time) || RND.nextDouble() > Sim.FIDELITY){ // if male wants to cheat
                        father = potentialMate;
                    }
                }
            } while(father == null);
        } else{
            father = mom.getMate();
        }
        return father;
    }

    public HashMap<Double, Integer> getPopulationGrowth() {
        return populationGrowth;
    }

    public PQ<Sim> getPopulation() {
        return population;
    }
    
}

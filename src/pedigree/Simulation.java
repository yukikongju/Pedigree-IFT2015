package pedigree;

import java.util.Random;
import java.util.TreeMap;

public class Simulation {

    public final Random RND;
    public final double REPRODUCTION_RATE;
    public static final int HUNDRED_YEARS = 100;

    private PQ<Event> eventQ; // PQ<Event> is sorted chronologically by death 
    private PQ<Sim> population; // PQ<Sim> is sorted chronologically by death
    private final AgeModel ageModel;

    private TreeMap<Double, Integer> populationGrowth; // could use HashMap but we would need to sort it as it doesn't keep order. TreeMap insertion is O(nlog n), which is similar to sorting

    private int nbHommes;

    public Simulation() {
        ageModel = new AgeModel();
        RND = new Random();
        REPRODUCTION_RATE = 2 / ageModel.expectedParenthoodSpan(Sim.MIN_MATING_AGE_F, Sim.MAX_MATING_AGE_F); // probabilité d'avoir un bébé à chaque année
        nbHommes = 0;
    }

    public void simulate(int n, double Tmax) {
        // initialized PQ and hashMap inside simulate() instead of constructor in case we want to perform several simulations
        eventQ = new PQ<>();
        population = new PQ<>();
        populationGrowth = new TreeMap<>();

        double lastReportTime = 0;

        // generate first generation
        for (int i = 0; i < n; i++) {
            Sim fondateur = new Sim(generateSex(RND));
            Event E = new Event(fondateur, 0, Event.EventType.BIRTH);
            eventQ.insert(E);
        }

        // Begin simulation
        while (!eventQ.isEmpty()) {
            Event E = (Event) eventQ.deleteMin();

            // add checkpoints every 100 years
            if (E.getScheduledTime() > lastReportTime + HUNDRED_YEARS) {
                populationGrowth.put(E.getScheduledTime(), population.size());
                lastReportTime = E.getScheduledTime();
            }

            if (E.getScheduledTime() > Tmax) {
                break; // arrêter à Tmax 
            }
            if (E.getSim().getDeathTime() >= E.getScheduledTime()) { // FIXED: we don't want a strict inequality bc we won't be able to simulate death
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
        }
    }

    private Sim.Sex generateSex(Random random) {
        int num = random.nextInt(2); // generate 0 or 1
        return Sim.Sex.values()[num];
    }

    private void birth(Event E) {
        // keep track of the number of males in the population
        if(E.getSim().isMale()){
            this.nbHommes++;
        } 
        
        // scheduling death
        double deathTime = generateLifeLength() + E.getScheduledTime(); 
        E.getSim().setDeathTime(deathTime); 
        Event death = new Event(E.getSim(), deathTime, Event.EventType.DEATH);
        eventQ.insert(death);
        population.insert(E.getSim()); // adding Sim to population active

        // scheduling reproduction
        double reproductionTime = E.getScheduledTime() + Sim.MIN_MATING_AGE_F + generateRandomWaitingTime();
        if (E.getSim().isFemale() && E.getSim().isMatingAge(reproductionTime)) { // schedule reproduction if female isn't dead
            Event reproduction = new Event(E.getSim(), reproductionTime, Event.EventType.REPRODUCTION); 
            eventQ.insert(reproduction);
        }

    }

    private void death(Event E) {
        Sim sim = population.deleteMin();
        if(sim.isMale()){
            this.nbHommes--;
        } 
        if(population.isEmpty()) System.out.println("pop 0" +"   " +E.getScheduledTime());
    }

    private void reproduction(Event E) {
        Sim mom = E.getSim();
        double birthTime = E.getScheduledTime();
        if (mom.isMatingAge(birthTime) && !population.isEmpty()) { // TO FIX: doesn't try to find mate if there is no males left (hasMale)
            Sim dad = findFather(birthTime, mom);
            if(dad != null){
                Sim baby = new Sim(mom, dad, birthTime, generateSex(RND));
                dad.setMate(mom);
                mom.setMate(dad);
                Event birth = new Event(baby, birthTime, Event.EventType.BIRTH);
                eventQ.insert(birth);

                // Schedule next reproduction if mom isn't dead by then
                double reproductionTime = generateRandomWaitingTime() + E.getScheduledTime();
                if (E.getSim().isMatingAge(reproductionTime)) { 
                    Event reproduction = new Event(E.getSim(), reproductionTime,
                            Event.EventType.REPRODUCTION);
                    eventQ.insert(reproduction);
                }
            }

        }
    }

    private double generateRandomWaitingTime() {
        return AgeModel.randomWaitingTime(RND, REPRODUCTION_RATE);
    }

    private double generateLifeLength() {
        return ageModel.randomAge(RND); // generate lifespan of a Sim
    }

    private Sim findFather(double time, Sim mom) { // FIXED: doesn't try to find a father if there is none left in population
        Sim father = null;
        int essai = 0;
        if (!mom.isInARelationship(time) || RND.nextDouble() > Sim.FIDELITY) { // if mom is single, has dead husband or mate cheated, find new partner
            do {
                if(essai > nbHommes) return null;
                Sim potentialMate = (Sim) population.getRandomElement(RND);
                if (potentialMate.isMale() && potentialMate.isMatingAge(time)) { // if mate is a fertile male
                    if (mom.isInARelationship(time) || !potentialMate.isInARelationship(time) || RND.nextDouble() > Sim.FIDELITY) { // if male wants to cheat
                        father = potentialMate;
                    }
                }
                essai++;
            } while (father == null);
        } else {
            father = mom.getMate();
        }
        return father;
    }

    public TreeMap<Double, Integer> getPopulationGrowth() {
        return populationGrowth;
    }

    public PQ<Sim> getPopulation() {
        return population;
    }

}
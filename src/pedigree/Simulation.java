package pedigree;

import java.util.HashMap;
import java.util.Random;
import java.util.TreeMap;

public class Simulation {

    public final Random RND;
    public final double REPRODUCTION_RATE;

    public static final int HUNDRED_YEARS = 100;

//    public static final double EPSILUM_HUNDRED_YEAR = 0.1; // error we are willing to accept when comparing time to hundred year
    private PQ<Event> eventQ; // PQ<Event> is sorted chronologically by death 
    private PQ<Sim> population; // PQ<Sim> is sorted chronologically by death
//    private PriorityQueue<Event> eventQ; 
    private final AgeModel ageModel;

    private TreeMap<Double, Integer> populationGrowth;
//    private TreeMap<Double, Integer> populationGrowth; // cannot use HashMap because it doesn't keep order. TreeMap insert is O(log n), which is not good. 

    public Simulation() {
        ageModel = new AgeModel();
        RND = new Random();
        REPRODUCTION_RATE = 2 / ageModel.expectedParenthoodSpan(Sim.MIN_MATING_AGE_F, Sim.MAX_MATING_AGE_F); // probabilité d'avoir un bébé à chaque année
    }

    public void simulate(int n, double Tmax) {
        // initialized PQ and hashMap inside simulate() instead of constructor in case we want to perform several simulations
        eventQ = new PQ<>();
        population = new PQ<>();
        populationGrowth = new TreeMap<>();

        double lastReportTime = 0;

        // generate first generation
        for (int i = 0; i < n; i++) {
            Sim fondateur = new Sim(generateSex(RND)); // TODO: refractor generateSex in SexModel or inside Sim class?
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
//                System.out.println(populationGrowth);
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
//            System.out.println("YEAR : + " + E.getScheduledTime() + " EVENT TYPE " + E.getEventType().toString());
        }
    }

    private Sim.Sex generateSex(Random random) {
        int num = random.nextInt(2); // generate 0 or 1
        return Sim.Sex.values()[num];
    }

    private void birth(Event E) {
        // scheduling death
        double deathTime = generateLifeLength() + E.getScheduledTime(); // lifespan of a Sim
        E.getSim().setDeathTime(deathTime); // TO FIX? should we set death to global deathtime or relative to sim
        Event death = new Event(E.getSim(), deathTime, Event.EventType.DEATH);
        eventQ.insert(death);
        population.insert(E.getSim()); // adding Sim to population active

        // scheduling reproduction
        double reproductionTime = E.getScheduledTime() + Sim.MIN_MATING_AGE_F + generateRandomWaitingTime();
        if (E.getSim().isFemale() && E.getSim().isMatingAge(reproductionTime)) { // schedule reproduction if female isn't dead
            Event reproduction = new Event(E.getSim(), reproductionTime, Event.EventType.REPRODUCTION); // PROBLEM: HALT
            eventQ.insert(reproduction);
        }

    }

    private void death(Event E) {
        Sim sim = population.deleteMin();         // remove sim from population active TOFIX??
    }

    private void reproduction(Event E) {
        Sim mom = E.getSim();
        double birthTime = E.getScheduledTime();
        if (mom.isMatingAge(birthTime) && !population.isEmpty()) { // TO FIX: doesn't try to find mate if there is no males left (hasMale)
            Sim dad = findFather(birthTime, mom);
            Sim baby = new Sim(mom, dad, birthTime, generateSex(RND));
            dad.setMate(mom);
            mom.setMate(dad);
            Event birth = new Event(baby, birthTime, Event.EventType.BIRTH);
            eventQ.insert(birth);

//            double reproductionWaitingTime = generateRandomWaitingTime();
//            Event reproduction = new Event(E.getSim(), E.getScheduledTime() + reproductionWaitingTime,
//                Event.EventType.REPRODUCTION); 
//            eventQ.insert(reproduction);
        }
        // Schedule next reproduction if mom isn't dead by then
        double reproductionTime = generateRandomWaitingTime() + E.getScheduledTime();
        if (E.getSim().isMatingAge(reproductionTime)) { // VERIFY
            Event reproduction = new Event(E.getSim(), reproductionTime,
                    Event.EventType.REPRODUCTION);
            eventQ.insert(reproduction);
        }
    }

//    private boolean hasMale(){ // check if there are male left in the population to avoid halt
//        if(population.isEmpty()) return false;
//        for(int i = 0; i< population.size(); i++) {
//            if(population.getHeap()[i].isMale()) return true;
//        }
//        return false;
//    }
    private double generateRandomWaitingTime() {
        return AgeModel.randomWaitingTime(RND, REPRODUCTION_RATE);
    }

    private double generateLifeLength() {
        return ageModel.randomAge(RND); // generate lifespan of a Sim
    }

    private Sim findFather(double time, Sim mom) { // PROBLEM 2: get caught up in infinite loop from time to time
        Sim father = null;
        if (!mom.isInARelationship(time) || RND.nextDouble() > Sim.FIDELITY) { // if mom is single, has dead husband or mate cheated, find new partner
            do {
                Sim potentialMate = (Sim) population.getRandomElement(RND);
                if (potentialMate.isMale() && potentialMate.isMatingAge(time)) { // if mate is a fertile male
                    if (mom.isInARelationship(time) || !potentialMate.isInARelationship(time) || RND.nextDouble() > Sim.FIDELITY) { // if male wants to cheat
                        father = potentialMate;
                    }
                }
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

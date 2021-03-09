
package pedigree;

public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//       Simulation simulation = new Simulation();
//       simulation.simulate(5, 200);
        
    // TEST: testing if pq works
        PQ p = new PQ();
        Sim sim = new Sim(Sim.Sex.F);
        Event e1 = new Event(sim, 1, Event.EventType.BIRTH);
        Event e2 = new Event(sim, 2, Event.EventType.BIRTH);
        Event e3 = new Event(sim, 3, Event.EventType.BIRTH);
        Event e4 = new Event(sim, 4, Event.EventType.BIRTH);
        Event e5 = new Event(sim, 5, Event.EventType.BIRTH);
        Event e6 = new Event(sim, 6, Event.EventType.BIRTH);
        Event e7 = new Event(sim, 7, Event.EventType.BIRTH);
        Event e8 = new Event(sim, 8, Event.EventType.BIRTH);
        
        p.insert(e5);
        p.insert(e4);
        
        p.insert(e6);
//        p.insert(e1);
//        p.insert(e2);
//        p.insert(e3);
//        p.insert(e7);
//        p.insert(e8);
        System.out.println(p);
//        p.deleteAt(e6);
    }

}

package pedigree;

public class Event implements Comparable<Event> {
    
    public enum EventType{
        BIRTH, 
        DEATH,
        REPRODUCTION
    }
    
    private Sim sim;
    private double scheduledTime;
    private EventType eventType;

    public Event(Sim sim, double time, EventType eventType) {
        this.sim = sim;
        this.scheduledTime = time;
        this.eventType = eventType;
    }
    
//    public abstract void simulate();

    public Sim getSim() {
        return sim;
    }

    public double getScheduledTime() {
        return scheduledTime;
    }

    @Override
    public int compareTo(Event t) { // verify if it works
        return Double.compare(this.scheduledTime, t.getScheduledTime());
    }

    @Override
    public String toString() {
        return this.getClass().getName() + " of " + sim.getSex() +
                " scheduled at time: " + this.getScheduledTime();
    }

}

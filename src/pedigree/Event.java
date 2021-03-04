package pedigree;

public abstract class Event implements Comparable<Event> {
    
    private Sim sim;
    private double scheduledTime;

    public Event(Sim sim, double time) {
        this.sim = sim;
        this.scheduledTime = time;
    }
    
    public abstract void simulate();

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

package pedigree;

public abstract class Event implements Comparable<Event> {
    private Sim sim;
    private double time;

    public Event(Sim sim, double time) {
        this.sim = sim;
        this.time = time;
    }
    
    public abstract void simulate();

    public Sim getSim() {
        return sim;
    }

    public double getTime() {
        return time;
    }

    @Override
    public int compareTo(Event t) {
        return Double.compare(this.time, t.getTime());
    }
    
    
}

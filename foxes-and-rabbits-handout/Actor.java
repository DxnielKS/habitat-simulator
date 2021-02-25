import java.util.List;
import java.util.Random;

/**
 * Write a description of class Actor here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Actor
{
    // instance variables - replace the example below with your own
    private boolean alive;
    // The animal's field.
    private Field field;
    // The animal's position in the field.
    private Location location;

    private Random random = new Random();

    /**
     * Constructor for objects of class Actor
     */
    public Actor(Field field, Location location)
    {
        alive = true;
        this.field = field;
        setLocation(location);
    }

    /**
     * An example of a method - replace this comment with your own
     *
     * @param  y  a sample parameter for a method
     * @return    the sum of x and y
     */
    protected void setLocation(Location newLocation)
    {
        if(location != null) {
            field.clear(location);
        }
        location = newLocation;
        field.place(this, newLocation);
    }
    /***
     * 
     */
    protected Field getField()
    {
        return field;
    }
        protected void setDead()
    { 
        alive = false;
        if(location != null) {
            field.clear(location);
            location = null;
            field = null;
        }
    }

    /**
     * Return the actors's location.
     * @return The actors's location.
     */
    protected Location getLocation()
    {
        return location;
    }
    /**
     * Check whether the animal is alive or not.
     * @return true if the animal is still alive.
     */
        protected boolean isAlive()
    {
        return alive;
    }
}

import java.util.List;
import java.util.Iterator;
import java.util.Random;
/**
 * Write a description of class Predator here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public abstract class Predator extends Animal
{
    
    private Field field;
    // The animal's position in the field.
    private Location location;
    /**
     * Constructor for objects of class Predator
     */
    public Predator(Field field, Location location)
    {
        super(field,location);
    }
    
    abstract public void act(List<Animal> newAnimals);
    
    /**
     * An example of a method - replace this comment with your own
     *
     * @param  y  a sample parameter for a method
     * @return    the sum of x and y
     */
    abstract public Location findFood(Animal prey, int FOOD_VALUE, int foodLevel);
}

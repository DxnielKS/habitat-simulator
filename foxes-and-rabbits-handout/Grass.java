import java.util.List;
import java.util.Random;
/**
 * Write a description of class Grass here.
 *
 * @author Taseen Rahman
 * @version (a version number or a date)
 */
public class Grass extends Plants
{
    // instance variables - replace the example below with your own
    private static final Random rand = Randomizer.getRandom();
    private Weather weather_machine;
    /**
     * Constructor for objects of class Grass
     */
    public Grass(Field field, Location location)
    {
        super(field,location);
        setMaxAge(5);
        setAge(0);
        setFertility(0.15);
        
    }

    public void act(List <Plants> newGrass)
    {
        incrementAge();
        deathByAge();
        grow(newGrass);
        
    }
    /**
     * If the plant is still alive the grass will find a location adjacent to it that is free
     * and then there is a probability that it will create a new object at that free location
     * this is then added to the list of plants which is the parameter
     */
    public void grow(List<Plants> newGrass)
    {
        if(isAlive())
        {
            Location newLocation = getField().freeAdjacentLocation(getLocation());
            if (newLocation != null)
            {
                if(rand.nextDouble() <= getFertility())
                {    
                    Grass grownGrass = new Grass(getField(),newLocation); 
                    newGrass.add(grownGrass);
                }
            }
        }
    }
}

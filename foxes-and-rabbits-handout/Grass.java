import java.util.List;
import java.util.Random;
/**
 * Write a description of class Grass here.
 *
 * @author (your name)
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
        setMaxAge(15);
        setAge(0);
        setFertility(0.47);
        
    }
    /**
     * An example of a method - replace this comment with your own
     *
     * @param  y  a sample parameter for a method
     * @return    the sum of x and y
     */
    public void act(List <Plants> newGrass)
    {
        incrementAge();
        deathByAge();
        if (isAlive()){
            grow(newGrass);
        }
    }
    
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

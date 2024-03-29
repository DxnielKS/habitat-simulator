import java.util.List;
import java.util.Random;
import java.util.Iterator;

/**
 * A simple model of a worm.
 * worms age, move, breed, and die.
 * 
 * @author Taseen Rahman
 * @version 2021.02.25
 */
public class Worm extends Animal
{
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();
    
    private static final int GRASS_FOOD_VALUE = 10;
       

    /**
     * Create a new worm. A worm may be created with age
     * zero (a new born) or with a random age.
     * 
     * @param randomAge If true, the worm will have a random age.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Worm(boolean randomAge, Field field, Location location)
    {
        super(field, location,3,0.4,4);
        setAge(0);
        setMaxAge(30);
        if(randomAge) {
            setAge(rand.nextInt(getMaxAge()));
        }
        setGender();
        setFoodLevel(GRASS_FOOD_VALUE);
    }
    
    /**
     * This is what the worm does most of the time - it runs 
     * around. Sometimes it will breed or die of old age.
     * @param newworms A list to return newly born worms.
     */
    public void act(List<Animal> newworms)
    {
        incrementAge();
        deathByAge();
        incrementHunger();
        overComeDisease();
        setInfectedAge(20,30);
        if(isAlive()){
            giveBirth(newworms);            
            // Try to move into a free location.
            Location newLocation = findFood();
            if (newLocation == null)
            {
                newLocation = getField().freeAdjacentLocation(getLocation());
            }
            
            if(newLocation != null) {
                setLocation(newLocation);
            }
            else {
                // Overcrowding.
                setDead();
            }
        }
    }

    
    /**
     * Check whether or not this worm is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newworms A list to return newly born worms.
     */
    private void giveBirth(List<Animal> newworms)
    {
        // New worms are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Worm young = new Worm(false, field, loc);
            newworms.add(young);
        }
    }

    /**
     * Look for worms adjacent to the current location.
     * Only the first live grass found is eaten.
     * @return Where food was found, or null if it wasn't.
     */
    private Location findFood()
    {
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Object plant = field.getObjectAt(where);
            if(plant instanceof Grass) {
                Grass grass = (Grass) plant;
                if(grass.isAlive()) { 
                    grass.setDead();
                    setFoodLevel(getFoodLevel()+GRASS_FOOD_VALUE);
                    if (getFoodLevel()>10)
                    {
                        setFoodLevel(10);
                    }
                    return where;
                }
            }
        }
        return null;
    }
    
}

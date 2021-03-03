import java.util.List;
import java.util.Iterator;
import java.util.Random;

/**
 * A simple model of a squirel.
 * squireles age, move, eat WORMs, and die.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2016.02.29 (2)
 */
public class Squirel extends Animal
{
    // Characteristics shared by all squireles (class variables).
    
    // The age at which a squirel can start to breed.
    private static final int BREEDING_AGE = 5;
    // The age to which a squirel can live.
    // The likelihood of a squirel breeding.
    private static final double BREEDING_PROBABILITY = 0.42;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 3;
    // The food value of a single WORM. In effect, this is the
    // number of steps a squirel can go before it has to eat again.
    private static final int WORM_FOOD_VALUE = 5;
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();
    // Individual characteristics (instance fields).
    // The squirel's age.
    // The squirel's food level, which is increased by eating WORMs.

 
    /**
     * Create a squirel. A squirel can be created as a new born (age zero
     * and not hungry) or with a random age and food level.
     * 
     * @param randomAge If true, the squirel will have random age and hunger level.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Squirel(boolean randomAge, Field field, Location location)
    {
        super(field, location,5,0.42,5);
        setMaxAge(70);
        if(randomAge) {
            setAge(rand.nextInt(getMaxAge()));
            setFoodLevel(rand.nextInt(WORM_FOOD_VALUE));
        }
        else {
            setAge(0);
            setFoodLevel(10);
        }
        setGender();
    }
    
    /**
     * This is what the squirel does most of the time: it hunts for
     * WORMs. In the process, it might breed, die of hunger,
     * or die of old age.
     * @param field The field currently occupied.
     * @param newsquireles A list to return newly born squireles.
     */
    public void act(List<Animal> newsquireles)
    {
        incrementAge();
        incrementHunger();
        deathByAge();
        setInfectedAge(30);
        //spreadDisease();
        if(isAlive()) {
            giveBirth(newsquireles);            
            // Move towards a source of food if found.
            Location newLocation = findFood();
            if(newLocation == null) { 
                // No food found - try to move to a free location.
                newLocation = getField().freeAdjacentLocation(getLocation());
            }
            // See if it was possible to move.
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
     * Look for WORMs adjacent to the current location.
     * Only the first live WORM is eaten.
     * @return Where food was found, or null if it wasn't.
     */
    private Location findFood()
    {
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        
        while(it.hasNext()) {
            Location where = it.next();
            Object animal = field.getObjectAt(where);
            if(animal instanceof Worm) {
                Worm worm = (Worm) animal;
                if(worm.isAlive()) { 
                    worm.setDead();
                    setFoodLevel(getFoodLevel()+WORM_FOOD_VALUE);
                    return where;
                }
            }
        }
        return null;
    }
    
    /**
     * Check whether or not this squirel is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newsquireles A list to return newly born squireles.
     */
    private void giveBirth(List<Animal> newsquireles)
    {
        // New squireles are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Squirel young = new Squirel(false, field, loc);
            newsquireles.add(young);
        }
    }

}

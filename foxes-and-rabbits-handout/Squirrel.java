import java.util.List;
import java.util.Iterator;
import java.util.Random;

/**
 * A simple model of a squirrel.
 * squirrels age, move, eat worms, and die.
 * 
 * @author Taseen Rahman
 * @version 2021.02.25
 */
public class Squirrel extends Animal
{
    // number of steps a squirrel can go before it has to eat again.
    private static final int WORM_FOOD_VALUE = 5;
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();
    // Individual characteristics (instance fields).

 
    /**
     * Create a squirrel. A squirrel can be created as a new born (age zero
     * and not hungry) or with a random age and food level.
     * 
     * @param randomAge If true, the squirrel will have random age and hunger level.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Squirrel(boolean randomAge, Field field, Location location)
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
     * This is what the squirrel does most of the time: it hunts for
     * WORMs. In the process, it might breed, die of hunger,
     * or die of old age.
     * @param field The field currently occupied.
     * @param newSquirrels A list to return newly born squirreles.
     */
    public void act(List<Animal> newSquirreles)
    {
        incrementAge();
        incrementHunger();
        deathByAge();
        overComeDisease();
        setInfectedAge(30,70);
        if(isAlive()) {
            giveBirth(newSquirreles);            
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
    private void giveBirth(List<Animal> newSquirrels)
    {
        // New squireles are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Squirrel young = new Squirrel(false, field, loc);
            newSquirrels.add(young);
        }
    }

}

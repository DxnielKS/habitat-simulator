import java.util.List;
import java.util.Iterator;
import java.util.Random;

/**
 * A simple model of a owl.
 * owles age, move, eat worms, and die.
 * 
 * @author Daniel Saisani
 * @version 18/02/2021
 */
public class Owl extends Animal
{

    
    // number of steps an owl can go before it has to eat again.
    private static final int WORM_FOOD_VALUE = 9;
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom(); 

    /**
     * Create a owl. A owl can be created as a new born (age zero
     * and not hungry) or with a random age and food level.
     * 
     * @param randomAge If true, the owl will have random age and hunger level.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Owl(boolean randomAge, Field field, Location location)
    {
        super(field, location,4,0.45,2);
        setMaxAge(40);

        if(randomAge) {
            setAge(rand.nextInt(getMaxAge()));
            setFoodLevel(rand.nextInt(WORM_FOOD_VALUE));
        }
        else {
            setAge(0);
            setFoodLevel(WORM_FOOD_VALUE);
        }
        this.toggleNocturnal();
        setGender();
    }
    
    /**
     * This is what the owl does most of the time: it hunts for
     * owls. In the process, it might breed, die of hunger,
     * or die of old age.
     * @param field The field currently occupied.
     * @param newowles A list to return newly born owles.
     */
    public void act(List<Animal> newOwls)
     {
        incrementAge();
        incrementHunger();
        deathByAge();
        overComeDisease();
        setInfectedAge(25,40);
         if(isAlive() ) {

            giveBirth(newOwls);            
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
     * Check whether or not this owl is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newowles A list to return newly born owles.
     */
    private void giveBirth(List<Animal> newOwls)
        {
        // New owles are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Owl young = new Owl(false, field, loc);
            newOwls.add(young);
         }
       }
        /**
     * Look for worms adjacent to the current location.
     * Only the first live worm is eaten.
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
                    if (worm.getAge()<7){
                        worm.setDead();
                        setFoodLevel( getFoodLevel() + WORM_FOOD_VALUE);
                        if(getFoodLevel() > 10)
                        {
                            setFoodLevel(10);
                        }
                        return where;
                }
                }
            }
        }
        return null;
    }
    
}

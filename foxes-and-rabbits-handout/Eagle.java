import java.util.List;
import java.util.Iterator;
import java.util.Random;

/**
 * A simple model of a Eagle.
 * Eagles age, move, eat, and die.
 * 
 * @author Taseen Rahman
 * @version 2021.02.25
 */
public class Eagle extends Animal
{    
    // The food value of a single prey. In effect, this is the
    private static final int SQUIREL_FOOD_VALUE = 5 ;
    private static final int OWL_FOOD_VALUE = 5;
    private static final int WORM_FOOD_VALUE = 3;
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();
    
    /**
     * Create a eagle. A eagle can be created as a new born (age zero
     * and not hungry) or with a random age and food level.
     * 
     * @param randomAge If true, the eagle will have random age and hunger level.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Eagle(boolean randomAge, Field field, Location location)
    {
        super(field, location,7,0.5,2);

        setMaxAge(50);
        
        if(randomAge) {
            setAge(rand.nextInt(getMaxAge()));
            setFoodLevel(rand.nextInt(SQUIREL_FOOD_VALUE));
        }
        else {
            setAge(0);
            setFoodLevel(SQUIREL_FOOD_VALUE);
        }
        setGender();
    }
    
    /**
     * This is what the eagle does most of the time: it hunts for
     * worms. In the process, it might breed, die of hunger,
     * or die of old age.
     * @param field The field currently occupied.
     * @param newEagles A list to return newly born eagles.
     */
    public void act(List<Animal> newEagles)
    {
        incrementAge();
        incrementHunger();
        deathByAge();
        overComeDisease();
        setInfectedAge(30,50);
        if(isAlive()) {
            giveBirth(newEagles);            
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
     * Look for worms adjacent to the current location.
     * Only the first live animal found is eaten.
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
            if(animal instanceof Squirrel) {
                Squirrel squirel = (Squirrel) animal;
                if(squirel.isAlive()) { 
                    if (squirel.getAge()<7){
                        squirel.setDead();
                        setFoodLevel( getFoodLevel() + SQUIREL_FOOD_VALUE);
                        if(getFoodLevel() > 10)
                        {
                            setFoodLevel(10);
                        }
                        return where;
                }
                }
            }
            else if(animal instanceof Worm)
            {
                Worm worm = (Worm) animal;
                if (worm.isAlive())
                {
                    worm.setDead();
                    setFoodLevel( getFoodLevel() + WORM_FOOD_VALUE);
                    if(getFoodLevel()> 10)
                    {
                        setFoodLevel(10);
                    }
                    return where;
                }
            }
             else if(animal instanceof Owl)
            {
                Owl owl = (Owl) animal;
                if (owl.isAlive())
                {
                    owl.setDead();
                    setFoodLevel( getFoodLevel() + OWL_FOOD_VALUE);
                    if(getFoodLevel()> 10)
                    {
                        setFoodLevel(10);
                    }
                    return where;
                }
            }
        }
        return null;
    }
    
    /**
     * Check whether or not this eagle is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newEagles A list to return newly born eagles.
     */
    private void giveBirth(List<Animal> newEagles)
    {
        // New eagles are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Eagle young = new Eagle(false, field, loc);
            newEagles.add(young);
        }
    }
        
    }


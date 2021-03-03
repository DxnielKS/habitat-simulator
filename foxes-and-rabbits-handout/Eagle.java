import java.util.List;
import java.util.Iterator;
import java.util.Random;

/**
 * A simple model of a squirrel.
 * squirrels age, move, eat, and die.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2016.02.29 (2)
 */
public class Eagle extends Animal
{
    // Characteristics shared by all squirrels (class variables).
    
    // The age at which a squirrel can start to breed.
    private static final int BREEDING_AGE = 7;
    // The age to which a squirrel can live.
    // The likelihood of a squirrel breeding.
    private static final double BREEDING_PROBABILITY = 0.6;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 2;
    // The food value of a single worm. In effect, this is the
    // number of steps a fox can go before it has to eat again.
    private static final int SQUIREL_FOOD_VALUE = 5 ;
    
    private static final int WORM_FOOD_VALUE = 3;
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom(); 

    
    // Individual characteristics (instance fields).
    // The fox's age.
    // The fox's food level, which is increased by eating worms.
    

 
    /**
     * Create a fox. A fox can be created as a new born (age zero
     * and not hungry) or with a random age and food level.
     * 
     * @param randomAge If true, the fox will have random age and hunger level.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Eagle(boolean randomAge, Field field, Location location)
    {
        super(field, location);
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
     * This is what the fox does most of the time: it hunts for
     * worms. In the process, it might breed, die of hunger,
     * or die of old age.
     * @param field The field currently occupied.
     * @param newFoxes A list to return newly born foxes.
     */
    public void act(List<Animal> newEagles)
    {
        incrementAge();
        incrementHunger();
        deathByAge();
        if(isAlive()) {
            canBreed(newEagles);            
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
            if(animal instanceof Squirel) {
                Squirel squirel = (Squirel) animal;
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
        }
        return null;
    }
    
    /**
     * Check whether or not this fox is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newFoxes A list to return newly born foxes.
     */
    private void giveBirth(List<Animal> newEagles)
    {
        // New foxes are born into adjacent locations.
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
        
    /**
     * Generate a number representing the number of births,
     * if it can breed.
     * @return The number of births (may be zero).
     */
    private int breed()
    {
        int births = 0;
        if(rand.nextDouble() <= BREEDING_PROBABILITY) {
            births = rand.nextInt(MAX_LITTER_SIZE) + 1;
        }
        return births;
    }

    /**
     * A fox can breed if it has reached the breeding age.
     */
    private void canBreed(List<Animal> newEagles)
    {
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Object animal = field.getObjectAt(where);
            if(animal instanceof Eagle) {
                Eagle eagle = (Eagle) animal;
                if(eagle.isAlive() && (getGender() != eagle.getGender()) && eagle.getAge() >= BREEDING_AGE && getAge() >= BREEDING_AGE) { 
                        giveBirth(newEagles);
                }
            }
        }
    }
    }


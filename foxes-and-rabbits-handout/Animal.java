import java.util.List;
import java.util.Random;
import java.util.Iterator;
import java.util.HashMap;

/**
 * A class representing shared characteristics of animals.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2016.02.29 (2)
 */
public abstract class Animal extends Actor 
{
    // Whether the animal is alive or not.
    private boolean alive;
    // The animal's field.
    // The animal's position in the field.
    private Location location;

    private Random random = new Random();
    
    private int foodLevel;

    private boolean isMale;
    
    private int BREEDING_AGE;
    
    private double BREEDING_PROBABILITY;
    
    private int MAX_LITTER_SIZE;
    
    private int infectionCount;
    
    Random rand = Randomizer.getRandom();
    
    private boolean isInfected; // a flag to indicate whether an animal is ill or not
    
    private static final double infection_chance = 0.01; // chance of infection when populating the field
    private static final double infection_rate = 0.01; // rate of infection
    private static final double death_rate = 0.01; // chance of death from disease

    
    /**
     * Create a new animal at location in field.
     * 
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Animal(Field field, Location location, int breedingAge, double breedingProbability, int maxLitterSize)
    {
        super(field,location);
        BREEDING_AGE = breedingAge;
        BREEDING_PROBABILITY = breedingProbability; 
        MAX_LITTER_SIZE = maxLitterSize;
        
        if (random.nextDouble() >= infection_chance)
        {
            isInfected = true;
        }
        else
        {
            isInfected = false;
        }
    }
    /**
     * Make this animal act - that is: make it do
     * whatever it wants/needs to do.
     * @param newAnimals A list to receive newly born animals.
     */
    abstract public void act(List<Animal> newAnimals);
    /***
     * 
     */
    public boolean getNight()
     {
       if (getField().getFieldTime().getNight())
       {
           return true;
        }
       return false;
    }
    protected void setGender()
     {
        if (random.nextBoolean())
        {
            isMale = true;
        }
        else
        {
            isMale = false;
        }
    }
    protected boolean getGender()
    {
        return isMale;
    }
   
    protected void setFoodLevel(int newLevel)
    {
        foodLevel = newLevel;
    }
    
    protected int getFoodLevel()
    {
        return foodLevel;
    }
    
    /**
     * Make this fox more hungry. This could result in the fox's death.
     */
    protected void incrementHunger()
     {
        foodLevel--;
        if(foodLevel <= 0) {
            setDead();
        }
    }
    
    protected void toggleInfected()
    {
       isInfected =! isInfected;
    }
    
    protected Boolean getInfected()
    {
        return this.isInfected;
    }
    
    protected boolean meet()
     {
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Object animal = field.getObjectAt(where);
            if(animal != null && animal.getClass() == this.getClass()) {
                Animal adjacentAnimal = (Animal) animal;
                spreadDisease(adjacentAnimal);
                if(adjacentAnimal.isAlive() && (getGender() != adjacentAnimal.getGender()) && adjacentAnimal.getAge() >= BREEDING_AGE && getAge() >= BREEDING_AGE) { 
                        return true;
                }
            }
        }
        return false;
    }
    protected int breed()
    {
        int births = 0;
        if(meet() && rand.nextDouble() <= BREEDING_PROBABILITY) {
            births = rand.nextInt(MAX_LITTER_SIZE) + 1;
        }
        return births;
    }
    /**
     * 
     */
    protected void setInfectedAge(int dyingAge)
    {
        if (this.getInfected())
        {
            this.setMaxAge(dyingAge);
        }
    }
    
    /**
     *  This spreads the disease if the animal making contact already has the disease
     */
    protected void spreadDisease(Animal adjacentAnimal)
    {
        if (this.getInfected())
         {
            if(rand.nextDouble() <= infection_chance) {
                if(adjacentAnimal.isAlive() && adjacentAnimal.getInfected()==false) { 
                        adjacentAnimal.toggleInfected();
                        System.out.println(adjacentAnimal.getInfected());
                        System.out.println(adjacentAnimal.getMaxAge());
                }
            }
           }
     }

}
    
    


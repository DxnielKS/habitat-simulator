import java.util.List;
import java.util.Random;
import java.util.Iterator;
import java.util.HashMap;

/**
 * A class representing shared characteristics of animals.
 * 
 * @author David J. Barnes and Michael Kölling - Modified and Updated by Taseen Rahman and Daniel Saisani
 * @version 2021.02.25
 */
public abstract class Animal extends Actor 
{
    // Whether the animal is alive or not.
    private boolean alive;

    // The animal's position in the field.
    private Location location;

    private Random random = new Random();
    //The animals food level
    private int foodLevel;
    //The animals gender
    private boolean isMale;
    //The age at which the animal can start breeding
    private int BREEDING_AGE;
    //The chance of the animal breeding
    private double BREEDING_PROBABILITY;
    //The maxium amount of offsprings that can be produced
    private int MAX_LITTER_SIZE;
    
    Random rand = Randomizer.getRandom();
    // a flag to indicate whether an animal is ill or not
    private boolean isInfected;
    private static final double infection_chance = 0.01; // chance of infection when populating the field
    private static final double infection_rate = 0.03; // rate of infection
    private static final double cure_rate = 0.1; // chance of death from disease

    
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
        
        //This allows for some animals to be born with illnesses
        if (random.nextDouble() <= infection_chance)
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
     *  This returns the boolean night that is stored within field 
     */
    public boolean getNight()
     {
       if (getField().getFieldTime().getNight())
       {
           return true;
        }
       return false;
    }
    /**
     * This generates a random boolean value which is then stored in isMale
     */
    protected void setGender()
     {
        isMale = random.nextBoolean();
    }
    /**
     * Returns the boolean variable isMale
     */
    protected boolean getGender()
    {
        return isMale;
    }
    /**
     * This allows the foodLevel to be set - it is set to the parameter that is passed
     */
    protected void setFoodLevel(int newLevel)
    {
        foodLevel = newLevel;
    }
    /**
     * Returns the foodLevel as an integer
     */
    protected int getFoodLevel()
    {
        return foodLevel;
    }
    
    /**
     * Make this animal more hungry. This could result in the animals's death.
     */
    protected void incrementHunger()
     {
        foodLevel--;
        if(foodLevel <= 0) {
            setDead();
        }
     }
    /**
     *  This can either make isInfected true or false
     *  This is because I want to add a way in which an animal can be cured of 
     *  disease 
     */
    protected void toggleInfected()
    {
       isInfected =! isInfected;
     }
    /**
     * This returns the boolean variable isInfected
     */
    protected Boolean getInfected()
    {
        return this.isInfected;
    }
    /**
     * This gets a list of locations adjacent to the animal and then iterates through the list
     * it will then validate the following condtions - there is an animal present and it is of the same class as this.getClass()
     * it will then make an animal in that ajacent location and run spreadDisease on it
     * and then validate whether the animal is alive and the genders are different and both ages are above breeding age
     * if so it will return true if not it will return
     */
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
    /**
     * This sets an integer to 0 and if a random double is less then the breeding probability then it will set that integer to a random
     * number from 1 to the max litter size 
     * this will then be returned 
     */
    protected int breed()
     {
        int births = 0;
        if(meet() && rand.nextDouble() <= BREEDING_PROBABILITY) {
            births = rand.nextInt(MAX_LITTER_SIZE) + 1;
        }
        return births;
    }
    /**
     * This checks if the animal is infected if so it's maxAge will be set to dyingAge parameter else it will be set to healthyAge parameter
     */
    protected void setInfectedAge(int dyingAge,int healthyAge)
    {
        if (this.getInfected())
        {
            this.setMaxAge(dyingAge);
        }
        else
        {
            this.setMaxAge(healthyAge);
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
               
                }
            }
           }
     }
    /**
     * This checks if the animal has the disease and if so has chance of being cured from said disease
     */
    protected void overComeDisease()
    {
        if (this.getInfected())
        {
           if(rand.nextDouble() <= cure_rate) 
           {
               this.toggleInfected();
            }
    }
   }
}
    
    


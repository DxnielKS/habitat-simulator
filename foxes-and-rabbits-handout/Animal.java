import java.util.List;
import java.util.Random;

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
    public Animal(Field field, Location location)
    {
        super(field,location);
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
    {
    }
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
}

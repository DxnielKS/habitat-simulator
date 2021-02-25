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
    private Field field;
    // The animal's position in the field.
    private Location location;

    private Random random = new Random();
    

    private boolean isMale;
    
    
    /**
     * Create a new animal at location in field.
     * 
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Animal(Field field, Location location)
    {
        super(field,location);
    }
    public void sleep()
    {
       if (field.getFieldTime().getNight())
       {
        
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
   
}

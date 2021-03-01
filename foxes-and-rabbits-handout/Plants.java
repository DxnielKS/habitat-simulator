import java.util.List;
import java.util.Random;

/**
 * Write a description of class Plants here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public abstract class Plants extends Actor
{
    // instance variables - replace the example below with your own
    private static double FERTILITY_RATE;
    
    
    public Plants(Field field, Location location)
    {
        super(field,location);
    }
    
    
    public abstract void act(List<Plants> newPlants);
    public boolean getNight()
     {
       if (getField().getFieldTime().getNight())
       {
           return true;
        }
       return false;
    }
    protected double getFertility()
    {
        return FERTILITY_RATE;
    }
    
    protected void setFertility(double newRate)
    {
        FERTILITY_RATE = newRate;
    }
    
}

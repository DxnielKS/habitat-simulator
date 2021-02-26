
/**
 * Write a description of class Time here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Time
{
    // instance variables - replace the example below with your own
    private static int time;
    private static int currentTime;
    private static int daysPassed;
    private static boolean morning;
    private static boolean evening;
    private static boolean afternoon;
    private static boolean night;

    /**
     * Constructor for objects of class Time
     */
    public Time()
    {
        time = 0;
        currentTime=0;
        daysPassed = 0;
        night = true;
        morning = false;
        afternoon = false;
        evening = false;
    }
    public static void setTime(int newTime)
    {
        time = newTime;
        daysPassed = 0;
        currentTime = time/10;
    }

    public static void incrementTime()
    {
        // put your code here
        time++;
        if (time>= 240)
        {
            time=0;
            daysPassed++;
        }
        currentTime = time/10;
       }
    
    public static String getStringTime()
    {
        String timeString = ("Time: "+ currentTime + ":00 and Days Passed: " + daysPassed); 
        return timeString;
    }
    
    public static void setTimeOfDay()
    {
        incrementTime();
        if (time>=0 && time < 70)
        {
            night = true;
            evening = false;
            afternoon = false;
            morning = false;
        }
        else if (time>=70 && time < 120)
        {
            night = false;
            evening = false;
            afternoon = false;
            morning = true;
        }
        else if (time>=120 && time < 170)
        {
            night = false;
            evening = false;
            afternoon = true;
            morning = false;
        }
        else
        {
            night = false;
            evening = true;
            afternoon = false;
            morning = false;
        }
    }
    
    public static boolean getMorning()
    {
        return morning;
    }
    
    public static boolean getNight()
    {
        return night;
    }
    
    public static boolean getEvening()
    {
        return evening;
    }
    public static boolean getAfternoon()
    {
        return afternoon;
    }
    public static int getDays()
    {
        return daysPassed;
    }
}

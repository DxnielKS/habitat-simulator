
/**
 * A class that keeps track of and increments time in the simulation.
 *
 * @author Taseen Rahman
 * @version 24/02/2021
 */
public class Time
{

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
    /**
     * A method to set a new time
     */
    public static void setTime(int newTime)
    {
        time = newTime;
        daysPassed = 0;
        currentTime = time/10;
    }
    /**
     * The method to increment time.
     */
    public static void incrementTime()
    {
        time++;
        if (time>= 240)
        {
            time=0;
            daysPassed++;
        }
        currentTime = time/10;
       }
    /**
     * A method to return the time and days elapsed to be displayed on the GUI
     */
    public static String getStringTime()
    {
        String timeString = ("Time: "+ currentTime + ":00 and Days Passed: " + daysPassed); 
        return timeString;
    }
    /**
     * A method to set the time of day.
     */
    public static void setTimeOfDay()
    {
        incrementTime();
        if (time>=0 && time < 70) // night
        {
            night = true;
            evening = false;
            afternoon = false;
            morning = false;
        }
        else if (time>=70 && time < 120) // morning
        {
            night = false;
            evening = false;
            afternoon = false;
            morning = true;
        }
        else if (time>=120 && time < 170) // afternoon
        {
            night = false;
            evening = false;
            afternoon = true;
            morning = false;
        }
        else // evening
        {
            night = false;
            evening = true;
            afternoon = false;
            morning = false;
        }
    }
    /**
     * Returns whether it is the morning.
     */
    public static boolean getMorning()
    {
        return morning;
    }
    /**
     * Returns whether it is the night.
     */
    public static boolean getNight()
    {
        return night;
    }
    /**
     * Returns whether it is the evening.
     */
    public static boolean getEvening()
    {
        return evening;
    }
    /**
     * Returns whether it is the afternoon.
     */
    public static boolean getAfternoon()
    {
        return afternoon;
    }
    /**
     * Returns the no. of days elapsed.
     */
    public static int getDays()
    {
        return daysPassed;
    }
}

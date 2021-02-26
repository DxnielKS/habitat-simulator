import java.util.ArrayList;
import java.util.Random;
/**
 * Write a description of class Weather here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Weather
{
    // instance variables - replace the example below with your own
    
    private boolean clear, rain, storm, cloudy;
    private ArrayList<String> weather_list;
    /**
     * Constructor for objects of class Weather
     */
    public Weather()
    {
        // initialise instance variables
        weather_list = new ArrayList<String>();
        weather_list.add("Clear");
        weather_list.add("Rain");
        weather_list.add("Storm");
        weather_list.add("Cloudy");
        clear = rain = storm = cloudy = false;
    }

    public void change_weather()
    {
        return;
    }

    public String get_weather()
    {//clear, rain, storm, cloudy
        if (clear)
        {
            return "Clear";
        }
        else if (rain)
        {
            return "Rain";
        }
        return null;
    }
}

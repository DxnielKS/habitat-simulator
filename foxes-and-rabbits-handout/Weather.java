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

    private Random randomGenerator;
    private boolean clear, rain, storm, cloudy;
    private ArrayList<String> weather_list;
    /**
     * Constructor for objects of class Weather. The weather defaults to clear weather.
     */
    public Weather()
    {
        // initialise instance variables
        randomGenerator = new Random()
        weather_list = new ArrayList<String>();
        weather_list.add("Clear");
        weather_list.add("Rain");
        weather_list.add("Storm");
        weather_list.add("Cloudy");
        rain = storm = cloudy = false;
        clear = true;
    }
    /**
     * Method to change the weather randomly in the simulator
     */
    public void change_weather()
    {
        int new_weather_index = randomGenerator.nextInt(4)
        if (weather_list.get(new_weather_index) == "Clear")
        {
            clear = true;
            rain = storm = cloudy = false;
        }
        else if(weather_list.get(new_weather_index) == "Rain")
        {
            rain = true;
            clear = storm = cloudy = false;
        }
        else if (weather_list.get(new_weather_index) == "Storm")
        {
            storm = true;
            clear = rain = cloudy = false;
        }
        else if (weather_list.get(new_weather_index) == "Cloudy")
        {
            cloudy = true;
            rain = storm = clear = false;
        }
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
        else if (Storm)
        {
            return "Storm";
        }
        else if (Cloudy)
        {
            return "Cloudy";
        }
        }
        }
        return null;
    }
}

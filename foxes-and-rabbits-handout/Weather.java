import java.util.Random;
import java.util.HashMap;
/**
 * A class that stores the current weather states and methods to change weather.
 *
 * @author Daniel Saisani
 * @version 02/03/2021
 */
public class Weather
{
    // instance variables - replace the example below with your own

    private Random randomGenerator;
    private HashMap<String, Boolean> weather_map;
    /**
     * Constructor for objects of class Weather. The weather defaults to clear weather.
     */
    public Weather()
    {
        // initialise instance variables
        randomGenerator = new Random();
        weather_map = new HashMap<>();
        fill_weather_map();
    }
    /**
     * initialises the map of weather states
     */
    private void fill_weather_map()
    {
        weather_map.put("Clear",true);
        weather_map.put("Rain",false);
        weather_map.put("Storm",false);
        weather_map.put("Cloudy",false);
    }
    /**
     * A method to change the weather randomly
     */
    public void change_weather()
    {
        int new_weather_index = randomGenerator.nextInt(4);
        if (new_weather_index == 0)
        {
            weather_map.put("Clear",true);
            weather_map.put("Rain",false);
            weather_map.put("Storm",false);
            weather_map.put("Cloudy",false);
        }
        else if(new_weather_index == 1)
        {
            weather_map.put("Clear",false);
            weather_map.put("Rain",true);
            weather_map.put("Storm",false);
            weather_map.put("Cloudy",false);
        }
        else if (new_weather_index == 2)
        {
            weather_map.put("Clear",false);
            weather_map.put("Rain",false);
            weather_map.put("Storm",true);
            weather_map.put("Cloudy",false);
        }
        else if (new_weather_index == 3)
        {
            weather_map.put("Clear",false);
            weather_map.put("Rain",false);
            weather_map.put("Storm",false);
            weather_map.put("Cloudy",true);
        }
    }
/**
 * A method to return the current weather in the simulator
 */
    public String get_weather()
    {    //clear, rain, storm, cloudy
        String current_weather;
        for (String weather: weather_map.keySet())
        {
            if (weather_map.get(weather) == true)
            {
                return weather;
            }
        }
        return "none";
    }
}

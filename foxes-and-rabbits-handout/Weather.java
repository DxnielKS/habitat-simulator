import java.util.ArrayList;
import java.util.Random;
import java.util.HashMap;
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
    //private HashMap<> weather_map;
    /**
     * Constructor for objects of class Weather. The weather defaults to clear weather.
     */
    public Weather()
    {
        // initialise instance variables
        randomGenerator = new Random();
        weather_list = new ArrayList<String>();
        weather_list.add("Clear");
        weather_list.add("Rain");
        weather_list.add("Storm");
        weather_list.add("Cloudy");
        change_weather();
    }
    /**
     * Method to change the weather randomly in the simulator
     */
    public void change_weather()
    {
        int new_weather_index = randomGenerator.nextInt(4);
        if (weather_list.get(new_weather_index) == "Clear")
        {
            clear = true;
            rain = storm = cloudy = false;
            System.out.println("Weather changed to clear");
        }
        else if(weather_list.get(new_weather_index) == "Rain")
        {
            rain = true;
            clear = storm = cloudy = false;
            System.out.println("Weather changed to rain");
        }
        else if (weather_list.get(new_weather_index) == "Storm")
        {
            storm = true;
            clear = rain = cloudy = false;
            System.out.println("Weather changed to storm");
        }
        else if (weather_list.get(new_weather_index) == "Cloudy")
        {
            cloudy = true;
            rain = storm = clear = false;
            System.out.println("Weather changed to cloudy");
        }
    }

    public String get_weather()
    {    //clear, rain, storm, cloudy
        if (this.clear)
        {
            return "Clear";
        }
        else if (this.rain)
        {
            return "Rain";
        }
        else if (this.storm)
        {
            return "Storm";
        }
        else if (this.cloudy)
        {
            return "Cloudy";
        }
        return null;
    }
}

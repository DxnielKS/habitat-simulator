import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.Color;

/**
 * A simple predator-prey simulator, based on a rectangular field
 * containing rabbits and foxes.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2016.02.29 (2)
 */
public class Simulator
{
    // Constants representing configuration information for the simulation.
    // The default width for the grid.
    
    // a weather machine to change weather
    
    private static final int DEFAULT_WIDTH = 120;
    // The default depth of the grid.
    private static final int DEFAULT_DEPTH = 80;

    // The probability that of different classes that will be created in any given grid position.
    private static final double WORM_CREATION_PROBABILITY = 0.2;    
    private static final double EAGLE_CREATION_PROBABILITY = 0.14; 
    private static final double OWL_CREATION_PROBABILITY = 0.2;
    private static final double GRASS_CREATION_PROBABILITY = 0.02;
    private static final double SQUIREL_CREATION_PROBABILITY = 0.015;
    // The object used to implement weather
    private Weather weather_machine;
    // List of animals in the field.
    private List<Animal> animals;
    // List of plants in the field
    private List<Plants> plants;
    // The current state of the field.
    private Field field;
    // The current step of the simulation.
    private int step;
    // A graphical view of the simulation.
    private SimulatorView view;

 
    
    /**
     * Construct a simulation field with default size.
     */
    public Simulator()
    {
        this(DEFAULT_DEPTH, DEFAULT_WIDTH);
    }
    
    /**
     * Create a simulation field with the given size.
     * @param depth Depth of the field. Must be greater than zero.
     * @param width Width of the field. Must be greater than zero.
     */
    public Simulator(int depth, int width)
    {
        if(width <= 0 || depth <= 0) {
            System.out.println("The dimensions must be greater than zero.");
            System.out.println("Using default values.");
            depth = DEFAULT_DEPTH;
            width = DEFAULT_WIDTH;
        }
        weather_machine = new Weather();
        animals = new ArrayList<>();
        field = new Field(depth, width);
        plants = new ArrayList<>();

        // Create a view of the state of each location in the field.
        view = new SimulatorView(depth, width);
        view.setColor(Worm.class, Color.ORANGE);
        view.setColor(Squirrel.class, Color.BLUE);
        view.setColor(Eagle.class,Color.RED);
        view.setColor(Owl.class,Color.MAGENTA);
        view.setColor(Grass.class,Color.GREEN);

        // Setup a valid starting point.
        reset();
    }
    
    /**
     * Run the simulation from its current state for a reasonably long period,
     * (4000 steps).
     */
    public void runLongSimulation()
    {
        simulate(4000);
    }
    
    /**
     * Run the simulation from its current state for the given number of steps.
     * Stop before the given number of steps if it ceases to be viable.
     * @param numSteps The number of steps to run for.
     */
    public void simulate(int numSteps)
    {
        for(int step = 1; step <= numSteps && view.isViable(field); step++) {
            simulateOneStep();
            delay(60);   // uncomment this to run more slowly
        }
    }
    
    /**
     * Run the simulation from its current state for a single step.
     * Iterate over the whole field updating the state of each
     * fox and rabbit.
     */
    public void simulateOneStep()
    {
        step++;
        if (step % 10 == 0) // every 40 steps
        {
            weather_machine.change_weather(); // change the weather
        }
       
        field.getFieldTime().setTimeOfDay();// This changes the time of day
        
        setBackgroundColor(); //This uses the time of day to set a background colour

        // Provide space for newborn animals.
        List<Animal> newAnimals = new ArrayList<>();    
        List<Plants> newPlants = new ArrayList<>();
        // Let all rabbits act.
        
        for(Iterator<Plants> it = plants.iterator(); it.hasNext(); ) {
            Plants plants = it.next();

            plants.act(newPlants);

            
            if(! plants.isAlive()) {
                it.remove();
            }
        }  
        
      
        for(Iterator<Animal> it = animals.iterator(); it.hasNext(); ) {
             Animal animal = it.next();
            if (field.getFieldTime().getMorning() == false && (animal.getNocturnal() == true) )//nocturnal animals sleep in the morning
            {
                animal.act(newAnimals);
            }
            else if (field.getFieldTime().getNight() == false && animal.getNocturnal() == false)//Animals should be asleep at night
            {
                animal.act(newAnimals);
            if(! animal.isAlive()) {
                it.remove();
            }
            }
        }
               
        // Add the newly born foxes and rabbits to the main lists.
        animals.addAll(newAnimals);
        plants.addAll(newPlants);
        plantsInRain();
        view.setInfoText(field.getFieldTime().getStringTime()+" the weather is: "+weather_machine.get_weather());
        view.showStatus(step, field);
    }
    
        
    /**
     * Reset the simulation to a starting position.
     */
    public void reset()
    {
        step = 0;
        field.getFieldTime().setTime(0);
        animals.clear();
        plants.clear();
        populate();
        
        // Show the starting state in the view.
        view.showStatus(step, field);
        view.setInfoText(field.getFieldTime().getStringTime());
    }
    
    /**
     * Randomly populate the field with wildlife.
     */
    private void populate()
    {
        Random rand = Randomizer.getRandom();
        field.clear();
        for(int row = 0; row < field.getDepth(); row++) {
            for(int col = 0; col < field.getWidth(); col++) {
                if(rand.nextDouble() <= SQUIREL_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Squirrel squirel = new Squirrel(true, field, location);
                    animals.add(squirel);
                }
                else if(rand.nextDouble() <= WORM_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Worm worm = new Worm(true, field, location);
                    animals.add(worm); 
                }
                else if(rand.nextDouble() <= EAGLE_CREATION_PROBABILITY)
                {
                  Location location = new Location(row, col);
                  Eagle eagle = new Eagle(true, field, location);
                  animals.add(eagle);  
                }

                else if(rand.nextDouble() <= OWL_CREATION_PROBABILITY)
                {
                  Location location = new Location(row, col);
                  Owl owl = new Owl(true, field, location);
                  animals.add(owl);   
                }
                  else if(rand.nextDouble() <= GRASS_CREATION_PROBABILITY)
                {
                  Location location = new Location(row, col);
                  Grass grass = new Grass(field, location);
                  plants.add(grass);  

                }
                // else leave the location empty.
            }
        }
    }
    /**
     * This will find free locations within the field and spawn in grass at a random probability
     */
    private void populatePlants()
    {
            Random rand = Randomizer.getRandom();
            for(int row = 0; row < field.getDepth(); row++) {
                for(int col = 0; col < field.getWidth(); col++) {
                     if(rand.nextDouble() <= GRASS_CREATION_PROBABILITY)
                      {
                          Location location = new Location(row, col);
                          if (field.getObjectAt(location)==null)
                          {
                              Grass grass = new Grass(field, location);
                              plants.add(grass);
                            }
                }
            }
    }
    }
    
    /**
     * Pause for a given time.
     * @param millisec  The time to pause for, in milliseconds
     */
    private void delay(int millisec)
    {
        try {
            Thread.sleep(millisec);
        }
        catch (InterruptedException ie) {
            // wake up
        }
    }
    /**
     * This changes the background color based on the time of day e.g. Night time is black.
     */
    private void setBackgroundColor()
    {
        if (field.getFieldTime().getNight())
        {
            view.setEmptyColor(Color.black);
        }
        else if (field.getFieldTime().getMorning())
        {
            view.setEmptyColor(Color.white);
        }
        else
        {
            view.setEmptyColor(Color.gray);
        }
    }
    /**
     * This checks to see if it is raining - if so plants will grow in any location that is free
     */
    private void plantsInRain()
    { 
        if(weather_machine.get_weather() == "Rain")
        {
            populatePlants();
        }
    }
}

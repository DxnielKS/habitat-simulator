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
    private static final int DEFAULT_WIDTH = 120;
    // The default depth of the grid.
    private static final int DEFAULT_DEPTH = 80;
    // The probability that a fox will be created in any given grid position.
    private static final double SQUIREL_CREATION_PROBABILITY = 0.013;
    // The probability that a rabbit will be created in any given grid position.
    private static final double WORM_CREATION_PROBABILITY = 0.2;    

    private static final double EAGLE_CREATION_PROBABILITY = 0.13; 

    private static final double OWL_CREATION_PROBABILITY = 0.09;
    
    private static final double GRASS_CREATION_PROBABILITY = 0.06;

    // List of animals in the field.
    private List<Animal> animals;
    
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
        
        animals = new ArrayList<>();
        field = new Field(depth, width);
        plants = new ArrayList<>();

        // Create a view of the state of each location in the field.
        view = new SimulatorView(depth, width);
        view.setColor(Worm.class, Color.ORANGE);
        view.setColor(Squirel.class, Color.BLUE);
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
            // delay(60);   // uncomment this to run more slowly
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
        field.getFieldTime().setTimeOfDay();
        
        setBackgroundColor();

        // Provide space for newborn animals.
        List<Animal> newAnimals = new ArrayList<>();    
        List<Plants> newPlants = new ArrayList<>();
        // Let all rabbits act.
        
        for(Iterator<Plants> it = plants.iterator(); it.hasNext(); ) {
            Plants plants = it.next();
            if (field.getFieldTime().getNight() == false)
                {
                    plants.act(newPlants);
            }
            
            if(! plants.isAlive()) {
                it.remove();
            }
        }   
      
        for(Iterator<Animal> it = animals.iterator(); it.hasNext(); ) {
            Animal animal = it.next();
            if (field.getFieldTime().getNight() && (animal.getNocturnal() == true) )
            {
                animal.act(newAnimals);
            }
            else if (field.getFieldTime().getNight() == false && animal.getNocturnal() == false)
            {
                animal.act(newAnimals);
            }
            
            if(! animal.isAlive()) {
                it.remove();
            }
        }
               
        // Add the newly born foxes and rabbits to the main lists.
        animals.addAll(newAnimals);
        plants.addAll(newPlants);
        view.setInfoText(field.getFieldTime().getStringTime());
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
     * Randomly populate the field with foxes and rabbits.
     */
    private void populate()
    {
        Random rand = Randomizer.getRandom();
        field.clear();
        for(int row = 0; row < field.getDepth(); row++) {
            for(int col = 0; col < field.getWidth(); col++) {
                if(rand.nextDouble() <= SQUIREL_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Squirel squirel = new Squirel(true, field, location);
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
}

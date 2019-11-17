import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
/**
 * Workers - a subclass of Units that creates a workers that can go to a factory and help 
 * create different vehicles or build a new factory when needed.
 * 
 * @author Aristos Theocharoulas 
 * @version November 2019
 */
public class Workers extends Units
{
    enum Action{walking, building, working};
    private Action action = Action.walking;
    private boolean buildFactory;
    private boolean targetAcquired = false;
    private static boolean declaredImages = false;
    private static HashSet<int[]> set = new HashSet<int[]>();
    private int[][] factoryCoords;
    private int[] targetFactoryCoords;
    private ArrayList<Factories> factories;
    private Factories fact;
    private static int frameTime = 1000/24;
    private SimpleTimer animation = new SimpleTimer();

    private static GreenfootImage[] moveLeft = new GreenfootImage[9];
    private static GreenfootImage[] moveRight = new GreenfootImage[9];
    private static GreenfootImage[] slashImagesLeft = new GreenfootImage[6];
    private static GreenfootImage[] slashImagesRight = new GreenfootImage[6];

    private int imageIndex;
    private int slashIndex;
    /**
     * Constructor - creates a worker and manages the animations for each team
     * 
     * @param   red             specifies the team (true for red, false for blue)
     * @param   buildFactory    true when worker has to build a factory, false when he just goes to a factory
     */
    public Workers(boolean red, boolean buildFactory)
    {
        super(SpaceWorld.WORKER_SPEED, red);
        this.buildFactory = buildFactory;

        if(!declaredImages){
            for(int i=0;i<moveLeft.length;i++)
            {
                moveLeft[i]=new GreenfootImage("up"+i+".png");
            }
            for(int i=0;i<moveRight.length;i++)
            {
                moveRight[i]=new GreenfootImage("down"+i+".png");
            }
            for(int i=0;i<slashImagesLeft.length;i++)
            {
                slashImagesLeft[i]=new GreenfootImage("slashLeft"+i+".png");
            }
            for(int i=0;i<slashImagesRight.length;i++)
            {
                slashImagesRight[i]=new GreenfootImage("slashRight"+i+".png");
            }
            declaredImages=true;
        }
        
        if (red) {
            factoryCoords = SpaceWorld.FACTORY_RED_LOCATIONS;
            setImage(moveLeft[0]);
        } else {
            factoryCoords = SpaceWorld.FACTORY_BLUE_LOCATIONS;
            setImage(moveRight[0]);
        }
        animation.mark();
    }

    /**
     * Act - do whatever the NewWorker wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */

    public void act() 
    {
        if(action == Action.walking)
        {
            targetRandomFactory();
            if (targetFactoryCoords != null)
            {
                goToFactory();
                if(this.getWorld()!=null && (this.getX() == targetFactoryCoords[0] && this.getY() == targetFactoryCoords[1]))
                {
                    if(buildFactory && (!factoryExistsAt(this.getX(), this.getY()) || action == Action.building))
                    {
                        build();
                    }
                    List<Factories> facts = getWorld().getObjectsAt(this.getX(), this.getY(), Factories.class);
                    if(facts!=null && !facts.isEmpty())
                    {
                        facts.iterator().next().addWorker();
                        if (action == Action.walking)
                        {  
                            action = Action.working;
                            getWorld().removeObject(this);
                        }
                    }
                    else{
                        die();
                    }    
                }
            }
        }
        else if(action==Action.building) build();
        else if(!factoryExistsAt(this.getX(), this.getY())) die();
    }

    /**
     * animateWorkerMovement - animation used when the worker is walking
     */
    private void animateWorkerMovement()
    {
        if(action == Action.walking && animation.millisElapsed()>=frameTime)
        {
            animation.mark();
            if(this.getRed())
            {
                setImage(moveLeft[imageIndex]);
                imageIndex=(imageIndex+1)%moveLeft.length;
            }   
            else
            {
                setImage(moveRight[imageIndex]);
                imageIndex= (imageIndex+1)%moveRight.length;
            }   
        }
    }

    /**
     * animateBuildingMovement - animation used when the worker is building a factory
     */
    private void animateBuildingMovement()
    {
        animation.mark();
        if(action == Action.building && animation.millisElapsed()>=frameTime)
        {
            if(this.getRed())
            {
                setImage(slashImagesLeft[slashIndex]);
                slashIndex=(slashIndex+1)%slashImagesLeft.length;
            }
            else
            {
                setImage(slashImagesRight[slashIndex]);
                slashIndex=(slashIndex+1)%slashImagesRight.length;
            }

        }
    }

    /**
     * die - removes the worker from the world
     */
    protected void die()
    {
        SpaceWorld w = (SpaceWorld)getWorld();
        getWorld().removeObject(this);
    }

    /**
     * goToFactory - goes to the targeted factory (at targetFactoryCoords)
     */
    private void goToFactory()
    {
        if (targetFactoryCoords != null){
            //if a factory still exists at that location or if it doesnt exist but buildFactory is true
            if ( factoryExistsAt(targetFactoryCoords[0], targetFactoryCoords[1]) || (!factoryExistsAt(targetFactoryCoords[0], targetFactoryCoords[1]) && buildFactory))
            {
                if(action ==Action.walking)
                {
                    animateWorkerMovement();
                }
                else if(action ==Action.building)
                {
                    animateBuildingMovement();
                }
                //go to target;
                moveTowardTarget();
                setRotation(0);
            }
            else die();
        }
    }

    /**
     * build - worker goes to a random empty location and takes 20 seconds to build a factory
     */
    private void build()
    {
        if(action == Action.walking)
        {
            action = Action.building;
            fact = new Factories(true, this.red); 
            getWorld().addObject(fact,targetFactoryCoords[0],targetFactoryCoords[1]);
        }
        else if(action == Action.building)
        {
            animateBuildingMovement();
            if(fact!=null && fact.getWorld()!=null){  
                if(!fact.getNotBuilt()){
                    action = Action.working;
                    fact.addWorker();
                    getWorld().removeObject(this);
                }
            }
            else{
                set.remove(targetFactoryCoords);
                die();
            }    
        }
    }  

    /**
     * getDistance - Gets the Distance between two actors.
     * 
     * @param   a1      get the location of actor a1
     * @param   a2      get the distance from the location of actor a1 to actor a2
     * @return  double  the distance from a1 to a2
     */
    private double getDistance(Actor a1, Actor a2)
    {
        return Math.sqrt(Math.pow(a1.getX() - a2.getX(), 2) + Math.pow(a1.getY() - a2.getY(), 2));
    }

    /**
     * targetRandomFactory - targets a random factory from either the ones that exist in the world or the ones that need to be built
     */
    private void targetRandomFactory ()
    {
        if(targetFactoryCoords!=null)
        {
            return;
        }
        if(!targetAcquired){
            int[][] availableCoords = getAvailableCoords();
            if(availableCoords.length == 1)
            {
                targetFactoryCoords = availableCoords[0];
            }
            else if(availableCoords.length >1)
            {
                int index = Greenfoot.getRandomNumber (availableCoords.length);
                targetFactoryCoords = availableCoords[index];
            }
            targetAcquired = true;
            if(buildFactory) set.add(targetFactoryCoords);
        }
        else die();
    }

    /**
     * getAvailableCoords - gets the available coordinates from the factories and adds them to an array.
     * 
     * @return  int[][]     returns the available coordinates as an array
     */
    private int[][] getAvailableCoords()
    {
        List<int[]> availableCoords = new ArrayList<int[]>();

        for(int[] coords: factoryCoords)
        {
            List<Factories> facts = getWorld().getObjectsAt(coords[0], coords[1], Factories.class);
            if (buildFactory && (facts == null || facts.isEmpty()))
            {
                int[] arr = {coords[0],coords[1]};
                if(!set.contains(arr)){
                    availableCoords.add(coords);
                }
            }
            else if (!buildFactory && (facts != null && !facts.isEmpty() && !facts.iterator().next().isFull() && !facts.get(0).getNotBuilt()))
            {
                availableCoords.add(coords);
            }
        }
        return availableCoords.toArray(new int[0][]);
    }

    /**
     * factoryExistsAt - checks to see if factory exists at a certain location
     * 
     * @param   x           x coordinate on the world
     * @param   y           y coordinate on the world
     * 
     * @return  boolean     returns true if factory exists at x,y, false if not
     */
    private boolean factoryExistsAt(int x, int y) {
        List<Factories> factories = getWorld().getObjectsAt(x, y, Factories.class);

        if (factories != null && factories.size() > 0) {
            return true;
        }

        return false;
    }

    /**
     * moveTowardTarget - moves toward targetFactoryCoords at the set speed
     * 
     */
    private void moveTowardTarget()
    {
        turnTowards(targetFactoryCoords[0], targetFactoryCoords[1]);
        move (speed);
    }

}
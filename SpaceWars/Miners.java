import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
import java.util.List;
/**
 * Miners - a subclass of Units that creates a miner that goes to a random mine from those 
 * that exist in the world and makes $100 every 2 seconds for the team it is on.
 * 
 * @author Aristos Theocharoulas
 * @version November 2019
 */
public class Miners extends Units
{
    enum Action{walking, mining};
    private Action action = Action.walking;
    private Mines targetMine;
    private static boolean declaredImages = false;
    private static int frameTime = 1000/24;
    private SimpleTimer animation = new SimpleTimer();

    private static GreenfootImage[] moveUp = new GreenfootImage[9];
    private static GreenfootImage[] moveDown = new GreenfootImage[9];
    private int imageIndex;
    /**
     * Constructor - creates a miner and sets the image
     * 
     * @param   red     specifies the team (true for red, false for blue)
     */
    public Miners(boolean red)
    {
        super(SpaceWorld.MINER_SPEED, red);
        
        //declares initial images
        if(!declaredImages){
            for(int i=0;i<moveUp.length;i++)
            {
                moveUp[i]=new GreenfootImage("left"+i+".png");
            }
            for(int i=0;i<moveDown.length;i++)
            {
                moveDown[i]=new GreenfootImage("right"+i+".png");
            }
            declaredImages=true;
        }
        
        //sets the image
        if (red) {
            setImage(moveUp[0]);
        } else {
            setImage(moveDown[0]);
        }
    }

    /**
     * Act - do whatever the Miners wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {      
        animateMinerMovement();
        // If my current target Mine exists, move toward it
        if (targetMine != null && targetMine.getWorld() != null)
        {
            if(action == Action.walking)
            {
                moveTowardMine();
                setRotation(0);
                if(this.getX() == targetMine.getX() && this.getY() == targetMine.getY())
                {
                    action = Action.mining;
                    targetMine.incrementMiner();
                    getWorld().removeObject(this);
                }
            }
            else mine();
        }
        else if(action == Action.mining) die();
        else targetRandomMine();   
    }

    /**
     * animateMinerMovement - animation used when the miner is walking
     */
    private void animateMinerMovement()
    {
        if(action == Action.walking && animation.millisElapsed()>=frameTime) 
        {
            animation.mark();
            if(this.getRed())
            {
                setImage(moveUp[imageIndex]);
                imageIndex=(imageIndex+1)%moveUp.length;
            }   
            else
            {
                setImage(moveDown[imageIndex]);
                imageIndex= (imageIndex+1)%moveDown.length; 
            }
        }
    }

    /**
     * die - removes the miner from the world
     */
    protected void die()
    {
        SpaceWorld w = (SpaceWorld)getWorld();
        getWorld().removeObject(this);
    }

    /**
     * mine - makes money for the team 
     */
    public void mine()
    {
        if(action == Action.walking)
        {
            action = Action.mining;
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
     * getMines - add all the mines to an arraylist so that the miner can choose one randomly to go
     * 
     * @return  Arralist<Mines>     the mines arraylist
     */
    private ArrayList<Mines> getMines()
    {
        SpaceWorld w = (SpaceWorld)getWorld();
        ArrayList<Mines> allMines = (ArrayList)w.getObjects(Mines.class);
        ArrayList<Mines> mines = new ArrayList <Mines>();

        if(!allMines.isEmpty())
        {
            for(Mines mine: allMines)
            {
                if(this.getRed() == mine.getRed())
                {
                    mines.add(mine);
                }
            }   
        }
        return mines;
    }

    /**
     * targetRandomMine - targets a random mine from the number of mines that exist in the world
     */
    private void targetRandomMine ()
    {    
        if(targetMine!=null && targetMine.getWorld()!=null)
        {
            return;
        } else if (targetMine != null && targetMine.getWorld() == null)
        {
            targetMine = null;
        }
        ArrayList<Mines> mines = getMines();

        int numMines = mines.size();
        if (numMines> 0)
        {
            targetMine = mines.get(Greenfoot.getRandomNumber(numMines));
        }
        else 
        {
            this.die();
        }
    }

    /**
     * moveTowardMine - makes the miner ove toward the targeted Mine
     */
    private void moveTowardMine ()
    {
        turnTowards(targetMine.getX(), targetMine.getY());
        move (speed);
    }

}
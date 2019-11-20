import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;

/**
 * Mines - the money generating machines. Miners work in them to make money for the war 
 * effort. A maximum of 10 workers can work in each mine and the world starts off with 4
 * mines. Every mine starts off at level one, and can be levelled up to level 4. Higher level
 * mines make more money per miner. Once mines are destroyed, they cannot be rebuilt. 
 * 
 * @author Albert Lai
 * @version November 2019
 */
public class Mines extends Buildings
{
    //Declare instance variables
    private boolean addedToWorld = false;
    private int miners = 0;
    private int level = 1;
    private int capacity = SpaceWorld.MINE_CAPACITY;
    private int maxLevel = 4;
    private int moneyPerMiner = 1000;
    private SimpleTimer timer = new SimpleTimer();

    //Declare image arrays
    private static final GreenfootImage [] MineRed = {new GreenfootImage("MineRed1.png"),new GreenfootImage("MineRed2.png"),new GreenfootImage("MineRed3.png"),new GreenfootImage("MineRed4.png")};
    private static final GreenfootImage [] MineBlue = {new GreenfootImage("MineBlue1.png"),new GreenfootImage("MineBlue2.png"),new GreenfootImage("MineBlue3.png"),new GreenfootImage("MineBlue4.png")};

    /**
     * Constructor - creates a Mine and sets the image
     * 
     * @param red       specifies the team (true for red, false for blue)
     */
    public Mines(boolean red){
        super(red, SpaceWorld.MINE_HP); 
        //set mine image
        if(red) setImage(MineRed[0]);
        else setImage(MineBlue[0]);
        timer.mark();
    }

    /**
     * Act - do whatever the Mines wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        // Adds the mine to world arraylist 
        if(!addedToWorld){
            SpaceWorld world = (SpaceWorld)getWorld();
            world.addMinesArrayList(this);
            addedToWorld = true;
        }    

        //checks if the Mine is destroyed
        checkBlowUp();

        //make money
        if(timer.millisElapsed()>=2000){
            timer.mark();
            SpaceWorld world = (SpaceWorld)getWorld();
            world.updateStat(4,red,miners*moneyPerMiner*level);
        }    
    }    

    /**
     * checkBlowUp - checks if the Mine is destroyed and if it is, removes it from the 
     * world, creates an explosion, and updates the world stat
     */
    protected void checkBlowUp(){
        if(hp <= 0) {
            SpaceWorld world = (SpaceWorld)getWorld();
            //updates stat, adds explosion, and removes the mine
            world.updateStat(1,red,-1);
            world.updateStat(2,red,-miners);
            world.addObject(new Explosion(), getX(), getY());
            world.removeMinesArrayList(this);
            world.removeObject(this);
            if(healthbar!= null) world.removeObject(healthbar);
        }
    }    

    /**
     * isFull - checks if the mines is at full capacity
     * 
     * @return boolean         true if mines is full and false if not
     */
    public boolean isFull(){
        return miners >= capacity;
    }

    /**
     * incrementMiner - increases the numbers of miners by 1
     */
    public void incrementMiner(){
        if(!isFull()) miners++;
    }    

    /**
     * getMineLevel - returns the level of the mine
     * 
     * @return int         the level of the mine
     */
    public int getMineLevel(){
        return level;
    }

    /**
     * levelUp - levels up the factory and sets the image accordingly
     */
    public void levelUp(){
        if(level<maxLevel){
            level++;
            levelUpSound.play();
        }
        //set image
        if (red) setImage(MineRed[level-1]);
        else setImage(MineBlue[level-1]);
    }    
}
import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
/**
 * Abstract class for all vehicles that target and attack enemies
 * 
 * @author Henry Ma, and Albert Lai Debugging(Star Xie) 
 * @version November 2019
 */
public abstract class Vehicles extends Units
{
    //Health Bar for all vehicles
    protected HealthBar healthbar;
    //Helps to count acts before another attack can occur
    protected int reloadCounter;
    //Current hp of vehicle
    protected int hp;
    //Used to determine closest targets
    protected double buildingDistance;
    protected double enemyDistance;
    protected double closestEnemyDistance;
    protected double closestEnemyBuildingDistance;
    //Used to check for, and determine closest targets
    protected ArrayList<Vehicles> allEnemies = null;
    protected ArrayList<Buildings> allBuildings = null;
    protected ArrayList<Vehicles> checkVehicles = null; 
    //Sounds effects for vehicles
    protected static final GreenfootSound death = new GreenfootSound("Death.wav");
    //Color of Health Bar
    protected Color hpColor = Color.GREEN;
    //Closest enemy vehicle available
    protected Vehicles closestEnemy = null;
    //Closest enemy building available
    protected Buildings closestEnemyBuilding = null;
    /**
     * Constructor -  constructor that sets initial values for subclasses
     * 
     * @param speed     Speed of the vehicle
     * @param red       Determines the team of the ship(true for red, false for blue)
     * @param hp        Max health points of the ship
     */
    public Vehicles (int speed, boolean red, int hp)
    {
        //Calls super constructor to set specified values
        super(speed,red);
        //Sets hp for the vehicle
        this.hp = hp;
        //Sets sound effects volume
        death.setVolume(1);

    }    

    /**
     * A Greenfoot method to run through code once
     * when object is added to world
     * 
     * @param w         World that healthbar is added to
     */
    public void addedToWorld(World w)
    {
        closestEnemy = (Vehicles)getClosestEnemy();
        closestEnemyBuilding = (Buildings)getClosestEnemyBuilding();
    }

    /**
     * Gets the closest enemy building relative to this actor
     */
    protected Actor getClosestEnemyBuilding()
    {
        //Building that is returned as the closest one
        Actor a = null;
        //Sets an array list of buildings
        allBuildings = (ArrayList)getWorld().getObjects(Buildings.class);
        //Sets an initial value for closestBuilding distance 
        closestEnemyBuildingDistance = 100000;
        //Runs through buildings array list
        for(int i = 0; i < allBuildings.size() ; i++)
        {
            //Check if building is on opposite team
            if (allBuildings.get((i)).getRed() != getRed())
            {
                //Gets building distance from this object
                buildingDistance = getDistance(allBuildings.get((i)));
                //Compares building distance to closest enemy
                //building distance
                if(buildingDistance < closestEnemyBuildingDistance)
                {
                    //Sets a new closest enemy building distance
                    closestEnemyBuildingDistance = buildingDistance;
                    //Sets closest building so far in the array
                    a = allBuildings.get((i));
                }
            }
        }
        return a;
    }

    /**
     * Gets closest enemy vehicle relative to this actor
     */
    protected Actor getClosestEnemy()
    {
        //Enemy vehicle that is returned as the closest one
        Actor a = null;
        //Sets an array list of vehicles
        allEnemies = (ArrayList)getWorld().getObjects(Vehicles.class);
        //Sets an initial value for closest enemy distance 
        closestEnemyDistance = 100000;
        //Runs through all enemies array list
        for(int i = 0; i < allEnemies.size() ; i++)
        {
            //Check if vehicle is on opposite team
            if (allEnemies.get((i)).getRed() != getRed())
            {
                //Gets vehicle distance from this object
                enemyDistance = getDistance(allEnemies.get((i)));
                //Compares vehicle distance to closest enemy
                //vehicle distance
                if(enemyDistance < closestEnemyDistance)
                {
                    //Sets a new closest enemy distance
                    closestEnemyDistance = enemyDistance;
                    //Sets closest enemy so far in the array
                    a = allEnemies.get((i));
                }
            }  
        }
        return a;
    }

    /**
     * Get distance from an object relative to
     * the object that calls this method
     * 
     * @param b         Actor that you want the distance between
     */
    protected double getDistance(Actor b)
    {
        //Returns distance from this actor to another
        return Math.sqrt(Math.pow(this.getX() - b.getX(), 2) + Math.pow(this.getY() - b.getY(), 2));
    }

    /**
     * Checks hp of object and acts acordingly
     */
    protected void checkHp()
    {
        //Check if hp is at or below 0
        if(hp <= 0)
        {
            //Play sound and remove objects
            death.play();
            SpaceWorld world = (SpaceWorld)getWorld();
            world.addObject(new Explosion(), getX(), getY());
            world.updateStat(3,!red,1);
            world.updateStat(2,red,-1);
            world.removeObject(this);
            world.removeObject(healthbar);
        }
    }

    /**
     * Reduces hp of object that calls this
     * 
     * @param damage        damage that actor takes
     */
    public void getHit(int damage)
    {
        //checks if healthbar exists and if not, makes one
        if(healthbar==null){
            healthbar = new HealthBar(50,5,hp,hpColor);
            getWorld().addObject(healthbar, getX(), getY() - 30);
            healthbar.update(hp, hpColor);
        }
        hp -= damage;
        //Update healthbar
        healthbar.update(hp, hpColor);
    }

    /**
     * Creates a bullet to damage enemy team
     * 
     * @param r Team the bullet is on
     * @param a Actor that bullet turn towards
     * @param damage Damage that bullet deals
     */
    protected void attack(boolean r, Actor a, int damage)
    {
        //Add bullet to world
        getWorld().addObject(new Bullet(r, a, damage), getX(), getY());
    }
}
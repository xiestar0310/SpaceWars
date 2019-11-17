import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
/**
 * Vikings attacks enemy vehicles when present in the world
 * otherwise attack buildings
 * Deals damage through bullets
 * 
 * @author Henry Ma, Debugging(Albert Lai and Star Xie) 
 * @version November 2019
 */
public class Vikings extends Vehicles
{
    //declare images
    private static final GreenfootImage vikingRed = new GreenfootImage("VikingRed.png");
    private static final GreenfootImage vikingBlue = new GreenfootImage("VikingBlue.png");
    /**
     * Constructor for Vikings that sets initial values and images
     * 
     * @param red       Team of the unit(true for red, false for blue)
     */
    public Vikings(boolean red)
    {
        //Calls super constructor to set initial values
        super(SpaceWorld.VIKING_SPEED, red, SpaceWorld.VIKING_HP);
        //Sets image depending on team
        if(red) setImage(vikingRed);
        else setImage(vikingBlue);
    }
    /**
     * Checks if enemies vehicles are in the world
     */
    private boolean checkEnemies()
    {
        //Create an array for checking enemy vehicles
        checkVehicles = (ArrayList)getWorld().getObjects(Vehicles.class);
        //If size is zero then return false
        if(checkVehicles.size() == 0)
        {
            return false;
        }
        //If one enemy exist return true
        for(int i = 0; i < checkVehicles.size(); i++)
        {
            //Checked team of vehicle at index
            if(checkVehicles.get((i)).getRed() != this.getRed())
            {
                return true;
            }
        }
        return false;     
    }
    /**
     * Act - Checks hp if zero and removes itself if true, if not vehicle
     * will attack vehicles first then buildings if no vehicles
     */    
    public void act()
    {
        checkHp();
        if(getWorld()!=null)
        {
            targetObject();
        }
    } 
    /**
     * Targets enemies vehicles to attack if no vehicles it will attack
     * buildings
     */
    private void targetObject()
    {
        //Checks if there are enemy vehicles or not
        if(!checkEnemies())
        { 
            //Checks if it has a building to target
            if(closestEnemyBuilding!=null && closestEnemyBuilding.getWorld() != null)
            {
                turnTowards(closestEnemyBuilding.getX(), closestEnemyBuilding.getY());
                //Checks if in range to attack
                if(getDistance(closestEnemyBuilding) > SpaceWorld.VIKING_RANGE)
                {
                    move(SpaceWorld.VIKING_SPEED);
                    if(healthbar!=null) healthbar.follow(getX(), getY() - 40);
                }
                //Attack building when in range
                else
                {
                    if(reloadCounter == SpaceWorld.VIKING_RELOAD_TIME)
                    {
                        attack(getRed(), closestEnemyBuilding, SpaceWorld.VIKING_DAMAGE);
                        reloadCounter = 0;
                    }
                    else
                    {
                        reloadCounter++;
                    }
                }
            }
            //Get a closest building to attack
            else
            {
                closestEnemyBuilding = (Buildings)getClosestEnemyBuilding();
            }
        }
        //Attack vehicles
        else
        {
            //Checks if it has a vehicle to attack
            if(closestEnemy!=null && closestEnemy.getWorld() != null)
            {   
                turnTowards(closestEnemy.getX(), closestEnemy.getY());
                //Checks if in range to attack
                if(getDistance(closestEnemy) > SpaceWorld.VIKING_RANGE)
                {
                    move(SpaceWorld.VIKING_SPEED);
                    if(healthbar!=null) healthbar.follow(getX(), getY() - 40);
                }
                //Attack enemy when in range
                else
                {
                    if(reloadCounter == SpaceWorld.VIKING_RELOAD_TIME)
                    {
                        attack(getRed(), closestEnemy, SpaceWorld.VIKING_DAMAGE);
                        reloadCounter = 0;
                    }
                    else
                    {
                        reloadCounter ++;
                    }
                }                
            }
            //Get the closest enemy to attack
            else
            {
                closestEnemy = (Vehicles)getClosestEnemy();
            }
        }
    }
}

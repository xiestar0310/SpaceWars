import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;

/**
 * Nightingale defends base when enemy vehicles are present
 * otherwise attack buildings, while using bullets to deal damage
 * 
 * @author Henry Ma, Debugging(Albert Lai and Star Xie) 
 * @version November 2019
 */
public class Nightingale extends Vehicles
{
    //declare images
    private static final GreenfootImage nightingaleRed = new GreenfootImage("NightingaleRed.png");
    private static final GreenfootImage nightingaleBlue = new GreenfootImage("NightingaleBlue.png");
    /**
     * Constructor for Nightingale that sets initial values and images
     * 
     * @param red       Team of the unit(true for red, false for blue)
     */
    public Nightingale(boolean red)
    {
        //Calls super constructor to set initial values
        super(SpaceWorld.NIGHTINGALE_SPEED, red, SpaceWorld.NIGHTINGALE_HP);
        //Sets image depending on team
        if(red) setImage(nightingaleRed);
        else setImage(nightingaleBlue);
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
     * Act - Checks if it enemies are in the world if not it attacks buildings, 
     * while also removing itself if hp is 0
     */
    public void act()
    { 
        //Removes object if hp is 0
        checkHp();
        //Checks if this object is in the world or not
        if(this.getWorld()!=null)
        {
            //Checks if enemies are in the world
            if(checkEnemies() == false)
            {
                //Check if it has a target building
                if(closestEnemyBuilding!= null && closestEnemyBuilding.getWorld()!= null)
                {
                    turnTowards(closestEnemyBuilding.getX(), closestEnemyBuilding.getY());
                    //Checks if in range to attack
                    if(getDistance(closestEnemyBuilding) > SpaceWorld.NIGHTINGALE_RANGE)
                    {
                        move(SpaceWorld.NIGHTINGALE_SPEED);
                        if(healthbar!=null) healthbar.follow(getX(), getY() - 40);
                    }
                    //Attack when in range
                    else
                    {
                        if(reloadCounter == SpaceWorld.NIGHTINGALE_RELOAD_TIME)
                        {
                            attack(red, closestEnemyBuilding, SpaceWorld.NIGHTINGALE_DAMAGE);
                            reloadCounter = 0;
                        }
                        else
                        {
                            reloadCounter ++;
                        }
                    }
                }
                //Get a building to attack
                else
                {
                    closestEnemyBuilding = (Buildings)getClosestEnemyBuilding();
                }
            }
            //Checks if nightingale is close enough to its command centre
            //while also defending buildings
            else
            {
                //Create a list for commandCentre buildings
                List<CommandCentre> checkCommandCentre = (List)getWorld().getObjects(CommandCentre.class);
                //Loop through list to find teams command centre
                for(int i = 0; i < checkCommandCentre.size(); i ++)
                {
                    //Check if command centre is on team
                    if(checkCommandCentre.get((i)).getRed() == this.red)
                    {
                        //the +10 sets the offset
                        //Check if vehicle is close enough to command centre
                        if(getDistance(checkCommandCentre.get((i))) > SpaceWorld.NIGHTINGALE_TETHER_RANGE+10)
                        {
                            //Move towards command centre until close enough
                            turnTowards(checkCommandCentre.get((i)).getX(), checkCommandCentre.get((i)).getY());
                            move(speed);
                            if(healthbar!=null) healthbar.follow(getX(), getY() - 40);
                        }
                        //Attack enemy vehicles in range
                        else 
                        {
                            //Checks if it has a target
                            if(closestEnemy != null && closestEnemy.getWorld() != null)
                            {
                                turnTowards(closestEnemy.getX(), closestEnemy.getY());
                                //-10 for offset
                                //Checks if it is in range to attack
                                if(getDistance(closestEnemy) > SpaceWorld.NIGHTINGALE_RANGE && getDistance(checkCommandCentre.get((i)))<SpaceWorld.NIGHTINGALE_TETHER_RANGE-10)
                                {
                                    //Move vehicle
                                    move(speed);
                                    if(healthbar!=null) healthbar.follow(getX(), getY() - 40);
                                }
                                //Attack vehicle when in range
                                else
                                {
                                    if(reloadCounter == SpaceWorld.NIGHTINGALE_RELOAD_TIME)
                                    {
                                        attack(red, closestEnemy, SpaceWorld.NIGHTINGALE_DAMAGE);
                                        reloadCounter = 0;
                                    }
                                    else
                                    {
                                        reloadCounter ++;
                                    }
                                }
                            }
                            else
                            {
                                closestEnemy = (Vehicles)getClosestEnemy();
                            }
                        }
                    }
                }
            }
        }
    }
}
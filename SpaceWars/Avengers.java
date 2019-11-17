import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Avengers targets and attacks buildings
 * Shoots bullets to deal damage
 * 
 * @author Henry Ma, Debugging(Albert Lai and Star Xie) 
 * @version November 2019
 */
public class Avengers extends Vehicles
{
    //declare images
    private static final GreenfootImage avengerRed = new GreenfootImage("AvengerRed.png");
    private static final GreenfootImage avengerBlue = new GreenfootImage("AvengerBlue.png");
    /**
     * Constructor for avengers that sets initial values and images
     * 
     * @param red       Team of the avenger(true for red, false for blue)
     */
    public Avengers(boolean red)
    {
        //Calls super constructor to set initial values
        super(SpaceWorld.AVENGER_SPEED, red, SpaceWorld.AVENGER_HP);
        //Sets image depending on team
        if(red) setImage(avengerRed);
        else setImage(avengerBlue);
    }    
    /**
     * Act - Checks if it has a target building and if so target and damage
     * said building, otherwise find target, while also removing itself
     * if hp is 0
     */
    public void act() 
    {
        //Removes object if hp is 0
        checkHp();
        //Checks if this object is in the world or not
        if(this.getWorld()!=null)
        {
            //Checks if it has a target or not
            if(closestEnemyBuilding!= null && closestEnemyBuilding.getWorld()!= null)
            {
                //Turn towards target
                turnTowards(closestEnemyBuilding.getX(), closestEnemyBuilding.getY());
                //Checks if in range to attack
                if(getDistance(closestEnemyBuilding) > SpaceWorld.AVENGER_RANGE)
                {
                    //Moves vehicle and its healthbar
                    move(speed);
                    if(healthbar!=null) healthbar.follow(getX(), getY() - 40);
                }
                //Attacks building
                else
                {
                    //Checks if it can shoot
                    if(reloadCounter == SpaceWorld.AVENGER_RELOAD_TIME)
                    {
                        //Attack building
                        attack(red, closestEnemyBuilding, SpaceWorld.AVENGER_DAMAGE);
                        //Set number of acts before shooting to 0
                        reloadCounter = 0;
                    }
                    else
                    {
                        reloadCounter ++;
                    }
                }
            }
            //Gets closest building to target
            else
            {
                closestEnemyBuilding = (Buildings)getClosestEnemyBuilding();
            }
        }
    }    
}
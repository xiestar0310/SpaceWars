import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Hyperion targets and attacks all enemy objects
 * while also shooting missiles instead of bullets
 * 
 * @author Henry Ma, Debugging(Albert Lai and Star Xie) 
 * @version November 2019
 */
public class Hyperion extends Vehicles
{
    //Target that the Hyperion attacks
    private Actor enemy = null;
    //declare images
    private static final GreenfootImage hyperionRed = new GreenfootImage("HyperionRed.png");
    private static final GreenfootImage hyperionBlue = new GreenfootImage("HyperionBlue.png");
    /**
     * Constructor for Hyperion that sets initial values and images
     * 
     * @param red       Team of the unit(true for red, false for blue)
     */
    public Hyperion(boolean red)
    {
        //Calls super constructor to set initial values
        super(SpaceWorld.HYPERION_SPEED, red, SpaceWorld.HYPERION_HP);
        //Sets image depending on team
        if(red) setImage(hyperionRed);
        else setImage(hyperionBlue);
    } 

    /**
     * Shoots and creates a missile that deals damage
     * 
     * @param r         Team of the missile(attacks opposite team)
     * @param a         Actor the missile targets
     * @param damage    Damage the missile does
     */
    private void shootMissile(boolean r, Actor a, int damage)
    {
        //Add missile to world with given parameters
        getWorld().addObject(new Missile(r, a, damage), getX(), getY());
    }    

    /**
     * Act - Checks for enemies and targets said enemies,
     * then shoots missiles if in range
     */
    public void act() 
    {
        //Removes object if hp is 0
        checkHp();
        //Checks if this object is in the world or not
        if(this.getWorld()!=null)
        {
            //Checks if it has a target or not
            if(enemy != null && enemy.getWorld() != null)
            {
                //Turn towards target
                turnTowards(enemy.getX(), enemy.getY());
                //Checks if in range to attack
                if(getDistance(enemy) > SpaceWorld.HYPERION_RANGE)
                {
                    //Moves vehicle and its healthbar
                    move(speed);
                    if(healthbar!=null) healthbar.follow(getX(), getY() - 40);
                }
                else
                {
                    //Checks if it can shoot
                    if(reloadCounter == SpaceWorld.HYPERION_RELOAD_TIME)
                    {
                        //Attack enemy
                        shootMissile(red, enemy, SpaceWorld.HYPERION_DAMAGE);
                        //Set number of acts before shooting to 0
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
                //Set closest enemy and building
                closestEnemy = (Vehicles)getClosestEnemy();
                closestEnemyBuilding = (Buildings)getClosestEnemyBuilding();
                //Attacks vehicles first
                if(closestEnemy != null && closestEnemy.getWorld() != null){
                    enemy = closestEnemy;
                }
                //Otherwise attacks buildings to win
                else
                {
                    enemy = closestEnemyBuilding;
                }
            }
        }
    }    
}
import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Abstract class of all dynamic and moving objects used to deal damage.
 * 
 * @author Star Xie 
 * @version November 2019
 */
public abstract class Ammunition extends Actor
{
    //Declare all instance variables
    protected boolean red;
    protected int speed;
    protected int damage;
    protected Actor target;
    /**
     * Constructor - calls the superclass and initializes values 
     * 
     * @param red               specifies the team (true for red, false for blue)
     * @param actor             the specific object that is being targeted
     * @param damage            specifies the damage taken for each hit
     * @param speed             the speed that the Ammunition travels at
     */
    public Ammunition (boolean red, Actor actor, int damage, int speed)
    {
        //Instantiates variables
        target = actor;
        this.red = red;
        this.damage=damage;
        this.speed=speed;
    }    

    /**
     * checkAndHit - checks if the Ammunition has hit a Vehicle or Building. To be implemented in subclasses
     */
    protected abstract void checkAndHit();
}

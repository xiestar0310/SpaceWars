import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Super class for all units in the game that move and have a team
 * 
 * @author Albert Lai, Henry Ma 
 * @version November 2019
 */
public abstract class Units extends Actor
{
    //Movement speed of unit
    protected int speed;
    //Team of the unit
    protected boolean red;
    /**
     *  Constructor - Constructor for all subclasses
     *  
     *  @param speed    Speed of unit(amount moved per move)
     *  @param red      Team of unit(true for red, false for blue)
     */
    public Units(int speed, boolean red)
    {
        //Sets speed of unit(amount unit moves per move)
        this.speed = speed;
        //Sets team of unit(true for red, false for blue)
        this.red=red;
    }
    /**
     * Returns the team of the unit
     */
    public boolean getRed()
    {  
        return red;
    }
}
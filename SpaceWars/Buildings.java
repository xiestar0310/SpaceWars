import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Buildings - abstract class of all standing unmoving big structures. 
 * 
 * @author Albert Lai and Alex Li
 * @version November 2019
 */
public abstract class Buildings extends Actor
{
    //Declare all instance variables
    protected int hp;
    protected boolean red;
    protected HealthBar healthbar;
    
    //Declare sound
    protected GreenfootSound levelUpSound = new GreenfootSound("LevelUp.wav");
    
    /**
     * Constructor - calls the superclass and creates the healthbar
     * 
     * @param red               specifies the team (true for red, false for blue)
     * @param hp                the amount of hp of the building
     */
    public Buildings(boolean red, int hp){
        this.red = red;
        this.hp = hp;
    }
    
    /**
     * getRed - returns the team of the building
     * 
     * @return boolean           specifies the team (true for red, false for blue)
     */
    public boolean getRed(){
        return red;
    }

    /**
     * getHit - deals damage to the building 
     * 
     * @param damage            amount of damage to transfer to building
     */
    public void getHit(int damage){
        //check if healthbar exists and creates one if not
        if(healthbar==null){
            healthbar = new HealthBar(50,5,hp,Color.GREEN);
            getWorld().addObject(healthbar, getX(), getY()-40);
            healthbar.update(hp, Color.GREEN);
        }    
        //deals damage
        hp-=damage;        
        healthbar.update(hp, Color.GREEN);
    }    
    
    /**
     * checkBlowUp - checks if building is destroyed. To be implemented in subclasses
     */
    protected abstract void checkBlowUp();
}

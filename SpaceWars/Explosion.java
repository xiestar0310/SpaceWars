import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Explosion is an actor that plays an explosion when added to to world
 * 
 * @author Alex Li 
 * @version 1.1.1
 */
public class Explosion extends Actor
{
    //instance variable 
    private int i = 0;
    //creates a frame by frame array to store animation
    private static final GreenfootImage[] frames = {new GreenfootImage ("Explosion1.2.png"),new GreenfootImage ("Explosion1.3.png"),new GreenfootImage ("Explosion1.4.png"),
            new GreenfootImage ("Explosion1.5.png"),new GreenfootImage ("Explosion2.1.png"),new GreenfootImage ("Explosion2.2.png"),new GreenfootImage ("Explosion2.3.png"),
            new GreenfootImage ("Explosion2.4.png"),new GreenfootImage ("Explosion2.5.png"),new GreenfootImage ("Explosion3.1.png"),new GreenfootImage ("Explosion3.2.png"),
            new GreenfootImage ("Explosion3.3.png"),new GreenfootImage ("Explosion3.4.png"),new GreenfootImage ("Explosion3.5.png"),new GreenfootImage ("Explosion4.1.png"),
            new GreenfootImage ("Explosion4.2.png")};

    //explosion sound
    private static final GreenfootSound explosionSound = new GreenfootSound("Death.wav");

    /**
     * Act - plays and explosion. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        //plays animation with a delay between frames
        explosionSound.play();
        i = (i+1)%(frames.length); 
        setImage(frames[i]);
        if(i == 0) getWorld().removeObject(this);
    }  
}
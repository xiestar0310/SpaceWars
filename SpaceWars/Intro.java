import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Introduction to the game
 * 
 * @author Albert Lai
 * @version November 2019
 */
public class Intro extends World
{
    //Declare button
    private TextButton startBtn = new TextButton("Start!", 50, true);
    
    /**
     * Adds the button to begin the simulation
     * 
     */
    public Intro()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(960, 640, 1); 
        
        addObject(startBtn,480,500);
    }
    
    /**
     * Act - checks if the reStart button is pressed. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act(){
        //starts new game if button is pressed
        if(Greenfoot.mousePressed(startBtn)){
            StartScreen startScreen = new StartScreen();
            Greenfoot.setWorld(startScreen);
        } 
    } 
}

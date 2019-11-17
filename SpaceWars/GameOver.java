import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * GameOver - the game over screen, which shows up when the game ends. It shows the
 * stats of length of game and the kills per each side. It also has a button to 
 * allow the user to restart the game.
 * 
 * @author Albert Lai
 * @version November 2019
 */
public class GameOver extends World
{
    //Declare instance variables
    private String time;
    
    //Declare greenfoot images
    private GreenfootImage redWin = new GreenfootImage ("EndScreenRedWin.png");
    private GreenfootImage blueWin = new GreenfootImage ("EndScreenBlueWin.png");
    
    //Declare Label
    private Label timeStats, redKillStats, blueKillStats;
    
    //Declare button
    private TextButton reStartBtn = new TextButton("Restart", 50, true);
    
    //Declare sound
    protected static final GreenfootSound victory = new GreenfootSound("Victory.mp3");
    
    /**
     * Constructor - sets the image, adds the labels and buttons, and plays the music
     * 
     * @param red               specifies the winning team (true for red, false for blue)
     * @param milliseconds      how long the simulation lasted in milliseconds 
     * @param redKills          amount of red kills
     * @param blueKills         amount of blue kills
     */
    public GameOver(boolean red, int milliseconds, int redKills, int blueKills)
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(960, 640, 1); 
        
        //set background
        if(red) setBackground(redWin);
        else setBackground(blueWin);
        
        //time stats
        if((milliseconds/1000)%60<10) time =milliseconds/60000 +":0"+ (milliseconds/1000)%60;
        else time = milliseconds/60000 +":"+ (milliseconds/1000)%60;
        timeStats = new Label ("Length of Game: " + time, 50, red);
        addObject(timeStats, 480, 375);
        
        //kills stats
        redKillStats = new Label ("Red Kills: " + redKills, 35, true);
        blueKillStats = new Label ("Blue Kills: " + blueKills, 35, false);
        if(red){
            addObject(redKillStats,480, 445);
            addObject(blueKillStats, 480, 480);
        }
        else{
            addObject(blueKillStats,480, 445);
            addObject(redKillStats, 480, 480);
        }
        
        //add button
        addObject(reStartBtn,480,550);
        
        //sound effects
        victory.setVolume(100);
        victory.play();
    }
    
    /**
     * Act - checks if the reStart button is pressed. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act(){
        //starts new game if button is pressed
        if(Greenfoot.mousePressed(reStartBtn)){
            StartScreen startScreen = new StartScreen();
            Greenfoot.setWorld(startScreen);
        } 
    }    
}

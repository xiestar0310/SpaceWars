import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A Generic Button to display text that is clickable. 
 * 
 * This should be added to, and controlled by, a world.
 * 
 * @author Jordan Cohen 
 * @author Alex Li
 * @version v1.0.1
 */
public class TextButton extends Actor
{
    // Declare variables
    private GreenfootImage myImage;
    private GreenfootImage myAltImage;
    private String buttonText;
    private int textSize, statValue;
    private boolean isRed, regularBtn;
    /**
     * Constructs a TextButton given a String and a text size
     * @param String        String to be displayed
     * @param textSize      The size of the text to be displayed
     * @param isRed         Changes the colour of the text depending on isRed
     * @param statValue     The value of the stat that the button holds
     */
    public TextButton (String text, int textSize, boolean isRed, int statValue){
        // Assign value to my internal String
        buttonText = text;
        //sets the text size
        this.textSize = textSize;
        //sets isRed
        this.isRed = isRed;
        //sets statValue
        this.statValue = statValue;
        // Draw a button with centered text:
        updateMe (text);
    } 
    /**
     * Creates a default button that only holds text. It has black text, a black border, and a white background. This button will not display as a 
     * regular button if regularBtn is false
     * @param String        String to be displayed
     * @param textSize      The size of the text to be displayed
     * @param regularBtn    A boolean to state whether this is a regular button or not
     */
    public TextButton (String text, int textSize, boolean regularBtn){
        // Assign value to my internal String
        buttonText = text;
        //sets text size
        this.textSize = textSize;
        //sets whether or not this is a regular Btn
        this.regularBtn = regularBtn;
        // Draw a button with centered text:
        updateMe (text);
    } 
    /**
     * Updates the current TextButton text
     * @param text      The text to be displayed
     */
    public void updateMe (String text)
    {
        //if it is a regular button, the text colour will be black on a white background. It will also have a black border
        if(regularBtn){
            buttonText = text;
            GreenfootImage tempTextImage = new GreenfootImage (text, textSize, Color.BLACK, Color.WHITE);
            
            myImage = new GreenfootImage (tempTextImage.getWidth() + 8, tempTextImage.getHeight() + 8);
            myImage.setColor (Color.WHITE);
            myImage.fill();
            myImage.drawImage (tempTextImage, 4, 4);

            myImage.setColor(Color.BLACK);
            myImage.drawRect (0,0,tempTextImage.getWidth() + 7, tempTextImage.getHeight() + 7);
            setImage(myImage);
            
            tempTextImage = new GreenfootImage (text, textSize, Color.WHITE, Color.BLACK);
            myAltImage = new GreenfootImage(tempTextImage.getWidth() + 8, tempTextImage.getHeight() + 8);
            myAltImage.setColor (Color.WHITE);
            myAltImage.fill();
            myAltImage.drawImage (tempTextImage, 4, 4);

            myAltImage.setColor(Color.BLACK);
            myAltImage.drawRect (0,0,tempTextImage.getWidth() + 7, tempTextImage.getHeight() + 7);
        }
        //if it isn't a regular button, it will have different text colours and border colours depending on isRed. 
        //if isRed is true, the text and border colour will be red, otherwise it will be blue
        else{
            buttonText = text;
            GreenfootImage tempTextImage = new GreenfootImage (text, textSize, Color.BLACK, Color.WHITE);
            myImage = new GreenfootImage (tempTextImage.getWidth() + 8, tempTextImage.getHeight() + 8);
            myImage.setColor (Color.WHITE);
            myImage.fill();
            myImage.drawImage (tempTextImage, 4, 4);

            if(isRed)myImage.setColor(Color.RED);
            else myImage.setColor(Color.BLUE);
            myImage.drawRect (0,0,tempTextImage.getWidth() + 7, tempTextImage.getHeight() + 7);
            setImage(myImage);

            if(isRed)tempTextImage = new GreenfootImage (text, textSize, Color.WHITE, Color.RED);
            else tempTextImage = new GreenfootImage (text, textSize, Color.WHITE, Color.BLUE);
            myAltImage = new GreenfootImage(tempTextImage.getWidth() + 8, tempTextImage.getHeight() + 8);
            myAltImage.setColor (Color.WHITE);
            myAltImage.fill();
            myAltImage.drawImage (tempTextImage, 4, 4);

            if(isRed) myAltImage.setColor(Color.RED);
            else myAltImage.setColor(Color.BLUE);
            myAltImage.drawRect (0,0,tempTextImage.getWidth() + 7, tempTextImage.getHeight() + 7);
        }
    } 
    /**
     * Updates button to be pressed (changes its image)
     */
    public void Update(){
        setImage(myAltImage);        
    }
    /**
     * Resets the button to no be pressed (changes its image)
     */
    public void buttonReset(){
        setImage(myImage);
    }
    /**
     * Returns the statValue that the button holds
     * @return int      The statValue that the button holds
     */
    public int getStatValue(){
        return statValue;
    }
}
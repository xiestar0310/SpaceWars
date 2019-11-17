import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A Label class that allows you to display a textual value on screen.
 * <p>
 * The Label is an actor, so you will need to create it, and then add it to the world
 * in Greenfoot. If you keep a reference to the Label then you can change the text it
 * displays.  
 *
 * @author Amjad Altadmri 
 * @author Alex Li
 * @version 1.2.1
 */
public class Label extends Actor
{
    //instance variables
    private String value;
    private int fontSize;
    //default colours
    private Color lineColor = Color.BLACK;
    private Color fillColor = Color.WHITE;
    private static final Color transparent = new Color(0,0,0,0);
    private static boolean done = false;
    //fonts and images
    private Font textFont;
    private GreenfootImage img;
    /**
     * Creates a default label with customizable font size and text. The text would have a white colour with a black outline
     * 
     * @param value     The text that you want to label to have
     * @param fontSize  The desired fontSize
     */
    public Label(String value, int fontSize)
    {
        this.value = value;
        this.fontSize = fontSize;
        textFont = new Font (java.awt.Font.DIALOG, fontSize);
        updateImage();
    }

    /**
     * Create a new label, but the text colour will be red if isRed is true or blue if it is false, initialises it with the needed text and the font size.
     * 
     * @param value     The text that you want to label to have
     * @param fontSize  The desired fontSize
     * @param isRed     A boolean state whether the text colour is red or blue
     */
    public Label(String value, int fontSize, boolean isRed)
    {
        //sets the test colour depending on isRed
        if(isRed) fillColor = Color.RED;
        else fillColor = Color.BLUE;
        //changes the outline to be white
        lineColor = Color.WHITE;
        this.value = value;
        this.fontSize = fontSize;
        textFont = new Font (java.awt.Font.DIALOG,fontSize);
        updateImage();
    }

    /**
     * Sets the value as a text
     * 
     * @param value     The text to be show
     */
    public void setValue(String value)
    {
        this.value = value;
        //draws the image
        updateImage();
    }

    /**
     * Sets the value as an integer
     * 
     * @param value the value to be show
     */
    public void setValue(int value)
    {
        this.value = Integer.toString(value);
        //draws the image
        updateImage();
    }

    /**
     * Sets the line color of the text
     * 
     * @param lineColor the line color of the text
     */
    public void setLineColor(Color lineColor)
    {
        this.lineColor = lineColor;
        //draws the image
        updateImage();
    }    

    /**
     * Sets the fill color of the text
     * 
     * @param fillColor the fill color of the text
     */
    public void setFillColor(Color fillColor)
    {
        this.fillColor = fillColor;
        updateImage();
    }

    /**
     * Updates the image on screen to show the current text with its specified font size and colour.
     */
    private void updateImage()
    {
        //creates a new image and sets the font
        img = new GreenfootImage(value, fontSize, fillColor, transparent, lineColor);
        /*img.clear();
        img.setFont(textFont);
        img.setColor(fillColor);
        img.drawString(value,0,fontSize);*/
        setImage(img);
        /*
        if(!done){
            done=true;
            System.out.println(img.getFont().toString());
        }*/
    }
}
import java.util.*;
import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * StartScreen is, well the start screen for the game. It allows for the user to modify certain starting values of team red and team blue by clicking on
 * different buttons. It also has the start game button.
 * 
 * @author Alex Li 
 * @version 1.2
 */
public class StartScreen extends World
{
    //array to hold all the labels
    private Label[] labelArray = new Label[8];
    //array to hold all the buttons 
    private TextButton[] ButtonArray = new TextButton[24];
    //start button
    private TextButton StartBtn = new TextButton("Start Game!", 50,true);
    //instance variables to be passed to space world
    private int redMoney = 5000, blueMoney = 5000, redViking = 5, blueViking = 5, redMiners = 4, blueMiners = 4;
    //instance variables to track how many buttons are down
    private int redMoneyBtnDown = 1, blueMoneyBtnDown = 1, redVikingBtnDown = 1, blueVikingBtnDown = 1, redMinersBntDown = 1, blueMinersBntDown = 1;
    //queues to hold which order the buttons were pressed in
    private Queue<Integer> redMoneyBtn = new LinkedList<>(); 
    private Queue<Integer> blueMoneyBtn = new LinkedList<>(); 
    private Queue<Integer> redVikingBtn = new LinkedList<>(); 
    private Queue<Integer> blueVikingBtn = new LinkedList<>(); 
    private Queue<Integer> redMinerBtn = new LinkedList<>(); 
    private Queue<Integer> blueMinerBtn = new LinkedList<>(); 
    /**
     * Constructor for objects of class StartScreen, it creates all the labels, and buttons and displays them onto the world.
     */
    public StartScreen()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(960, 640, 1); 
        //filling the label array with all the necessary labels
        labelArray[0] = new Label ("Set Team Red's Stats", 50, true);
        labelArray[1] = new Label ("Set Team Blue's Stats", 50, false);
        labelArray[2] = new Label ("Red's Starting $", 30);
        labelArray[3] = new Label ("Blue's Starting $", 30);
        labelArray[4] = new Label ("Red's Starting Viking Count", 30);
        labelArray[5] = new Label ("Blue's Starting Viking Count", 30);
        labelArray[6] = new Label ("Red's Starting Miner Count", 30);
        labelArray[7] = new Label ("Blue's Starting Miner Count", 30);
        //filling the button array with all the necessary buttons
        //these buttons are for the starting money
        ButtonArray[0] = new TextButton("1000", 30, true,1000);
        ButtonArray[1] = new TextButton("5000", 30, true,5000);
        ButtonArray[2] = new TextButton("10,000", 30, true,10000);
        ButtonArray[3] = new TextButton("100K", 30, true,100000);
        ButtonArray[4] = new TextButton("1000", 30, false,1000);
        ButtonArray[5] = new TextButton("5000", 30, false,5000);
        ButtonArray[6] = new TextButton("10,000", 30, false,10000);
        ButtonArray[7] = new TextButton("100K", 30, false,100000);
        //these buttons are for the starting amount of vikings
        ButtonArray[8] = new TextButton("1", 30, true,1);
        ButtonArray[9] = new TextButton("3", 30, true,3);
        ButtonArray[10] = new TextButton("5", 30, true,5);
        ButtonArray[11] = new TextButton("10", 30, true,10);
        ButtonArray[12] = new TextButton("1", 30, false,1);
        ButtonArray[13] = new TextButton("3", 30, false,3);
        ButtonArray[14] = new TextButton("5", 30, false,5);
        ButtonArray[15] = new TextButton("10", 30, false,10);
        //these buttons are for the starting amount of workers
        ButtonArray[16] = new TextButton("2", 30, true,2);
        ButtonArray[17] = new TextButton("4", 30, true,4);
        ButtonArray[18] = new TextButton("6", 30, true,6);
        ButtonArray[19] = new TextButton("8", 30, true,8);
        ButtonArray[20] = new TextButton("2", 30, false,2);
        ButtonArray[21] = new TextButton("4", 30, false,4);
        ButtonArray[22] = new TextButton("6", 30, false,6);
        ButtonArray[23] = new TextButton("8", 30, false,8);
        //adding all the labels and buttons to the world
        addObject(labelArray[0],250,200);
        addObject(labelArray[1],700,200);
        //team red's money buttons + labels
        addObject(labelArray[2],137,250);
        addObject(ButtonArray[0],80,300);
        addObject(ButtonArray[1],155,300);
        addObject(ButtonArray[2],240,300);
        addObject(ButtonArray[3],320,300);
        //team blue's money buttons + labels
        addObject(labelArray[3],587,250);
        addObject(ButtonArray[4],525,300);
        addObject(ButtonArray[5],600,300);
        addObject(ButtonArray[6],685,300);
        addObject(ButtonArray[7],765,300);
        //team red's viking count buttons + labels
        addObject(labelArray[4],200,350);
        addObject(ButtonArray[8],80,400);
        addObject(ButtonArray[9],155,400);
        addObject(ButtonArray[10],240,400);
        addObject(ButtonArray[11],320,400);
        //team blues's viking count buttons + labels
        addObject(labelArray[5],651,350);
        addObject(ButtonArray[12],525,400);
        addObject(ButtonArray[13],600,400);
        addObject(ButtonArray[14],685,400);
        addObject(ButtonArray[15],765,400);
        //team red's worker count buttons + labels
        addObject(labelArray[6],196,450);
        addObject(ButtonArray[16],80,500);
        addObject(ButtonArray[17],155,500);
        addObject(ButtonArray[18],240,500);
        addObject(ButtonArray[19],320,500);
        //team blue's viking count buttons + labels
        addObject(labelArray[7],646,450);
        addObject(ButtonArray[20],525,500);
        addObject(ButtonArray[21],600,500);
        addObject(ButtonArray[22],685,500);
        addObject(ButtonArray[23],765,500);
        //adds the start button
        addObject(StartBtn,480,600);
        //since certain values are set by defualt, these buttons will already be "pressed"
        ButtonArray[1].Update();
        redMoneyBtn.add(2);
        ButtonArray[5].Update();
        blueMoneyBtn.add(2);
        ButtonArray[10].Update();
        redVikingBtn.add(3);
        ButtonArray[14].Update();
        blueVikingBtn.add(3);
        ButtonArray[17].Update();
        redMinerBtn.add(2);
        ButtonArray[21].Update();
        blueMinerBtn.add(2);
    }
    /**
     * Act - checks if a button is pressed and changes that buttons image to be selected. It also changes the starting money, viking count, or worker 
     * count of a team. If more than one button is pressed, the previous button will be reset. This method is called whenever the 'Act' or 'Run' button 
     * gets pressed in the environment.
     */
    public void act()
    {
        //checks whether the start button is pressed
        if(Greenfoot.mousePressed(StartBtn)){
            //passes all the needed variabled to the game world
            SpaceWorld gameWorld = new SpaceWorld(redMoney,blueMoney,redViking,blueViking, redMiners, blueMiners);
            //sets the world to the gameWorld
            Greenfoot.setWorld(gameWorld);
        } 
        if(Greenfoot.mousePressed(ButtonArray[0])){
            //changes red's starting money
            redMoney =  ButtonArray[0].getStatValue();
            //adds that this button is pressed to the queue
            redMoneyBtn.add(1);
            //inceases the amount of buttons are that are pressed
            redMoneyBtnDown++;
            //if more than one button is pressed, it resets the previously selected button
            if(redMoneyBtnDown == 2) resetBnt1();
            //updates the button's image
            ButtonArray[0].Update();
        }
        //everything above is repeated for the rest of the buttons, but with different variables and for different stats
        if(Greenfoot.mousePressed(ButtonArray[1])){
            redMoney =  ButtonArray[1].getStatValue();
            redMoneyBtn.add(2);
            redMoneyBtnDown++;
            if(redMoneyBtnDown == 2) resetBnt1();
            ButtonArray[1].Update();
        }
        if(Greenfoot.mousePressed(ButtonArray[2])){
            redMoney =  ButtonArray[2].getStatValue();
            redMoneyBtn.add(3);
            redMoneyBtnDown++;
            if(redMoneyBtnDown == 2) resetBnt1();
            ButtonArray[2].Update();
        }
        if(Greenfoot.mousePressed(ButtonArray[3])){
            redMoney =  ButtonArray[3].getStatValue();
            redMoneyBtn.add(4);
            redMoneyBtnDown++;
            if(redMoneyBtnDown == 2) resetBnt1();
            ButtonArray[3].Update();
        }
        //buttons for blue's starting money
        if(Greenfoot.mousePressed(ButtonArray[4])){
            blueMoney =  ButtonArray[4].getStatValue();
            blueMoneyBtn.add(1);
            blueMoneyBtnDown++;
            if(blueMoneyBtnDown == 2) resetBnt2();
            ButtonArray[4].Update();
        }
        if(Greenfoot.mousePressed(ButtonArray[5])){
            blueMoney =  ButtonArray[5].getStatValue();
            blueMoneyBtn.add(2);
            blueMoneyBtnDown++;
            if(blueMoneyBtnDown == 2) resetBnt2();
            ButtonArray[5].Update();
        }
        if(Greenfoot.mousePressed(ButtonArray[6])){
            blueMoney =  ButtonArray[6].getStatValue();
            blueMoneyBtn.add(3);
            blueMoneyBtnDown++;
            if(blueMoneyBtnDown == 2) resetBnt2();
            ButtonArray[6].Update();
        }
        if(Greenfoot.mousePressed(ButtonArray[7])){
            blueMoney =  ButtonArray[7].getStatValue();
            blueMoneyBtn.add(4);
            blueMoneyBtnDown++;
            if(blueMoneyBtnDown == 2) resetBnt2();
            ButtonArray[7].Update();
        }
        //buttons for red's starting viking count
        if(Greenfoot.mousePressed(ButtonArray[8])){
            redViking =  ButtonArray[8].getStatValue();
            redVikingBtn.add(1);
            redVikingBtnDown++;
            if(redVikingBtnDown == 2) resetBnt3();
            ButtonArray[8].Update();
        }
        if(Greenfoot.mousePressed(ButtonArray[9])){
            redViking =  ButtonArray[9].getStatValue();
            redVikingBtn.add(2);
            redVikingBtnDown++;
            if(redVikingBtnDown == 2) resetBnt3();
            ButtonArray[9].Update();
        }
        if(Greenfoot.mousePressed(ButtonArray[10])){
            redViking =  ButtonArray[10].getStatValue();
            redVikingBtn.add(3);
            redVikingBtnDown++;
            if(redVikingBtnDown == 2) resetBnt3();
            ButtonArray[10].Update();
        }
        if(Greenfoot.mousePressed(ButtonArray[11])){
            redViking = ButtonArray[11].getStatValue();
            redVikingBtn.add(4);
            redVikingBtnDown++;
            if(redVikingBtnDown == 2) resetBnt3();
            ButtonArray[11].Update();
        }
        //buttons for blue's starting viking count
        if(Greenfoot.mousePressed(ButtonArray[12])){
            blueViking = ButtonArray[12].getStatValue();
            blueVikingBtn.add(1);
            blueVikingBtnDown++;
            if(blueVikingBtnDown == 2) resetBnt4();
            ButtonArray[12].Update();
        }
        if(Greenfoot.mousePressed(ButtonArray[13])){
            blueViking = ButtonArray[13].getStatValue();
            blueVikingBtn.add(2);
            blueVikingBtnDown++;
            if(blueVikingBtnDown == 2) resetBnt4();
            ButtonArray[13].Update();
        }
        if(Greenfoot.mousePressed(ButtonArray[14])){
            blueViking = ButtonArray[14].getStatValue();
            blueVikingBtn.add(3);
            blueVikingBtnDown++;
            if(blueVikingBtnDown == 2) resetBnt4();
            ButtonArray[14].Update();
        }
        if(Greenfoot.mousePressed(ButtonArray[15])){
            blueViking = ButtonArray[15].getStatValue();
            blueVikingBtn.add(4);
            blueVikingBtnDown++;
            if(blueVikingBtnDown == 2) resetBnt4();
            ButtonArray[15].Update();
        }
        //buttons for red's starting miner count
        if(Greenfoot.mousePressed(ButtonArray[16])){
            redMiners = ButtonArray[16].getStatValue();
            redMinerBtn.add(1);
            redMinersBntDown++;
            if(redMinersBntDown == 2) resetBnt5();
            ButtonArray[16].Update();
        }
        if(Greenfoot.mousePressed(ButtonArray[17])){
            redMiners = ButtonArray[17].getStatValue();
            redMinerBtn.add(2);
            redMinersBntDown++;
            if(redMinersBntDown == 2) resetBnt5();
            ButtonArray[17].Update();
        }
        if(Greenfoot.mousePressed(ButtonArray[18])){
            redMiners = ButtonArray[18].getStatValue();
            redMinerBtn.add(3);
            redMinersBntDown++;
            if(redMinersBntDown == 2) resetBnt5();
            ButtonArray[18].Update();
        }
        if(Greenfoot.mousePressed(ButtonArray[19])){
            redMiners = ButtonArray[19].getStatValue();
            redMinerBtn.add(4);
            redMinersBntDown++;
            if(redMinersBntDown == 2) resetBnt5();
            ButtonArray[19].Update();
        }
        //buttons for blue's starting miner count
        if(Greenfoot.mousePressed(ButtonArray[20])){
            blueMiners = ButtonArray[16].getStatValue();
            blueMinerBtn.add(1);
            blueMinersBntDown++;
            if(blueMinersBntDown == 2) resetBnt6();
            ButtonArray[20].Update();
        }
        if(Greenfoot.mousePressed(ButtonArray[21])){
            blueMiners = ButtonArray[17].getStatValue();
            blueMinerBtn.add(2);
            blueMinersBntDown++;
            if(blueMinersBntDown == 2) resetBnt6();
            ButtonArray[21].Update();
        }
        if(Greenfoot.mousePressed(ButtonArray[22])){
            blueMiners = ButtonArray[18].getStatValue();
            blueMinerBtn.add(3);
            blueMinersBntDown++;
            if(blueMinersBntDown == 2) resetBnt6();
            ButtonArray[22].Update();
        }
        if(Greenfoot.mousePressed(ButtonArray[23])){
            blueMiners = ButtonArray[19].getStatValue();
            blueMinerBtn.add(4);
            blueMinersBntDown++;
            if(blueMinersBntDown == 2) resetBnt6();
            ButtonArray[23].Update();
        }
    }
    /**
     * Resets the previously selected button that changes team red's money 
     */
    private void resetBnt1(){
        //if the first number in the queue is x, then button x is reset
        if(redMoneyBtn.peek() == 1) ButtonArray[0].buttonReset();
        else if(redMoneyBtn.peek() == 2) ButtonArray[1].buttonReset();
        else if(redMoneyBtn.peek() == 3) ButtonArray[2].buttonReset();
        else if(redMoneyBtn.peek() == 4) ButtonArray[3].buttonReset();
        //lowers the amount of buttons pressed that changes team red's starting money by one
        redMoneyBtnDown--;
        //removes the first value in the queue
        redMoneyBtn.poll();
    }
    /**
     * Resets the previously selected button that changes team blue's starting money     
     */
    private void resetBnt2(){
        //if the first number in the queue is x, then button x is reset
        if(blueMoneyBtn.peek() == 1) ButtonArray[4].buttonReset();
        else if(blueMoneyBtn.peek() == 2) ButtonArray[5].buttonReset();
        else if(blueMoneyBtn.peek() == 3) ButtonArray[6].buttonReset();
        else if(blueMoneyBtn.peek() == 4) ButtonArray[7].buttonReset();
        //lowers the amount of buttons pressed that changes team blue's starting money by one
        blueMoneyBtnDown--;
        //removes the first value in the queue
        blueMoneyBtn.poll();
    }
    /**
     * Resets the previously selected button that changes team red's starting viking count 
     */
    private void resetBnt3(){
        //if the first number in the queue is x, then button x is reset
        if(redVikingBtn.peek() == 1) ButtonArray[8].buttonReset();
        else if(redVikingBtn.peek() == 2) ButtonArray[9].buttonReset();
        else if(redVikingBtn.peek() == 3) ButtonArray[10].buttonReset();
        else if(redVikingBtn.peek() == 4) ButtonArray[11].buttonReset();
        //lowers the amount of buttons pressed that changes team red's starting viking count by one
        redVikingBtnDown--;
        //removes the first value in the queue
        redVikingBtn.poll();
    }
    /**
     * Resets the previously selected button that changes team blue's starting viking count
     */
    private void resetBnt4(){
        //if the first number in the queue is x, then button x is reset
        if(blueVikingBtn.peek() == 1) ButtonArray[12].buttonReset();
        else if(blueVikingBtn.peek() == 2) ButtonArray[13].buttonReset();
        else if(blueVikingBtn.peek() == 3) ButtonArray[14].buttonReset();
        else if(blueVikingBtn.peek() == 4) ButtonArray[15].buttonReset();
        //lowers the amount of buttons pressed that changes team blue's starting viking count by one
        blueVikingBtnDown--;
        //removes the first value in the queue
        blueVikingBtn.poll();
    }
    /**
     * Resets the previously selected button that changes team red's starting miner count 
     */
    private void resetBnt5(){
        //if the first number in the queue is x, then button x is reset
        if(redMinerBtn.peek() == 1) ButtonArray[16].buttonReset();
        else if(redMinerBtn.peek() == 2) ButtonArray[17].buttonReset();
        else if(redMinerBtn.peek() == 3) ButtonArray[18].buttonReset();
        else if(redMinerBtn.peek() == 4) ButtonArray[19].buttonReset();
        //lowers the amount of buttons pressed that changes team team red's starting miner count by one
        redMinersBntDown--;
        //removes the first value in the queue
        redMinerBtn.poll();
    }
    /**
     * Resets the previously selected button that changes team blue's starting miner count 
     */
    private void resetBnt6(){
        //if the first number in the queue is x, then button x is reset
        if(blueMinerBtn.peek() == 1) ButtonArray[20].buttonReset();
        else if(blueMinerBtn.peek() == 2) ButtonArray[21].buttonReset();
        else if(blueMinerBtn.peek() == 3) ButtonArray[22].buttonReset();
        else if(blueMinerBtn.peek() == 4) ButtonArray[23].buttonReset();
        //lowers the amount of buttons pressed that changes team team blue's starting miner count by one
        blueMinersBntDown--;
        //removes the first value in the queue
        blueMinerBtn.poll();
    }
}
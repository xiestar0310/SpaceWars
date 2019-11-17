import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * SpaceWorld - the setting of the war. It contains all the static constants of the game
 * along with the scoreboards and arraylists for the mines and factories of the world. It 
 * contains 2 planets where the 2 teams hold their base and perform all their activities.
 * <p>
 * Description of simulation: Space Wars is a complex simulation featuring multiple elements
 * for a fast-paced and exciting screenplay of a war fought beween Team Red and Team Blue in
 * outer space. Each team inhabits a planet and starts with a Command Centre, 2 factories, and
 * 4 mines. Each Command Centre commands its team, creates workers and miners, and upgrades 
 * mines and factories. Workers work in factories and also build new factories, while miners
 * work in Mines to make money. Factories build ships and can be upgraded to build better 
 * ships. Mines can also be upgraded to increase miner productivity. Money is used to purchase 
 * ships and upgrades, create people, and build factories. The war is fought with ships/vehicles. 
 * There are 4 different kinds of ships (Vikings, Nightingales, Avengers, and Hyperions), some 
 * more powerful than others and each acting in a different manner. Most ships shoot bullets at 
 * each other which deal damage, except for the Hyperion, which fires missiles (radius damage).
 * In the startscreen, the user can specify the amount of money, number of miners, and number of
 * Vikings for each side to start. The game ends when one team destroys the Command Centre of
 * the team, which brings up the end screen. The end screen states the team that won and the 
 * length of the game, also allowing the user the option to restart!
 * <p>
 * Credits:
 * Graphics: Clash of Clans, StarCraft, Clash of Lords, sccpre.cat, pngkey.com, 
 * topping.com, Roblox, Gaurav.munjal.us, wallpaperplay.com
 * Music: Relaxation Ambient Music (AMAZING SPACE TRAVELING), www.gamedevmarket.net (TOP DOWN SHOOTER – 1)
 * 
 * 
 * @author Albert Lai 
 * @version November 2019
 */
public class SpaceWorld extends World
{
    //Declare static constants for game purposes
    //costs
    public static final int WORKER_COST = 500;
    public static final int MINER_COST = 500;
    public static final int VIKING_COST = 2000;
    public static final int NIGHTINGALE_COST = 4000;
    public static final int AVENGER_COST = 6000;
    public static final int HYPERION_COST = 30000;
    public static final int FACTORY_COST = 8000;
    public static final int FACTORY_UPGRADE_1_COST = 5000;
    public static final int FACTORY_UPGRADE_2_COST = 6500;
    public static final int FACTORY_UPGRADE_3_COST = 8000;
    public static final int MINE_UPGRADE_1_COST = 5000;
    public static final int MINE_UPGRADE_2_COST = 7000;
    public static final int MINE_UPGRADE_3_COST = 9000;

    //hp
    public static final int VIKING_HP = 500;
    public static final int NIGHTINGALE_HP = 1000;
    public static final int AVENGER_HP = 2000;
    public static final int HYPERION_HP = 10000;
    public static final int FACTORY_HP = 5000;
    public static final int MINE_HP = 5000;
    public static final int CC_HP = 15000;

    //times
    public static final int WORKER_TIME = 2000;
    public static final int MINER_TIME = 2500;
    public static final int VIKING_TIME = 5000;
    public static final int NIGHTINGALE_TIME = 9000;
    public static final int AVENGER_TIME = 14000;
    public static final int HYPERION_TIME = 30000;
    public static final int FACTORY_TIME = 20000;

    //reload times
    public static final int VIKING_RELOAD_TIME = 45;
    public static final int NIGHTINGALE_RELOAD_TIME = 40;
    public static final int AVENGER_RELOAD_TIME = 35;
    public static final int HYPERION_RELOAD_TIME = 90;

    //damage
    public static final int VIKING_DAMAGE = 75;
    public static final int NIGHTINGALE_DAMAGE = 125;
    public static final int AVENGER_DAMAGE = 250;
    public static final int HYPERION_DAMAGE = 5000;

    //range
    public static final int VIKING_RANGE = 125;
    public static final int NIGHTINGALE_RANGE = 150;
    public static final int NIGHTINGALE_TETHER_RANGE = 400;
    public static final int AVENGER_RANGE = 200;
    public static final int HYPERION_RANGE = 350;

    //speed
    public static final int BULLET_SPEED = 3;
    public static final int MISSILE_SPEED = 5;
    public static final int WORKER_SPEED = 1;
    public static final int MINER_SPEED = 1;
    public static final int VIKING_SPEED = 3;
    public static final int NIGHTINGALE_SPEED = 4;
    public static final int AVENGER_SPEED = 4;
    public static final int HYPERION_SPEED = 6;

    //spawn constants
    public static final int PEOPLE_SPAWN = 4;
    public static final int VEHICLE_SPAWN = 4;
    public static final int FACTORY_SPAWN = 15;
    public static final int UPGRADE_RANDOM = 30;

    //capacity constants
    public static final int MINE_CAPACITY = 10;
    public static final int FACTORY_CAPACITY = 10;

    //location constants
    public static final int[][] FACTORY_RED_LOCATIONS = {{650,400},{750,400},{850,400},{850,330}};
    public static final int[][] FACTORY_BLUE_LOCATIONS = {{75,200},{175,200},{275,200},{75,300}};

    //Declare arraylists
    private ArrayList <Factories> factories = new ArrayList<Factories>();
    private ArrayList <Mines> mines = new ArrayList<Mines>();
    public ArrayList <Vehicles> vehicles = new ArrayList<Vehicles>();

    //Declare scoreboard array
    private Scoreboard [] scoreboard = new Scoreboard[4];

    //Declare music
    private static final GreenfootSound music = new GreenfootSound("Background Music.mp3");
    /**
     * Constructor - creates all the initial actors of the game, sets the scoreboard stats and plays music
     * 
     * @param redMoney          the amount of money team red starts with
     * @param blueMoney         the amount of money team blue starts with
     * @param redViking         the amount of vikings team red starts with
     * @param blueViking        the amount of vikings team blue starts with
     * @param redMiners         the amount of miners team red starts with
     * @param blueMiners        the amount of miners team blue starts with
     */
    public SpaceWorld(int redMoney,int blueMoney,int redViking,int blueViking,int redMiners,int blueMiners)
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(960, 640, 1, true);

        //randomly initialize blue or red team first
        if(Greenfoot.getRandomNumber(2)==0){
            makeRed(redViking,redMiners);
            makeBlue(blueViking,blueMiners);
        }   
        else{
            makeBlue(blueViking,blueMiners);
            makeRed(redViking,redMiners);
        }    

        //initiate scoreboards
        for(int i=0;i<scoreboard.length;i++){
            scoreboard[i] = new Scoreboard(150, 50, 20);
        }

        //add scoreboards to world
        addObject(scoreboard[0], 510, 25);
        addObject(scoreboard[1], 660, 25);
        addObject(scoreboard[2], 781, 25); 
        addObject(scoreboard[3], 900, 25); 

        //add stats for scoreboards
        scoreboard[3].addStat(true," $$$",redMoney);
        scoreboard[3].addStat(false," $$$",blueMoney);
        scoreboard[1].addStat(true,"SHIPS",redViking);
        scoreboard[1].addStat(false,"SHIPS",blueViking);
        scoreboard[0].addStat(true,"BUILDINGS",7);
        scoreboard[0].addStat(false,"BUILDINGS",7);
        scoreboard[2].addStat(true,"KILLS",0);
        scoreboard[2].addStat(false,"KILLS",0);

        //set music
        music.setVolume(70);
        music.play();
    }

    /**
     * Makes all the red buildings and units
     */
    private void makeRed(int redViking, int redMiners){
        //make red buildings and add to world
        //CC 
        CommandCentre CCred = new CommandCentre(true);
        addObject(CCred,870,550);
        //factories and workers
        Factories f1red = new Factories(false,true), f2red = new Factories(false, true);
        addObject(f1red,650,400);
        addObject(f2red,750,400);
        addObject(new Workers(true,false),CCred.getX(), CCred.getY());
        addObject(new Workers(true,false),CCred.getX(), CCred.getY());
        for(int i=0;i<redViking;i++){
            if(Greenfoot.getRandomNumber(2)==0) addObject(new Vikings(true), f1red.getX() + Greenfoot.getRandomNumber(5), f1red.getY() + Greenfoot.getRandomNumber(5));
            else addObject(new Vikings(true), f2red.getX() + Greenfoot.getRandomNumber(5), f2red.getY() + Greenfoot.getRandomNumber(5));
        }    
        //mines and miners
        Mines [] mred = new Mines[4];
        for(int i=0;i<mred.length;i++){
            mred[i]=new Mines(true);
        }    
        addObject(mred[0],630,580);
        addObject(mred[1],730,580);
        addObject(mred[2],630,500);
        addObject(mred[3],730,500);
        for(int i=0;i<redMiners;i++){
            addObject(new Miners(true), CCred.getX(), CCred.getY());
        } 
    }    

    /**
     * Makes all the blue buildings and units
     */
    private void makeBlue(int blueViking, int blueMiners){
        //make blue buildings and add to world
        //CC 
        CommandCentre CCblue = new CommandCentre(false);
        addObject(CCblue,75,75);
        //factories and workers
        Factories f1blue = new Factories(false,false), f2blue = new Factories(false,false);
        addObject(f1blue,75,200);
        addObject(f2blue,175,200);
        addObject(new Workers(false,false),CCblue.getX(), CCblue.getY());
        addObject(new Workers(false,false),CCblue.getX(), CCblue.getY());
        for(int i=0;i<blueViking;i++){
            if(Greenfoot.getRandomNumber(2)==0) addObject(new Vikings(false), f1blue.getX() + Greenfoot.getRandomNumber(5), f1blue.getY()+ Greenfoot.getRandomNumber(5));
            else addObject(new Vikings(false), f2blue.getX() + Greenfoot.getRandomNumber(5), f2blue.getY() + Greenfoot.getRandomNumber(5));
        } 
        //mines and miners
        Mines [] mblue = new Mines[4];
        for(int i=0;i<mblue.length;i++){
            mblue[i]=new Mines(false);
        } 
        addObject(mblue[0],200,50);
        addObject(mblue[1],300,50);
        addObject(mblue[2],200,130);
        addObject(mblue[3],300,130);
        for(int i=0;i<blueMiners;i++){
            addObject(new Miners(false), CCblue.getX(), CCblue.getY());
        }
    }    

    /**
     * updateStat - updates the correct scoreboard by a certain specified value
     * 
     * @param board         board to update (1: buildings, 2: units, 3: kills, 4: money)
     * @param red           specifies the team (true for red, false for blue)
     * @param value         the new value of the stat
     */
    public void updateStat(int board, boolean red, int value){
        if(board>=1 && board<=4){
            scoreboard[board-1].updateStats(red, value);
            if(getStat(board,red)<0) scoreboard[board-1].updateStats(red, -getStat(board,red));
        }
    }

    /**
     * getStat - retrieves a stat from the correct scoreboard 
     * 
     * @param board         board to retrieve from (1: buildings, 2: units, 3: kills, 4: money)
     * @param red           specifies the team (true for red, false for blue)
     * 
     * @return int          stat to be retrieved or -1 if invalid board
     */
    public int getStat(int board, boolean red){
        if(board>=1 && board<=4) return scoreboard[board-1].getStat(red);
        else return -1;
    }    

    /**
     * getFactoriesArrayList - accessor for factories arraylist 
     * 
     * @return ArrayList<Factories>          factories arraylist
     */
    public ArrayList<Factories> getFactoriesArrayList(){
        return factories;
    }

    /**
     * addFactoriesArrayList - adds a factory to the factories arraylist
     * 
     * @param f          factory to be added to the arraylist
     */
    public void addFactoriesArrayList(Factories f){
        factories.add(f);
    }

    /**
     * removeFactoriesArrayList - removes a factory from the factories arraylist
     * 
     * @param f          factory to be removed from the arraylist
     */
    public void removeFactoriesArrayList(Factories f){
        factories.remove(f);
    }

    /**
     * getMinesArrayList - accessor for mines arraylist 
     * 
     * @return ArrayList<Mines>          mines arraylist
     */
    public ArrayList<Mines> getMinesArrayList(){
        return mines;
    }

    /**
     * addMinesArrayList - adds a mine to the mines arraylist
     * 
     * @param m          mine to be added to the arraylist
     */
    public void addMinesArrayList(Mines m){
        mines.add(m);
    }

    /**
     * removesMinesArrayList - removes a mine from the mines arraylist
     * 
     * @param m          mine to be removed from the arraylist
     */
    public void removeMinesArrayList(Mines m){
        mines.remove(m);
    }

    /**
     * stopMusic - stops the music
     */
    public void stopMusic(){
        music.stop();
    }
}

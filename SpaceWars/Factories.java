import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;

/**
 * Factory is an actor that makes ships and can be upgraded to make even better ships. 
 * <p>
 * All the ships take x amount of time to build, and can be decreased by 100 milliseconds multiplied by how many workers there are in the factory. A maximum 
 * of 10 workers are allowed per factory at one instance of time. Each factory starts with one worker. The factory will play a sound when a ship is made.
 * <p>
 * New factories can be built, but a maximum of 4 per side is allowed. They can also be rebuilt if one of them is destroyed, this rebuilt factory 
 * will start at lvl 1. All of its functions are controlled by the command centre.
 * <p>
 * The factory increases in size and will play a sound when it is upgraded. The factory will not lvl up if it is making something.
 * Its sizes are as followed (in pixels):
 * <p>
 * Lvl 1: 90 x 88
 * <p>
 * Lvl 2: 93 x 73
 * <p>
 * Lvl 3: 96 x 90
 * <p>
 * Lvl 4: 100 x 96
 * 
 * @author Alex Li  
 * @version 1.12.4
 */
public class Factories extends Buildings
{
    //instance variables
    private int levelOfFactory = 1,workerCnt = 0, actCnt = 0, productionTime = 0, lvlUpCnt = 0, actCountAnimation = 0;
    private boolean addedToWorld = false, isBusy = false, isAnimating = false, notBuilt;
    //the production queue of the factory
    private Queue<Integer> productionQ = new LinkedList<>(); 
    //a queue to hold how long it will take to complete a task
    private Queue<Integer> productionTimeQ = new LinkedList<>();
    //a timer that will be added to the factory when it is making units
    private Timer temp;
    //an array to hold all the images of factory (0-7 for images, 8-15 for when making)
    private static final GreenfootImage factoryImages[] = {new GreenfootImage ("B2Blue.png"),new GreenfootImage ("B2Red.png"),new GreenfootImage ("B1Blue.png"),new GreenfootImage ("B1Red.png"),
            new GreenfootImage ("B3Blue.png"),new GreenfootImage ("B3Red.png"),new GreenfootImage ("B4Blue.png"),new GreenfootImage ("B4Red.png"),new GreenfootImage ("B2Blue2.png"),
            new GreenfootImage ("B2Red2.png"),new GreenfootImage ("B1Blue2.png"),new GreenfootImage ("B1Red2.png"), new GreenfootImage ("B3Blue2.png"),new GreenfootImage ("B3Red2.png"),
            new GreenfootImage ("B4Blue2.png"),new GreenfootImage ("B4Red2.png")};
    //sound effects for factory
    private static final GreenfootSound makeShipSound = new GreenfootSound("ShipMade.wav");
    private boolean building;
    /**
     * Creates a factory belonging to either team red or team blue and sets its image. The factory will have a maximum health of 3000.
     * @param red       Specifies which side the factory belongs to
     */
    public Factories(boolean notBuilt, boolean red){
        super(red, SpaceWorld.FACTORY_HP);
        //sets the current factory image
        this.notBuilt = notBuilt;
        if(notBuilt){
            setImage("wood.png");
        } 
        else if(red) setImage(factoryImages[1]);
        else setImage(factoryImages[0]);
        //set volume for sound
        makeShipSound.setVolume(50);
    }

    /**
     * Adds timer to build the factory
     */
    public void addedToWorld(World w){
        if(notBuilt){
            temp = new Timer(red,SpaceWorld.FACTORY_TIME,SpaceWorld.FACTORY_TIME);
            getWorld().addObject(temp,getX(),getY());
            getWorld().setPaintOrder(HealthBar.class, Timer.class,Workers.class);
        }
    }    

    /**
     *  Act - checks whether or not the factory has been added to the factories arraylist in SpaceWorld, checks whether or not its production queue is empty 
     *  or not very 30 acts, and checks whether or not it should play its animation 
     */
    public void act(){
        //checks if this factory has been added to the factories arraylist in SpaceWorld
        if(notBuilt){
            //checks if factory is built
            if(temp==null || temp.getWorld()==null){
                notBuilt = false;
                SpaceWorld w = (SpaceWorld)getWorld();
                w.updateStat(1,red,1);
                if(red) setImage(factoryImages[1]);
                else setImage(factoryImages[0]);
            }
        }
        else if(workerCnt>0)
        {
            if(!addedToWorld){
                SpaceWorld world = (SpaceWorld)getWorld();
                world.addFactoriesArrayList(this);
                addedToWorld = true;
            }
            //checks whether or not its production queue is empty
            if(actCnt % 30 == 0 && !productionQ.isEmpty() && workerCnt > 0) {
                //makes a unit
                makeUnit();
            }
            //plays its animation if needs to
            if(isAnimating){
                actCountAnimation++;
                animation(levelOfFactory);
            }
        }
        checkBlowUp();
    }

    /**
     * Makes the first unit in its queue. It creates a timer based on how much time it takes to make the unit and then adds a unit when the timer equals to 0
     * After that, it decreases its total production time based on how much time it took to produce that unit.
     */
    private void makeUnit(){
        SpaceWorld w = (SpaceWorld)getWorld();
        //if its not busy, make a new unit
        if(!isBusy){
            //makes a viking
            if(productionQ.peek() == 1 && this.levelOfFactory >= 1){
                temp = new Timer(red,productionTimeQ.peek(),productionTimeQ.peek());
                //adds a timer to the factory
                w.addObject(temp,getX(), getY());
                isBusy = true;
            }
            //makes an avenger
            else if(productionQ.peek() == 2 && this.levelOfFactory>=3) {
                temp = new Timer(red,productionTimeQ.peek(),productionTimeQ.peek());
                //adds a timer to the factory
                getWorld().addObject(temp,getX(),getY());
                isBusy = true;
            }
            //makes a nightingale
            else if(productionQ.peek() == 3 && this.levelOfFactory >= 2){
                temp = new Timer(red,productionTimeQ.peek(),productionTimeQ.peek());
                //adds a timer to the factory
                getWorld().addObject(temp,getX(),getY());
                isBusy = true;
            } 
            //makes a hyperion
            else if(productionQ.peek() == 4 && this.levelOfFactory >= 4){
                temp = new Timer(red,productionTimeQ.peek(), productionTimeQ.peek());
                //adds a timer to the factory
                getWorld().addObject(temp,getX(),getY());
                isBusy = true;
            }
        }
        //if it is busy check whether or not the timer equals zero and adds a unit to the world
        else{
            if(temp.getWorld() == null){
                //plays a sound when a unit is made
                makeShipSound.play();
                //updates the scoreboard tracking unit count
                w.updateStat(2,red,1);
                //checks if the ship made is a viking and adds a viking to the world if true
                if(productionQ.peek() == 1) getWorld().addObject(new Vikings(red),getX(), getY());
                //checks if the ship made is an avenger and adds an avenger to the world if true
                else if(productionQ.peek() == 2) getWorld().addObject(new Avengers(red),getX(), getY());
                //checks if the ship made is a viking and adds a viking to the world if true
                else if(productionQ.peek() == 3) getWorld().addObject(new Nightingale(red),getX(), getY());
                //checks if the ship made is a viking and adds a viking to the world if true
                else if(productionQ.peek() == 4) getWorld().addObject(new Hyperion(red),getX(), getY());
                //checks if the factory needs to lvlup
                checkLvlUp();
                //removes the first unit 
                productionQ.poll();
                //reduces total production time
                productionTime -= productionTimeQ.peek();
                //sets isAnimating to true so it can start its animation
                isAnimating = true;
                actCountAnimation = 0;
                //plays its animation
                animation(this.getFactoryLevel());
                //sets isBusy to false so it can make a new unit
                isBusy = false;
                //removes the time it took to produce the unit
                productionTimeQ.poll();
            }
        }
    }

    /**
     * Adds a unit to the factories production queue. It also changes its total production time.
     * 
     * @ param n    an integer to specify what ship to make (1 = viking, 2 = avenger, 3 = nightingale, 4 = hyperion)
     */
    public void addToProductionQueue(int n){
        if(n >= 1 && n <= 4){
            //adds a viking to the queue, adds time it takes to make a viking to the production time queue and changes its total production time
            if(n == 1 && this.levelOfFactory >= 1){
                productionQ.add(1);
                productionTime += SpaceWorld.VIKING_TIME - (250*workerCnt);
                productionTimeQ.add(SpaceWorld.VIKING_TIME - (250*workerCnt));
            } 
            //adds an avenger to the queue, adds time it takes to make an avenger to the production time queue and changes its total production time
            else if(n == 2 && this.levelOfFactory>=3) {
                productionQ.add(2);
                productionTime += SpaceWorld.AVENGER_TIME - (250*workerCnt);
                productionTimeQ.add(SpaceWorld.AVENGER_TIME - (250*workerCnt));
            }
            //adds a nightingale to the queue, adds time it takes to make a nightingale to the production time queue and changes its total production time
            else if(n == 3 && this.levelOfFactory >= 2){
                productionQ.add(3);
                productionTime += SpaceWorld.NIGHTINGALE_TIME - (250*workerCnt);
                productionTimeQ.add(SpaceWorld.NIGHTINGALE_TIME - (250*workerCnt));
            } 
            //adds a hyperion to the queue, adds time it takes to make a hyperion to the production time queue and changes its total production time
            else if (n == 4 && this.levelOfFactory >= 4){
                productionQ.add(4);
                productionTime += SpaceWorld.HYPERION_TIME - (250*workerCnt);
                productionTimeQ.add(SpaceWorld.HYPERION_TIME - (250*workerCnt));
            }
        }
    }

    /**
     * Plays the animation for making a unit according to its factory level
     */
    private void animation(int factoryLvl){
        if(factoryLvl == 1){
            if(red){
                if(actCountAnimation==0) setImage(factoryImages[9]);
                else if(actCountAnimation==15){
                    setImage(factoryImages[1]);
                    actCountAnimation = 0;
                    isAnimating = false;
                }
            }
            else{
                if(actCountAnimation==0) setImage(factoryImages[8]);
                else if(actCountAnimation==15){
                    setImage(factoryImages[0]);
                    actCountAnimation = 0;
                    isAnimating = false;
                }
            }
        }
        if(factoryLvl == 2){
            if(red){
                if(actCountAnimation==0) setImage(factoryImages[11]);
                else if(actCountAnimation==15){
                    setImage(factoryImages[3]);
                    actCountAnimation = 0;
                    isAnimating = false;
                }
            }
            else{
                if(actCountAnimation==0) setImage(factoryImages[10]);
                else if(actCountAnimation==15){
                    setImage(factoryImages[2]);
                    actCountAnimation = 0;
                    isAnimating = false;
                }
            }
        }
        if(factoryLvl == 3){
            if(red){
                if(actCountAnimation==0) setImage(factoryImages[13]);
                else if(actCountAnimation==15){
                    setImage(factoryImages[5]);
                    actCountAnimation = 0;
                    isAnimating = false;
                }
            }
            else{
                if(actCountAnimation==0) setImage(factoryImages[12]);
                else if(actCountAnimation==15){
                    setImage(factoryImages[4]);
                    actCountAnimation = 0;
                    isAnimating = false;
                }
            }
        }
        if(factoryLvl == 4){
            if(red){
                if(actCountAnimation==0) setImage(factoryImages[15]);
                else if(actCountAnimation==15){
                    setImage(factoryImages[7]);
                    actCountAnimation = 0;
                    isAnimating = false;
                }
            }
            else{
                if(actCountAnimation==0) setImage(factoryImages[14]);
                else if(actCountAnimation==15){
                    setImage(factoryImages[6]);
                    actCountAnimation = 0;
                    isAnimating = false;
                }
            }
        }
    }  

    /**
     * Checks if the factory needs to level up and levels it up if it has to 
     */
    private void checkLvlUp(){
        //if lvlUpCnt > 0 lvlup!
        if(lvlUpCnt != 0){
            for(int i = 0; i < lvlUpCnt;i++){
                this.levelUp();
            }
            //resets lvlupCnt
            lvlUpCnt = 0;
        }
    }

    /**
     * Levels up the factory if its current lvl is less than 4 so that it can make better units, changes its design, and plays a sound
     */
    private void levelUp(){
        //plays the lvl up sound
        levelUpSound.play();
        //increases its lvl if it is less than four and sets a new image
        if(levelOfFactory < 4){
            this.levelOfFactory++; 
            if(levelOfFactory == 2 && red)setImage(factoryImages[3]);
            else if(levelOfFactory == 2 && !red)setImage(factoryImages[2]);
            else if(levelOfFactory == 3 && red)setImage(factoryImages[5]);
            else if(levelOfFactory == 3 && !red)setImage(factoryImages[4]);
            else if(levelOfFactory == 4 && red)setImage(factoryImages[7]);
            else if(levelOfFactory == 4 && !red)setImage(factoryImages[6]);
        }
    }  

    /**
     * Returns the time it will take to make all the units in the factory's production queue.
     * @return int      The total time it will take to make all the units in its queue
     */
    public int getTime(){  
        return productionTime;
    }

    /**
     * Returns the factory level
     * @return int      The factory level
     */
    public int getFactoryLevel(){
        return this.levelOfFactory;
    }    

    /**
     * Returns the amount of workers in the factory
     * @return int      The amount of workers in the factory
     */
    public int getWorkerCnt(){
        return workerCnt;
    }        

    /**
     * Adds a worker to the factory if it has less than 10 workers
     */
    public void addWorker(){
        //if there is still enough room for more workers let them into the factory
        if(workerCnt <10)workerCnt++;
    }

    /**
     * Returns a boolean stating whether it is full or not
     * @return boolean      A boolean stating whether the factory is full or not
     */
    public boolean isFull(){
        if(workerCnt == 10) return true;
        return false;
    }  

    /**
     * Increments the amount of times the factory needs to level up since it can level up when making a unit
     */
    public void addToLvlUpCnt(){
        //adds a level up to the "queue"
        lvlUpCnt++;
    }   

    /**
     * Checks if the factory is destroyed and if it is, removes it from the world, and creates an explosion.
     **/
    public void checkBlowUp(){
        if(hp < 0) {
            SpaceWorld world = (SpaceWorld)getWorld();
            //removes one building from the scoreboard
            world.updateStat(1,red,-1);
            //removes workers from the scoreboard
            world.updateStat(2,red,-workerCnt);
            //plays an explosion
            world.addObject(new Explosion(), getX(), getY());
            //removes the factory from the SpaceWorld arrayList
            world.removeFactoriesArrayList(this);
            //removes the factory
            world.removeObject(this);
            //if the timer still exists, remove that too
            if(temp!= null) world.removeObject(temp);
            //if the healthbar isn't removed, remove that too
            if(healthbar!= null) world.removeObject(healthbar);
        }
    }  

    /**
     * Checks if the queue size is capped
     * @return boolean          True if capped, false if not
     */
    public boolean isCapped(){
        return productionQ.size()>3;
    }    

    /**
     * Checks if factory is not built yet 
     * @return boolean          True if not built, false if built
     */
    public boolean getNotBuilt()
    {
        return notBuilt;
    }

}
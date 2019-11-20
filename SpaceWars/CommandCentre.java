import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;

/**
 * CommandCentre - the commander of each side. They make the workers and miners, level up factories 
 * and mines, and order the factories to make vehicles. It lights up whenver it makes a person.  
 * When the CommandCentre explodes, the war is lost. 
 * 
 * @author Albert Lai and Alex Li
 * @version November 2019
 * 
 */
public class CommandCentre extends Buildings
{
    //Declare instance variables
    private boolean isBusy = false;
    private boolean isAnimating = false;
    private int actCount;
    private int actCountAnimation;
    private String currentUnit;
    private int actmod = 15;
    private int costBuffer = 2500;
    private boolean madeHyperion = false;

    //Declare timers
    private Timer t;
    private SimpleTimer time = new SimpleTimer();
    private SimpleTimer gameTime = new SimpleTimer();

    //Declare images
    private static final GreenfootImage [] CCBlue = {new GreenfootImage ("CCBlue.png"),new GreenfootImage ("CCBlue2.png")};
    private static final GreenfootImage [] CCRed = {new GreenfootImage ("CCRed.png"),new GreenfootImage ("CCRed2.png")};

    /**
     * Constructor - creates a Command Centre, sets the image, and starts the gametime
     * 
     * @param red       specifies the team (true for red, false for blue)
     */
    public CommandCentre(boolean red){
        super(red, SpaceWorld.CC_HP);
        if(red) setImage(CCRed[0]);
        else setImage(CCBlue[0]);
        gameTime.mark();
    }

    /**
     * Act - do whatever the CommandCentre wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        //checks if the CommandCentre is destroyed
        checkBlowUp();
        try{
            if(actCount%15==0){
                SpaceWorld world = (SpaceWorld)getWorld();
                //make person
                if(isBusy || (world.getStat(4,red) >= world.WORKER_COST*2 && !isBusy)){
                    makePersonUnit();
                }

                if(actCount%actmod==0){
                    //make vehicle
                    addToProductionQueueUnit();

                    //randomly upgrade factory or mine
                    upgrade();

                    //change actmod if necessary
                    if(world.getStat(4,red)<=2000) actmod = 30;
                    else if(world.getStat(4,red)<=costBuffer) actmod = 25;
                    else if(world.getStat(4,red)<=10000) actmod = 20;
                    else actmod = 15;
                }
            }
            actCount++;
            //animation
            if(isAnimating){
                actCountAnimation++;
                animation();
            } 
        }
        catch(NullPointerException e){
            checkBlowUp();
        }
    }    

    /**
     * Animation - makes the CommandCentre light up whenever producing a unit
     */
    private void animation(){
        if(red){
            if(actCountAnimation==0) setImage(CCRed[1]);
            else if(actCountAnimation==15){
                setImage(CCRed[0]);
                actCountAnimation = 0;
                isAnimating = false;
            } 
        }
        else{
            if(actCountAnimation==0) setImage(CCBlue[1]);
            else if(actCountAnimation==15){
                setImage(CCBlue[0]);
                actCountAnimation = 0;
                isAnimating = false;
            } 
        }
    }    

    /**
     * checkBlowUp - checks if the CommandCentre is destroyed and if it is, removes it from the 
     * world, creates an explosion, and ends the game by setting the GameOver world
     */
    protected void checkBlowUp(){
        if(hp <= 0) {
            SpaceWorld world = (SpaceWorld)getWorld();
            //updates the stats,adds an explosion, and removes itself
            world.updateStat(1,red,-1);
            world.addObject(new Explosion(), getX(), getY());
            getWorld().removeObject(this);
            //stops music
            Greenfoot.delay(30);
            world.stopMusic();
            //removes healthbar
            if(healthbar!= null) world.removeObject(healthbar);
            //sets GameOver world
            GameOver gameWorld = new GameOver(!red, gameTime.millisElapsed(),world.getStat(3,!red),world.getStat(3,red));
            Greenfoot.setWorld(gameWorld);
        }
    }    

    /**
     * upgrade - randomly upgrades a mine or a factory. 
     */
    private void upgrade(){
        SpaceWorld world = (SpaceWorld)getWorld();
        int random = Greenfoot.getRandomNumber(SpaceWorld.UPGRADE_RANDOM);
        int curMoney = world.getStat(4,red);
        //changes random depending on the amount of money
        if(world.getStat(4,red) > 500000){
            random = Greenfoot.getRandomNumber(5);
        }
        else if(world.getStat(4,red) > 300000){
            random = Greenfoot.getRandomNumber(10);
        }
        else if(curMoney > 200000){
            random = Greenfoot.getRandomNumber(15);
        }
        else if(curMoney > 100000){
            random = Greenfoot.getRandomNumber(25);
        }
        //upgrade factory
        if(random==0 && curMoney>=world.FACTORY_UPGRADE_1_COST+costBuffer){
            //get arraylist of all factories
            ArrayList <Factories> factories = world.getFactoriesArrayList();
            if(factories.size()>0){
                //gets random factory
                Factories f = factories.get(Greenfoot.getRandomNumber(factories.size()));
                if(f.getFactoryLevel()==1 && curMoney>=SpaceWorld.FACTORY_UPGRADE_1_COST+costBuffer){
                    f.addToLvlUpCnt();
                    world.updateStat(4,red,(-1)*SpaceWorld.FACTORY_UPGRADE_1_COST);
                }    
                else if(f.getFactoryLevel()==2 && curMoney>=SpaceWorld.FACTORY_UPGRADE_2_COST+costBuffer){
                    f.addToLvlUpCnt();
                    world.updateStat(4,red,(-1)*SpaceWorld.FACTORY_UPGRADE_2_COST);
                }
                else if(f.getFactoryLevel()==3 && curMoney>=SpaceWorld.FACTORY_UPGRADE_3_COST+costBuffer){
                    f.addToLvlUpCnt();
                    world.updateStat(4,red,(-1)*SpaceWorld.FACTORY_UPGRADE_3_COST);
                }
            }
        } 
        //upgrade mine
        else if(random==1 && curMoney>=world.MINE_UPGRADE_1_COST+costBuffer){
            //get arraylist of all mines
            ArrayList <Mines> mines = world.getMinesArrayList();
            if(mines.size()>0){
                //gets random mine
                Mines m = mines.get(Greenfoot.getRandomNumber(mines.size()));
                if(m.getMineLevel()==1 && curMoney>=SpaceWorld.MINE_UPGRADE_1_COST+costBuffer){
                    m.levelUp();
                    world.updateStat(4,red,(-1)*SpaceWorld.MINE_UPGRADE_1_COST);
                }    
                else if(m.getMineLevel()==2 && curMoney>=SpaceWorld.MINE_UPGRADE_2_COST+costBuffer){
                    m.levelUp();
                    world.updateStat(4,red,(-1)*SpaceWorld.MINE_UPGRADE_2_COST);
                }
                else if(m.getMineLevel()==3 && curMoney>=SpaceWorld.MINE_UPGRADE_3_COST+costBuffer){
                    m.levelUp();
                    world.updateStat(4,red,(-1)*SpaceWorld.MINE_UPGRADE_3_COST);
                }
            }
        }    
    }

    /**
     * addToProductionQueueUnit - adds a vehicle to the queue of the factory with the lowest time
     * 
     * @return boolean         true if vehicle was added to the queue and false if not  
     */
    private boolean addToProductionQueueUnit(){
        SpaceWorld world = (SpaceWorld)getWorld();

        //gets arraylist of all factories
        ArrayList <Factories> factories = world.getFactoriesArrayList();

        if(factories.size()==0) return false;

        //finds factory with lowest production time
        Factories f = null;
        int lowestTime = 0x3f3f3f3f;
        for(Factories factory: factories){
            if(factory.getRed()==red && factory.getTime()<lowestTime){
                f = factory;
                lowestTime = factory.getTime();
            }    
        }

        if(f==null) return false;

        //gets random factory
        int random = Greenfoot.getRandomNumber(f.getFactoryLevel())+1;

        //adds random vehicle to queue
        int curMoney = world.getStat(4,red);
        if(f.getWorkerCnt()>0 && !f.isCapped()){
            if((random==4 || !madeHyperion) && f.getFactoryLevel()==4 && curMoney>=world.HYPERION_COST+costBuffer){
                f.addToProductionQueue(f.getFactoryLevel());
                world.updateStat(4,red,(-1)*SpaceWorld.HYPERION_COST);
                madeHyperion = true;
                return true;
            }
            else if(random==3 && f.getFactoryLevel()>=3 && curMoney>=world.AVENGER_COST+costBuffer){
                f.addToProductionQueue(f.getFactoryLevel()-1);
                world.updateStat(4,red,(-1)*SpaceWorld.AVENGER_COST);
                return true;
            }
            else if(random==2 && f.getFactoryLevel()>=2 && curMoney>=world.NIGHTINGALE_COST*2){
                f.addToProductionQueue(f.getFactoryLevel()+1);
                world.updateStat(4,red,(-1)*SpaceWorld.NIGHTINGALE_COST);
                return true;
            }
            else if(random==1 && f.getFactoryLevel()>=1 && curMoney>=world.VIKING_COST*2){
                f.addToProductionQueue(f.getFactoryLevel());
                world.updateStat(4,red,(-1)*SpaceWorld.VIKING_COST);
                return true;
            }
        }
        return false;
    }    

    /**
     * makePersonUnit - creates either a worker or a miner randomly. 
     */
    private void makePersonUnit(){
        SpaceWorld world = (SpaceWorld)getWorld();
        if(isBusy){
            if(t.getWorld() == null){

                //animation
                isAnimating = true;
                actCountAnimation = 0;
                animation();

                //creates miner or worker
                if(currentUnit == "WORKERS"){
                    //make new factory random
                    int random = Greenfoot.getRandomNumber(SpaceWorld.FACTORY_SPAWN);
                    //changes random depending on the amount of money
                    if(world.getStat(4,red) > 500000){
                        random = Greenfoot.getRandomNumber(3);
                    }
                    else if(world.getStat(4,red) > 300000){
                        random = Greenfoot.getRandomNumber(5);
                    }
                    else if(world.getStat(4,red) > 200000){
                        random = Greenfoot.getRandomNumber(7);
                    }
                    else if(world.getStat(4,red) > 100000){
                        random = Greenfoot.getRandomNumber(10);
                    }
                    if(random==0 && world.getStat(4,red)>=world.FACTORY_COST*2){
                        //adds worker to the world to build a factory
                        world.addObject(new Workers(getRed(),true),getX(),getY()); 
                    }
                    else{
                        //check if factory on same team exists
                        ArrayList <Factories> factories = world.getFactoriesArrayList();
                        boolean isFactory = false;
                        for(Factories f: factories){
                            if(f.getRed()==this.red){
                                isFactory = true;
                                break;
                            }    
                        }

                        //adds worker to the world to go to factory
                        if(isFactory){
                            world.addObject(new Workers(getRed(),false),getX(),getY()); 
                        }    
                    }    
                }
                else {
                    //check if mine on the same team exists
                    ArrayList <Mines> mines = world.getMinesArrayList();
                    boolean isMines = false;
                    for(Mines m: mines){
                        if(m.getRed()==this.red){
                            isMines = true;
                            break;
                        }    
                    }
                    //add miner to go to the mine
                    if(isMines){
                        world.addObject(new Miners(getRed()),getX(),getY()); 
                    }    
                }
                isBusy = false;
            }
        }    
        else if(actCount%actmod==0){
            //make new person random
            int random = Greenfoot.getRandomNumber(SpaceWorld.PEOPLE_SPAWN);
            int curMoney =  world.getStat(4, getRed());

            if(random==0){
                //check if mine on same team exists
                ArrayList <Mines> mines = world.getMinesArrayList();
                boolean isMines = false;
                for(Mines m: mines){
                    if(m.getRed()==this.red){
                        isMines = true;
                        break;
                    }    
                }

                //sets current unit to miner, updates stat and adds timer
                if(isMines){
                    currentUnit = "MINERS";
                    t = new Timer(getRed(),SpaceWorld.MINER_TIME,SpaceWorld.MINER_TIME);
                    world.updateStat(4, getRed(), (-1)*SpaceWorld.MINER_COST);
                    world.addObject(t,getX(),getY());
                    isBusy = true;
                }
            }   
            else if(random==1){
                //sets current unit to worker, updates stat and adds timer
                currentUnit = "WORKERS";
                t = new Timer(getRed(),SpaceWorld.WORKER_TIME,SpaceWorld.WORKER_TIME);
                world.updateStat(4, getRed(), (-1)* SpaceWorld.WORKER_COST);
                world.addObject(t,getX(),getY());
                isBusy = true;
            }    
        }   
    }  
}

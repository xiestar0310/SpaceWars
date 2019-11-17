import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*; 
/**
 * Missile- A subclass of Ammunition with the purpose is to target buildings 
 * and vehicles. Upon collision, the Missile deals damage within a radius of
 * 50 pixels and decreases the HP of all Vehicles and Buildings around it.
 * This class is only meant to be used with the Hyperion Vehicle.
 *  
 * @author Star Xie 
 * @version November 2019
 */
public class Missile extends Ammunition 
{
    /**
     * Constructor - creates a Missile, sets the team, targets an Actor, and specifies damage dealt
     * 
     * @param red               specifies the team (true for red, false for blue)
     * @param actor             the specific object that is being targetted
     * @param damage            specifies the damage taken for each hit
     */ 
    public Missile(boolean red, Actor actor, int damage)
    {
        //The speed of the Missile is set to the speed in the world
        super(red,actor,damage,SpaceWorld.MISSILE_SPEED);
    }
    /**
     * Act - do whatever the Missile wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */ 
    public void act()
    {
        //Accounts for any null pointer exception issues
        if(target.getWorld() != null && target != null)
        {
            turnTowards(target.getX(), target.getY());
            //Moves at missile speed
            move(speed);
            //Checks and deals damage to intersecting classes
            checkAndHit();
        }
        else
        {
            //Moves at missile speed
            move(speed);
            //Checks and deals damage to intersecting classes
            checkAndHit();
        }
        if(getWorld()!=null){
            if(isAtEdge()) getWorld().removeObject(this);
        }
    }

    /**
     * checkAndHit - checks if the Missile has hit a Vehicle or Building 
     * and deals damage to their HP if collision is detected.
     */ 
    public void checkAndHit(){
        //Gets a building that is intersecting a bullet
        Buildings building = (Buildings)getOneObjectAtOffset(0,0,Buildings.class);
        //Gets a vehicle that is intersecting a bullet
        Vehicles vehicle = (Vehicles)getOneObjectAtOffset(0,0,Vehicles.class);
        
        if((building != null && this.red!=building.getRed()) || (vehicle != null && this.red!=vehicle.getRed())){
            
            //Creates a list of all Buildings in a 50 pixel radius range
            ArrayList<Buildings>buildings=(ArrayList)getObjectsInRange(107,Buildings.class);
            //Creates a list of all Vehicles in a 50 pixel radius range
            ArrayList<Vehicles>vehicles=(ArrayList)getObjectsInRange(107,Vehicles.class);
            
            //Iterates through all vehicles and damages vehicles on the opposing team
            for(int i = 0; i < vehicles.size(); i ++)
            {
                //Checks to see if the vehicles in the radius are on the opposite team
                if(vehicles.get((i)).getRed() != this.red)
                {
                    //Deals damage to the intersecting building
                    vehicles.get((i)).getHit(damage);
                }
            }
            
            //Iterates through all buildings and damages buildings on the opposing team
            for(int i=0; i<buildings.size();i++)
            {
                //Checks to see if the buildings in the radius are on the opposite team
                if(buildings.get((i)).getRed() != this.red)
                {
                    //Deals damage to the intersecting vehicle
                    buildings.get((i)).getHit(damage);
                }
            }   
            //Removes the Missile object after damage has been dealt
            getWorld().removeObject(this);
        }
    }

}

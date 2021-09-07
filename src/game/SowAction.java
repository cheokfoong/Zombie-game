package game;

import java.util.Random;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;

/**
 * Special Action for sowing a crop.
 */
public class SowAction extends Action{

	/**
	 * The location to sow the crop
	 */
	protected Location dirt;
	
	Random rand = new Random();
	
	/**
	 * 
	 * @param location the location of the dirt to sow the crop
	 */
	public SowAction(Location location) {
		this.dirt = location;
	}
	
	/**
	 * Perform the Action of sowing a crop
	 *
	 * @param actor The actor performing the action.
	 * @param map The map the actor is on.
	 * @return a description of sowing a crop 
	 */
	@Override
	public String execute(Actor actor, GameMap map) {
		int rand_num =rand.nextInt(100);
		
		//farmer has a 33% chance to sow a crop
		if (rand_num <= 33) {
			dirt.setGround(new Crop());
			return actor + " has sow a crop at location x = " + dirt.x() + ", y = " + dirt.y();
		}
		return menuDescription(actor);
	}

	/**
	 * Returns a descriptive string on sowing a crop
	 * @param actor The actor performing the action.
	 * @return the text we put on the menu
	 */
	@Override
	public String menuDescription(Actor actor) {
		// TODO Auto-generated method stub
		return actor + " did not sow a crop ";
	}
}

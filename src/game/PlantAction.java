package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.Location;

/**
 * 	An action that allows Player to plant seeds
 */
public class PlantAction extends Action {
	
	private String result;
	private Location dirt;
	private Item seed;
	
	/**
	 * Constructor
	 * @param location location to plant seed on
	 * @param seed seed to be planted
	 */
	public PlantAction(Location location, Item seed) {
		this.dirt = location;
		this.seed = seed;
	}
	
	/**
	 * Plant a seed
	 *
	 * @param actor The actor performing the action
	 * @param map The map the actor is on
	 * @return a description of the action after it is processed
	 */
	@Override
	public String execute(Actor actor, GameMap map) {
		
		if (seed.getDisplayChar() == '*')
		{
			dirt.setGround(new MapleTree());
		}
		else if (seed.getDisplayChar() == '^')
		{
			dirt.setGround(new PineTree());
		}
		
		actor.removeItemFromInventory(seed);
		result = "Player has planted a " + seed.toString();
		return result;
	}

	/**
	 * A string describing the action suitable for displaying in the UI menu.
	 *
	 * @param actor The actor performing the action.
	 * @return a String, e.g. "Player plants a seed"
	 */
	@Override
	public String menuDescription(Actor actor) {
		return actor + " plants a " + seed.toString();
	}
}

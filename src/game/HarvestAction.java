package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;

/**
 * Special Action for harvesting a crop.
 */
public class HarvestAction extends Action{

	/**
	 * The crop that is to be harvested
	 */
	protected Location ripeCrop;
	
	/**
	 *  Constructor.
	 *  
	 * @param location the location of where the crop is
	 */
	public HarvestAction(Location location) {
		this.ripeCrop = location;
	}
	
	/**
	 * Perform the Action of harvesting a crop
	 *
	 * @param actor The actor performing the action.
	 * @param map The map the actor is on.
	 * @return a description of harvesting a crop 
	 */
	@Override
	public String execute(Actor actor, GameMap map) {
		//if farmer harvest crop then food item then drop on the ground
		if (actor.getDisplayChar() == 'F') {
			// after harvesting the crop the ground becomes dirt again
			ripeCrop.setGround(new Dirt());
			// drop a food item on the ground
			ripeCrop.addItem(new FoodItem(ItemAbleToPickUp.Food));
		}
		
		//if player harvest crop then store food item in inventory
		else if (actor.getDisplayChar() == '@') {
			ripeCrop.setGround(new Dirt());
			actor.addItemToInventory(new FoodItem(ItemAbleToPickUp.Food));
		}
		return actor + " harvested crop at location x = " + ripeCrop.x() + ", y = " + ripeCrop.y();
	}

	/**
	 * Returns a descriptive string on harvesting a crop
	 * @param actor The actor performing the action.
	 * @return the text we put on the menu
	 */
	@Override
	public String menuDescription(Actor actor) {
		// TODO Auto-generated method stub
		return actor + " harvest a crop";
	}
}

package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Location;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;

/**
 * A class that generates a ConsumeAction if the current Actor is damaged.
 * 
 * @author ram
 *
 */
public class ConsumeBehaviour implements Behaviour{
	private ItemAbleToPickUp food;
	
	/**
	 * Constructor.
	 * 
	 * Sets the item (i.e. ItemAbleToPickUp) that the owner of this
	 * Behaviour is allowed to pick up and eat.
	 * 
	 * @param food item descriptor for Actors that can be picked up and eat
	 */
	public ConsumeBehaviour(ItemAbleToPickUp food) {
		this.food = food;
	}
	
	/**
	 * Returns a ConsumeAction that picks up an item and eat it.
	 * 
	 * item can be picked up and eat if their ItemAbleToPickUp matches the 
	 * "Food status" set 
	 */
	@Override
	public Action getAction(Actor actor, GameMap map) {
		
		for (Item item : map.locationOf(actor).getItems()) {
			if (item.hasCapability(food))
				return new ConsumeAction(item);
		}
		return null;
	}
}

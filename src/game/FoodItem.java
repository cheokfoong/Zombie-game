package game;

import edu.monash.fit2099.engine.Item;

/**
 * Class representing items that can be used as a food to recover health.
 */

public class FoodItem extends Item{

	/**
	 * The health points that the food recovers
	 */
	private int health = 15;
	
	/**
	 * Constructor 
	 * 
	 * @param status the enumerator status of the item
	 */
	public FoodItem(ItemAbleToPickUp status) {
		super("Food" , 'f', true);
		addCapability(status);
	}
	
	/**
	 * Accessor for health gain from eating this food.
	 *
	 * @return the health
	 */
	public int getHealth() {
		return health;
	}
}

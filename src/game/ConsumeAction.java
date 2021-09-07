package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Item;

/**
 * Special Action for consuming a food item.
 */
public class ConsumeAction extends Action{

	/**
	 * The food item that is to be consumed
	 */
	protected FoodItem food;
	
	/**
	 * Constructor.
	 * 
	 * @param food The item that food belongs to
	 */
	public ConsumeAction(Item food) {
		this.food = (FoodItem) food;
	}

	/**
	 * Perform the Action of consuming a food item
	 *
	 * @param actor The actor performing the action.
	 * @param map The map the actor is on.
	 * @return a description of actor eating a food 
	 */
	@Override
	public String execute(Actor actor, GameMap map) {
		if (actor.isConscious()) 
			actor.heal(food.getHealth());
			map.locationOf(actor).removeItem(food);
			actor.removeItemFromInventory(food);
		return menuDescription(actor);
	}

	/**
	 * Returns a descriptive string on consuming a food item
	 * @param actor The actor performing the action.
	 * @return the text we put on the menu
	 */
	@Override
	public String menuDescription(Actor actor) {
		// TODO Auto-generated method stub
		return actor + " eat a food to recover health points";
	}
}

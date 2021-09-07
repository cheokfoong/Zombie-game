package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.Location;

/**
 * 	An action that allows Player to revive corpses
 */
public class ReviveAction extends Action {
	
	private Item corpse;
	private Location corpseLocation;
	private RevivePotion potion;
	
	/**
	 * Constructor 
	 * @param potion potion used to revive corpse
	 * @param corpse corpse of the dead human
	 * @param corpseLocation location of the corpse
	 */
	public ReviveAction(RevivePotion potion, Item corpse, Location corpseLocation)
	{
		this.corpse = corpse;
		this.corpseLocation = corpseLocation;
		this.potion = potion;
	}
	
	/**
	 * Revive a corpse
	 *
	 * @param actor The actor performing the action
	 * @param map The map the actor is on
	 * @return a description of the action after it is processed
	 */
	@Override
	public String execute(Actor actor, GameMap map) {
		corpseLocation.removeItem(corpse);
		corpseLocation.addActor(new Human(corpse.toString()));
		actor.removeItemFromInventory(potion);
		return actor + " used a Revive Potion to bring " + corpse.toString() + " back to life!";
	}

	/**
	 * A string describing the action suitable for displaying in the UI menu.
	 *
	 * @param actor The actor performing the action.
	 * @return a String, e.g. "Player revives a Human"
	 */
	@Override
	public String menuDescription(Actor actor) {
		return actor + " uses a Revive Potion to bring " + corpse.toString() + " back to life";
	}
}

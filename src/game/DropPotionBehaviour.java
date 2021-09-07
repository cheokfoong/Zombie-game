package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.Location;

/**
 *	A class that generates a DropPotionAction if Sorcerer has the ingredients for a potion
 */
public class DropPotionBehaviour implements Behaviour {

	private RevivePotion potion;
	private Location playerLocation;
	
	/**
	 * Constructor
	 * @param playerLocation location of player
	 */
	public DropPotionBehaviour(Location playerLocation)
	{
		this.playerLocation = playerLocation;
	}
	
	/**
	 * Returns a new DropPotionAction that drops a potion at the player's current location
	 */
	@Override
	public Action getAction(Actor actor, GameMap map) {

		return new DropPotionAction(playerLocation);
	}
}

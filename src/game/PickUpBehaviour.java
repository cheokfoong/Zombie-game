package game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Exit;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.Location;
import edu.monash.fit2099.engine.PickUpItemAction;
import edu.monash.fit2099.engine.WeaponItem;

/**
 * A class that generates a PickUpAction if the current Actor is standing
 * next to an item that they can picked up.
 * 
 * @author ram
 *
 */

public class PickUpBehaviour implements Behaviour {
	private ItemAbleToPickUp pickableItem;
	
	/**
	 * Constructor
	 * 
	 * @param pickableItem the ItemAbleToPickUp status of this item
	 */
	public PickUpBehaviour(ItemAbleToPickUp pickableItem) {
		this.pickableItem = pickableItem;
	}
	
	/**
	 * Returns a PickUpItemAction that picks up an Item at the
	 * actor's location if it is portable
	 * 
	 */
	@Override
	public Action getAction(Actor actor, GameMap map) {
		// Is there an item at my location that can be picked up ?
		for (Item item : map.locationOf(actor).getItems()) {
			if (item.hasCapability(pickableItem ))
				return item.getPickUpAction();	
		}
		return null;
	}

}

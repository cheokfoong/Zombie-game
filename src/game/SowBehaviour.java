package game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Exit;
import edu.monash.fit2099.engine.GameMap;

/**
 * A class that generates a SowAction if the current Actor is standing
 * next to a patch of dirt that they can sow a crop on it.
 * 
 * @author ram
 *
 */
public class SowBehaviour implements Behaviour{

	/**
	 * Returns a SowAction that sows a crop on the ground
	 * 
	 * Crop can be sowed if the ground farmer is standing next to is a patch of dirt
	 * and does not contain an actor
	 */
	@Override
	public Action getAction(Actor actor, GameMap map) {
		// Is there an attackable Actor next to me?
		List<Exit> exits = new ArrayList<Exit>(map.locationOf(actor).getExits());
		Collections.shuffle(exits);
		
		for (Exit e: exits) {
			if (!(e.getDestination().containsAnActor()) && (e.getDestination().getGround().getDisplayChar() == '.'))
				return new SowAction(e.getDestination());
		}
		return null;
	}
}

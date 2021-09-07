package game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Exit;
import edu.monash.fit2099.engine.GameMap;

/**
 * A class that generates a HarvestAction if the current Actor is standing
 * next to a crop that they can harvest.
 * 
 * @author ram
 *
 */
public class HarvestBehaviour implements Behaviour{

	/**
	 * Returns a HarvestAction that harvest a ripe Crop
	 * 
	 * Crop can be harvest if their display character is 'R' which indicates 
	 * that it is ripe.
	 */
	@Override
	public Action getAction(Actor actor, GameMap map) {
		// Is there an attackable Actor next to me?
		List<Exit> exits = new ArrayList<Exit>(map.locationOf(actor).getExits());
		Collections.shuffle(exits);
		
		if (map.locationOf(actor).getGround().getDisplayChar() == 'R')
			return new HarvestAction(map.locationOf(actor));
		
		for (Exit e: exits) {
			if (e.getDestination().getGround().getDisplayChar() == 'R')
				return new HarvestAction(e.getDestination());
			}
		
		return null;
	}
}

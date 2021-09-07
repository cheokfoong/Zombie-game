package game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Exit;
import edu.monash.fit2099.engine.GameMap;

/**
 * A class that generates a FertilizeAction if the current Actor is standing
 * next to a crop which they can fertilize.
 * 
 * @author ram
 *
 */
public class FertilizeBehaviour implements Behaviour{
	
	/**
	 * Returns a FertilizeAction that fertilize an unripe Crop
	 * 
	 * Crop can be fertilize if their display character is 'C' which indicates 
	 * that it is unripe.
	 */
	@Override
	public Action getAction(Actor actor, GameMap map) {
		// Is there an attackable Actor next to me?
		if (map.locationOf(actor).getGround().getDisplayChar() == 'C') {
			return new FertilizeAction(map.locationOf(actor).getGround());
		}
		
		return null;
	}
}

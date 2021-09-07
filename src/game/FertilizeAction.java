package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;

/**
 * Special Action for fertilizing an unripe crop.
 */
public class FertilizeAction extends Action{
	
	/**
	 * The crop that is to be fertilize
	 */
	protected Crop unripeCrop;
	
	/**
	 * Constructor.
	 * 
	 * @param ground the ground where the crop is
	 */
	public FertilizeAction(Ground ground) {
		this.unripeCrop = (Crop) ground;
	}
	
	/**
	 * Perform the Action of fertilizing a crop.
	 *
	 * @param actor The actor performing the action.
	 * @param map The map the actor is on.
	 * @return a description of actor fertilizing the crop at which location
	 */
	@Override
	public String execute(Actor actor, GameMap map) {
		unripeCrop.age += 10;
		return actor + " fertilize crop at location x = " + map.locationOf(actor).x() + " y = " + map.locationOf(actor).y();
	}
	
	/**
	 * Returns a descriptive string on fertilizing a crop
	 * @param actor The actor performing the action.
	 * @return the text we put on the menu
	 */
	@Override
	public String menuDescription(Actor actor) {
		// TODO Auto-generated method stub
		return actor + " fertilize a crop";
	}
}

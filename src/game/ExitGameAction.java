package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;

/**
 * An Action class that allows player to exit the game
 */
public class ExitGameAction extends Action{

	/**
	 * Ends the game by terminating the program
	 * 
	 * @param actor The actor performing the action.
	 * @param map The map the actor is on.
	 * @return null
	 */
	@Override
	public String execute(Actor actor, GameMap map) {
		System.exit(0);
		return null;
	}

	/**
	 * Returns a string for exiting the game
	 * @param actor The actor performing the action.
	 * @return the text we put on the menu
	 */
	@Override
	public String menuDescription(Actor actor) {
		// TODO Auto-generated method stub
		return actor + " exits game";
	}

}

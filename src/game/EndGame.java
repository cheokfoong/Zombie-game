package game;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.World;

/**
 *An extended class of World to end the game properly
 */
public class EndGame extends World{

	private boolean humans;
	private boolean zombies;
	
	/**
	 * Constructor
	 * @param display the Display that will display this World.
	 */
	public EndGame(Display display) {
		super(display);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Run the game. Same as run() method from World class
	 */
	@Override
	public void run() {
		super.run();
	}
	
	/**
	 * Returns true if the game is still running.
	 * 
	 * The game will stop if player or all humans and farmers or
	 * all zombies and mambo marie dies
	 * 
	 * @return true if all humans, zombies and players boolean are true and false otherwise
	 */
	@Override
	public boolean stillRunning() {
		humans = false;
		zombies = false;
		for (Actor actor : actorLocations) {
			if (actor.hasCapability(ZombieCapability.ALIVE) && (actor.getDisplayChar() == 'H')) {
				humans = true;
			}
			if (actor.hasCapability(ZombieCapability.UNDEAD))
				zombies = true;
		}
		
		if (humans && zombies && super.stillRunning())
			return true;
		
		return false;
	}
	
	/**
	 * Return a string that can be displayed when the game ends.
	 * 
	 * @return A string message indicating player loses or wins
	 */
	@Override
	public String endGameMessage() {
		if (zombies == false)
			return "Game Over, player wins!";
		return super.endGameMessage() + ", player loses";	
	}
}

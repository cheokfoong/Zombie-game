package game;

import java.util.Random;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;

/**
 *	An area that spawns Mambo Marie
 */
public class MarieSpawn extends Ground{
	
	private Random rand = new Random();
	private Boolean spawned = false;
	private int turns;
	private MamboMarie mamboMarie;
	private boolean dead = false;
	
	/**
	 * Constructor for MarieSpawn
	 */
	public MarieSpawn() {
		super('m');
	}
	
	/**
	 * Dead is true when Mambo Marie has died
	 */
	private void marieDead() {
		dead = true;
	}
	
	/**
	 * Override this to implement impassable terrain, or terrain that is only passable if conditions are met.
	 *
	 * @param actor the Actor to check
	 * @return true
	 */
	@Override
	public boolean canActorEnter(Actor actor) {
		return false;
	}
	
	/**
	 * Override this to implement terrain that blocks thrown objects but not movement, or vice versa
	 * @return true
	 */
	@Override
	public boolean blocksThrownObjects() {
		return true;
	}
	
	/**
	 * To keep track of the number of turns of Mambo Marie
	 * @param location The location of the Ground 
	 */
	@Override
	public void tick(Location location) {
		super.tick(location);
		
		//	Check if Mambo Marie is already on the map, if not, creates one
		// 	This loop is entered only when Mambo Marie is not dead
		if (dead == false)
		{
			if (spawned == false)
			{
				int rand_num = rand.nextInt(100);
				if (rand_num < 5)
				{
					mamboMarie = new MamboMarie("Mambo Marie");
					location.addActor(mamboMarie);
					System.out.println("Mambo Marie has appeared");
					turns = 1;
					spawned = true;
				}
			}
			
			//	If Mambo Marie is on the map, increases its turn, once it reaches 30,
			//	removes it from the map
			else if (spawned == true)
			{	
				turns = turns + 1;
				if (turns == 30)
				{
					location.map().removeActor(mamboMarie);
					spawned = false;
					System.out.println("Mambo Marie has vanished");
				}
			}
		}

		// If Mambo Marie is no longer on the map, it means she's dead
		if (spawned == true && !location.map().contains(mamboMarie))
		{
			marieDead();
		}
	}
}

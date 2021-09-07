package game;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Exit;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;

/**
 * A portable item corpse
 *
 */
public class Corpse extends PortableItem {
	/**
	 * How many turns it is after an actor turn into a corpse
	 */
	private int turn = 0;
	private GameMap map;
	
	/**
	 * The turn it takes for a corpse to rise
	 */
	private int rise =  ThreadLocalRandom .current().nextInt(5, 11);
	
	/**
	 * 
	 * Constructor 
	 * 
	 * @param name the name of the corpse
	 * @param displayChar the display character of the corpse
	 * @param map the map that the corpse is on
	 */
	public Corpse(String name, char displayChar, GameMap map) {
		super(name, displayChar);
		this.name = name;
	}
	
	/**
     * Inform a carried Item of the passage of time.
     * 
     * This method is called once per turn, if the Item is being carried.
     * @param currentLocation The location of the actor carrying this Item.
     * @param actor The actor carrying this Item.
     */
	
	@Override
	public void tick(Location currentLocation, Actor actor) {
		super.tick(currentLocation);
		
		
		turn++;
		if (turn == rise) {
			// if no other actor is at the same location then can create new zombie
			if (actor.getInventory().contains(this)) {
				actor.removeItemFromInventory(this);
				for (Exit exit: currentLocation.getExits()) {
					Location destination = exit.getDestination();
					if (!(destination.containsAnActor())) {
						destination.addActor(new Zombie(this.name, map));
						System.out.println(this.name + " turns into a zombie");
						break;
					}
				}
			}
		}
	}
	
	/**
	 * Inform an Item on the ground of the passage of time. 
     * This method is called once per turn, if the item rests upon the ground.
     * @param currentLocation The location of the ground on which the corpse lie.
	 */
	
	@Override
	public void tick(Location currentLocation) {
		super.tick(currentLocation);
		
		turn++;
		if (turn == rise) {
			if (!(currentLocation.containsAnActor())) {
				currentLocation.addActor(new Zombie(this.name, map));
				currentLocation.removeItem(this);
				System.out.println(this.name + " turns into a zombie");
			}
			
			else if (currentLocation.containsAnActor()) {
				for (Exit exit: currentLocation.getExits()) {
					Location destination = exit.getDestination();
					if (!(destination.containsAnActor())) {
						destination.addActor(new Zombie(this.name, map));
						currentLocation.removeItem(this);
						System.out.println(this.name + " turns into a zombie");
						break;
					}
				}
			}
		}
	}
}

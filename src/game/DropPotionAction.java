package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.Location;

/**
 * 	An action that allows Sorcerer to drop a potion at Player's location
 */
public class DropPotionAction extends Action {

	private RevivePotion potion;
	private Location locationToDrop;
	
	private int revPotionZombieArm = 1;
	private int revPotionZombieLeg = 1;
	private int revPotionMapleSeed = 1;
	private int revPotionPineSeed = 1;
	
	private ZombieArm zombieArm;
	private ZombieLeg zombieLeg;
	private MapleSeed mapleSeed;
	private PineSeed pineSeed;

	/**
	 * Constructor
	 * @param locationToDrop location to drop the potion
	 */
	public DropPotionAction(Location locationToDrop) {
		this.locationToDrop = locationToDrop;
	}

	/**
	 * Drop the potion
	 *
	 * @param actor The actor performing the action
	 * @param map The map the actor is on
	 * @return a description of the action after it is processed
	 */
	@Override
	public String execute(Actor actor, GameMap map) {
		potion = new RevivePotion();
		locationToDrop.addItem(potion);
		
		//	After dropping the potion at Player's location, remove the ingredients from Sorcerer's inventory
		//	Four loops for Zombie Arm, Leg and Maple, Pine Seed
		int arm = 0;
		while (arm < revPotionZombieArm)
		{
			for (Item item : actor.getInventory())
			{
				if (item.getDisplayChar() == ':')
				{
					zombieArm = (ZombieArm) item;
					break;
				}
			}
			actor.removeItemFromInventory(zombieArm);
			arm++;
		}
		
		int leg = 0;
		while (leg < revPotionZombieLeg)
		{
			for (Item item : actor.getInventory())
			{
				if (item.getDisplayChar() == ';')
				{
					zombieLeg = (ZombieLeg) item;
					break;
				}
			}
			actor.removeItemFromInventory(zombieLeg);
			leg++;
		}
		
		int maple = 0;
		while (maple < revPotionMapleSeed)
		{
			for (Item item : actor.getInventory())
			{
				if (item.getDisplayChar() == '*')
				{
					mapleSeed = (MapleSeed) item;
					break;
				}
			}
			actor.removeItemFromInventory(mapleSeed);
			maple++;
		}
		
		int pine = 0;
		while (pine < revPotionPineSeed)
		{
			for (Item item : actor.getInventory())
			{
				if (item.getDisplayChar() == '^')
				{
					pineSeed = (PineSeed) item;
					break;
				}
			}
			actor.removeItemFromInventory(pineSeed);
			pine++;
		}
		
		return actor + " accepted the offer and has created a Revive Potion for Player";
	}

	/**
	 * A string describing the action suitable for displaying in the UI menu.
	 *
	 * @param actor The actor performing the action.
	 * @return a String, e.g. "Player drops the Revive Potion"
	 */
	@Override
	public String menuDescription(Actor actor) {
		return actor + " drops the Revive Potion";
	}
}

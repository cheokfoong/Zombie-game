package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.Location;

/**
 * 	An action that allows Player to offer ingredients to Sorcerer
 */
public class OfferAction extends Action {
	
	private Location sorcererLocation;
	
	private int revPotionZombieArm = 1;
	private int revPotionZombieLeg = 1;
	private int revPotionMapleSeed = 1;
	private int revPotionPineSeed = 1;
	
	private ZombieArm zombieArm;
	private ZombieLeg zombieLeg;
	private MapleSeed mapleSeed;
	private PineSeed pineSeed;
	
	private String result;
	
	/**
	 * Constructor
	 * @param sorcererLocation location of Sorcerer
	 */
	public OfferAction(Location sorcererLocation)
	{
		this.sorcererLocation = sorcererLocation;
	}
	
	/**
	 * Offer ingredients
	 *
	 * @param actor The actor performing the action
	 * @param map The map the actor is on
	 * @return a description of the action after it is processed
	 */
	@Override
	public String execute(Actor actor, GameMap map) {

		//	Four loops to remove each ingredient separately from Player's inventory
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
			sorcererLocation.addItem(zombieArm);
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
			sorcererLocation.addItem(zombieLeg);
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
			sorcererLocation.addItem(mapleSeed);
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
			sorcererLocation.addItem(pineSeed);
			actor.removeItemFromInventory(pineSeed);
			pine++;
		}	
		
		result = "Player has offered ingredients to the Sorcerer in exchange for a Revive Potion";
		return result;
	}

	/**
	 * A string describing the action suitable for displaying in the UI menu.
	 *
	 * @param actor The actor performing the action.
	 * @return a String, e.g. "Player offer ingredients to Sorcerer to create a Revive Potion"
	 */
	@Override
	public String menuDescription(Actor actor) {
		return actor + " offer ingredients to Sorcerer to create a Revive Potion";
	}
}

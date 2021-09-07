package game;

import java.util.Random;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.Weapon;

/**
 * Special Action for attacking other Actors.
 */
public class AttackAction extends Action {

	/**
	 * The Actor that is to be attacked
	 */
	protected Actor target;
	/**
	 * Random number generator
	 */
	protected Random rand = new Random();

	/**
	 * Constructor.
	 * 
	 * @param target the Actor to attack
	 */
	public AttackAction(Actor target) {
		this.target = target;
	}

	/**
	 * Attack a target
	 *
	 * @param actor The actor performing the action
	 * @param map The map the actor is on
	 * @return a description of the action after it is processed
	 */
	@Override
	public String execute(Actor actor, GameMap map) {

		Weapon weapon = actor.getWeapon();
		
		// if actor is a player
		if (actor.getDisplayChar() == '@') {
			// player has 80% chance to attack for all his attacks
			if (rand.nextInt(10) <= 2) 
				return actor + " misses " + target + ".";
		}
		
		// If actor is a Sorcerer
		if (actor.getDisplayChar() == 'S') {
			// Has 10% chance of missing attack
			if (rand.nextInt(10) == 0)
			{
				return actor + " misses " + target + ".";
			}
		}
		
		// if actor is a zombie
		else if (actor.getDisplayChar() == 'Z') {
			// if zombie gets bite weapon
			if(weapon.verb() == "bites") {
				// bites has 30% chance to land a hit
				if (rand.nextInt(10) <= 7)
					return actor + " misses " + target + ".";
			}
			// else every other zombie attacks has 40% chance for successful attack
			else if(rand.nextInt(10) <= 6)
				return actor + " misses " + target + ".";
		}

		int damage = weapon.damage();
		String result = actor + " " + weapon.verb() + " " + target + " for " + damage + " damage";
		
		//zombies uses shotgun and sniper as melee weapon and deals less damage
		if (weapon.verb() == "shot" || weapon.verb() == "snipe") {
			damage = 20;
			result = actor + " attacks " + target + " for " + damage + " damage";
		}

		target.hurt(damage);
		//check if it is bites weapon, if yes then add 5 hp
		if (weapon.verb()=="bites") {
			actor.heal(5);
			result += " and heals itself for 5 hp";
		}
				
		if (!target.isConscious()) {
			if ((target.getDisplayChar() == 'H') || (target.getDisplayChar() == 'F')) {
				Item corpse = new Corpse(target.toString(), '%', map);
				map.locationOf(target).addItem(corpse);
			}
			
			Actions dropActions = new Actions();
			for (Item item : target.getInventory())
				dropActions.add(item.getDropAction());
			for (Action drop : dropActions)		
				drop.execute(target, map);
			map.removeActor(target);	
			
			result += System.lineSeparator() + target + " is killed.";
		}

		return result;
	}

	/**
	 * A string describing the action suitable for displaying in the UI menu.
	 *
	 * @param actor The actor performing the action.
	 * @return a String, e.g. "Player attacks Mortalis"
	 */
	@Override
	public String menuDescription(Actor actor) {
		return actor + " attacks " + target;
	}
}

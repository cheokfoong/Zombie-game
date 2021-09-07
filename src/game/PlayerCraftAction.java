package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
//import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.Weapon;
import edu.monash.fit2099.engine.WeaponItem;

/**
 *	An action that allows Player to craft weapons
 */
public class PlayerCraftAction extends Action {

	private WeaponItem pickedUpWeapon;
	private String result;
	
	/**	Constructor
	 * 
	 * @param weapon weapon to be crafted
	 */
	public PlayerCraftAction(WeaponItem weapon) {
		pickedUpWeapon = weapon;
	}
	
	/**
	 * Craft a weapon
	 *
	 * @param actor The actor performing the action
	 * @param map The map the actor is on
	 * @return a description of the action after it is processed
	 */
	@Override
	public String execute(Actor actor, GameMap map) {
		if (pickedUpWeapon.getDisplayChar() == ':') {
			Weapon club = new Club(ItemAbleToPickUp.Weapons);
			actor.removeItemFromInventory(pickedUpWeapon);
			actor.addItemToInventory((WeaponItem) club);	//Remove arm and add club into inventory
			result = "Player has successfully crafted a new weapon: Zombie Club";
		}
		
		else {
			Weapon mace = new Mace(ItemAbleToPickUp.Weapons);
			actor.removeItemFromInventory(pickedUpWeapon);
			actor.addItemToInventory((WeaponItem) mace);	//Remove leg and add mace into inventory
			result = "Player has successfully crafted a new weapon: Zombie Mace";
		}
		
		return result;
	}
	
	/**
	 * A string describing the action suitable for displaying in the UI menu.
	 *
	 * @param actor The actor performing the action.
	 * @return a String, e.g. "Player crafts a new weapon"
	 */
	@Override
	public String menuDescription(Actor actor) {
		return actor + " crafts a new weapon";
	}
}

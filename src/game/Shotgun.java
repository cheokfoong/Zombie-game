package game;

import edu.monash.fit2099.engine.WeaponItem;

/**
 *  Class representing item which is a shotgun rangedWeapon which is portable
 */
public class Shotgun extends WeaponItem {
	
	protected int ammo = 0;

	/**
	 * Constructor
	 * @param status the enumerator status of the item
	 */
	public Shotgun(ItemAbleToPickUp status) {
		super("shotgun", 'G', 40, "shot");
		addCapability(status);
	}
	
	/**
	 * Reduces the ammo by 1
	 */
	public void reduceAmmo() {
		ammo -= 1;
	}
	
	/**
	 * Increases the ammo by the stated ammoAmount
	 * 
	 * @param ammoAmount The amount of ammo being reloaded
	 */
	public void reloadAmmo(int ammoAmount) {
		ammo += ammoAmount;
	}
}

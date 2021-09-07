package game;

import edu.monash.fit2099.engine.WeaponItem;

/**
 * Class representing item which is a sniper rangedWeapon which is portable
 */
public class Sniper extends WeaponItem{
	
	protected int bullet = 0;
	
	/**
	 * Constructor
	 * @param status the enumerator status of the item
	 */
	public Sniper(ItemAbleToPickUp status) {
		super("sniper", 'E', 45, "snipe");
		addCapability(status);
	}
	
	/**
	 * Reduces the bullet by 1
	 */
	public void reduceBullet() {
		bullet -= 1;
	}
	
	/**
	 * Increase the bullet by the stated bulletAmount
	 * @param bulletAmount The amount of bullet being reloaded
	 */
	public void reloadBullet(int bulletAmount) {
		bullet += bulletAmount;
	}
	
}

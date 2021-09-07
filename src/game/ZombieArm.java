package game;

import edu.monash.fit2099.engine.WeaponItem;

/**
 * A zombie arm as a weapon
 */
public class ZombieArm extends WeaponItem {
	
	public ZombieArm() {
		super("zombie arm", ':', 10, "smacks");
		addCapability(ItemAbleToPickUp.Ingredient);
	}
	
}

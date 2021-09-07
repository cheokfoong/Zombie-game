package game;

import edu.monash.fit2099.engine.WeaponItem;

/**
 * A zombie leg as a weapon
 */
public class ZombieLeg extends WeaponItem {
	
	public ZombieLeg() {
		super("zombie leg", ';', 15, "smacks");
		addCapability(ItemAbleToPickUp.Ingredient);
	}
	
}

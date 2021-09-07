package game;

import edu.monash.fit2099.engine.WeaponItem;

/**
 * A primitive weapon.
 * 
 * @author ram
 *
 */
public class Plank extends WeaponItem {

	public Plank(ItemAbleToPickUp status) {
		super("plank", ')', 20, "whacks");
		addCapability(status);
		// TODO Auto-generated constructor stub
	}

}

package game;

import edu.monash.fit2099.engine.WeaponItem;

/**
 * A mace which is crafted using a zombie leg
 */
public class Mace extends WeaponItem {
	
	/** Constructor
	 * 
	 * @param status to indicate whether it is a weapon or food, 
	 * 		  in this case it is a weapon
	 */
	public Mace(ItemAbleToPickUp status) {
		super("mace", '[', 35, "smacks");
		addCapability(status);
	}

}

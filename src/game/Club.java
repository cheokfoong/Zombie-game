package game;

import edu.monash.fit2099.engine.WeaponItem;

/**
 * A club which is crafted using a zombie arm
 */
public class Club extends WeaponItem {
	
	/** Constructor
	 * 
	 * @param status to indicate whether it is a weapon or food, 
	 * 		  in this case it is a weapon
	 */
	public Club(ItemAbleToPickUp status) {
		super("club", ']', 30, "thwacks");
		addCapability(status);
	}

}

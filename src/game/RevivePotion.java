package game;

import edu.monash.fit2099.engine.Item;

/**
 *	A Revive Potion
 */
public class RevivePotion extends Item {

	/**
	 * Constructor
	 * Has capability potion
	 */
	public RevivePotion() {
		super("Revive Potion", '$', true);
		addCapability(ItemAbleToPickUp.Potion);
	}
}

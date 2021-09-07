package game;

/**
 *	A Pine seed
 */
public class PineSeed extends Seed{
	
	/**
	 * Constructor
	 * Has capabilities seed and ingredient
	 */
	public PineSeed() {
		super("Pine Seed", '^');
		addCapability(ItemAbleToPickUp.Seed);
		addCapability(ItemAbleToPickUp.Ingredient);
	}
}

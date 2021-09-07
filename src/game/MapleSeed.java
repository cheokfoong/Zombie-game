package game;

/**
 *	A Maple seed
 */
public class MapleSeed extends Seed{
	
	/**
	 * Constructor
	 * Has capabilities seed and ingredient
	 */
	public MapleSeed() {
		super("Maple Seed", '*');
		addCapability(ItemAbleToPickUp.Seed);
		addCapability(ItemAbleToPickUp.Ingredient);
	}
}

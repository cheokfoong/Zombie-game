package game;

import edu.monash.fit2099.engine.Item;

/**
 * Class representing items that is used as bullet for sniper
 */
public class SniperAmmunition extends Item{
	
	protected int bulletAmount = 5;
	
	/**
	 * Constructor
	 */
	public SniperAmmunition() {
		super("SniperAmmo", 'e', true);
		// TODO Auto-generated constructor stub
	}

}

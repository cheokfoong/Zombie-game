package game;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.Location;

/**
 * Class representing items that is used as ammo for shotgun 
 */
public class ShotgunAmmunition extends Item{
	
	protected int ammoAmount = 5;
	
	/**
	 * Constructor
	 */
	public ShotgunAmmunition() {
		super("ShotgunAmmo", 'g', true);
		// TODO Auto-generated constructor stub
	}
	

}

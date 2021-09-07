package game;

import edu.monash.fit2099.engine.Location;
import edu.monash.fit2099.engine.Ground;

/**
 *  A crop that starts as an unripe crop and turns ripe after 20 turns.
 *  
 * @author Cheok Foong
 *
 */
public class Crop extends Ground{
	/**
	 * the age of the crop
	 */
	public int age = 0;
	
	private boolean ripe = false;
	
	/**
	 * Constructor 
	 */
	public Crop() {
		super('C');
	}
	
	/**
	 * Ground can also experience the joy of time.
	 * @param location The location of the Ground where the crop is at
	 */
	@Override
	public void tick(Location location) {
		super.tick(location);

		age++;
		if (age == 20)
			displayChar = 'R';
	}
}

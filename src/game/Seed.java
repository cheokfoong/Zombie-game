package game;

import edu.monash.fit2099.engine.Item;

/**
 *	An abstract class that represents all seeds
 */
public abstract class Seed extends Item{

	/**
	 * Constructor 
	 * @param name name of seed
	 * @param displayChar character of seed to be displayed
	 */
	public Seed(String name, char displayChar)
	{
		super(name, displayChar, true);
	}
}

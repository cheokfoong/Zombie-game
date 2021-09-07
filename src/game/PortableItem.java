package game;

import edu.monash.fit2099.engine.Item;

/**
 * Base class for any item that can be picked up and dropped.
 */
public class PortableItem extends Item {

	/**
	 * Constructor
	 * 
	 * @param name the name of the this item
	 * @param displayChar the character to use to represent this item if it is on the ground
	 */
	public PortableItem(String name, char displayChar) {
		super(name, displayChar, true);
	}
}

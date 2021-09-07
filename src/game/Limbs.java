package game;

/**
 * An abstract class that represents the limbs (arms/legs) of zombies
 */
public abstract class Limbs {
	
	private int limbCount;
	
	/** Constructor
	 * 
	 * @param limbAmount amount of limbs to be given
	 */
	public Limbs(int limbAmount) {
		limbCount = limbAmount;
	}
	
	/**
	 * To decrease the limb count of zombie by one
	 */
	public void knockLimbOff() {
		limbCount = limbCount - 1;
	}

	/**
	 * To return the amount of limbs left
	 * @return the limb count
	 */
	public int limbsLeft() {
		return limbCount;
	}
}

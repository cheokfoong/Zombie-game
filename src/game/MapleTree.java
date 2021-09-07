package game;

import java.util.Random;

import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;

/**
 * A grown Maple tree
 */
public class MapleTree extends Ground {
	private int age = 0;
	private Random rand = new Random();

	public MapleTree() {
		super('y');
	}

	@Override
	public void tick(Location location) {
		super.tick(location);

		age++;
		if (age == 25)
			displayChar = 'Y';
		
		// Will only start dropping seeds at age 25 or above
		// Has a 0.1% chance to drop seeds
		if (age >= 25)
		{
			int dropSeed = rand.nextInt(1000);
			if (dropSeed == 0)
			{
				location.addItem(new MapleSeed());
			}
		}
	}
}

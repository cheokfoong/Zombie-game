package game;

import java.util.ArrayList;
import java.util.Random;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;

/**
 * 
 * A class that generates a ChantAction if there are 5 locations available
 * for zombies to spawn on.
 *
 */
public class ChantBehaviour implements Behaviour {

	private int rangeX = 80;
	private int rangeY = 25;
	private Random rand = new Random();
	private ArrayList<ArrayList<Integer>> allCoordinates = new ArrayList<>();

	/**
	 * A method to determine whether the location is out of bounds or not
	 * @param oriX original random value of x
	 * @param moveX direction that is added with oriX as the new location
	 * @param oriY original random value of y
	 * @param moveY direction that is added with oriY as the new location
	 * @return true if not out of bounds, false otherwise
	 */
	private boolean findAltLocation(int oriX, int moveX, int oriY, int moveY) {
		int altX = oriX + moveX;
		int altY = oriY + moveY;
		if ((rangeX > altX && altX >= 0) && (rangeY > altY && altY >= 0))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * Creates random coordinates for 5 zombies
	 * If there is an Actor/Fence/Marie's spawn area on the location of the coordinate, searches its surroundings.
	 */
	@Override
	public Action getAction(Actor actor, GameMap map) {
		
		for(int i = 0; i < 5; i++)
		{
			int randX = rand.nextInt(rangeX);
			int randY = rand.nextInt(rangeY);

			// If nothing is blocking
			if (!map.at(randX, randY).containsAnActor() && map.at(randX, randY).getDisplayChar() != '#' && map.at(randX, randY).getDisplayChar() != 'm')
			{
				// Creates an ArrayList to store coordinates of a zombie(x and y), and then stores them in the main ArrayList
				ArrayList<Integer> coordinates = new ArrayList<Integer>();
				coordinates.add(randX);
				coordinates.add(randY);
				allCoordinates.add(coordinates);
			}
			// If something is blocking, keeps looping until a location is available to spawn the zombie on
			else
			{
				while (map.at(randX, randY).containsAnActor() || map.at(randX, randY).getDisplayChar() == '#' || map.at(randX, randY).getDisplayChar() == 'm')
				{
					// Random direction to determine the new location
					// e.g North-West, South, East, South-East
					int altLocation = rand.nextInt(8);
					if (altLocation == 0)
					{
						boolean possible = findAltLocation(randX, -1, randY, -1);
						if (possible)
						{
							randX = randX - 1;
							randY = randY - 1;
						}
					}
					else if (altLocation == 1)
					{
						boolean possible = findAltLocation(randX, 0, randY, -1);
						if (possible)
						{
							randY = randY - 1;
						}
					}
					else if (altLocation == 2)
					{
						boolean possible = findAltLocation(randX, 1, randY, -1);
						if (possible)
						{
							randX = randX + 1;
							randY = randY - 1;
						}
					}
					else if (altLocation == 3)
					{
						boolean possible = findAltLocation(randX, -1, randY, 0);
						if (possible)
						{
							randX = randX - 1;
						}
					}
					else if (altLocation == 4)
					{
						boolean possible = findAltLocation(randX, 1, randY, 0);
						if (possible)
						{
							randX = randX + 1;
						}
					}
					else if (altLocation == 5)
					{
						boolean possible = findAltLocation(randX, -1, randY, 1);
						if (possible)
						{
							randX = randX - 1;
							randY = randY + 1;
						}
					}
					else if (altLocation == 6)
					{
						boolean possible = findAltLocation(randX, 0, randY, 1);
						if (possible)
						{
							randY = randY + 1;
						}
					}
					else if (altLocation == 7)
					{
						boolean possible = findAltLocation(randX, 1, randY, 1);
						if (possible)
						{
							randX = randX + 1;
							randY = randY + 1;
						}
					}
				}
				
				if (!map.at(randX, randY).containsAnActor() && map.at(randX, randY).getDisplayChar() != '#' && map.at(randX, randY).getDisplayChar() != 'm')
				{
					ArrayList<Integer> coordinates = new ArrayList<Integer>();
					coordinates.add(randX);
					coordinates.add(randY);
					allCoordinates.add(coordinates);
				}
			}
		}
		
		// If there are 5 coordinates created, returns a ChantAction with the main ArrayList of coordinates
		if (allCoordinates.size() == 5)
		{
			return new ChantAction(allCoordinates);
		}
		
		return null;
	}
}

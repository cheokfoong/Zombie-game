package game;

import java.util.ArrayList;
import java.util.Random;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;

/**
 * An action that allows Mambo Marie to chant
 */
public class ChantAction extends Action {

	private Random rand = new Random();
	private ArrayList<ArrayList<Integer>> allCoordinates;
	private String[] names = {"Lee", "Gabriel", "Diana", "Andre", "Hershel",
			"Kenny", "Glenn", "Lily", "Travis", "Omid", "Logan", "Vernon", "Roman",
			"Michelle", "Louis", "James", "Wyatt", "Alvin", "Javier", "Hector",
			"Zach", "Jonas", "Natasha", "Augustus", "Tyler", "Wick", "Sven", "Hank", 
			"Igor", "Barry", "Jane", "Maximus", "Kuro", "Wesley", "Fisher", "Skyler",
			"Summers", "Leonardo", "Madison", "Ray", "Theodore", "Yamato", "Desmond",
			"Emerson", "Nico", "Piper", "Maurice", "Evans", "Donovan", "Brock"};
			
	/**
	 * Constructor for ChantAction
	 * @param allCoordinates an ArrayList of ArrayList of coordinates
	 */
	public ChantAction(ArrayList<ArrayList<Integer>> allCoordinates) {
		this.allCoordinates = allCoordinates;
	}

	/**
	 * Performs chant
	 *
	 * @param actor The actor performing the action
	 * @param map The map the actor is on
	 * @return a description of the action after it is processed
	 */
	@Override
	public String execute(Actor actor, GameMap map) {
		// The names of the zombies are randomed from a list of 50 names
		// To decrease the chances of two zombies having the same name
		for(int i = 0; i < allCoordinates.size(); i++)
		{
			int randName = rand.nextInt(names.length);
			int getX = allCoordinates.get(i).get(0);
			int getY = allCoordinates.get(i).get(1);
			String name = names[randName];
			map.at(getX, getY).addActor(new Zombie(name, map));
		}
		
		String result = "5 new Zombies have spawned across the map!";
		return result;
	}

	/**
	 * Returns a descriptive string on performing a chant
	 * @param actor The actor performing the action
	 * @return the text we put on the menu
	 */
	@Override
	public String menuDescription(Actor actor) {
		return actor + " performs a chant";
	}
}

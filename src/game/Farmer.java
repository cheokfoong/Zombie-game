package game;

import java.util.ArrayList;
import java.util.Collections;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.DoNothingAction;
import edu.monash.fit2099.engine.GameMap;

/**
 * Class representing a Farmer
 * @author User
 *
 */
public class Farmer extends Human{
	
	ArrayList<Behaviour> behaviours = new ArrayList<Behaviour>();
	
	/**
	 * Constructor
	 * 
	 * @param name the farmer's display name
	 */
	public Farmer(String name) {
		super(name, 'F', 50);
	}
	
	@Override
	public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
		// require shuffling everytime so that farmer has a chance to perform different action everytime
		behaviours.clear();
		behaviours.add(new SowBehaviour());
		behaviours.add(new FertilizeBehaviour());
		behaviours.add(new HarvestBehaviour());
		behaviours.add(new WanderBehaviour());

		Collections.shuffle(behaviours);
		
		for (Behaviour behaviour : behaviours) {
			Action action = behaviour.getAction(this, map);
			if (action != null) 
				return action;
		}
		
		return new DoNothingAction();	
	}
}

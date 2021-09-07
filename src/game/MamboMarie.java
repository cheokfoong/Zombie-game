package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.DoNothingAction;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.IntrinsicWeapon;

/**
 *	Mambo Marie, weaker than zombies but has stronger attacks
 */
public class MamboMarie extends ZombieActor{
	private Behaviour[] behaviours = {
			new AttackBehaviour(ZombieCapability.ALIVE),
			new WanderBehaviour()
	};
	
	private int turns = 1;

	/**
	 * A constructor for Mambo Marie
	 * @param name Mambo Marie's name
	 */
	public MamboMarie(String name) {
		super(name, 'M', 80, ZombieCapability.UNDEAD);
	}

	/**
	 * Increment the number of turns
	 */
	private void incTurns() {
		turns = turns + 1;
	}
	
	/**
	 * Obtain the number of turns
	 * @return turns number of turns
	 */
	private int checkTurns() {
		return turns;
	}
	
	/**
	 * Creates and returns an intrinsic weapon.
	 *
	 * By default, the Actor 'punches' for 5 damage. Override this method to create
	 * an Actor with more interesting descriptions and/or different damage.
	 *
	 * @return a freshly-instantiated IntrinsicWeapon
	 */
	@Override
	public IntrinsicWeapon getIntrinsicWeapon() {
		return new IntrinsicWeapon(30, "strikes");
	}
	
	/**
	 * Mambo Marie stops and chants every 10 turns. If not she wanders around the map
	 * and can attack the Player, Humans or Farmers. 
	 *
	 * @param actions    collection of possible Actions for Mambo Marie
	 * @param lastAction The Action Mambo Marie took last turn. Can do interesting things in conjunction with Action.getNextAction()
	 * @param map        the map containing Mambo Marie
	 * @param display    the I/O object to which messages may be written
	 * @return the Action to be performed
	 */
	@Override
	public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
		// Turns 1-10 & 12-21, MamboMarie does not chant, till the 11th and 22nd turn
		// Increment turns using incTurns() for every turn
		if (checkTurns() == 11 || checkTurns() == 22)
		{
			Behaviour[] withChanting = {
					new ChantBehaviour(),
					new AttackBehaviour(ZombieCapability.ALIVE),
					new WanderBehaviour()
					};
			behaviours = withChanting;
			incTurns();
		}
		else
		{
			Behaviour[] withoutChanting = {
					new AttackBehaviour(ZombieCapability.ALIVE),
					new WanderBehaviour()
					};
			behaviours = withoutChanting;
			incTurns();
		}
				
		for (Behaviour behaviour : behaviours) {
			Action action = behaviour.getAction(this, map);
			if (action != null) 
				return action;
		}
		
		return new DoNothingAction();
	}
}

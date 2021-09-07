package game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.DoNothingAction;
import edu.monash.fit2099.engine.Exit;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.IntrinsicWeapon;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.Weapon;

/**
 *	The one and only Sorcerer, extremely powerful, but too lazy to move
 */
public class Sorcerer extends ZombieActor{

	private Behaviour[] behaviours = {
			new PickUpBehaviour(ItemAbleToPickUp.Ingredient),
			new PickUpBehaviour(ItemAbleToPickUp.Potion),
			new AttackBehaviour(ZombieCapability.UNDEAD),
			};
	
	private int revPotionZombieArm = 1;
	private int revPotionZombieLeg = 1;
	private int revPotionMapleSeed = 1;
	private int revPotionPineSeed = 1;
	
	/**
	 * Constructor 
	 * @param name name of Sorcerer
	 */
	public Sorcerer(String name) {
		super(name, 'S', 200, ZombieCapability.ALIVE);
	}
	
	/**
	 * @return IntrinsicWeapon fireblasts
	 */
	@Override
	public Weapon getWeapon() {
		return getIntrinsicWeapon();
	}
	
	/**
	 * @return IntrinsicWeapon fireblasts
	 */
	@Override
	public IntrinsicWeapon getIntrinsicWeapon() {	
		return new IntrinsicWeapon(1000, "obliterates");
	}

	/**
	 * If Sorcerer has the ingredients needed to make a Potion, it will
	 * 
	 * @param actions list of possible Actions
	 * @param lastAction previous Action, if it was a multiturn action
	 * @param map the map where the Sorcerer is
	 * @param display the Display where the Sorcerer's utterances will be displayed
	 */
	@Override
	public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
		
		List<Exit> exits = new ArrayList<Exit>(map.locationOf(this).getExits());
		
		//	To check whether Sorcerer has enough amount of ingredients to create a Revive Potion
		// 	Four loops for Zombie Arm, Leg and Maple, Pine Seed
		int arm = 0;
		for (Item zombieArm : this.getInventory())
		{
			if (arm == revPotionZombieArm)
			{
				break;
			}
			else if (zombieArm.getDisplayChar() == ':')
			{
				arm = arm + 1;
			}
		}
		
		int leg = 0;
		for (Item zombieLeg : this.getInventory())
		{
			if (leg == revPotionZombieLeg)
			{
				break;
			}
			else if (zombieLeg.getDisplayChar() == ';')
			{
				leg = leg + 1;
			}
		}
		
		int maple = 0;
		for (Item mapleSeed : this.getInventory())
		{
			if (maple == revPotionMapleSeed)
			{
				break;
			}
			else if (mapleSeed.getDisplayChar() == '*')
			{
				maple = maple + 1;
			}
		}
		
		int pine = 0;
		for (Item pineSeed : this.getInventory())
		{
			if (pine == revPotionPineSeed)
			{
				break;
			}
			else if (pineSeed.getDisplayChar() == '^')
			{
				pine = pine + 1;
			}
		}
		
		//	If Sorcerer has enough ingredients, if can create a potion and drop it to the Player
		if (arm == revPotionZombieArm && leg == revPotionZombieLeg && maple == revPotionMapleSeed && pine == revPotionPineSeed)
		{
			for (Exit e: exits)
			{
				if (e.getDestination().containsAnActor())
				{
					if (e.getDestination().getActor().getDisplayChar() == '@')
					{
						Behaviour[] withPotionBehaviours = {
								new PickUpBehaviour(ItemAbleToPickUp.Ingredient),
								new PickUpBehaviour(ItemAbleToPickUp.Potion),
								new AttackBehaviour(ZombieCapability.UNDEAD),
								new DropPotionBehaviour(e.getDestination())
								};
						behaviours = withPotionBehaviours;
						arm = 0;
						leg = 0;
						maple = 0;
						pine = 0;
					}
				}
			}
		}
		// Else, it is unable to create a potion and drop it to the Player
		else
		{
			Behaviour[] withoutPotionBehaviours = {
					new PickUpBehaviour(ItemAbleToPickUp.Ingredient),
					new PickUpBehaviour(ItemAbleToPickUp.Potion),
					new AttackBehaviour(ZombieCapability.UNDEAD),
					};
			behaviours = withoutPotionBehaviours;
		}
		
		for (Behaviour behaviour : behaviours) {
			Action action = behaviour.getAction(this, map);
			if (action != null) 
				return action;
		}
		
		return new DoNothingAction();	
	}
}

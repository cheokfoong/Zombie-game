package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.DoNothingAction;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.IntrinsicWeapon;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.Weapon;
import edu.monash.fit2099.engine.WeaponItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * A Zombie.
 * 
 * This Zombie is pretty boring.  It needs to be made more interesting.
 * 
 * @author ram
 *
 */
public class Zombie extends ZombieActor {
	private Behaviour[] behaviours = {
			new PickUpBehaviour(ItemAbleToPickUp.Weapons),
			new AttackBehaviour(ZombieCapability.ALIVE),
			new HuntBehaviour(Human.class, 10),
			new WanderBehaviour()
	};

	private Random rand = new Random();
	private Random randAttack = new Random();
	private int punchChance = 49;
	private int skipTurn = 0;
	
	private GameMap map;
	
	private Limbs arms = new Arms(2);
	private Limbs legs = new Legs(2);
	
	public Zombie(String name, GameMap map) {
		super(name, 'Z', 100, ZombieCapability.UNDEAD);
		this.map = map;
	}
	
	/**
	 * IntrinsicWeapon for zombies are "punches" and "bites" and has a 50% chance
	 * of returning each one 
	 * @return IntrinsicWeapon either punch or bite
	 */
	@Override
	public IntrinsicWeapon getIntrinsicWeapon() {
		
		int rand_num = randAttack.nextInt(100);
		
		if (rand_num <= punchChance) {
			return new IntrinsicWeapon(10, "punches");
		}
		else {	
			return new IntrinsicWeapon(20, "bites");
		}
	}
	
	/**
	 * Has a 25% chance to knock of limbs, 50% for either arm or leg.
	 * The four main loops are for:
	 * 1. Knock an arm off, when there are 2 arms left
	 * 2. Knock a leg off, when there are 2 legs left
	 * 3. Knock an arm off, when there is 1 arm left
	 * 4. Knock a leg off, when there is 1 leg left
	 * 
	 * @param points amount of damage done
	 */
	@Override
	public void hurt(int points) {
		hitPoints -= points;    
		
		if (points == 1000)
		{
			for (Item weapon : this.getInventory())
			{
				if (weapon.hasCapability(ItemAbleToPickUp.Weapons))
				{
					map.locationOf(this).addItem(weapon);
				}
			}
			
			for (int i = 0; i < 2; i++)
			{
				this.arms.knockLimbOff();
			}
			
			for (int i = 0; i < 2; i++)
			{
				this.legs.knockLimbOff();
			}
		}
		
		if (arms.limbsLeft() > 0 || legs.limbsLeft() > 0)
		{
			boolean chance = rand.nextInt(4) == 1;
			if (chance) 
			{
				int knockLimb = rand.nextInt(2);
				if (knockLimb == 0)
				{
					if (arms.limbsLeft() == 2)
					{
						this.arms.knockLimbOff();
						punchChance = 24;	//Probability of punching is halved, therefore chance of biting increases
						boolean dropWeapon = rand.nextBoolean();
						if (dropWeapon)
						{
							Weapon weaponToDrop = this.getWeapon();
							if (!(weaponToDrop instanceof IntrinsicWeapon))
							{
								this.removeItemFromInventory((WeaponItem) weaponToDrop);
								map.locationOf(this).addItem((WeaponItem) weaponToDrop);
							}
						}
						map.locationOf(this).addItem(new ZombieArm());
					}
					else if (arms.limbsLeft() == 1)
					{
						{
							this.arms.knockLimbOff();
							punchChance = 0;
							Weapon weaponToDrop = this.getWeapon();
							if (!(weaponToDrop instanceof IntrinsicWeapon))
							{
								this.removeItemFromInventory((WeaponItem) weaponToDrop);
								map.locationOf(this).addItem((WeaponItem) weaponToDrop);
							}
							map.locationOf(this).addItem(new ZombieArm());
						}
					}
				}
				else
				{
					if (legs.limbsLeft() == 2)
					{
						this.legs.knockLimbOff();
						skipTurn = 1;	//Zombie can only move every 2 turns
						map.locationOf(this).addItem(new ZombieLeg());
					}
					else if (legs.limbsLeft() == 1)
					{
						this.legs.knockLimbOff();
						skipTurn = -1;	//Zombie will never be able to move
						map.locationOf(this).addItem(new ZombieLeg());
					}
				}
			}
		}
	}
	
	/**
	 *  Each Zombie has a 10% chance of Returning a String  “Braaaaains” for every turn
	 *  @return A string that a zombie will say or null
	 */
	public String groaningSound() {
		Random rand = new Random();
		
		int rand_num = rand.nextInt(10);
		
		if (rand_num <= 1) {
			return " mutters Braaaaaains";
		}
		else
			return null;
	}		

	/**
	 * If a Zombie can attack, it will.  If not, it will chase any human within 10 spaces.  
	 * If no humans are close enough it will wander randomly.
	 * 
	 * @param actions list of possible Actions
	 * @param lastAction previous Action, if it was a multiturn action
	 * @param map the map where the current Zombie is
	 * @param display the Display where the Zombie's utterances will be displayed
	 */
	@Override
	public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
		
		//When the first loop is entered, it means that Zombie has lost both legs and will not be able to move
		if (skipTurn == -1) {
			Behaviour[] noMovementBehaviours = {
					new PickUpBehaviour(ItemAbleToPickUp.Weapons),
					new AttackBehaviour(ZombieCapability.ALIVE)
					};
			behaviours = noMovementBehaviours;
		}
		//Second loop is entered when skipTurn is an odd number, meaning that in this turn the Zombie will not be
		//able to move
		else if (skipTurn > 0 && skipTurn % 2 != 0) {
			Behaviour[] noMovementBehaviours = {
					new PickUpBehaviour(ItemAbleToPickUp.Weapons),
					new AttackBehaviour(ZombieCapability.ALIVE)
					};
			behaviours = noMovementBehaviours;
			skipTurn = skipTurn + 1;
		}
		//Third loop is entered when skipTurn is an even number, meaning that in this turn the Zombie will be able
		//to move
		else {
			Behaviour[] withMovementBehaviours = {
					new PickUpBehaviour(ItemAbleToPickUp.Weapons),
					new AttackBehaviour(ZombieCapability.ALIVE),
					new HuntBehaviour(Human.class, 10),
					new WanderBehaviour()
					};
			behaviours = withMovementBehaviours;
			skipTurn = skipTurn + 1;
		}
		
		//Zombie can make some sounds
		String speak = groaningSound();
		if (speak != null)
			System.out.println(this.name + speak);
		
		for (Behaviour behaviour : behaviours) {
			Action action = behaviour.getAction(this, map);
			if (action != null) 
				return action;
		}
		
		return new DoNothingAction();	
	}
}

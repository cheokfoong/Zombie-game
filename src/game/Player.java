package game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.DropItemAction;
import edu.monash.fit2099.engine.Exit;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.IntrinsicWeapon;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.Menu;
import edu.monash.fit2099.engine.MoveActorAction;
import edu.monash.fit2099.engine.Weapon;
import edu.monash.fit2099.engine.WeaponItem;
import edu.monash.fit2099.engine.World;

/**
 * Class representing the Player.
 */
public class Player extends Human {
	
	private GameMap startMap;
	private GameMap townMap;
	private Menu menu = new Menu();
	
	private int revPotionZombieArm = 1;
	private int revPotionZombieLeg = 1;
	private int revPotionMapleSeed = 1;
	private int revPotionPineSeed = 1;

	/**
	 * Constructor.
	 *
	 * @param name        Name to call the player in the UI
	 * @param displayChar Character to represent the player in the UI
	 * @param hitPoints   Player's starting number of hitpoints
	 */
	public Player(String name, char displayChar, int hitPoints, GameMap startMap, GameMap townMap) {
		super(name, displayChar, hitPoints);
		this.startMap = startMap;
		this.townMap = townMap;
	}
	
	/**
	 * @return IntrinsicWeapon to punch a target
	 */
	@Override
	public IntrinsicWeapon getIntrinsicWeapon() {
		return new IntrinsicWeapon(10, "punches");
	}
	
	/**
	 * Processes the actions available for Player
	 * 
	 * @param actions list of possible Actions
	 * @param lastAction previous Action, if it was a multiturn action
	 * @param map the map where the Player is
	 * @param display the Display where the Player's utterances will be displayed
	 */
	@Override
	public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
		Actions rangedActions = new Actions();
		
		// Handle multi-turn Actions
		if (lastAction.getNextAction() != null)
			return lastAction.getNextAction();
		
		//Player can harvest food if it is standing on or next to a ripe crop
		List<Exit> exits = new ArrayList<Exit>(map.locationOf(this).getExits());
		Collections.shuffle(exits);
		if (map.locationOf(this).getGround().getDisplayChar() == 'R')
			actions.add(new HarvestAction(map.locationOf(this)));
		
		for (Exit e: exits) {
			if (e.getDestination().getGround().getDisplayChar() == 'R')
				actions.add(new HarvestAction(e.getDestination()));
			}
		
		//Player can craft limbs into a club or mace
		for (Item weapon : this.getInventory())
		{
			if (weapon.getDisplayChar() == ':')
			{
				actions.add(new PlayerCraftAction((WeaponItem) weapon));
			}
			else if (weapon.getDisplayChar() == ';')
			{
				actions.add(new PlayerCraftAction((WeaponItem) weapon));
			}
		}
		

		for (Item item: this.getInventory()) {
			
			//Player can consume food
			if (item.hasCapability(ItemAbleToPickUp.Food))
			{
				actions.add(new ConsumeAction(item));
			}
			
			//if player has shotgun in inventory
			if (item instanceof Shotgun) {				
				for (Exit e: exits) {
					actions.add(new RangedAttackAction(e.getDestination(), e.getName()));
				}
				
				//if player has shotgun ammo in inventory
				for (Item items: this.getInventory()) {
					if (items.getDisplayChar() == 'g')
						actions.add(new ReloadAction(items));
				}

			}
			
			//if player has sniper in inventory
			if (item instanceof Sniper) {
				//List containing actors to be a sniper target
				ArrayList<Actor> targets = new ArrayList<Actor>();
				int radius = (int)map.getXRange().max()/3;
				
				//check if the actors that are in the circle is a zombie and add to the list
				for (int x = 0; x < map.getXRange().max(); x ++) {
					for (int y = 0; y < map.getYRange().max(); y ++) {
						if (map.at(x, y).containsAnActor() && map.at(x, y).getActor().hasCapability(ZombieCapability.UNDEAD)) {
							if (isInsideCircle(map.locationOf(this).x(), map.locationOf(this).y(), radius, map.locationOf(map.at(x, y).getActor()).x(), map.locationOf(map.at(x, y).getActor()).y())) 
							targets.add(map.at(x, y).getActor());	
						}
					}
				}
				
				// if player has sniper ammo in inventory
				for (Item items: this.getInventory()) {
					if (items.getDisplayChar() == 's')
						rangedActions.add(new ReloadAction(items));
				}
				
				for (Actor zombie: targets) {
					rangedActions.add(new RangedAttackAction(zombie));
				}
				
				//create submenu for sniper weapon
				rangedActions.add(new DropItemAction(item));
				return menu.showMenu(this, rangedActions, display);
			}
		}
		
		//Player can travel between two maps
		if (map.locationOf(this).getGround().getDisplayChar() == '_') {
			if (map == startMap)
			{
				actions.add(new MoveActorAction(townMap.at(0, 0), "to Town Map!"));
			}
			else if (map == townMap)
			{
				actions.add(new MoveActorAction(startMap.at(0, 0), "to Start Map!"));
			}
		}
		
		//Player can plant seeds
		for (Item seed : this.getInventory())
		{
			if (seed.getDisplayChar() == '*' || seed .getDisplayChar() == '^')
			{
				if (map.locationOf(this).getGround().getDisplayChar() == '.')
				{
					actions.add(new PlantAction(map.locationOf(this), seed));
				}
			}
		}
		
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
		
		for (Exit e: exits)
		{
			if (e.getDestination().containsAnActor())
			{
				if (e.getDestination().getActor().getDisplayChar() == 'S')
				{
					if (arm == revPotionZombieArm && leg == revPotionZombieLeg && maple == revPotionMapleSeed && pine == revPotionPineSeed)
					{
						actions.add(new OfferAction(e.getDestination()));
						arm = 0;
						leg = 0;
						maple = 0;
						pine = 0;
					}
				}
			}
		}
		
		for (Exit e: exits)
		{
			for (Item destItem : e.getDestination().getItems())
			{
				if (destItem.getDisplayChar() == '%')
				{
					for (Item item : this.getInventory())
					{
						if (item.getDisplayChar() == '$')
						{
							actions.add(new ReviveAction((RevivePotion) item, destItem, e.getDestination()));
						}
					}
				}
			}
		}
		
		actions.add(new ExitGameAction());
		return menu.showMenu(this, actions, display);
	}
	

	/**
	 * A function to check if the target's location lies inside a circle or not. A method for sniper
	 * 
	 * @param circle_x The x coordinate of the player
	 * @param circle_y The y coordinate of the player
	 * @param rad      The radius of the circle 
	 * @param x        The x coordinate of the target
	 * @param y		   The y coordinate of the target
	 * @return		   Boolean value of whether the target lies inside the circle
	 */
	public boolean isInsideCircle(int circle_x, int circle_y, int rad, int x, int y) {
				
		// Compare radius of circle with 
		// distance of its center from 
		// given point
		if ((x - circle_x) * (x - circle_x) + (y - circle_y) * (y - circle_y) <= rad * rad) 
			return true; 
		else
			return false;
		}
}

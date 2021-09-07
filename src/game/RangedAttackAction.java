package game;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.Location;
import edu.monash.fit2099.engine.Weapon;

/**
 * Special attack action for ranged weapon
 *
 */
public class RangedAttackAction extends Action{
	
	protected Shotgun shotgun;
	protected Sniper sniper;
	protected Actor target;
	protected Actor zombie;
	protected Location locationToShoot;
	protected String direction;
	protected Display display = new Display();
	
	protected Random rand = new Random();
	
	/**
	 * Constructor for sniper
	 * 
	 * @param zombie The zombie which act as a target 
	 */
	public RangedAttackAction(Actor zombie) {
		this.zombie = zombie;
	}
	
	/**
	 * Constructor for shotgun
	 * 
	 * @param locationToShoot The location where the player is shooting
	 * @param direction       The direction where the player is shooting
	 */
	public RangedAttackAction(Location locationToShoot, String direction) {
		this.locationToShoot = locationToShoot;
		this.direction = direction;
	}

	/**
	 * Performing the Action of shooting a rangedWeapon.
	 *
	 * @param actor The actor performing the action.
	 * @param map The map the actor is on.
	 * @return a description of the action after it is processed
	 */
	@Override
	public String execute(Actor actor, GameMap map) {

		Weapon weapon = actor.getWeapon();
		int damage = weapon.damage();
		Location northEastVertex;
		Location northWestVertex;
		
		
		//list containing all the actors on the map
		ArrayList<Actor> locationOfActors = new ArrayList<Actor>();
		
		//Adding each actors on the map into the list
		for (int x = 0; x < map.getXRange().max(); x ++) {
			for (int y = 0; y < map.getYRange().max(); y ++) {
				
				if (map.at(x, y).containsAnActor() && !(map.at(x, y).getActor().getDisplayChar() == '@')) {
					Actor actors = map.at(x, y).getActor();
					locationOfActors.add(actors);
					
	
				}
			}						
		}
		
		String result = "";
		
		//if actor is a player
		if (actor.getDisplayChar() == '@') {
			// if weapon is a shotgun 

			if (weapon.verb() == "shot") {
				
				// in order to access shotgun class
				this.shotgun = (Shotgun) weapon;
				
				// shotgun has a 75% chance to hit target
				if(rand.nextInt(100) <= 25) {
					return actor + " failed to fire shotgun ";
				}
				else {				
					
					if (shotgun.ammo == 0) {
						return "Shotgun ran out of ammo";
					}
					
					//reduce shotgun ammo by 1 everytime actor tries to shoot
					shotgun.reduceAmmo();
					
					northEastVertex = map.at(locationToShoot.x() + 2, locationToShoot.y() - 2);
					northWestVertex = map.at(locationToShoot.x() - 2, locationToShoot.y() - 2);			
					
					
					for (Actor thisActor: locationOfActors) {
						
						if (isInsideTriangle(locationToShoot.x(), locationToShoot.y(), northEastVertex.x(), northEastVertex .y(), 
								northWestVertex.x(), northWestVertex.y(), map.locationOf(thisActor).x(),  map.locationOf(thisActor).y())) {
							this.target = thisActor;
							target.hurt(damage);
							result += System.lineSeparator() +  actor + " shot " + target + " for " + damage + " damage";
							
							// for shotgun that includes humans and zombies
							// When human is killed it wont become corpse because it is killed by player
							if (!target.isConscious()) {
								
								Actions dropActions = new Actions();
								for (Item item : target.getInventory())
									dropActions.add(item.getDropAction());
								for (Action drop : dropActions)		
									drop.execute(target, map);
								map.removeActor(target);	
								
								result += System.lineSeparator() + target + " is killed.";
							}
						}
					}
					
				}
			}		
	
		
			// if weapon is a sniper 
			else if (weapon.verb() == "snipe") {
				char read;
				int aim = 0;
				
				//in order to access sniper class
				this.sniper = (Sniper) weapon;
				
				Scanner input = new Scanner(System.in);

				while(true) {
					display.println("Enter 'a' to aim, 's' to shoot or 'r' to choose another target");
					read = input.next().charAt(0);
					
					if (read != 'a' && read != 's' && read != 'r')
						display.println("Invalid character input");
					
					if (read == 'a') {
						if (aim < 2)
							aim += 1;
						else 
							// maximum aim twice only
							return "Already aim twice, maximum damage output reached";
					}
					
					else if (read == 's') {
						
						if (sniper.bullet == 0) {
							return "sniper ran out of bullet";
						}
						
						//reduce sniper bullet by 1 everytime actor tries to shoot
						sniper.reduceBullet();
						
						// if no deal aim standard damage
						if (aim == 0) {
							if(rand.nextInt(100) <= 25) {
								return actor + " failed to shoot " + zombie;
							}
							else { 
								zombie.hurt(damage);	
								result += actor + " snipe " + zombie + " for " + damage + " damage";
								break;
							}
						}
						
						//if aim once deal double the damage
						else if (aim == 1) {
							if(rand.nextInt(100) <= 10) {
								return actor + " failed to shoot " + zombie;
							}
							else {
								zombie.hurt(damage*2);
								result += actor + " snipe " + zombie + " for " + damage*2 + " damage";
								break;
							}
						}
						
						else {
							//aim 2 times instant kill
							zombie.hurt(100);
							result += actor + " instantly killed " + zombie;
							break;
						}
					}
					
					else if (read == 'r') {
						return actor + "'s aim concentration on " + zombie + " is broken " + System.lineSeparator() + "player chooses another target to shoot";
					}
				}
				
				// for sniper that includes zombies only
				if (!zombie.isConscious()) {
					
					Actions dropActions = new Actions();
					for (Item item : zombie.getInventory())
						dropActions.add(item.getDropAction());
					for (Action drop : dropActions)		
						drop.execute(zombie, map);
					map.removeActor(zombie);	
					
					result += System.lineSeparator() + zombie + " is killed.";
				}			
			}
		}
			
		
		return menuDescription(actor) + System.lineSeparator() + result;
	}
	
	/**
	 * A utility function to calculate area of triangle formed by (x1, y1) (x2, y2) and (x3, y3)
	 * 
	 * @param x1 The X coordinate of the first vertex
	 * @param y1 The Y coordinate of the first vertex
	 * @param x2 The X coordinate of the second vertex
	 * @param y2 The Y coordinate of the second vertex
	 * @param x3 The X coordinate of the third vertex
	 * @param y3 The Y coordinate of the third vertex
	 * @return The area of the triangle 
	 */
	public double areaOfTriangle(int x1, int y1, int x2, int y2, int x3, int y3) {
		return Math.abs((x1*(y2-y3) + x2*(y3-y1)+ x3*(y1-y2))/2.0);
	}
	
	/**
	 * A function to check whether point P(x, y) lies inside the triangle formed by A(x1, y1), B(x2, y2) and C(x3, y3). A method for shotgun
	 * 
	 * @param x1 The X coordinate of the location where the player is shooting
	 * @param y1 The Y coordinate of the location where the player is shooting
	 * @param x2 The X coordinate of North-East and 2 squares away from the location where the player is shooting
	 * @param y2 The Y coordinate of North-East and 2 squares away from the location where the player is shooting
	 * @param x3 The X coordinate of North-West and 2 squares away from the location where the player is shooting
	 * @param y3 The Y coordinate of North-West and 2 squares away from the location where the player is shooting
	 * @param x  The X coordinate of the actor that exist in the map
	 * @param y  The Y coordinate of the actor that exist in the map
	 * @return   Boolean value whether the actor lies within the triangle area
	 */
	public boolean isInsideTriangle(int x1, int y1, int x2, int y2, int x3, int y3, int x, int y) {
		
		// Let A = northEastVertex, B = northWestVertex, C = locationToShoot, P = location of actors in the map
		/* Calculate area of triangle ABC */
		double A = areaOfTriangle(x1, y1, x2, y2, x3, y3);
		
		/* Calculate area of triangle PBC */  
		double A1 = areaOfTriangle(x, y, x2, y2, x3, y3);
		
		/* Calculate area of triangle PAC */  
		double A2 = areaOfTriangle(x1, y1, x, y, x3, y3);
		
		/* Calculate area of triangle PAB */
		double A3 = areaOfTriangle(x1, y1, x2, y2, x, y);
		
		/* Check if sum of A1, A2 and A3 is same as A */
		return (A == A1 + A2 + A3); 
	}


	/**
	 * Returns a descriptive string
	 * @param actor The actor performing the action.
	 * @return the text we put on the menu
	 */
	@Override
	public String menuDescription(Actor actor) {
		// TODO Auto-generated method stub
		if (!(direction == null))
			return actor + " fires at " + direction;
		else
			return "choose " + zombie + " as target";
	}


}

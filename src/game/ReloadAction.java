package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.Weapon;

/**
 * Special Action for reloading a rangedWeapon
 *
 */
public class ReloadAction extends Action{
	
	protected Shotgun shotgun;
	protected Sniper sniper;
	protected ShotgunAmmunition ammo;
	protected SniperAmmunition bullet;

	/**
	 * Constructor
	 * 
	 * @param item The ammunition for shotgun or sniper
	 */
	public ReloadAction(Item item) {
		if (item.getDisplayChar() == 'g')
			this.ammo = (ShotgunAmmunition) item;
		if (item.getDisplayChar() == 's')
			this.bullet = (SniperAmmunition) item;
	}
	
	/**
	 * Perform the action of reloading a rangedWeapon
	 * 
	 * @param actor The actor performing the action.
	 * @param map The map the actor is on.
	 * @return a description of the action reloading the rangedWeapon
	 */
	@Override
	public String execute(Actor actor, GameMap map) {
		String result = "";
		
		Weapon weapon = actor.getWeapon();
		
		if (weapon.verb() == "shot") {
			
			this.shotgun = (Shotgun) weapon;			
			shotgun.reloadAmmo(ammo.ammoAmount);
			actor.removeItemFromInventory(ammo);
			
			result += actor + " reloaded " + weapon.toString() + " with " + ammo.ammoAmount + " ammo";
		}
		
		if (weapon.verb() == "snipe") {
			
			this.sniper = (Sniper) weapon;
			sniper.reloadBullet(bullet.bulletAmount);
			actor.removeItemFromInventory(bullet);
			
			result += actor + " reloaded " + weapon.toString() + " with " + bullet.bulletAmount + " bullet";
		}
		// TODO Auto-generated method stub
		return result;
	}

	/**
	 * Returns a descriptive string
	 * @param actor The actor performing the action.
	 * @return the text we put on the menu
	 */
	@Override
	public String menuDescription(Actor actor) {
		// TODO Auto-generated method stub
		return actor + " reload " + actor.getWeapon().toString();
	}

}

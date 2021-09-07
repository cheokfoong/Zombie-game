package game;

import java.util.Arrays;
import java.util.List;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.FancyGroundFactory;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.World;

/**
 * The main class for the zombie apocalypse game.
 *
 */
public class Application {

	public static void main(String[] args) {
		//World world = new World(new Display());
		EndGame world = new EndGame(new Display());

		FancyGroundFactory groundFactory = new FancyGroundFactory(new Dirt(), new Fence(), new Tree(), new Vehicle(), 
																	new MarieSpawn(), new MapleTree(), new PineTree());

		List<String> map = Arrays.asList(
		"................................................................................",
		"................................................................................",
		"....................................##########..................................",
		"..........................###########........#####..............................",
		"............++...........##......................########.......................",
		"..............++++.......#..............................##......................",
		".............+++...+++...#...............................#......................",
		".........................##..............................##.....................",
		"..........................#...............................#.....................",
		".........................##...............................##....................",
		".........................#...............................##.....................",
		".........................###..............................##....................",
		"...........................####......................######.....................",
		"................................#######.........####............................",
		"............+++.......................#.........#...............................",
		".............+++++....................#.........#...............................",
		"...............++......................._................+++++..................",
		".............+++....................................++++++++....................",
		"............+++.......................................+++.......................",
		"................................................................................",
		".........................................................................++.....",
		"........................................................................++.++...",
		".........................................................................++++...",
		"..........................................................................++....",
		"m...............................................................................");
		
		List<String> TownMap = Arrays.asList(
		"_........pppppp....pppp.......pp..pppppp...yyyyyy.",
		".............pppppppppp.....ppp....ppp...ppp..yyyy",
		"...............pppp.............ppppp..........yyy",
		"yyy....yyyyy.................................yyyyy",
		"yy.......yy.....................................yy",
		"yyy.....yy..yy....yyy...yyyyyy........ppp......yyy",
		"pp..p........yyyyyyy..yyy.............ppppp.....yy",
		"pp...ppp............yyyyyy.........pppp..pp......y",
		"p..ppp............yyyy......................yyyyyy",
		"pp.pppppp..............................yyyyyy....y",
		"p..................pppp..pppp......yyyyyy.........",
		"pp.....yyyyyyyyy........................yyyyyyy...",
		"pp............yyy...yyy......................yyyyy",
		"pppp..ppp......yyyyyyyyyy.....ppppppppp.......yyyy",
		"ppp...............................................",
		"pp..............................ppp....pppp.......",
		"pppppppp.......ppppppp..........pp..ppp........yyy",
		"ppppp.......pppp.........pppppppppp...........yyyy");
				
		GameMap gameMap = new GameMap(groundFactory, map);
		world.addGameMap(gameMap);
		
		GameMap gameMap2 = new GameMap(groundFactory, TownMap);
		world.addGameMap(gameMap2);
		
		Actor player = new Player("Player", '@', 100, gameMap, gameMap2);
		world.addPlayer(player, gameMap.at(42, 15));
		
	    // Place some random humans
		String[] humans = {"Carlton", "May", "Vicente", "Andrea", "Wendy",
				"Elina", "Winter", "Clem", "Jacob", "Jaquelyn"};
		int x, y;
		for (String name : humans) {
			do {
				x = (int) Math.floor(Math.random() * 20.0 + 30.0);
				y = (int) Math.floor(Math.random() * 7.0 + 5.0);
			} 
			while (gameMap.at(x, y).containsAnActor());
			gameMap.at(x,  y).addActor(new Human(name));	
		}
		
		// place a simple weapon
		gameMap.at(74, 20).addItem(new Plank(ItemAbleToPickUp.Weapons));
		gameMap.at(60, 14).addItem(new Plank(ItemAbleToPickUp.Weapons));
		gameMap.at(30, 16).addItem(new Plank(ItemAbleToPickUp.Weapons));
		gameMap.at(43, 16).addItem(new Shotgun(ItemAbleToPickUp.Weapons));
		gameMap.at(45, 17).addItem(new ShotgunAmmunition());
		gameMap.at(45, 18).addItem(new SniperAmmunition());
		gameMap.at(44, 16).addItem(new Sniper(ItemAbleToPickUp.Weapons));
		
		// FIXME: Add more zombies!
//		gameMap.at(30, 20).addActor(new Zombie("Groan", gameMap));
		gameMap.at(45,  16).addActor(new Zombie("Boo", gameMap));
		gameMap.at(46,  16).addActor(new Zombie("Uuuurgh", gameMap));
		gameMap.at(50, 18).addActor(new Zombie("Mortalis", gameMap));
		gameMap.at(1, 10).addActor(new Zombie("Gaaaah", gameMap));
		gameMap.at(62, 12).addActor(new Zombie("Aaargh", gameMap));	
		
		gameMap.at(35, 12).addActor(new Farmer("Frank"));
		gameMap.at(0, 1).addActor(new Sorcerer("Aghanim"));
		
//		To test out Revive Potion
		gameMap.at(5, 4).addItem(new Corpse("Slain", '%', gameMap));
		gameMap.at(5, 0).addActor(new Human("Victim"));
		gameMap.at(6, 0).addActor(new Zombie("Walker", gameMap));
		gameMap.at(4, 0).addActor(new Zombie("Deado", gameMap));
		gameMap.at(5, 1).addActor(new Zombie("Slacks", gameMap));
		
		// Humans for TownMap
		String[] humans2 = {"Ben", "Laura", "Sandy", "Candy"};
		
		int x2, y2;
		for (String name : humans2) {
			do {
				x2 = (int) Math.floor(Math.random() * 20.0 + 30.0);
				y2 = (int) Math.floor(Math.random() * 7.0 + 5.0);
			} 
			while (gameMap2.at(x2, y2).containsAnActor());
			gameMap2.at(x2,  y2).addActor(new Human(name));	
		}
		
		// Added zombies for TownMap
		gameMap2.at(4, 5).addActor(new Zombie("Loon", gameMap2));
		gameMap2.at(8, 10).addActor(new Zombie("Bun", gameMap2));
		gameMap2.at(7, 7).addActor(new Zombie("Mortimer", gameMap2));
		
//		To test out interaction between Player and Sorcerer (ingredients for potion)
		gameMap.at(1, 0).addItem(new MapleSeed());
		gameMap.at(1, 0).addItem(new PineSeed());
		gameMap.at(1, 0).addItem(new ZombieLeg());
		gameMap.at(1, 0).addItem(new ZombieArm());

		world.run();
	}
}

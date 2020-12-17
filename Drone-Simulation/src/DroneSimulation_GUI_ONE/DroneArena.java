package DroneSimulation_GUI_ONE;

/**
 * @author Zhelyo Zhelev
 *
 */

/* This is the DroneArena class for the
 * drone simulation
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DroneArena {

	private List<NormalDrone> drones;				    // Array list with the normal drones in the arena
	private List <Obstacle> obstacles;					// Array list with the killer drones in the arena
	private List <KillerDrone> killerDrones;			// Array list with the obstacles in the arena

	/* Constructor to initialize the size of the arena and the drone list */
	public DroneArena() {
		drones = new ArrayList<NormalDrone>();
		obstacles = new ArrayList<Obstacle>();
		killerDrones = new ArrayList <KillerDrone>();
	}
	
	/*return list of Normal drones
	 * @return
	 */
	public List<NormalDrone> getDrones() {
		return drones;
	}
	
	/*return list of Killer drones
	 * @return
	 */
	public List<Obstacle> getObstacles() {
		return obstacles;
	}
	
	/*return list of Obstacles
	 * @return
	 */
	public List<KillerDrone> getKillerDrones() {
		return killerDrones;
	}
	
	/* A method that adds normal drones to the arena
	 * @param mc - Canvas of drones */	
	public void addDrone(MyCanvas mc) {
		double valx;		
		double valy;
		int radius = 20;					//give our default radius as a variable
			do {
				Random randomPos = new Random();
				valx = randomPos.nextInt(mc.getCanvasX() - radius + 1);						//generate random x
				valy = randomPos.nextInt(mc.getCanvasY() - radius + 1);						//generate random y
			} while (valx < radius || valy < radius || !isFreePosition(valx, valy, radius));	    // checks if the coordinates are valid and position is free
				drones.add(new NormalDrone(valx, valy));								 			// if coordinates are valid, add drone to arena
		}
	
	/* A method that adds killer drones to the arena
	 * @param mc - Canvas of drones */	
	public void addKillerDrone(MyCanvas mc) {
		double valx;
		double valy;
		int radius = 20;					//give our default radius as a variable
			do {
				Random randomPos = new Random();
				valx = randomPos.nextInt(mc.getCanvasX() - radius + 1);						//generate random x
				valy = randomPos.nextInt(mc.getCanvasY() - radius + 1);						//generate random y
			} while (valx < radius || valy < radius || !isFreePosition(valx, valy, radius));	    // checks if the coordinates are valid and position is free
				killerDrones.add(new KillerDrone(valx, valy));								 			// if coordinates are valid, add drone to arena
		}
	
	/* A method that adds obstacles to the arena
	 * @param mc - Canvas of drones */	
	public void addObstacle(MyCanvas mc) {
		double valx;
		double valy;
		int radius = 30;					//give our default radius as a variable
			do {
				Random randomPos = new Random();
				valx = randomPos.nextInt(mc.getCanvasX() - radius + 1);						//generate random x
				valy = randomPos.nextInt(mc.getCanvasY() - radius + 1);						//generate random y
			} while (valx < radius || valy < radius || !isFreePosition(valx, valy, radius));	    // checks if the coordinates are valid and position is free
				obstacles.add(new Obstacle(valx, valy));								 			// if coordinates are valid, add drone to arena
		}
	
	/* A method that checks if a position is free
	 * @param ox
	 * @param oy
	 * @param or
	 * @return false if true if position is free
	 *  */
	private boolean isFreePosition(double ox, double oy, double or) {
		for (int i = 0; i < drones.size(); i++) {
			if (drones.get(i).hitting(ox, oy, or)) {
				return false;
			}
		}

		for (int i = 0; i < killerDrones.size(); i++) {
			if (killerDrones.get(i).hitting(ox, oy, or)) {
				return false;
			}
		}
		for (int i = 0; i < obstacles.size(); i++) {
			if (obstacles.get(i).hitting(ox, oy, or)) {
				return false;
			}
		}
		return true;
	}
	
	/*A method to check if two objects are colliding and adjust angle
	 * @param x
	 * @param y
	 * @param rad
	 * @param ang
	 * @param notID
	 * @return new angle
	 */
	public double collision (double x, double y, double rad, double ang, int notID) {
		double ans = ang;
		for (Obstacle o: obstacles) {
			if (o.hitting(x, y, rad)) ans = 180*Math.atan2(y-o.getY(), x-o.getX())/Math.PI;
				// check all obstacles except one with given id
				// if hitting, return angle between the other drone and this one.
		}
		for (NormalDrone d : drones) 
			if (d.getUniqueID() != notID && d.hitting(x, y, rad)) ans = 180*Math.atan2(y-d.getY(), x-d.getX())/Math.PI;
				// check all drones except one with given id
				// if hitting, return angle between the other drone and this one.
		for (KillerDrone k : killerDrones) 
			if (k.getUniqueID() != notID && k.hitting(x, y, rad)) ans = 180*Math.atan2(y-k.getY(), x-k.getX())/Math.PI;
				// check all drones except one with given id
				// if hitting, return angle between the other drone and this one.
		return ans;		// return the angle
	}
	
	/* A method that checks if a KillerDrone has killed a NormalDrone
	 */
	public void confirmKill() {
		for (NormalDrone d: drones) {
			for (KillerDrone k: killerDrones) {
				if (k.hitting(d)) {						//if a killer drone has hit a normal drone
					drones.remove(d);					//remove killed normal drone
			}
			}
		}
	}
	
	/* A method that checks if a KillerDrone has died by an Obstacle
	 */
	public void killerKilled() {
		for (Obstacle o: obstacles) {
			for (KillerDrone k: killerDrones) {
				if (k.hitting(o)) {						//if killer drone has hit an obstacle
					killerDrones.remove(k);				//remove the dead killer drone
			}
			}
		}
	}
	
	/* A method to update the world for animation purposes
	 * @param mc - Canvas of drones
	 * @param arena - Arena of drones
	 */
	public void updateWorld(MyCanvas mc,DroneArena arena) {
		for (KillerDrone k: killerDrones) {
			k.checkDrone(mc, arena);						//check for collisions
			k.adjustDrone();								//move drone
		}
		for (NormalDrone d: drones) {
			d.checkDrone(mc, arena);						//check for collisions
			d.adjustDrone();								//move drone
		}
	}
	
	/* A method to draw a circle on the canvas for each drone and obstacle
	 * @param mc - Canvas of drones
	 */
	public void drawWorld(MyCanvas mc) {
		mc.clearCanvas();															//clear previous canvas
		for (Obstacle o: obstacles) {												
			mc.showCircle(o.getX(), o.getY(), o.getRad(), o.getCol());				//draw obstacles
		}
		for (NormalDrone d: drones) {
			mc.showCircle(d.getX(), d.getY(), d.getRad(), d.getCol());				//draw Normal drones
		}
		for (KillerDrone k: killerDrones) {
			mc.showCircle(k.getX(), k.getY(), k.getRad(), k.getCol());				//draw Killer drones
		}
	}
	
	/* method to display information about all objects in arena
	 * @return*/
	@Override
	public String toString() {
		String s = "";
		for (NormalDrone d: drones) {
			s += d.toString() + "\n";
		}
		for (KillerDrone k: killerDrones) {
			s += k.toString() + "\n";
		}
		for (Obstacle o: obstacles) {
			s += o.toString() + "\n";
		}
		return s;
	}
}


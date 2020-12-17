package DroneSimulation;

public class Drone {

	private int droneX, droneY, uniqueID;  //Drone coordinates and ID
	private static int ID = 1;
	/*
	 * Constructor for Drone initiates all of the class variables.
	 */
	public Drone(int x, int y) {
		this.droneX = x;
		this.droneY = y;
		this.uniqueID = ID++;
	}

	/* Getter functions */
	public int getX() {
		return droneX;
	}
	
	public int getY() {
		return droneY;
	}
	
	public int getUniqueID() {
		return this.uniqueID;
	}
	
	/* A method to move each drone to a random direction*/
	public void moveDrone(DroneArena a, Directions b) {
		Directions dir = Directions.getRandomDirection();   // gets a random direction
		switch (b != null ? b : dir) {
		case North:
			if (a.canGoHere((droneX - 1),droneY)) {			// if positions is free, move to North
			droneX = droneX - 1;
			System.out.println("Drone " + uniqueID + " was moved North");
			}
			else {											// else get next direction 
				dir = dir.nextDirection();
				moveDrone(a, dir);
			}
			break;
		case East:
			if (a.canGoHere(droneX, (droneY + 1))) {		// if positions is free, move to East
			droneY = droneY + 1;
			System.out.println("Drone " + uniqueID + " was moved East");
			}
			else {
				dir = dir.nextDirection();                  // else get next direction
				moveDrone(a, dir);
			}
			break;
		case South:
			if (a.canGoHere((droneX + 1), droneY)) {        // if positions is free, move to South
			droneX = droneX + 1;
			System.out.println("Drone " + uniqueID + " was moved South");
		    }
			else {                                          // else get next direction
				dir = dir.nextDirection();
				moveDrone(a, dir);
			}
			break;
		case West:
			if (a.canGoHere(droneX, (droneY - 1))) {      // if positions is free, move to West
			droneY = droneY - 1;
			System.out.println("Drone " + uniqueID + " was moved West");
			}
			else {										  // else get next direction
				dir = dir.nextDirection();
				moveDrone(a, dir);
			}
			break;
		}
	}
	
	/* A method to display each drone */
	public void displayDrone(ConsoleCanvas c) {
		char droneChr = 'D';
		c.showIt(droneX, droneY, droneChr);
	}
	
	/* A method to print information for each drone*/
	public String toString() {
		return "Drone " + uniqueID + " is at position " + droneX + ", " + droneY;
	}
}

package DroneSimulation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DroneArena {

	private int arenaX, arenaY;                 // the length and the height of the arena
	public List<Drone> drones;				    // Array list with the drones in the arena

	/* Constructor to initialize the size of the arena and the drone list */
	public DroneArena(int maxX, int maxY) {
		this.arenaX = maxX;
		this.arenaY = maxY;
		drones = new ArrayList<Drone>();
	}

	/* Getter methods */
	public int getArenaX() {
		return arenaX;
	}
	
	public int getArenaY() {
		return arenaY;
	}
	
	/* A method that add drones to the arena */	
	public void addDrone() {
		int valx;
		int valy;
		int isFull = (arenaX * arenaY);		
		if (drones.size() < isFull) {				// checks if the arena has space
			do {
				Random randomPos = new Random();
				valx = randomPos.nextInt(arenaX + 1);
				valy = randomPos.nextInt(arenaY + 1);
			} while (valx == 0 || valy == 0 || !isFreePosition(valx, valy));	 // checks if the coordinates are valid and position is free
				drones.add(new Drone(valx, valy));								 // if coordinates are valid, add drone to arena
				System.out.println("New drone added to position " + valx + ", " + valy);
		}
		else {
			System.out.println("The arena is full");
		}
	}
	
	/* A method that checks if a position is free */
	private boolean isFreePosition(int x, int y) {

		for (int i = 0; i < drones.size(); i++) {
			if (drones.get(i).getX() == x && drones.get(i).getY() == y) {
				return false;
			}
		}

		return true;

	}

	/* A method that moves all drones in the arena once */
	public void moveDrones() {
			for (Drone d : drones) {
			d.moveDrone(this, null);
			}
		}
	
	/* A method that checks if a drone can move to a certain position */
	public boolean canGoHere(int x, int y) {
		if (x!=0 && x<(arenaX+1) && y!=0 && (y<arenaY+1) && isFreePosition(x, y)) {
			return true;
		}
		else return false;
	}
	
	/* A method that displays drones on ConsoleCanvas */
	public void showDrones(ConsoleCanvas c) {
		for (Drone d : drones) {
			d.displayDrone(c);
		}
	}


	@Override
	public String toString() {
		System.out.println("The arena length is " + arenaX + " with height " + arenaY);
		for (int i = 0; i < drones.size(); i++) {
			System.out.println(drones.get(i).toString());
		}
		return "";
	}
}

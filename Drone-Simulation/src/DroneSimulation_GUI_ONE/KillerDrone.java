package DroneSimulation_GUI_ONE;

import java.util.Random;

/**
 * @author Zhelyo Zhelev
 *
 */

/*
 * This is a child class, derived from NormalDrone with some extended
 * functionalities
 */
public class KillerDrone extends NormalDrone {
	private static int ID = 1; // static integer used to set a unique identifier for each KillerDrone

	/*
	 * Create a Killer drone with size rad at ex, ey with speed(between 3 and 7)
	 * droneSpeed and angle droneAngle
	 * @param ex
	 * @param ey
	 */
	public KillerDrone(double kx, double ky) {
		super(kx, ky); 								// set x and y inherited from the super class
		random = new Random(); 						// crate the random object
		rad = 20; 									// set default radius to 20
		col = 'r'; 									// set fill color to red
		uniqueID = ID++; 							// set unique identifier for the drone class
	}

	/*A method to change the angle of the drone if
	 * it collides with the walls or another object
	 * @param mc	The canvas of drones
	 * @param arena	The arena of drones
	 */
	protected void checkDrone(MyCanvas mc, DroneArena arena) {
		arena.confirmKill();
					//check if this Killer Drone has killed a Normal Drone
		arena.killerKilled();
					//check if this Killer Drone has killed itself by hitting an Obstacle
		droneAngle = arena.collision(x, y, rad, droneAngle, uniqueID);
					//call collision method from DroneArena to change the droneAngle accordingly
		if (x < rad || x > mc.getCanvasX() - rad)
			droneAngle = 180 - droneAngle;
					// if drone hits (tried to go through) left or right walls, set mirror angle, being 180-angle
		if (y < rad || y > mc.getCanvasY() - rad)
			droneAngle = -droneAngle;
					// if try to go off top or bottom, set mirror angle
	}

	/* return Drone type
	 * @return
	 */
	@Override
	protected String getStrType() {
		return "KillerDrone";
	}
}

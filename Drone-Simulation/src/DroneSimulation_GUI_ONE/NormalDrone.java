package DroneSimulation_GUI_ONE;

import java.util.Random;

/**
 * @author Zhelyo Zhelev
 *
 */

/* This is class is derived from the abstract Entity class and
 * it serves as a parent class for the other type of drone
 */
public class NormalDrone extends Entity {
	protected double droneAngle, droneSpeed;		//angle and speed of travel of the drone
	private static int ID = 1;						//static integer used to set a unique identifier for each NormalDrone
	protected Random random;						//random object, used to generate random speed and angle

	/* Create a Normal drone with size rad at ex, ey with
	 * speed(between 3 and 7) droneSpeed and angle droneAngle
	 * @param ex 
	 * @param ey
	 */
	NormalDrone(double ex, double ey) {
		super(ex, ey);										//set x and y inherited from the super class
		random = new Random();								//crate the random object
		rad = 20;											//set default radius to 20
		col = 'g';											//set the fill color green
		droneAngle = random.nextInt(360 - 1 + 1) + 1;		//generate random angle between 1 and 360 degrees	
		droneSpeed = random.nextInt(7 - 3 + 1) + 3;			//generate random speed between 3 and 7
		uniqueID = ID++;									//set unique identifier for the drone class
	}

	/* return angle
	 * @return
	 */
	public double getAng() {
		return droneAngle;
	}

	/*return speed
	 * @return
	 */
	public double getSpeed() {
		return droneSpeed;
	}

	/*return radius
	 * @return
	 */
	public double getRad() {
		return rad;
	}

	/*A method to change the angle of the drone if
	 * it collides with the walls or another object
	 * @param mc	The canvas of drones
	 * @param arena	The arena of drones
	 */
	protected void checkDrone(MyCanvas mc, DroneArena arena) {
		droneAngle = arena.collision(x, y, rad, droneAngle, uniqueID);    
							//call collision method from DroneArena to change the droneAngle accordingly
		if (x < rad || x > mc.getCanvasX() - rad)						  
			droneAngle = 180 - droneAngle;
							// if drone hits (tried to go through) left or right walls, set mirror angle, being 180-angle
		if (y < rad || y > mc.getCanvasY() - rad)
			droneAngle = -droneAngle;
							// if try to go off top or bottom, set mirror angle
	}

	/* Method for adjusting (moving) the drones accordingly*/
	protected void adjustDrone() {
		double radAngle = droneAngle * Math.PI / 180;
		x += droneSpeed * Math.cos(radAngle);
		y += droneSpeed * Math.sin(radAngle);
	}
	
	/* return Drone type
	 * @return
	 */
	@Override
	protected String getStrType() {
		return "Drone";
	}

	/* return Obstacle type
	 * @return
	 */
	@Override
	public String toString() {
		return getStrType() + " " + uniqueID + " at " + Math.round(x) + ", " + Math.round(y) + " moving with speed "
				+ droneSpeed;
	}
}

package DroneSimulation_GUI_ONE;

/**
 * @author Zhelyo Zhelev
 *
 */

/* This is the child class of Entity used
 * for creating an Obstacle object in the arena
 */
public class Obstacle extends Entity {
	private static int ID = 1;					//unique identifier for this class
	private double droneAngle = 0;				//set angle to 0 
	
	/* Create a Obstacle with size rad at ex, ey 
	 * @param ex 
	 * @param ey
	 */
	public Obstacle(double ex, double ey) {
		super(ex, ey);								//set x and y
		rad = 30;									//set default radius to 30
		col = 'b';									//set fill color to blue
		uniqueID = ID++;							//set identifier and set the next static integer
		}
	
	/* Check if a drone has hit the obstacle
	 * @parameter arena - The arena of drones
	 */
	public void checkObstacle(DroneArena arena) {
		arena.collision(x, y, rad, droneAngle, uniqueID);
	}
	
	/* method to display information about obstacle
	 * @return*/
	@Override
	public String toString() {
		return getStrType()+ " " + uniqueID+ " at " +Math.round(x)+ ", " + Math.round(y);
	}
	
	/* method to display information about obstacle*/
	@Override
	protected String getStrType() {
		return "Obstacle";
	}
}

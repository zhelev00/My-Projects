package DroneSimulation_GUI_ONE;

public class Drone {
	
	private double droneX, droneY, droneRad, droneAngle, droneSpeed;  //Drone coordinates and ID
	private int uniqueID;
	private static int ID = 1;
	/*
	 * Constructor for Drone initiates all of the class variables.
	 */
	public Drone(double x, double y) {
		this.droneX = x;
		this.droneY = y;
		this.droneRad = 20;	
		this.droneAngle = 45;
		this.droneSpeed = 5;
		this.uniqueID = ID++;
	}

	/* Getter functions */
	public double getX() {
		return droneX;
	}
	
	public double getY() {
		return droneY;
	}
	
	public double getRad() {
		return droneRad;
	}
	
	public double getAng() {
		return droneAngle;
	}
	
	public int getUniqueID() {
		return this.uniqueID;
	}
	
	public void checkDrone(MyCanvas mc, DroneArena arena) {
		droneAngle = arena.collision(droneX, droneY, droneRad, droneAngle, uniqueID);
		if (droneX < droneRad || droneX > mc.getCanvasX() - droneRad) droneAngle = 180 - droneAngle;
		if (droneY < droneRad || droneY > mc.getCanvasY() - droneRad) droneAngle = -droneAngle;
	}
	
	public void adjustDrone() {
		double radAngle = droneAngle*Math.PI/180;
		droneX += droneSpeed * Math.cos(radAngle);
		droneY += droneSpeed * Math.sin(radAngle);
	}
	
	public boolean hitting(double ox, double oy, double or) {
		return (ox-droneX)*(ox-droneX) + (oy-droneY)*(oy-droneY) < (or+droneRad)*(or+droneRad);
	}
	
	public boolean hitting (Drone drone) {
		return hitting(drone.getX(), drone.getY(), drone.getRad());
	}
	
	public String toString() {
		return "Drone " + uniqueID + " is at position " + droneX + ", " + droneY;
	}
}

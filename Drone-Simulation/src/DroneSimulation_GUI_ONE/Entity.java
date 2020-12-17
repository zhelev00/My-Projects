package DroneSimulation_GUI_ONE;

/**
 * @author Zhelyo Zhelev
 *
 */

/* This is an abstract class, used as the
 * parent class for all other classes.
 */
public abstract class Entity {
	protected double x, y, rad;							//position and size of the entity
	protected int uniqueID;								//static integer used to set a unique identifier for each entity
	private static int ID = 1;							//unique identifier for this class
	protected char col;									//character used to set the fill color of entity
	
	
	/* construct an entity at position ex, ey
	 * @param ex
	 * @param ey
	 */
	public Entity (double ex,double ey) {
		x = ex;
		y = ey;
		uniqueID = ID++;				//set identifier and set the next static integer
	}
	
	/* return x position
	 * @return
	 */
	public double getX() { 
		return x; 
	}	
	
	/* return y position
	 * @return
	 */
	public double getY() { 
		return y;
	}
	
	/* return radius of Entity
	 * @return
	 */
	public double getRad() { 
		return rad; 
	}
	
	/* return fill color of Entity
	 * @return
	 */
	public char getCol() { 
		return col; 
	}
	
	/* return identifier of Entity
	 * @return
	 */
	public int getUniqueID() { 
		return uniqueID; 
	}

	/* return Entity type
	 * @return
	 */
	protected String getStrType() {
		return "Entity";
	} 
	
	/* is the entity at ox, oy with size or hitting this entity
	 * @param ox
	 * @param oy
	 * @param or
	 * @return true if hitting
	 */
	
	public boolean hitting(double ox, double oy, double or) {
		return (ox-x)*(ox-x) + (oy-y)*(oy-y) < (or+rad)*(or+rad);
	}
	
	/* is the Entity hitting another entity
	 * @param entity - the other entity
	 * @return true if hitting
	 */
	public boolean hitting (Entity entity) {
		return hitting(entity.getX(), entity.getY(), entity.getRad());
	}
	
	/* method to print out information about entity
	 * @return*/
	public String toString() {
		return getStrType()+ " " + uniqueID+ " at " +Math.round(x)+ ", " + Math.round(y);
	}


}
	
	

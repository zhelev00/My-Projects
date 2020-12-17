package DroneSimulation_GUI_ONE;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;

/**
 * @author Zhelyo Zhelev
 *
 */

/* This is the Canvas class, used to display all of the arena
 *  data on the GUI
 */
public class MyCanvas {
	int canvasX = 512;									//set the default canvas x
	int canvasY = 512;									//set the default canvas y
	GraphicsContext gc;								    //GraphicsContext object
	
	/*Create a new Canvas with GraphicsContext g and sizes xcx, ycs
	 * 
	 */
	public MyCanvas (GraphicsContext g, int xcs, int ycs) {
		gc = g;											//set GraphicsContext
		canvasX = xcs;									//set Canvas x
		canvasY = ycs;									//set Canvas y
	}
	
	/* return canvas x size
	 * @return
	 */
	public int getCanvasX() {
		return canvasX;
	}
	
	/* return canvas y size
	 * @return
	 */
	public int getCanvasY() {
		return canvasY;
	}
	
	/* a method to clear the canvas
	 */
	public void clearCanvas() {
		gc.clearRect(0, 0, canvasX, canvasY);
	}
	
	/* a method to draw on the canvas
	 */
	public void drawIt (Image i, double x, double y, double sz) {
		gc.drawImage(i, canvasX * (x - sz/2), canvasY * (y - sz/2), canvasX * sz, canvasY * sz);
	}
	
	/*a method to choose the fill color of the circle
	 * @param char c - get color
	 * @return color
	 */
	Color colFromChar (char c) {
		Color ans = Color.BLACK;
		switch (c) {
		case 'r': ans = Color.RED;
				  break;
		case 'g': ans = Color.GREEN;
				  break;
		case 'b': ans = Color.BLUE;
				  break;
		}
		return ans;
	}
	
	/*a method to set the fill color*/
	public void setFillColor(Color c) {
		gc.setFill(c);
	}
	
	/* a method to display the circle 
	 * @param x
	 * @param y
	 * @param rad
	 * @param col
	 */
	public void showCircle (double x, double y, double rad, char col) {
		setFillColor(colFromChar(col));
		gc.fillArc(x-rad, y-rad, rad*2, rad*2, 0, 360, ArcType.ROUND);
	}
}

package DroneSimulation;

public class ConsoleCanvas {

	private char[][] map;         // A 2D Array that contains the map
	private int mapX, mapY;		  // X and Y map variables

	/* Constructor to initiate all of variables and fills the walls of the map*/
	public ConsoleCanvas(int arrayX, int arrayY) {
		mapX = arrayX;
		mapY = arrayY;
		map = new char[arrayX][arrayY];

		for (int i = 0; i < mapX; i++) {
			for (int j = 0; j < mapY; j++) { 
				if (i == 0) {
					map[i][j] = '#';			// Displays the North wall
				}
				if (j == 0) {
					map[i][j] = '#';			// Displays the West wall
				}
				if (j == arrayY - 1) {          
					map[i][j] = '#';            // Displays the East wall
				}
				if (i == arrayX - 1) {
					map[i][j] = '#';            // Displays the South wall
				}

			}
		}
	  }
	
	/* a method to show drones on the map*/
	public void showIt(int droneX, int droneY, char d) {
		map[droneX][droneY] = d;
	}

	public String toString() {
		String res = "";
		for (int i = 0; i < mapX; i++) {
			for (int j = 0; j < mapY; j++) {
				res = res + map[i][j] + " ";
			}
			res = res + "\n";
		}
		return res;
	}
}

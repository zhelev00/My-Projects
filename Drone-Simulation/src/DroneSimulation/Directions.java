package DroneSimulation;

import java.util.Random;

public enum Directions {
	North, East, South, West;     // The four directions presented as enums

	/* A simple method that gets a random Direction out of the four */
	public static Directions getRandomDirection() {	
		Random dir = new Random();								// Create random object
		return values()[dir.nextInt(values().length)];
	}

	/* A simple method that iterates trough the directions and gets the next one */
	public Directions nextDirection() {
		if (this.ordinal() == 3) {
			return values()[0];
		}
		else {
			return values()[this.ordinal() + 1];
		}
	}
}

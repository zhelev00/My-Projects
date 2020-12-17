package DroneSimulation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import javax.swing.JFileChooser;

/**
 * Simple program to show arena with multiple drones
 * 
 * @author Zhelyo Zhelev
 *
 */
public class DroneInterface {

	private Scanner s; // scanner used for input from user
	private DroneArena myArena;// arena in which drones are shown
	private int arenaX, arenaY; //Dimensions for creating new arena
	private boolean check = false; //A boolean variable for continuing to the next menu

	/**
	 * constructor for DroneInterface sets up scanner used for input and the arena
	 * then has main loop allowing user to enter commands
	 * @throws InterrupaedException 
	 */
	public DroneInterface() throws InterruptedException {
		s = new Scanner(System.in); // set up scanner for user input
		char ch = ' ';
		do {
			System.out.println("Create (N)ew arena, (L)oad arena from file or e(X)it");
			ch = s.next().charAt(0);
			s.nextLine();
			switch (ch) {
			case 'N':
			case 'n':
				System.out.println("Input length of the arena: "); 
				arenaX = s.nextInt(); // gets the arena length
				if (arenaX <= 0) {	  // checks if the length is valid, if not prompts user to input another value
					System.out.println("The length of the arena sould be a whole number bigger than 0! Please input again: ");
					arenaX = s.nextInt();
				}
				System.out.println("Input height of the arena: "); 
				arenaY = s.nextInt();  // gets the arena height
				if (arenaY <= 0) {	   // checks if height is valid, if not prompts user to input another value
					System.out.println("The height of the arena sould be a whole number bigger than 0! Please input again: ");
					arenaY = s.nextInt();
				}
				myArena = new DroneArena(arenaX, arenaY); //creates arena
				check = true;
				break;
			case 'L':
			case 'l':
				try {
					load();		//when L is detected, loads arena
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.err.print("");
				}
				System.out.println();
				check = true;
				break;

			case 'x':
				ch = 'X'; // when X detected program ends
				System.exit(0);
				break;
			}
					
		} while (!check);
		
		do {
			System.out.print("Enter (A)dd drone, (I)nformation, (D)isplay arena, (M)ove all drones, Move all drones (T)en times, (S)ave arena or e(X)it > ");
			ch = s.next().charAt(0);
			s.nextLine();
			switch (ch) {
			case 'A':
			case 'a':
				System.out.println();
				myArena.addDrone(); // add a new drone to arena
				System.out.println();
				break;
			case 'I':
			case 'i':
				System.out.println();
				System.out.print(myArena.toString()); // prints information about the arena and drones to the console
				System.out.println();
				break;
			case 'D':
			case 'd':
				System.out.println();
				display();	// displays the drones in the arena
				break;
			case 'M':
			case 'm':
					System.out.println();
					myArena.moveDrones();	// moves all drones once
					System.out.println();
					display();				// displays updated positions in the arena
				break;
			case 'T':
			case 't':
				System.out.println();
				for (int a = 0; a < 10; a++) {	// moves all drones 10 times
					myArena.moveDrones();
					System.out.println();
					display();                  // displays updated positions in the arena
					Thread.sleep(300);			// slows down the printing of the arena
				}
				break;
			case 'S':
			case 's':
				System.out.println();
				try {
					save();						// when S is detected, saves arena
				} catch (Exception e) {
					System.err.print("");
				}
				System.out.println();
				break;
			case 'x':
				ch = 'X'; // when X detected program ends
				break;
			}
		} while (ch != 'X'); // test if end

		s.close(); // close scanner
	}
	
	private void display() {
		if (myArena.getArenaX() > 0 && myArena.getArenaY() > 0) {
			ConsoleCanvas c = new ConsoleCanvas(myArena.getArenaX() + 2, myArena.getArenaY() + 2);
			myArena.showDrones(c);
			System.out.println(c.toString());
			System.out.println("-----------------------------------------------------------------------------------------------------------------------------------");
		}
		else {
			System.out.println("Arena doesn't exist");
		}
	}
	
	private void load() throws Exception {
		JFileChooser chooser = new JFileChooser("C:\\Users\\Asus 1\\eclipse-workspace\\dronesimulation\\src\\DroneSimulation");
		chooser.setDialogTitle("Choose File: ");
		chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		String fileContents = " ";
		int returnVal = chooser.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File selectedFile = chooser.getSelectedFile();
			if (chooser.getSelectedFile().isFile()) {
				System.out.println("Arena Loaded from " + selectedFile.getName());
				}
				FileReader fileReader = new FileReader(selectedFile);
				BufferedReader reader = new BufferedReader(fileReader);
				fileContents = reader.readLine();
				String[] sizeOfArena = fileContents.split(",");
				int fileX = Integer.parseInt(sizeOfArena[0]); 
				int fileY = Integer.parseInt(sizeOfArena[1]);
				myArena = new DroneArena(fileX, fileY); 
				while (fileContents != null) {
					fileContents = reader.readLine();
					String[] droneValues = fileContents.split(",");
					for (int i = 0; i < droneValues.length; i =+ 2) {
					int x = Integer.parseInt(droneValues[i]);
					int y = Integer.parseInt(droneValues[i+1]);
					myArena.drones.add(new Drone(x, y));
					}
				}
				reader.close();
				}
	}
	
	private void save() throws Exception {
		JFileChooser chooser = new JFileChooser("C:\\Users\\Asus 1\\eclipse-workspace\\dronesimulation\\src\\DroneSimulation");
		chooser.setDialogTitle("Choose File: ");
		chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		String fileContents = " ";
		int returnVal = chooser.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File selectedFile = chooser.getSelectedFile();
			if (chooser.getSelectedFile().isFile()) {
				System.out.println("Arena Saved in " + selectedFile.getName());
				}
				FileWriter fileWriter = new FileWriter(selectedFile);
				BufferedWriter writer = new BufferedWriter(fileWriter);
				writer.write(Integer.toString(myArena.getArenaX()));
				writer.write(",");
				writer.write(Integer.toString(myArena.getArenaY()));
				writer.newLine();
				for (int i = 0; i < myArena.drones.size(); i++) {
					writer.write(Integer.toString(myArena.drones.get(i).getX()));
					writer.write(",");
					writer.write(Integer.toString(myArena.drones.get(i).getY()));
					writer.newLine();
				}
				writer.close();
		}
	}
	
	
	public static void main(String[] args) throws InterruptedException, IOException {
		DroneInterface r = new DroneInterface(); // just call the interface
	}
}
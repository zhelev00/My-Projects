package DroneSimulation_GUI_ONE;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import javax.swing.JFileChooser;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class DroneInterface extends Application {
	private int canvasX = 600, canvasY = 600;
	private AnimationTimer timer;
	private VBox rtPane;
	private MyCanvas mc;
	private DroneArena arena;

	private void showAbout() {
	    Alert alert = new Alert(AlertType.INFORMATION);				
	    alert.setTitle("About");									
	    alert.setHeaderText(null);
	    alert.setContentText("This is Zhelyo's Drone Simulation");			
	    alert.showAndWait();										
	}
	
	private void showControls() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Game Controls");
		alert.setHeaderText(null);
		alert.setContentText("Green - Normal drones \nRed - Killer drones \nBlue - Obstacles "
				+ "\nKiller drones kill Normal drones! \n"
				+ "Killer drones die when touching obstacle!");
		alert.showAndWait();
	}
	
	MenuBar setMenu() {
	
		MenuBar menuBar = new MenuBar();						
	
		Menu mFile = new Menu("File");							
		
		MenuItem mLoad = new Menu("Load");
		mLoad.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				try {
					load();
					updatePosition();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.err.print("");
				}
			}
		});
		
		MenuItem mSave = new Menu("Save");
		mSave.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent ActionEvent) {
				try {
					save();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.err.print("");
				}
			}
		});
		
		MenuItem mExit = new MenuItem("Exit");					
		mExit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent ActionEvent) {					
	        	timer.stop();									
		        System.exit(0);									
		    }
		});
		
		mFile.getItems().addAll(mLoad, mSave, mExit);							
		
		Menu mHelp = new Menu("Help");							
		MenuItem mAbout = new MenuItem("About");			
		mAbout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
            	showAbout();									
            }	
		});
		MenuItem mControls = new MenuItem("More Help");
		mControls.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				showControls();
			}
		});
		mHelp.getItems().addAll(mAbout, mControls);						
		
		menuBar.getMenus().addAll(mFile, mHelp);				
		return menuBar;											
	}
	
	private HBox setButtons() {
		Button btnNew = new Button("New Simulation");					
	    btnNew.setOnAction(new EventHandler<ActionEvent>() {	
	        @Override
	        public void handle(ActionEvent event) {
	        	clearArena();
	        	arena.addDrone(mc);
	        	arena.addKillerDrone(mc);
	        	arena.addObstacle(mc);
	        	arena.drawWorld(mc);
	        	updatePosition();
	       }
	    });
		
	    Button btnStart = new Button("Start");					
	    btnStart.setOnAction(new EventHandler<ActionEvent>() {	
	        @Override
	        public void handle(ActionEvent event) {
	        	timer.start();									
	       }
	    });

	    Button btnStop = new Button("Pause");					
	    btnStop.setOnAction(new EventHandler<ActionEvent>() {
	        @Override
	        public void handle(ActionEvent event) {
	           	timer.stop();									
	       }
	    });
	    
	    Button btnClear = new Button("Clear");					
	    btnClear.setOnAction(new EventHandler<ActionEvent>() {
	        @Override
	        public void handle(ActionEvent event) {
	           	clearArena();
	           	arena.drawWorld(mc);
	       }
	    });

	    Button btnAddDrone = new Button("Add Random Drone");				
	    btnAddDrone.setOnAction(new EventHandler<ActionEvent>( ) {
			@Override
			public void handle(ActionEvent event) {
				arena.addDrone(mc);
				arena.drawWorld(mc);
				updatePosition();
			}
		});
	    
	    Button btnAddKillerDrone = new Button("Add Killer Drone");				
	    btnAddKillerDrone.setOnAction(new EventHandler<ActionEvent>( ) {
			@Override
			public void handle(ActionEvent event) {
				arena.addKillerDrone(mc);
				arena.getKillerDrones().get(arena.getKillerDrones().size() - 1).toString();
				arena.drawWorld(mc);
				updatePosition();
			}
		});
	    
	    Button btnAddObstacle = new Button("Add Obstacle");				
	    btnAddObstacle.setOnAction(new EventHandler<ActionEvent>( ) {
			@Override
			public void handle(ActionEvent event) {
				arena.addObstacle(mc);
				arena.getObstacles().get(arena.getKillerDrones().size() - 1).toString();
				arena.drawWorld(mc);
				updatePosition();
			}
		});
	    														// now add these buttons + labels to a HBox
	    return new HBox(new Label("   Simulation: "), btnNew, btnStart, btnStop, btnClear, new Label("Controls: "), btnAddDrone, btnAddKillerDrone,
	    		btnAddObstacle);
	}
	
	private void clearArena() {
		arena.getDrones().clear();
		arena.getKillerDrones().clear();
		arena.getObstacles().clear();
	}
	
	private void updatePosition() {
		rtPane.getChildren().clear();
		Label l = new Label(arena.toString());
		rtPane.getChildren().add(l);
	}
	
	private void load() throws Exception {
		JFileChooser chooser = new JFileChooser("C:\\Users\\Asus 1\\eclipse-workspace\\dronesimulation\\src\\DroneSimulation_GUI_ONE");
		chooser.setDialogTitle("Choose File: ");
		chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		String fileContents = " ";
		int returnVal = chooser.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File selectedFile = chooser.getSelectedFile();
			if (chooser.getSelectedFile().isFile()) {
				clearArena();
				System.out.println("Arena Loaded from " + selectedFile.getName());
				}
				FileReader fileReader = new FileReader(selectedFile);
				BufferedReader reader = new BufferedReader(fileReader); 
				fileContents = reader.readLine();
				if (fileContents != null) {
					String[] droneValues = fileContents.split(",");
					for (int i = 0; i < droneValues.length; i =+ 2) {
						double x = Double.parseDouble(droneValues[i]);
						double y = Double.parseDouble(droneValues[i+1]);
						arena.getDrones().add(new NormalDrone(x, y));
						arena.drawWorld(mc);
					}
				} 
				fileContents = reader.readLine();
				if (fileContents != null) {
					String[] killerDrones = fileContents.split(",");
					for (int i = 0; i < killerDrones.length; i=+2) {
						double x = Double.parseDouble(killerDrones[i]);
						double y = Double.parseDouble(killerDrones[i+1]);
						arena.getKillerDrones().add(new KillerDrone(x, y));
						arena.drawWorld(mc);
					}
				}
				fileContents = reader.readLine();
				if (fileContents != null) {
					String[] Obstacles = fileContents.split(",");
					for (int i = 0; i < Obstacles.length; i=+2) {
						double x = Double.parseDouble(Obstacles[i]);
						double y = Double.parseDouble(Obstacles[i+1]);
						arena.getObstacles().add(new Obstacle(x, y));
						arena.drawWorld(mc);
				}
				}
				reader.close();
				}
	}
	
	private void save() throws Exception {
		JFileChooser chooser = new JFileChooser("C:\\Users\\Asus 1\\eclipse-workspace\\dronesimulation\\src\\DroneSimulation_GUI_ONE");
		chooser.setDialogTitle("Choose File: ");
		chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		int returnVal = chooser.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File selectedFile = chooser.getSelectedFile();
			if (chooser.getSelectedFile().isFile()) {
				System.out.println("Arena Saved in " + selectedFile.getName());
				}
				FileWriter fileWriter = new FileWriter(selectedFile);
				BufferedWriter writer = new BufferedWriter(fileWriter);
				for (int i = 0; i < arena.getDrones().size(); i++) {
					writer.write(Double.toString(arena.getDrones().get(i).getX()));
					writer.write(",");
					writer.write(Double.toString(arena.getDrones().get(i).getY()));
				}
				writer.newLine();
				for (int i = 0; i < arena.getKillerDrones().size(); i++) {
					writer.write(Double.toString(arena.getKillerDrones().get(i).getX()));
					writer.write(",");
					writer.write(Double.toString(arena.getKillerDrones().get(i).getY()));
				}
				writer.newLine();
				for (int i = 0; i < arena.getObstacles().size(); i++) {
					writer.write(Double.toString(arena.getObstacles().get(i).getX()));
					writer.write(",");
					writer.write(Double.toString(arena.getObstacles().get(i).getY()));
				}
				writer.close();
		}
	}
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage.setTitle("Drone Simulator");
		
		BorderPane bp = new BorderPane();
	    bp.setPadding(new Insets(0, 0, 10, 0));
	    bp.setTop(setMenu());
		
		Group root = new Group();
		Scene scene = new Scene (bp, 1000, 700);
		
		Canvas canvas = new Canvas(canvasX, canvasY);
		root.getChildren().add(canvas);
		bp.setLeft(root);
		
		mc = new MyCanvas(canvas.getGraphicsContext2D(), canvasX, canvasY);
	    arena = new DroneArena();
	    arena.drawWorld(mc);
	    
	    timer = new AnimationTimer() {
	    	public void handle(long currentNanoTime) {
	    		arena.updateWorld(mc, arena);
	    		arena.drawWorld(mc);
	    		updatePosition();
	    	}
	    };
	    
	    rtPane = new VBox();											// set vBox on right to list items
		rtPane.setAlignment(Pos.TOP_RIGHT);								// set alignment
		rtPane.setPadding(new Insets(5, 75, 75, 5));					// padding
 		bp.setRight(rtPane);											// add rtPane to borderpane right
 		bp.setBottom(setButtons());
 
 		primaryStage.setScene(scene);
	    primaryStage.show();
	    
	    bp.prefHeightProperty().bind(scene.heightProperty());
        bp.prefWidthProperty().bind(scene.widthProperty());
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

}

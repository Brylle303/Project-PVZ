package application;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;

//NOTE: I have optimized the game parameters & variables to be as close to the original game as possible 
//		but I don't understand some of the logic that you guys placed so I wont touch those. - Brylle

//UPDATE: I FINALLY FIXED THE MEMORY LEAK, MY LAPTOP RAM ALMOST DIED - Brylle

public class Adventure {
	
	private static final int ROWS = 5;
	private static final int COLS = 9;
	private static final double GRID_WIDTH = 780;
	private static final double GRID_HEIGHT = 500;

	private static final double CELL_WIDTH = GRID_WIDTH / COLS;
	private static final double CELL_HEIGHT = GRID_HEIGHT / ROWS;

	
	static 		 ArrayList<GameObject> 	 allObjects 			  = new ArrayList<>();// A list of all the Objects that exist in the grid. be it plant, zombie or pea.
	private static 		 ArrayList<Plant> 	 	 allPlants 				  = new ArrayList<>();//A list of all plant objects in the grid
	static 		 ArrayList<Zombie> 	 	 allZombies 			  = new ArrayList<>();//A list of all zombie objects in the grid
	static 		 ArrayList<Projectile> 		 allProjectiles 		  = new ArrayList<>();// A list of all the Pea objects in the grid
	private static final double[] LANE_Y = { 110, 220, 320, 420, 520 }; // tweak to match lawn

	private static Timeline spawnTimeline;
	private static Timeline zombieMoveTimeline;
	private static Timeline peaMoveTimeline;
	private static Timeline collisionBoxTimeline;

	
	// Align zombie feet to lane
	public static double getLaneY(int lane, Zombie zombie) {
		double offset = zombie.getImageView().getBoundsInParent().getHeight() / 2;
		return LANE_Y[lane] - offset;
	}

	public static AnchorPane getScreen(Stage stage) {

		final AnchorPane root = new AnchorPane();

		// Background
		BackgroundImage bg = new BackgroundImage(
				new javafx.scene.image.Image(Adventure.class.getResource("/Assets/Main Battleground.png").toExternalForm()),
				BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT,
				BackgroundPosition.CENTER,
				new BackgroundSize(100, 100, true, true, true, true));
		root.setBackground(new Background(bg));


		double xOffset = 115;
		double yOffset = 80;
		
		// Exit button
		Button exitButton = new Button("EXIT");
		Font font = Font.font("Courier New", FontWeight.BOLD, 18);
		exitButton.setFont(font);
		exitButton.setPrefWidth(90);
		exitButton.setPrefHeight(50);
		exitButton.setLayoutX(780);
		exitButton.setLayoutY(20);
		exitButton.setOnAction(e -> {
			WavPlayer.stop();
			Adventure.cleanup();
			Platform.exit();
		});
		root.getChildren().add(exitButton);

		
		// Play background music
		WavPlayer.play("src/Assets/LawnMusic.wav", true);
		
		// ----- Zombie Spawning -----
		spawnTimeline = new Timeline(new KeyFrame(Duration.seconds(2), e -> {
				int lane = (int) (Math.random() * LANE_Y.length);
				Zombie z = createNewZombie(lane);
				root.getChildren().add(z.getImageView());
				

		}));
		spawnTimeline.setCycleCount(Timeline.INDEFINITE);
		spawnTimeline.play();
		
		Timeline zombieMoveTimeline = new Timeline(new KeyFrame(Duration.millis(3), ev -> {
			for(Zombie zombie : allZombies) {
				zombie.move();
				if (zombie.getX() <= 0) {
					root.getChildren().remove(zombie.getImageView());
					zombie.isAlive = false;
		        	
					WavPlayer.stop();
		            Platform.runLater(() -> stage.setScene(GameOver.getGameOverScene(stage)));
				}
			}		
		}));
		zombieMoveTimeline.setCycleCount(Timeline.INDEFINITE);
		zombieMoveTimeline.play();
		
		

		// ----- Plant Placement -----
		
		root.setOnMouseClicked((MouseEvent e) -> {
		    double mouseX = e.getX();
		    double mouseY = e.getY();

		    int col = (int) ((mouseX - xOffset) / CELL_WIDTH);
		    int lane = (int) ((mouseY - yOffset) / CELL_HEIGHT);

		    if (col < 0 || col >= COLS || lane < 0 || lane >= ROWS)
		        return; // out of bounds

		    double plantX = xOffset + col * CELL_WIDTH;
		    double laneY = LANE_Y[lane];      
		    double plantY = laneY - 40;             
		    
		    createNewPlant(plantX + 20, plantY + 20, root);
		});


		// ----- Pea Shooting -----

		peaMoveTimeline = new Timeline(new KeyFrame(Duration.millis(0.9), ev -> {
			for(Projectile projectile : allProjectiles) {
				projectile.move();
				if (projectile.getImageView().getLayoutX() > 1000) {
					root.getChildren().remove(projectile.getImageView());
					projectile.isAlive = false;

				}
			}
			checkForCollisions();
			removeDeadObjectsFromScreen(root);
			
		}));
		peaMoveTimeline.setCycleCount(Timeline.INDEFINITE);
		peaMoveTimeline.play();
		
		
		//this one below is made entirely with gemini i do not take credit for this
		collisionBoxTimeline = new Timeline(new KeyFrame(Duration.millis(50), ev -> {
		    // Clear old boxes
		    root.getChildren().removeIf(node -> 
		        node instanceof Rectangle && 
		        ((Rectangle)node).getStroke() == Color.LIME
		    );

		}));
		collisionBoxTimeline.setCycleCount(Timeline.INDEFINITE);
		collisionBoxTimeline.play();
		

		return root;
	}
	
	public static Zombie createNewZombie(int lane) {
	    Zombie newZombie;
	    double y = LANE_Y[lane] - 60; // align center
	    if (Math.random() < 0.3) {
	        newZombie = new ConeZombie(900, y);
	    } else {
	        newZombie = new BasicZombie(900, y);
	    }
	    newZombie.lane = lane; // store lane index
	    allObjects.add(newZombie);
	    allZombies.add(newZombie);
	    return newZombie;
	}

	public static void createNewPlant(double x, double y, AnchorPane root) {
	    int lane = 0;
	    for (int i = 0; i < LANE_Y.length; i++) {
	        if (Math.abs(y - LANE_Y[i]) < 50) { // find closest lane
	            lane = i;
	            break;
	        }
	    }
	    Plant newPlant = new Peashooter(x, y, root);
	    newPlant.lane = lane; // store lane index
	    allObjects.add(newPlant);
	    allPlants.add(newPlant);
	    root.getChildren().add(newPlant.getImageView());
	}

	
	public static void checkForCollisions() {
	    for (Zombie zombie : allZombies) {

	        for (Projectile projectile : allProjectiles) {

	            // Check if projectile overlaps zombie
	            boolean verticalOverlap = zombie.y <= projectile.y + 1;
	            boolean horizontalOverlap = zombie.x <= projectile.x + 1;

	            if (verticalOverlap && horizontalOverlap) {
	                zombie.takeDamage(projectile.damage);
	                projectile.takeDamage(zombie.damage);
	            }
	        }

	        // Zombie eating plants
	        for (Plant plant : allPlants) {
	            if (plant.lane != zombie.lane) continue;

	            // Check if zombie has reached plant's tile
	            if (zombie.x <= plant.x + 10) {
	            	
	                zombie.isEating = true;
	                plant.takeDamage(zombie.damage);

	                if (!plant.isAlive) {
	                    zombie.stopEating();
	                }
	            } else {
	                zombie.stopEating();
	            }
	        }

	    }
	}

	public static void removeDeadObjectsFromScreen(AnchorPane root) {

	    ArrayList<GameObject> dead = new ArrayList<>();

	    for (GameObject obj : allObjects) {
	    	if (!obj.isAlive) {
	    	    root.getChildren().remove(obj.getImageView());

	    	    if (obj instanceof Peashooter p) {
	    	        p.stopShooting();
	    	    }
	    	    if (obj instanceof Zombie z) {
	    	        z.stopEating();
	    	    }

	    	    dead.add(obj);
	    	}

	    }

	    allPlants.removeAll(dead);
	    allProjectiles.removeAll(dead);
	    allZombies.removeAll(dead);
	    allObjects.removeAll(dead);
	}
	
	public static void cleanup() {
	    if (spawnTimeline != null) spawnTimeline.stop();
	    if (zombieMoveTimeline != null) zombieMoveTimeline.stop();
	    if (peaMoveTimeline != null) peaMoveTimeline.stop();
	    if (collisionBoxTimeline != null) collisionBoxTimeline.stop();

	    for (Plant plant : allPlants) {
	        plant.stopShooting();
	    }

	    for (Zombie z : allZombies) {
	        z.stopEating();
	    }

	    allObjects.clear();
	    allPlants.clear();
	    allZombies.clear();
	    allProjectiles.clear();
	}

	
	
}


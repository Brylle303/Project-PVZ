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
import javafx.util.Duration;

import java.util.ArrayList;

public class Adventure {

	private static final double[] LANE_Y = { 110, 220, 320, 420, 520 }; // Just adjust this if u adjust lawn

	// Plants list
	private static ArrayList<Plant> plants = new ArrayList<>();
	private static ArrayList<Pea> activePeas = new ArrayList<>();


	// Align zombie feet to lane
	public static double getLaneY(int lane, Zombie zombie) {
		double offset = zombie.getImageView().getBoundsInParent().getHeight() / 2;
		return LANE_Y[lane] - offset;
	}

	// Just to make sure the game doesnt spam zombies in a single lane lol
	private static long[] laneCooldowns = new long[5];  
	private static final long LANE_COOLDOWN_NS = 10000000000L; 
	
	public static AnchorPane getScreen() {

		final AnchorPane root = new AnchorPane();

		// Background
		BackgroundImage bg = new BackgroundImage(
				new javafx.scene.image.Image(Adventure.class.getResource("/Assets/Main Battleground.png").toExternalForm()),
				BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT,
				BackgroundPosition.CENTER,
				new BackgroundSize(100, 100, true, true, true, true));
		root.setBackground(new Background(bg));

		// Game Over Screen
		
		ImageView gameOver = new ImageView(new Image(Adventure.class.getResource("/Assets/Game Over.png").toExternalForm()));
		gameOver.setLayoutX((root.getWidth() - gameOver.getFitWidth()) / 2);
		gameOver.setLayoutY((root.getHeight() - gameOver.getFitHeight()) / 2);
		
		
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
			Platform.exit();
		});
		root.getChildren().add(exitButton);

		// Play background music
		WavPlayer.play("src/Assets/LawnMusic.wav", true);

		// ----- Zombie Spawning -----
		Timeline spawnTimeline = new Timeline(new KeyFrame(Duration.seconds(5), e -> {

		    long now = System.nanoTime();

		    int lane;
		    do {
		        lane = (int) (Math.random() * LANE_Y.length);
		    } while (now - laneCooldowns[lane] < LANE_COOLDOWN_NS); // pick another if lane is still cooling down

		    laneCooldowns[lane] = now; // lock lane

		    Zombie zombie;
		    if (Math.random() < 0.1) {
		        zombie = new ConeZombie();
		    } else {
		        zombie = new Zombie("/Assets/walking-zombie.gif", 170, 340);
		    }

		    zombie.setX(920);
		    zombie.setY(getLaneY(lane, zombie));
		    root.getChildren().add(zombie.getImageView());

		    Timeline moveTimeline = new Timeline(new KeyFrame(Duration.millis(50), ev -> {
		        zombie.setX(zombie.getX() - 2);

		    }));
		    moveTimeline.setCycleCount(Timeline.INDEFINITE);
		    moveTimeline.play();
	        if (zombie.getX() <= 0) {
	            root.getChildren().remove(zombie.getImageView());
	            root.getChildren().add(gameOver);
	            moveTimeline.stop();
	        }

		}));

		spawnTimeline.setCycleCount(Timeline.INDEFINITE);
		spawnTimeline.play();

		// ----- Grid Overlay -----
		int rows = 5;
		int cols = 9;
		double gridWidth = 780;
		double gridHeight = 500;
		double cellWidth = gridWidth / cols;
		double cellHeight = gridHeight / rows;

		double xOffset = 120;
		double yOffset = 80;

		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {
				Rectangle rect = new Rectangle(xOffset + c * cellWidth, yOffset + r * cellHeight, cellWidth, cellHeight);
				rect.setFill(Color.TRANSPARENT);
				rect.setStroke(Color.RED);
				rect.setStrokeWidth(2);
				root.getChildren().add(rect);
			}
		}

		// ----- Plant Placement -----
		root.setOnMouseClicked((MouseEvent e) -> {
			double mouseX = e.getX();
			double mouseY = e.getY();

			int col = (int) ((mouseX - xOffset) / cellWidth);
			int lane = (int) ((mouseY - yOffset) / cellHeight);

			if (col < 0 || col >= cols || lane < 0 || lane >= rows)
				return;

			double plantX = xOffset + col * cellWidth;
			double plantY = yOffset + lane * cellHeight;

			Peashooter p = new Peashooter(plantX, plantY);
			plants.add(p);
			root.getChildren().add(p.getImageView());
		});

		// ----- Pea Shooting -----
		Timeline peaTimeline = new Timeline(new KeyFrame(Duration.millis(5000), e -> {
		    for (Plant plant : plants) {
		        if (plant instanceof Peashooter) {
		            Pea pea = ((Peashooter) plant).shoot();
		            if (pea != null) { // in case you add a cooldown
		                root.getChildren().add(pea.getNode());
		                activePeas.add(pea);
		            }
		        }
		    }
		}));
		peaTimeline.setCycleCount(Timeline.INDEFINITE);
		peaTimeline.play();
		
		//Moves the peas
		Timeline movePeasTimeline = new Timeline(new KeyFrame(Duration.millis(50), e -> {
		    ArrayList<Pea> toRemove = new ArrayList<>();
		    for (Pea pea : activePeas) {
		        pea.move();
		        if (pea.getNode().getLayoutX() > 900) {
		            root.getChildren().remove(pea.getNode());
		            toRemove.add(pea);
		        }
		    }
		    activePeas.removeAll(toRemove);
		}));
		movePeasTimeline.setCycleCount(Timeline.INDEFINITE);
		movePeasTimeline.play();


		return root;
	}
}

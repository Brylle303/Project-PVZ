package application;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class Adventure {
	
	public static AnchorPane getScreen() {
		
		Image bgImage = new Image(Adventure.class.getResource("/Assets/Main Battleground.png").toExternalForm());

		BackgroundImage bg = new BackgroundImage(
		        bgImage,
		        BackgroundRepeat.NO_REPEAT,
		        BackgroundRepeat.NO_REPEAT,
		        BackgroundPosition.CENTER,
		        new BackgroundSize(100, 100, true, true, true, true)
		);

		Button exitButton = new Button();
		
		Font font = Font.font("Courier New", FontWeight.BOLD, 18);
		
		exitButton.setFont(font);
		exitButton.setText("EXIT");
		exitButton.setPrefWidth(90);       
		exitButton.setPrefHeight(50);
		exitButton.setLayoutX(880);
		exitButton.setLayoutY(20);
		
		exitButton.setOnAction(e -> {
				
		    WavPlayer.stop();
		    Platform.exit();
		});

		WavPlayer.play("src/Assets/LawnMusic.wav", true);

				
		AnchorPane root = new AnchorPane();
		
		root.getChildren().add(exitButton);
		root.setBackground(new Background(bg));
		
		return root;
	}
}

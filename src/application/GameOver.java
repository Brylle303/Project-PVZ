package application;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class GameOver {
	
	public static Scene getGameOverScene(Stage stage) {
	    
		AnchorPane root = new AnchorPane();
	    root.setStyle("-fx-background-color: black;");

	    ImageView gameOver = new ImageView(new Image(GameOver.class.getResource("/Assets/Game Over.png").toExternalForm()));
	    
	    gameOver.setFitHeight(500);
	    gameOver.setFitWidth(500);
	    
	    gameOver.setLayoutX( (930 - gameOver.getImage().getWidth()) / 2 );
	    gameOver.setLayoutY( (600 - gameOver.getImage().getHeight()) / 2 );

		Button exitButton = new Button("EXIT");
		Font font = Font.font("Courier New", FontWeight.BOLD, 18);
		exitButton.setFont(font);
		exitButton.setPrefWidth(90);
		exitButton.setPrefHeight(50);
		exitButton.setLayoutX(450);
		exitButton.setLayoutY(500);
	    
		exitButton.setOnAction(e -> {
			Platform.exit();
		});
		
	    root.getChildren().add(exitButton);
	    root.getChildren().add(gameOver);

	    return new Scene(root, 930, 600);
	}

}

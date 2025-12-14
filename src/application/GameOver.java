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
	    
	    gameOver.setFitWidth(500);
	    gameOver.setFitHeight(500);

	    gameOver.setLayoutX((920 - 500) / 2);
	    gameOver.setLayoutY((600 - 500) / 2);

	    
		Button exitButton = new Button("EXIT");
		Font font = Font.font("Courier New", FontWeight.BOLD, 18);
		exitButton.setFont(font);
		exitButton.setPrefWidth(90);
		exitButton.setPrefHeight(50);
		exitButton.setLayoutX(450);
		exitButton.setLayoutY(500);
	    
		exitButton.setOnAction(e -> {
			Adventure.cleanup();
			Platform.exit();
		});
		
	    root.getChildren().add(gameOver);
	    root.getChildren().add(exitButton);

	    return new Scene(root, 930, 600);
	}

}

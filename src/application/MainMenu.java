package application;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.stage.Stage;

public class MainMenu {
	
	public static AnchorPane getScreen(Stage stage) {
		
		Image bgImage = new Image(MainMenu.class.getResource("/Assets/MainMenu-Complete.png").toExternalForm());

		BackgroundImage bg = new BackgroundImage(
		        bgImage,
		        BackgroundRepeat.NO_REPEAT,
		        BackgroundRepeat.NO_REPEAT,
		        BackgroundPosition.CENTER,
		        new BackgroundSize(100, 100, true, true, true, true)
		);

		Button playBtn = new Button();
		ImageView buttonImage = new ImageView(new Image("Assets/StartButton1.png"));
		
		playBtn.setGraphic(buttonImage);
		playBtn.setStyle("-fx-background-color: transparent;");
		
		buttonImage.setFitWidth(350);
		buttonImage.setPreserveRatio(false);
		
		playBtn.setLayoutX(570);
		playBtn.setLayoutY(80);
		
		AnchorPane root = new AnchorPane();
		root.setBackground(new Background(bg));
		
		     	
		playBtn.setOnAction(e -> {
			
			stage.setScene(new Scene(Adventure.getScreen(stage), 930, 600));

		});
		
		root.getChildren().add(playBtn);
		
		return  root;
	}
}

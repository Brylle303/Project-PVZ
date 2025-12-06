package application;
	
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;


public class Main extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		
		VBox root = new VBox(10); 
		
		//Group root = new Group();
		Scene scene = new Scene(root,Color.LIGHTGREY);
		Image icon = new Image("Assets/Logo.png");
		Image title = new Image("Assets/Title Card.png");
		ImageView viewTitle = new ImageView(title);
		Text subtext = new Text();
		     
		stage.getIcons().add(icon);
		stage.setTitle("Brylle JavaFX - v1.0");
		
		stage.setScene(scene);
		stage.setWidth(700);
		stage.setHeight(700);
		stage.setResizable(false);
		
		//viewTitle.setX(120);
		//viewTitle.setY(-90);
		viewTitle.setFitWidth(700);   
		viewTitle.setFitHeight(450);  
		viewTitle.setPreserveRatio(true);
		
		Button spButton = new Button("SINGLEPLAYER");
		spButton.setOnAction(e -> {
            System.out.println("You are playing in SINGLEPLAYER");
        });
		
		Button mpButton = new Button("MULTIPLAYER");
		mpButton.setOnAction(e -> {
            System.out.println("You are playing in MULTIPLAYER");
        });
		
		Button exitButton = new Button("EXIT");
		exitButton.setOnAction(e -> {
            System.out.println("You have clicked the exit button");
        });
		
		spButton.setPrefWidth(150);
		spButton.setPrefHeight(60);
		mpButton.setPrefWidth(150);
		mpButton.setPrefHeight(60);
		exitButton.setPrefWidth(150);
		exitButton.setPrefHeight(60);
        
        
		subtext.setText("by Brylle");
		//subtext.setX(270);
		//subtext.setY(200);
		subtext.setFont(Font.font("Times New Roman", 30));
		
		
		VBox.setMargin(spButton, new Insets(30, 0, 0, 0));
		VBox.setMargin(viewTitle, new Insets(70, 0, 0, 0));
		root.setAlignment(Pos.TOP_CENTER);
		root.getChildren().add(viewTitle);
		root.getChildren().add(subtext);
		root.getChildren().add(spButton);
		root.getChildren().add(mpButton);
		root.getChildren().add(exitButton);
		
		
		stage.show();
		
	}
}

package application;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {

        WavPlayer.play("src/Assets/MainMenuMusic.wav", true);
        String videoURL = getClass().getResource("/Assets/PVZ-Intro-NoMusic.mp4").toExternalForm();

        WebView webView = new WebView();
        WebEngine engine = webView.getEngine();

        String html = "<html><body style='margin:0; background:black; overflow:hidden;'>"
                + "<video width='100%' height='100%' autoplay onended='javaAppSkip()'>"
                + "<source src='" + videoURL + "' type='video/mp4'>"
                + "</video>"
                + "<script>"
                + "function javaAppSkip() { "
                + "    alert('videoFinished'); "
                + "} "
                + "</script>"
                + "</body></html>";

        engine.loadContent(html);

        webView.setPrefSize(930, 600);

        AnchorPane root = new AnchorPane(webView);
        Scene scene = new Scene(root, 930, 600);
        Image icon = new Image(getClass().getResource("/Assets/Logo.png").toExternalForm());

        stage.getIcons().add(icon);
        stage.setTitle("PVZ Prototype v1.0");
        stage.setScene(scene);
        stage.setResizable(false);

        // Skip button
        Button skipButton = new Button("SKIP");
        skipButton.setPrefWidth(60);
        skipButton.setPrefHeight(30);
        skipButton.setLayoutX(830);
        skipButton.setLayoutY(10);

        // If you're bored of seeing the intro video you can use this button to go straight to main menu
        skipButton.setOnAction(e -> {
            engine.setOnAlert(null);      
            webView.getEngine().load(null); 
            Platform.runLater(() -> scene.setRoot(MainMenu.getScreen(stage)));
        });


        root.getChildren().add(skipButton);

        // This automatically switches to the main menu after the intro video
        engine.setOnAlert(event -> {
            if ("videoFinished".equals(event.getData())) {
                engine.setOnAlert(null);
                Platform.runLater(() -> scene.setRoot(MainMenu.getScreen(stage)));
            }
        });

        stage.show();
    }
}

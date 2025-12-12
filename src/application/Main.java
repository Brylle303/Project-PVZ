package application;

import javafx.application.Application;
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

        skipButton.setOnAction(e -> {
            scene.setRoot(MainMenu.getScreen(scene)); // go to adventure immediately
        });

        root.getChildren().add(skipButton);

        // Video finished alert
        engine.setOnAlert(event -> {
            if ("videoFinished".equals(event.getData())) {
                scene.setRoot(MainMenu.getScreen(scene)); // load adventure after video
            }
        });

        stage.show();
    }
}

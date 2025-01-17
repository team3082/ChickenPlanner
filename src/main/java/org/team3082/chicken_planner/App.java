package org.team3082.chicken_planner;

import java.io.IOException;

import org.team3082.chicken_planner.UIElements.LandingScene;

import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

    private Stage stage;
    private LandingScene landingScene;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;

        landingScene = new LandingScene(stage);
        String themesStylesheet = getClass().getResource("/themes/themes.css").toExternalForm();
        landingScene.getStylesheets().add(themesStylesheet);
        
        String globalStylesheet = getClass().getResource("/themes/styles.css").toExternalForm();
        landingScene.getStylesheets().add(globalStylesheet);
        
        stage.setScene(landingScene);

        stage.setTitle("ChickenPlanner 2025");
        stage.show();
    }   
}

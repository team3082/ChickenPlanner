package org.team3082.chicken_planner;

import org.team3082.chicken_planner.UIElements.LandingScene;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class App extends Application {
    private Stage stage;
    private LandingScene landingScene;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        landingScene = new LandingScene();
        landingScene.getStylesheets().add(App.class.getResource())
        stage.setScene(landingScene);
        stage.setTitle("ChickenPlanner");
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();

    }

    
}

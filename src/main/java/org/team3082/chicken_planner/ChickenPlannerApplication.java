package org.team3082.chicken_planner;

import org.team3082.chicken_planner.State.AppState;
import org.team3082.chicken_planner.UI.Main.UIManager;

import javafx.application.Application;
import javafx.stage.Stage;


public class ChickenPlannerApplication extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        AppState appState = AppState.getInstance();
        UIManager UIManager = new UIManager(primaryStage);

    
    }
}

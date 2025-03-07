package org.team3082.chicken_planner;

import org.team3082.chicken_planner.IO.Project.ProjectLoadManager;
import org.team3082.chicken_planner.IO.Project.Team2025Loader;
import org.team3082.chicken_planner.State.AppState;
import org.team3082.chicken_planner.State.EventBus;
import org.team3082.chicken_planner.State.Events.ProjectLoadRequest;
import org.team3082.chicken_planner.State.Events.ProjectLoadedEvent;
import org.team3082.chicken_planner.UI.Main.UIManager;

import javafx.application.Application;
import javafx.stage.Stage;


public class ChickenPlannerApplication extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Settings settings = Settings.getInstance();
        Globals.themeProperty.set(settings.getColorTheme());

        AppState appState = AppState.getInstance();
        UIManager UIManager = new UIManager(primaryStage);

        ProjectLoadManager projectLoadManager = new Team2025Loader();

        EventBus.register(event -> {
            switch (event) {
                case ProjectLoadRequest projectLoadRequest -> {
                    boolean isWPLIBDirectory = true;

                    if(isWPLIBDirectory){
                        settings.addProject(projectLoadRequest.getFile().toString());
                        EventBus.fireEvent(new ProjectLoadedEvent(projectLoadManager.loadProject(projectLoadRequest.getFile())));
                        
                    }
                }
                default -> {}
            }
        });
    
    }
}

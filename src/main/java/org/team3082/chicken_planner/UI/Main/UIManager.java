package org.team3082.chicken_planner.UI.Main;

import org.team3082.chicken_planner.Globals;
import org.team3082.chicken_planner.State.EventBus;
import org.team3082.chicken_planner.State.Events.PageSwitchEvent;
import org.team3082.chicken_planner.State.Events.ProjectLoadedEvent;
import org.team3082.chicken_planner.UI.Components.WindowBar;

import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class UIManager {
    private Stage primaryStage;
    private Scene landingScene;
    private Scene editorScene;

    public UIManager(Stage primaryStage) {
        this.primaryStage = primaryStage;
        
        landingScene = WindowBar.load(new VBox(), primaryStage, new LandingPage(primaryStage));
        editorScene = WindowBar.load(new VBox(), primaryStage, new EditorPage(primaryStage));
        reloadStyles();

        Globals.themeProperty.addListener((_, _, _)->{
            reloadStyles();
        });

        primaryStage.setScene(landingScene);
        primaryStage.setTitle("ChickenPlanner 2025");
        primaryStage.show();
        
        EventBus.register(event -> {
            switch (event) {
                case PageSwitchEvent pageSwitchEvent -> switchScene(pageSwitchEvent.getTargetPage());
                case ProjectLoadedEvent loadedEvent -> switchScene(Page.EDITOR_PAGE);
                default -> {}
            }
        });
    }

    private void reloadStyles() {
        while (landingScene.getStylesheets().size() > 1) {
            landingScene.getStylesheets().remove(1);
        }
        while (editorScene.getStylesheets().size() > 1) {
            editorScene.getStylesheets().remove(1);
        }
        
        String themeStylesheet = getClass().getResource("/styles/themes/" + Globals.themeProperty.getValue() + ".css")
                .toExternalForm();
        String globalStylesheet = getClass().getResource("/styles/style.css").toExternalForm();
        String windowStylesheet = getClass().getResource("/styles/window.css").toExternalForm();
        
        landingScene.getStylesheets().addAll(themeStylesheet, globalStylesheet, windowStylesheet);
        editorScene.getStylesheets().addAll(themeStylesheet, globalStylesheet, windowStylesheet);
    }

    private void switchScene(Page page){
        switch (page){
            case LANDING_PAGE:
                primaryStage.setScene(landingScene);
                break;
            case EDITOR_PAGE:
                primaryStage.setScene(editorScene);
                break;
            default:
                break;
        }
    }

    
    public void initUI() {
        
    }
}

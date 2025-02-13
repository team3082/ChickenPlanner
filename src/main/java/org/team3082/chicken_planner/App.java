package org.team3082.chicken_planner;

import java.io.IOException;

import org.team3082.chicken_planner.UIElements.CustomNodes.WindowBarNode;
import org.team3082.chicken_planner.UIElements.EditorPage;
import org.team3082.chicken_planner.UIElements.LandingPage;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    private Stage stage;
    private Scene landingScene;
    private Scene editorScene;

    private static App app;

    public static App getAppInstance(){
        return app;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        app = this;
        // Creates the JavaFX stage
        this.stage = stage;

        // Creates the window container
        

        // Inits the landing scene with a window bar
        landingScene = WindowBarNode.load(stage, new LandingPage(stage));
        editorScene = WindowBarNode.load(stage, new EditorPage(stage));

        // Adds styles to scene
        Globals.themeProperty.addListener((_, _, _) -> {
            reloadStyles();
        });
        reloadStyles();

        // Opens stage to scene
        stage.setScene(editorScene);
        stage.setTitle("ChickenPlanner 2025");
        stage.show();
    }

    /*
     * Reloads the styles for a specified scene.
     * z
     * 
     * @param scene The scene to reload styles for
     */
    private void reloadStyles() {
        // while (landingScene.getStylesheets().size() > 1) {
        //     landingScene.getStylesheets().remove(1);
        // }

        while (editorScene.getStylesheets().size() > 1) {
            editorScene.getStylesheets().remove(1);
        }

        String themeStylesheet = getClass().getResource("/styles/themes/" + Globals.themeProperty.getValue() + ".css")
                .toExternalForm();
        String globalStylesheet = getClass().getResource("/styles/style.css").toExternalForm();
        String windowStylesheet = getClass().getResource("/styles/window.css").toExternalForm();

        // landingScene.getStylesheets().addAll(themeStylesheet, globalStylesheet, windowStylesheet);
        editorScene.getStylesheets().addAll(themeStylesheet, globalStylesheet, windowStylesheet);
    }
}

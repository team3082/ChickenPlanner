package org.team3082.chicken_planner;

import java.io.IOException;

import org.team3082.chicken_planner.UIElements.CustomNodes.WindowBarNode;
import org.team3082.chicken_planner.UIElements.EditorPage;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {

    private Stage stage;
    private Scene landingScene;
    private Scene editorScene;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        // Creates the JavaFX stage
        this.stage = stage;
        stage.setMinWidth(Constants.UI.MIN_WINDOW_WIDTH);
        stage.setMinHeight(Constants.UI.MIN_WINDOW_HEIGHT);

        // Creates the window container
        VBox window = new VBox();
        window.setPrefSize(Constants.UI.WINDOW_WIDTH, Constants.UI.WINDOW_HEIGHT);
        // Theme.load(Globals.themeProperty.getValue(), window);

        // Inits the landing scene with a window bar
        //WindowBarNode landingNode = new WindowBarNode();
        WindowBarNode editorNode = new WindowBarNode();

        //landingScene = landingNode.load(window, stage, new LandingPage(stage));
        editorScene = editorNode.load(window, stage, new EditorPage(stage));

        // Adds styles to scene
        Globals.themeProperty.addListener((_, _, _) -> {
            reloadStyles(editorScene);
        });
        reloadStyles(editorScene);

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
    private void reloadStyles(Scene scene) {
        while (scene.getStylesheets().size() > 1) {
            scene.getStylesheets().remove(1);
        }

        System.out.println(scene.getStylesheets().size());
        System.out.println(Globals.themeProperty.getValue());
        String themeStylesheet = getClass().getResource("/styles/themes/" + Globals.themeProperty.getValue() + ".css")
                .toExternalForm();
        String globalStylesheet = getClass().getResource("/styles/style.css").toExternalForm();
        String windowStylesheet = getClass().getResource("/styles/window.css").toExternalForm();

        scene.getStylesheets().addAll(themeStylesheet, globalStylesheet, windowStylesheet);
    }
}

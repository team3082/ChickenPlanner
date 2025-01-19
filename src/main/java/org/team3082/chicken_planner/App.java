package org.team3082.chicken_planner;

import java.io.IOException;

import org.team3082.chicken_planner.UIElements.CustomNodes.WindowBarNode;
import org.team3082.chicken_planner.UIElements.LandingPage;
import org.team3082.chicken_planner.UIElements.Utilities.Theme;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {

    private Stage stage;

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
        Theme.load(Globals.theme);
        window.getStyleClass().add(Globals.theme);
        // Inits the landing scene with a window bar
        Scene landingScene = WindowBarNode.load(window, stage, new LandingPage(stage).returnContent());

        // Adds styles to scene
        reloadStyles(landingScene);

        // Opens stage to scene
        stage.setScene(landingScene);
        stage.setTitle("ChickenPlanner 2025");
        stage.show();
    }

    /*
     * Reloads the styles for a specified scene.
     * 
     * @param scene The scene to reload styles for
     */
    private void reloadStyles(Scene scene) {
        scene.getStylesheets().removeAll();

        String globalStylesheet = getClass().getResource("/styles/style.css").toExternalForm();
        scene.getStylesheets().add(globalStylesheet);

        String themeStylesheet = getClass().getResource("/styles/themes.css").toExternalForm();
        scene.getStylesheets().add(themeStylesheet);

        String windowStylesheet = getClass().getResource("/styles/window.css").toExternalForm();
        scene.getStylesheets().add(windowStylesheet);
    }
}

package org.team3082.chicken_planner;

import java.io.IOException;

import org.team3082.chicken_planner.UIElements.LandingPage;
import org.team3082.chicken_planner.UIElements.Utilities.Theme;
import org.team3082.chicken_planner.UIElements.WindowBar;

import com.catwithawand.borderlessscenefx.scene.BorderlessScene;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class App extends Application {

    private Stage stage;
    private LandingPage landingPage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;
        VBox window = new VBox();
        window.setPrefSize(Constants.UI.WINDOW_WIDTH, Constants.UI.WINDOW_HEIGHT);

        Scene landingScene = WindowBar.load(window, stage, new LandingPage(stage).returnContent());

        String globalStylesheet = getClass().getResource("/styles/style.css").toExternalForm();
        landingScene.getStylesheets().add(globalStylesheet);

        String themeStylesheet = getClass().getResource("/styles/themes.css").toExternalForm();
        landingScene.getStylesheets().add(themeStylesheet);

        stage.setScene(landingScene);
        stage.setMinWidth(Constants.UI.MIN_WINDOW_WIDTH);
        stage.setMinHeight(Constants.UI.MIN_WINDOW_HEIGHT);

        stage.setTitle("ChickenPlanner 2025");

        stage.show();
    }
}

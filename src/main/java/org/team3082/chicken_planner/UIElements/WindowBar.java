package org.team3082.chicken_planner.UIElements;

import org.team3082.chicken_planner.Constants;
import org.team3082.chicken_planner.Globals;
import org.team3082.chicken_planner.UIElements.Utilities.Icon;
import org.team3082.chicken_planner.UIElements.Utilities.Theme;

import com.catwithawand.borderlessscenefx.scene.BorderlessScene;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class WindowBar {
    public static Scene load(VBox root, Stage stage, Parent page) {
        Theme.load(Globals.theme); // load current theme

        root.setAlignment(Pos.TOP_CENTER);
        root.getStyleClass().removeAll();
        root.getStyleClass().addAll("window", Globals.theme);

        // A close button
        Button closeButton = new Button();
        Icon close = new Icon();
        ImageView closeIcon = close.get("icons/x.svg", 12, "text");
        closeButton.setGraphic(closeIcon);
        closeButton.setOnAction(event -> stage.close());
        closeButton.getStyleClass().add("windowClose");
        closeButton.setPrefSize(48, 48);

        // A maximize button
        Button maxButton = new Button();
        Icon max = new Icon();
        ImageView maxIcon = max.get("icons/maximize.svg", 12, "text");
        maxButton.setGraphic(maxIcon);
        maxButton.getStyleClass().add("windowMaximize");
        maxButton.setPrefSize(48, 48);

        // A minimize button
        Button minButton = new Button();
        Icon min = new Icon();
        ImageView minIcon = min.get("icons/minus.svg", 12, "text");
        minButton.setGraphic(minIcon);
        minButton.getStyleClass().add("windowMinimize");
        minButton.setPrefSize(48, 48);

        Text title = new Text("ChickenPlanner");
        title.getStyleClass().add("windowTitle");

        HBox controls = new HBox(minButton, maxButton, closeButton);
        Region spacer = new Region();
        
        // Make the spacer Region expand as much as possible
        HBox.setHgrow(spacer, Priority.ALWAYS);
        // Our top bar
        var topBar = new HBox(title, spacer, controls);
        topBar.setMinHeight(48);
        topBar.getStyleClass().add("windowBar");
        topBar.setAlignment(Pos.CENTER_RIGHT);

        VBox content = new VBox(page);
        VBox.setVgrow(content, Priority.ALWAYS);
        content.getStyleClass().addAll("content");
        content.setAlignment(Pos.CENTER);

        // Add children to scene
        root.getChildren().addAll(topBar, content);

        // Create the BorderlessScene scene
        BorderlessScene scene = new BorderlessScene(stage, StageStyle.TRANSPARENT, root,
                Color.TRANSPARENT);
        maxButton.setOnAction(event -> scene.maximizeStage());

        minButton.setOnAction(event -> scene.minimizeStage());

        // Make the top bar draggable, so we can move the stage
        scene.setMoveControl(topBar);

        scene.maximizedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                root.getStyleClass().add("windowIsMaximized");
            } else {
                root.getStyleClass().remove("windowIsMaximized");
            }
        });

        return scene;
    }
}

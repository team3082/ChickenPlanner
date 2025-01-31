package org.team3082.chicken_planner.UIElements.CustomNodes;

import com.catwithawand.borderlessscenefx.scene.BorderlessScene;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class WindowBarNode {
    /**
     * Loads a scene including the window bar and the contents of the scene.
     * 
     * @param root  The root of the overall scene - the "window frame".
     * @param stage The current stage.
     * @param page  The content to be displayed.
     * @return
     */
    public Scene load(VBox root, Stage stage, Parent page) {
        root.setAlignment(Pos.TOP_CENTER);
        root.getStyleClass().removeAll();
        root.getStyleClass().addAll("window");
        

        // A close button
        Button closeButton = new Button();
        {
            Icon closeIcon = new Icon("icons/x.svg", 12, "-fx-text");
            closeButton.setGraphic(closeIcon);
            closeButton.setOnAction(_ -> stage.close());
            closeButton.getStyleClass().add("windowClose");
            closeButton.setPrefSize(48, 48);
        }

        // A maximize button
        Button maxButton = new Button();
        {
            Icon maxIcon = new Icon("icons/maximize.svg", 12, "-fx-text");
            maxButton.setGraphic(maxIcon);
            maxButton.getStyleClass().add("windowMaximize");
            maxButton.setPrefSize(48, 48);
        }

        // A minimize button
        Button minButton = new Button();
        {
            Icon minIcon = new Icon("icons/minus.svg", 12, "-fx-text");
            minButton.setGraphic(minIcon);
            minButton.getStyleClass().add("windowMinimize");
            minButton.setPrefSize(48, 48);
        }

        Text title = new Text("ChickenPlanner");
        {
            title.getStyleClass().add("windowTitle");
        }

        HBox controls = new HBox(minButton, maxButton, closeButton); // the window controls container

        Region spacer = new Region(); // To have the title float left and the controls float right
        HBox.setHgrow(spacer, Priority.ALWAYS); // Make the spacer Region expand as much as possible

        // Our top bar
        var topBar = new HBox(title, spacer, controls);
        topBar.setMinHeight(48);
        topBar.getStyleClass().add("windowBar");
        topBar.setAlignment(Pos.CENTER_RIGHT);

        // The content to be displayed in the window
        VBox content = new VBox(page);
        VBox.setVgrow(content, Priority.ALWAYS);
        content.getStyleClass().addAll("content");
        content.setAlignment(Pos.CENTER);

        // Add children to scene
        root.getChildren().addAll(topBar, content);

        // Create the BorderlessScene scene
        BorderlessScene scene = new BorderlessScene(stage, StageStyle.TRANSPARENT, root,
                Color.TRANSPARENT);
        maxButton.setOnAction(_ -> scene.maximizeStage());
        minButton.setOnAction(_ -> scene.minimizeStage());

        // Make the top bar draggable, so we can move the stage
        scene.setMoveControl(topBar);

        // check to see if the window is maximized; removes drop shadow if so.
        scene.maximizedProperty().addListener((_, _, newValue) -> {
            if (newValue) {
                root.getStyleClass().add("windowIsMaximized");
            } else {
                root.getStyleClass().remove("windowIsMaximized");
            }
        });

        return scene;
    }
}

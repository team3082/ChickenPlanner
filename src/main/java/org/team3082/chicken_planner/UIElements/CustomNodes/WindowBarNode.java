package org.team3082.chicken_planner.UIElements.CustomNodes;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class WindowBarNode extends HBox {
    Button exitButton;
    Button maxButton;
    Button minimize;

    private double xOffset = 0;
    private double yOffset = 0;

    public WindowBarNode() {
        setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        setOnMouseDragged(event -> {
            ((Stage) getScene().getWindow()).setX(event.getScreenX() - xOffset);
            ((Stage) getScene().getWindow()).setY(event.getScreenY() - yOffset);
        });


        exitButton = new Button("X");
        maxButton = new Button("[]");
        minimize = new Button("-");

        // Exit button action
        exitButton.setOnAction((event) -> {
            Stage stage = (Stage) getScene().getWindow();
            stage.close();
        });

        // Maximize button action
        maxButton.setOnAction((event) -> {
            Stage stage = (Stage) getScene().getWindow();
            if (stage.isMaximized()) {
                stage.setMaximized(false); // Unmaximize
            } else {
                stage.setMaximized(true); // Maximize
            }
        });

        // Minimize button action
        minimize.setOnAction((event) -> {
            Stage stage = (Stage) getScene().getWindow();
            stage.setIconified(true); // Minimize the window
        });

        getChildren().addAll(addTitleText(), new Region(), minimize, maxButton, exitButton);

        setId("NavigationBar");

        setPrefHeight(30);
        setAlignment(Pos.CENTER_LEFT);
    }

    public Text addTitleText() {
        Text titleText = new Text("Chicken Planner");
        HBox.setMargin(titleText, new Insets(0, 0, 0, 8));
        titleText.setId("TitleText");
        return titleText;
    }
}

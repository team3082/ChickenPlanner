package org.team3082.chicken_planner.UI.Components.Editor;

import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class ControlPointSelector extends VBox {

    public ControlPointSelector() {
        super();
        initializeUI();
    }

    private void initializeUI() {
        // Title with "Move" in a separate color
        Text titlePrefix = new Text("Selected Node ");
        Text moveKeyword = new Text("(Move)");
        

        HBox titleBox = new HBox(titlePrefix, moveKeyword);
        titleBox.getStyleClass().add("optionHeader");

        // X Coordinate Field
        HBox xLine = createCoordinateField("X", "0");

        // Y Coordinate Field
        HBox yLine = createCoordinateField("Y", "0");

        // Position Layout
        VBox positionFields = new VBox(xLine, yLine);
        positionFields.setSpacing(5);

        HBox positionLayout = new HBox(new Text("Position"), new Region(), positionFields);
        positionLayout.setSpacing(10);

        // Add all components to the VBox
        getChildren().addAll(titleBox, positionLayout);
        setSpacing(10);
    }

    private HBox createCoordinateField(String label, String defaultValue) {
        Text coordinateLabel = new Text(label);
        coordinateLabel.setStyle("-fx-font-weight: bold;");
        TextField coordinateField = new TextField(defaultValue);

        // Set background color for the TextField
        coordinateField.setStyle("-fx-control-inner-background:rgb(146, 71, 200);");

        // Set fixed width for the TextField
        coordinateField.setMinWidth(100);
        coordinateField.setMaxWidth(100);

        HBox coordinateLine = new HBox(coordinateLabel, coordinateField);
        coordinateLine.setSpacing(5); // Add spacing between label and field

        return coordinateLine;
    }
}
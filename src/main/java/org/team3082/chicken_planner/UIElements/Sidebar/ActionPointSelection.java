package org.team3082.chicken_planner.UIElements.Sidebar;

import java.util.function.UnaryOperator;

import org.team3082.chicken_planner.ChickenPlannerApplication;

import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class ActionPointSelection extends VBox {
    ChickenPlannerApplication application;
    public ActionPointSelection(ChickenPlannerApplication application){
       
        this.application = application;
        HBox rotation = createTextField("T Value", "0");
        getChildren().addAll(rotation);
    }
   
    private HBox createTextField(String label, String defaultValue) {
        Text coordinateLabel = new Text(label);
        coordinateLabel.setStyle("-fx-font-weight: bold;");
        TextField coordinateField = new TextField(defaultValue);

        // Set background color for the TextField
        coordinateField.setStyle("-fx-control-inner-background:rgb(138, 135, 206);");

        // Set fixed width for the TextField
        coordinateField.setMinWidth(50);
        coordinateField.setMaxWidth(50);

        UnaryOperator<Change> filter = change -> {
            String text = change.getText();

            if (text.matches("[0-9]*")) {
                return change;
            }

            return null;
        };
        TextFormatter<String> textFormatter = new TextFormatter<>(filter);
        // coordinateField.setTextFormatter(textFormatter);
        coordinateField.textProperty().addListener((_, _, _)->{
            application.getAppState().autoT.set(coordinateField.getText().equals("") ? 0 : Double.parseDouble(coordinateField.getText()));
        });
        application.getAppState().autoT.addListener((_, _, newValue) -> {
            System.err.println("Updating TextField with: " + application.getAppState().autoT.doubleValue());
            double val = (int)(application.getAppState().autoT.doubleValue()*1000+.5);
            coordinateField.setText((val/1000)+"");
        });

        HBox coordinateLine = new HBox(coordinateLabel, coordinateField);
        coordinateLine.setSpacing(5); 

        return coordinateLine;
    }
}

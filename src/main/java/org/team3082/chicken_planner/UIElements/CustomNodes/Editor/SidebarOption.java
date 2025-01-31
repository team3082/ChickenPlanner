package org.team3082.chicken_planner.UIElements.CustomNodes.Editor;

import org.team3082.chicken_planner.UIElements.CustomNodes.Icon;
import static org.team3082.chicken_planner.UIElements.Utilities.TextUtil.createText;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class SidebarOption extends HBox {

    public SidebarOption(String title, String type, String id, String... args) {
        super(16);
        setAlignment(Pos.TOP_LEFT);
        getChildren().add(createText(title, title + "OptionHeader", "optionHeader"));
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        getChildren().add(spacer);
        switch (type) {
            case "button":
                for (String arg : args) {
                    Button button = new Button(arg);
                    button.getStyleClass().add("optionButton");
                    getChildren().add(button);
                }
                break;
            case "field":
                VBox argProps = new VBox(16);

                for (String arg : args) {
                    HBox fieldRow = new HBox(8);
                    fieldRow.setAlignment(Pos.CENTER_LEFT);
                    fieldRow.setPrefWidth(128);

                    Text text = createText(arg, id + "OptionFieldText", "optionFieldText");
                    text.getStyleClass().add("optionField");

                    TextField textField = new TextField();
                    textField.getStyleClass().add("optionField");
                    textField.setPrefHeight(24);
                    fieldRow.getChildren().addAll(text, textField);

                    argProps.getChildren().add(fieldRow);
                }
                getChildren().add(argProps);

                break;
            case "icons":
                for (String arg : args) {
                    Icon icon = new Icon(arg, 12, "-fx-text");
                    Button button = new Button();
                    button.setGraphic(icon);
                    button.getStyleClass().add("optionButton");
                    getChildren().add(button);
                }
        }

    }

    public Node getInput(String id) {
        return lookup("#" + id);
    }
}

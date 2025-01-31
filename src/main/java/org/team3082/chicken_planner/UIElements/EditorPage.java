package org.team3082.chicken_planner.UIElements;

import org.team3082.chicken_planner.UIElements.CustomNodes.Editor.Sidebar;

import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class EditorPage extends VBox {

    private final Stage stage;

    public EditorPage(Stage stage) {
        super();
        this.stage = stage; // Keep a reference to the stage

        HBox hBox = createContentLayout();
        getChildren().add(hBox);
    }

    private HBox createContentLayout() {
        HBox layout = new HBox(32);
        layout.setAlignment(Pos.TOP_LEFT);
        Sidebar sidebar = new Sidebar();
        layout.getChildren().add(sidebar);

        return layout;
    }

}

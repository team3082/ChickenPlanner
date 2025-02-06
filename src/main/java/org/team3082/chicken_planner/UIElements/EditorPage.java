package org.team3082.chicken_planner.UIElements;

import org.team3082.chicken_planner.UIElements.CustomNodes.Editor.Sidebar;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
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
        StackPane field = new StackPane();

        Image fieldImage = new Image(getClass().getResource("/assets/reefscape_field.png").toExternalForm());
        ImageView fieldImageView = new ImageView();

        fieldImageView.setImage(fieldImage);
        fieldImageView.setFitWidth(100);
        fieldImageView.setPreserveRatio(true);
        fieldImageView.setSmooth(true);
        fieldImageView.setCache(true);

        field.getChildren().add(fieldImageView);
        layout.getChildren().add(field);
 
       return layout;
    }
}

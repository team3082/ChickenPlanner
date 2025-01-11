package org.team3082.chicken_planner.UIElements;

import org.team3082.chicken_planner.Constants;
import org.team3082.chicken_planner.UIElements.CustomNodes.WindowBarNode;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class LandingScene extends Scene {
    VBox root;
    
    public LandingScene() {
        super(new VBox(), Constants.UI.WINDOW_WIDTH, Constants.UI.WINDOW_HEIGHT);
        root = (VBox) getRoot();
        root.getStyleClass().add("root");

        root.getChildren().add(new WindowBarNode());

        ImageView imageView = new ImageView();
        Image image = new Image(LandingScene.class.getResource("/chicken.svg").toExternalForm());
        imageView.setImage(image);
        imageView.setFitWidth(200);

        root.getChildren().add(new HBox(imageView));
    }

    
}

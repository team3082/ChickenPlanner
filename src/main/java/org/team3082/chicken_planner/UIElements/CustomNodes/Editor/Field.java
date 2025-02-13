package org.team3082.chicken_planner.UIElements.CustomNodes.Editor;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class Field extends StackPane {
    private static Canvas splineCanvas;
    private static Canvas robotCanvas;


    public Field(BorderPane parent, VBox sidebar) {
        // Load the field image
        Image fieldImage = new Image(getClass().getResource("/assets/reefscape_field.png").toExternalForm());
        ImageView imageView = new ImageView();

        // Set the image to the ImageView
        imageView.setImage(fieldImage);
        imageView.setPreserveRatio(true);

        // Bind the ImageView's width and height to the available size after the sidebar is accounted for
        imageView.fitWidthProperty().bind(parent.widthProperty().subtract(sidebar.widthProperty().add(50)));
        imageView.fitHeightProperty().bind(imageView.fitWidthProperty().divide(fieldImage.getWidth() / fieldImage.getHeight()));

        splineCanvas = new Canvas();
        robotCanvas = new Canvas();
        
        splineCanvas.widthProperty().bind(imageView.fitWidthProperty());
        splineCanvas.heightProperty().bind(imageView.fitHeightProperty());

        robotCanvas.widthProperty().bind(imageView.fitWidthProperty());
        robotCanvas.heightProperty().bind(imageView.fitHeightProperty());

        // Add the image view to the StackPane
        getChildren().addAll(imageView, splineCanvas);

        // Ensure StackPane doesn't stretch infinitely
        setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
    }

}

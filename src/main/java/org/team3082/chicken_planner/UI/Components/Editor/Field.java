package org.team3082.chicken_planner.UIElements.CustomNodes.Editor;


import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class Field extends StackPane {
    private static Canvas splineCanvas;
    private static Canvas robotCanvas;


    public Field(HBox parent, VBox sidebar) {
        // Load the field image
        Image fieldImage = new Image(getClass().getResource("/assets/reefscape_field_expanded.png").toExternalForm());
        ImageView imageView = new ImageView();
        setAlignment(Pos.CENTER);
        // Set the image to the ImageView
        imageView.setImage(fieldImage);
        imageView.setPreserveRatio(true);

        // Bind the ImageView's width and height to the available size after the sidebar is accounted for
        imageView.fitWidthProperty().bind(parent.widthProperty().subtract(sidebar.widthProperty().add(59)));
        imageView.fitHeightProperty().bind(imageView.fitWidthProperty().divide(fieldImage.getWidth() / fieldImage.getHeight()));

        splineCanvas = new Canvas();
        robotCanvas = new Canvas();
        
        splineCanvas.widthProperty().bind(imageView.fitWidthProperty());
        splineCanvas.heightProperty().bind(imageView.fitHeightProperty());

        splineCanvas.widthProperty().addListener((_, _, _) -> test());
        splineCanvas.heightProperty().addListener((_, _, _) -> test());

        robotCanvas.widthProperty().bind(imageView.fitWidthProperty());
        robotCanvas.heightProperty().bind(imageView.fitHeightProperty());

        // Add the image view to the StackPane
        getChildren().addAll(imageView, splineCanvas);

        // Ensure StackPane doesn't stretch infinitely
        setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
    }

    public void test() {
        System.err.println("Hello");
        GraphicsContext context = splineCanvas.getGraphicsContext2D();
        
        // Clear previous drawings if necessary
        context.clearRect(0, 0, splineCanvas.getWidth(), splineCanvas.getHeight());

        // Set fill color with transparency (Red with 50% opacity)
        context.setFill(javafx.scene.paint.Color.rgb(255, 255, 255, 0.01));

        // Draw filled rectangle
        context.fillRect(0, 0, splineCanvas.getWidth(), splineCanvas.getHeight());
    }

}

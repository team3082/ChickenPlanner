package org.team3082.chicken_planner.UIElements;

import org.team3082.chicken_planner.ChickenPlannerApplication;
import org.team3082.chicken_planner.Constants;
import org.team3082.chicken_planner.MathUtils.BezierSpline;
import org.team3082.chicken_planner.MathUtils.CubicBezierCurve;
import org.team3082.chicken_planner.MathUtils.ExampleCurves;
import org.team3082.chicken_planner.MathUtils.Vector2;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

/**
 * Manages the field UI
 */
public class Field {
    private final ChickenPlannerApplication application;

    private final StackPane field;
    private ImageView imageView;
    private Canvas splineCanvas;
    private Canvas robotCanvas;

    /**
     * Constructor that initializes the field manager.
     *
     * @param application The ChickenPlanner application instance.
     */
    public Field(ChickenPlannerApplication application) {
        this.application = application;
        this.field = new StackPane();

        initImageView();
        robotCanvas = setupCanvas();
        splineCanvas = setupCanvas();
    
    
        field.getChildren().addAll(imageView, robotCanvas, splineCanvas);
        application.getRoot().setCenter(field);
    }

    /**
     * Initializes the ImageView for displaying the field image.
     */
    private void initImageView() {
        //Inits the image view of the field
        imageView = new ImageView();
        Image image = new Image(Field.class.getResource("/crescendo-field-space.jpg").toExternalForm());
        imageView.setImage(image);
        imageView.setPreserveRatio(true);

        // Bind the width and height of the image view to the field size
        imageView.fitWidthProperty().bind(application.getRoot().widthProperty().subtract(Constants.SIDEBAR_WIDTH + Constants.FIELD_PADDING));
        imageView.fitHeightProperty().bind(imageView.fitWidthProperty().divide(image.getWidth() / image.getHeight()));
    }

    /**
     * Sets up the canvas
     */
    private Canvas setupCanvas() {
        Canvas canvas = new Canvas();

        // Bind the canvas size to the ImageView size
        canvas.widthProperty().bind(imageView.fitWidthProperty());
        canvas.heightProperty().bind(imageView.fitHeightProperty());
        
        return canvas;
    }

    //This is lucas's code, I am using it for testing purposes
    public void drawRobot(Vector2 fieldCords, double rotation) {
        GraphicsContext fieldGC = robotCanvas.getGraphicsContext2D();
        fieldGC.save();
        fieldGC.clearRect(0, 0, fieldGC.getCanvas().getWidth(), fieldGC.getCanvas().getHeight());

        double height = fieldGC.getCanvas().getHeight();

        Vector2 pixeVector2 = fieldCords.fieldToPixel(robotCanvas);
        
        double robotWidth = .08 * height;
        double robotHeight = .08 * height;

        double halfRobotWidth = robotWidth / 2.0;
        double halfRobotHeight = robotHeight / 2.0;

        try {
            fieldGC.translate(pixeVector2.getX(), pixeVector2.getY()); 
            fieldGC.rotate(rotation);

            fieldGC.setFill(new Color(198.0/256.0, 190.0/256.0, 252.0/256.0, .9));
            fieldGC.setStroke(new Color(198.0/256.0, 190.0/256.0, 252.0/256.0, .9));

            fieldGC.fillPolygon(new double[]{-halfRobotWidth, halfRobotWidth, 0}, new double[]{-halfRobotHeight, -halfRobotHeight, halfRobotHeight}, 3);
            fieldGC.strokePolygon(new double[]{-halfRobotWidth, halfRobotWidth, halfRobotWidth, -halfRobotWidth}, new double[]{-halfRobotHeight, -halfRobotHeight, halfRobotHeight, halfRobotHeight}, 4);
        } finally {
            fieldGC.restore();
        }
    }

    /**
     * Returns the root field pane.
     *
     * @return The StackPane that contains the field and image view.
     */
    public StackPane getRoot() {
        return field;
    }

    /**
     * Returns the field canvas.
     * @return The canvas used for drawing trajectories
     */
    public Canvas getSplineCanvas() {
        return splineCanvas;
    }
}


package org.team3082.chicken_planner.UIElements.HiddenMenus.LoadMenuUI;

import java.util.ArrayList;

import org.team3082.chicken_planner.Constants;
import org.team3082.chicken_planner.AutoPlanning.AutoRoutine.ActionPoint;
import org.team3082.chicken_planner.AutoPlanning.AutoRoutine.AutoRoutine;
import org.team3082.chicken_planner.MathUtils.BezierSpline;
import org.team3082.chicken_planner.MathUtils.CubicBezierCurve;
import org.team3082.chicken_planner.MathUtils.CurvePoint;
import org.team3082.chicken_planner.MathUtils.Vector2;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class RoutinePreview extends VBox {

    private final Label label;
    private final Button trashButton;
    private final ImageView imageView;
    private final Canvas canvas;

    /**
     * Constructs a RoutinePreview UI element that displays a preview of the given AutoRoutine.
     *
     * @param autoRoutine The AutoRoutine to preview.
     * @param width       The desired width of the preview.
     * @param height      The desired height of the preview.
     */
    public RoutinePreview(AutoRoutine autoRoutine, double width, double height) {
        super();
        // Initialize label and delete button
        if(autoRoutine.getRoutineName() == null){
            label = new Label("New Routine");
        } else {
            label = new Label(autoRoutine.getRoutineName());
        }
        label.setStyle("-fx-font-weight: bold;" + 
                        "-fx-text-fill: #cfcfff;"+
                        "-fx-font-size: 14;");
        HBox.setMargin(label, new Insets(0, 0, 0, 6));
        HBox topBar = new HBox(10, label);
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setStyle("-fx-background-color: #40407a;");
        trashButton = new Button("X");
        trashButton.setStyle("-fx-background-color: #474787; -fx-text-fill: #cfcfff; -fx-font-size: 7.7; -fx-font-weight: bold;");

        double size = 16;
        trashButton.setMinSize(size, size);
        trashButton.setMaxSize(size, size);
        if(autoRoutine.getRoutineName() != null) topBar.getChildren().add(trashButton);
    
    
        // Load and configure the background image
        imageView = new ImageView();
        Image image = new Image(RoutinePreview.class.getResource("/crescendo-field-space.jpg").toExternalForm());
        imageView.setImage(image);
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(width);

        // Maintain aspect ratio for the image
        imageView.fitHeightProperty().bind(imageView.fitWidthProperty().divide(image.getWidth() / image.getHeight()));
        
        // Create and configure a canvas for drawing
        canvas = new Canvas();
        canvas.widthProperty().bind(imageView.fitWidthProperty());
        canvas.heightProperty().bind(imageView.fitHeightProperty());
        topBar.setMaxWidth(canvas.getWidth());
        setMaxWidth(canvas.getWidth());
    
        drawSpline(autoRoutine.getSpline(), canvas);
        drawActionPoints(autoRoutine.getSpline(), autoRoutine.getActionPoints());
         
        // Combine image and canvas in a StackPane
        StackPane centerPane = new StackPane(imageView, canvas);

        getChildren().addAll(topBar, centerPane);
    }

    /**
     * Gets the label displaying the routine name.
     * 
     * @return The routine label.
     */
    public Label getLabel() {
        return label;
    }

    /**
     * Gets the button used to delete the routine.
     * 
     * @return The delete button.
     */
    public Button getTrashButton() {
        return trashButton;
    }

    /**
     * Gets the ImageView used to display the background image.
     * 
     * @return The ImageView.
     */
    public ImageView getImageView() {
        return imageView;
    }

    /**
     * Gets the Canvas used for drawing the spline and action points.
     * 
     * @return The Canvas.
     */
    public Canvas getCanvas() {
        return canvas;
    }

    /**
     * Draws the spline on the canvas.
     *
     * @param spline The BezierSpline to be drawn.
     * @param canvas The canvas to draw on.
     */
    private void drawSpline(BezierSpline spline, Canvas canvas) {
        double canvasWidth = canvas.getWidth();
        double canvasHeight = canvas.getHeight();
        double lineWidth = canvasHeight * Constants.LINE_WIDTH;

        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setLineWidth(lineWidth);
        gc.setStroke(Constants.LINE_COLOR);
        gc.beginPath();

        boolean isOutsideBounds = false;

        // Iterate through each curve in the spline
        for (int index = 0; index < spline.getCurveCount(); index++) {
            CubicBezierCurve curve = spline.getCurveAt(index);

            // Iterate through each point in the curve
            for (CurvePoint point : curve.getPoints()) {
                Vector2 movePoint = point.getPosition().fieldToPixel(canvasWidth, canvasHeight);
                boolean outsideBounds = point.getPosition().clamp(16.542, 8.211).equals(point.getPosition());

                // Handle transitions between in-bounds and out-of-bounds
                if (!outsideBounds && !isOutsideBounds) {
                    gc.lineTo(movePoint.getX(), movePoint.getY());
                    gc.stroke();
                    gc.setStroke(Color.CRIMSON);
                    isOutsideBounds = true;
                } else if (outsideBounds && isOutsideBounds) {
                    gc.lineTo(movePoint.getX(), movePoint.getY());
                    gc.stroke();
                    gc.setStroke(Constants.LINE_COLOR);
                    isOutsideBounds = false;
                }

                // Draw line segment
                gc.lineTo(movePoint.getX(), movePoint.getY());
                gc.moveTo(movePoint.getX(), movePoint.getY());
            }
        }

        gc.stroke();
    }

    /**
     * Draws action points on the canvas.
     *
     * @param spline       The spline associated with the action points.
     * @param actionPoints The list of action points to draw.
     */
    private void drawActionPoints(BezierSpline spline, ArrayList<ActionPoint> actionPoints) {
        for (ActionPoint actionPoint : actionPoints) {
            Vector2 pixelVector = spline.getPoint(actionPoint.getT()).fieldToPixel(canvas.getWidth(), canvas.getHeight());
            drawCircle(Constants.ACTION_POINT_SCALE, pixelVector, Constants.ACTION_POINT_COLOR);
        }
    }

    /**
     * Draws a circle at a specified location on the canvas.
     *
     * @param sizeFactor  The scale factor to determine the circle size.
     * @param pixelCoords The coordinates to center the circle at.
     * @param color       The color to fill the circle with.
     */
    private void drawCircle(double sizeFactor, Vector2 pixelCoords, Color color) {
        double radius = canvas.getHeight() * sizeFactor;
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(color);
        gc.fillOval(pixelCoords.getX() - radius, pixelCoords.getY() - radius, radius * 2, radius * 2);
    }
}

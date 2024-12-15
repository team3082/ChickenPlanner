package org.team3082.chicken_planner.UIElements.HiddenMenus.LoadMenuUI;

import java.util.ArrayList;

import org.team3082.chicken_planner.Constants;
import org.team3082.chicken_planner.AutoPlanning.AutoRoutine.ActionPoint;
import org.team3082.chicken_planner.AutoPlanning.AutoRoutine.AutoRoutine;
import org.team3082.chicken_planner.MathUtils.BezierSpline;
import org.team3082.chicken_planner.MathUtils.CubicBezierCurve;
import org.team3082.chicken_planner.MathUtils.CurvePoint;
import org.team3082.chicken_planner.MathUtils.Vector2;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.StageStyle;

public class RoutinePreview extends VBox {
    
    // UI Components
    protected final Label label;
    protected final Button trashButton;
    protected final StackPane centerPane;
    protected final Canvas canvas;
    protected final HBox topBar;

    /**
     * Constructs a RoutinePreview UI element that displays a preview of the given AutoRoutine.
     *
     * @param autoRoutine The AutoRoutine to preview.
     * @param width       The desired width of the preview.
     * @param height      The desired height of the preview.
     */
    public RoutinePreview(AutoRoutine autoRoutine, double width, double height) {
        super();
        
        // Initialize label and trash button
        label = createLabel(autoRoutine);
        topBar = createTopBar();
        trashButton = createTrashButton(autoRoutine);
        if(autoRoutine.getRoutineName() != null){
            Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);
            topBar.getChildren().addAll(spacer, trashButton);
        }
        
        // Load and configure the background image
        ImageView imageView = createImageView(width);
        
        // Create and configure the canvas for drawing
        canvas = new Canvas();
        setupCanvas(canvas, imageView);

        // Drawing the spline and action points on canvas
        drawSpline(autoRoutine.getSpline(), canvas);
        drawActionPoints(autoRoutine.getSpline(), autoRoutine.getActionPoints());
        
        // Set the image and canvas in a StackPanet
        centerPane = new StackPane(imageView, canvas);

        // Add components to the VBox
        getChildren().addAll(topBar, centerPane);
    }

    // Private helper methods to improve readability

    /**
     * Creates a label for the routine's name, or defaults to "New Routine" if none exists.
     */
    private Label createLabel(AutoRoutine autoRoutine) {
        String routineName = autoRoutine.getRoutineName() != null ? autoRoutine.getRoutineName() : "New Routine";
        Label label = new Label(routineName);
        label.setStyle("-fx-font-weight: bold;" +
                        "-fx-text-fill: #cfcfff;" +
                        "-fx-font-size: 14;");
        return label;
    }

    /**
     * Creates a top bar containing the routine label and trash button.
     */
    private HBox createTopBar() {
        HBox topBar = new HBox(10, label); // 10 is the spacing between label and trash button
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setStyle("-fx-background-color: #40407a;");
        HBox.setMargin(label, new Insets(0, 10, 0, 10)); // Add a margin around the label
        return topBar;
    }
    

    /**
     * Creates a trash button with an event handler to show a confirmation dialog when clicked.
     */
    private Button createTrashButton(AutoRoutine autoRoutine) {
        Button trashButton = new Button("X");
        trashButton.setStyle("-fx-background-color: #474787; -fx-text-fill: #cfcfff; -fx-font-size: 10; -fx-font-weight: bold;");
        trashButton.setOnAction(e -> showDeleteConfirmationDialog(autoRoutine));
        trashButton.setMinSize(20, 20);
        trashButton.setMaxSize(20, 20);
        HBox.setMargin(trashButton, new Insets(0, 5, 2, 0)); 
        return trashButton;
    }

    /**
     * Shows a confirmation dialog to ask the user if they want to delete the routine.
     */
    private void showDeleteConfirmationDialog(AutoRoutine autoRoutine) {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Warning");

        dialog.getDialogPane().setStyle("-fx-background-color: #706fd3; -fx-border-color: #474787; -fx-border-width: 2px;");
        Label contentText = new Label("Do you wish to delete " + autoRoutine.getRoutineName() + "?");
        contentText.setStyle("-fx-text-fill: #474787; -fx-font-size: 14; -fx-font-weight: bold;");
        dialog.getDialogPane().setContent(contentText);

        ButtonType yes = new ButtonType("Yes", ButtonData.OK_DONE);
        ButtonType no = new ButtonType("No", ButtonData.NO);
        dialog.getDialogPane().getButtonTypes().addAll(yes, no);

        dialog.getDialogPane().lookupButton(yes).setStyle("-fx-background-color: #474787; -fx-text-fill: #cfcfff;");
        dialog.getDialogPane().lookupButton(no).setStyle("-fx-background-color: #474787; -fx-text-fill: #cfcfff;");

        dialog.initStyle(StageStyle.UNDECORATED);
        dialog.showAndWait();
    }

    /**
     * Creates an ImageView to display the background image for the routine preview.
     */
    private ImageView createImageView(double width) {
        ImageView imageView = new ImageView();
        Image image = new Image(RoutinePreview.class.getResource("/crescendo-field-space.jpg").toExternalForm());
        imageView.setImage(image);
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(width);
        imageView.fitHeightProperty().bind(imageView.fitWidthProperty().divide(image.getWidth() / image.getHeight()));
        return imageView;
    }

    /**
     * Sets up the canvas to match the dimensions of the background image.
     */
    private void setupCanvas(Canvas canvas, ImageView imageView) {
        canvas.widthProperty().bind(imageView.fitWidthProperty());
        canvas.heightProperty().bind(imageView.fitHeightProperty());
        topBar.setMaxWidth(canvas.getWidth());
        topBar.setMinHeight(30);
        setMaxWidth(canvas.getWidth());
    }

    /**
     * Draws the Bezier spline on the canvas.
     */
    private void drawSpline(BezierSpline spline, Canvas canvas) {
        double canvasWidth = canvas.getWidth();
        double canvasHeight = canvas.getHeight();
        double lineWidth = canvasHeight * Constants.LINE_WIDTH;

        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setLineWidth(lineWidth);
        gc.setStroke(Constants.LINE_COLOR);
        gc.beginPath();

        for (int index = 0; index < spline.getCurveCount(); index++) {
            CubicBezierCurve curve = spline.getCurveAt(index);

            for (CurvePoint point : curve.getPoints()) {
                Vector2 movePoint = point.getPosition().fieldToPixel(canvasWidth, canvasHeight);
                gc.lineTo(movePoint.getX(), movePoint.getY());
                gc.moveTo(movePoint.getX(), movePoint.getY());
            }
        }

        gc.stroke();
    }

    /**
     * Draws action points on the canvas at specified locations.
     */
    private void drawActionPoints(BezierSpline spline, ArrayList<ActionPoint> actionPoints) {
        for (ActionPoint actionPoint : actionPoints) {
            Vector2 pixelVector = spline.getPoint(actionPoint.getT()).fieldToPixel(canvas.getWidth(), canvas.getHeight());
            drawCircle(Constants.ACTION_POINT_SCALE, pixelVector, Constants.ACTION_POINT_COLOR);
        }
    }

    /**
     * Draws a circle at the specified pixel coordinates on the canvas.
     */
    private void drawCircle(double sizeFactor, Vector2 pixelCoords, Color color) {
        double radius = canvas.getHeight() * sizeFactor;
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(color);
        gc.fillOval(pixelCoords.getX() - radius, pixelCoords.getY() - radius, radius * 2, radius * 2);
    }

    // Getter methods for the UI elements

    public Label getLabel() {
        return label;
    }

    public Button getTrashButton() {
        return trashButton;
    }

    public StackPane getCenterPane() {
        return centerPane;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    /**
     * Sets a custom click event handler for the RoutinePreview.
     */
    public void setOnClick(EventHandler<? super MouseEvent> action) {
        centerPane.setOnMouseClicked(action);
    }
}

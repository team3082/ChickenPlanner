package org.team3082.chicken_planner.AutoPlanning.TrajectoryDrawing.InputManagment;

import org.team3082.chicken_planner.ChickenPlannerApplication;
import org.team3082.chicken_planner.AutoPlanning.TrajectoryDrawing.TrajectoryManager;

import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;

/**
 * Manages user input for the trajectory drawing planner, 
 * interacting with action points and splines based on mouse events.
 */
public class InputManager {
    private ChickenPlannerApplication application; // Reference to the main application

    private ActionPointManager actionPointManager; // Manages action points for the trajectory
    private SplineManager splineManager;           // Manages splines for the trajectory

    /**
     * Constructor initializes the InputManager with the application and trajectory manager.
     * Sets up event handlers for mouse input on the canvas.
     *
     * @param application        Reference to the main application
     * @param trajectoryManager  Manages trajectory-related logic
     */
    public InputManager(ChickenPlannerApplication application, TrajectoryManager trajectoryManager){
        this.application = application;

        // Initialize the managers for action points and splines
        this.actionPointManager = new ActionPointManager(application, trajectoryManager.getSplineDrawingManager());
        this.splineManager = new SplineManager(application, trajectoryManager.getSplineDrawingManager());

        // Get the canvas from the application's field and set mouse event listeners
        Canvas canvas = application.getField().getSplineCanvas();
       
        canvas.setOnMousePressed(this::OnMousePressed); // Handle mouse press events
        canvas.setOnMouseDragged(this::OnMouseDragged); // Handle mouse drag events
        canvas.setOnMouseReleased(this::OnMouseReleased); // Handle mouse release events
    }

    /**
     * Handles mouse press events and delegates based on the current drawing state.
     *
     * @param event  The mouse press event
     */
    public void OnMousePressed(MouseEvent event){
        switch (application.getAppState().getDrawingState()) {
            case CONTROL_POINT: // If in control point mode, pass event to spline manager
                splineManager.onMousePressed(event);
                break;
            case ACTION_POINT: // If in action point mode, pass event to action point manager
                actionPointManager.onMousePressed(event);
            default: // Default case does nothing
                break;
        }
    }

    /**
     * Handles mouse drag events and delegates based on the current drawing state.
     *
     * @param event  The mouse drag event
     */
    public void OnMouseDragged(MouseEvent event){
        switch (application.getAppState().getDrawingState()) {
            case CONTROL_POINT: // If in control point mode, pass event to spline manager
                splineManager.onMouseDragged(event);
                break;
            case ACTION_POINT: // If in action point mode, pass event to action point manager
                actionPointManager.onMouseDragged(event);
            default: // Default case does nothing
                break;
        }
    }

    /**
     * Handles mouse release events and notifies both managers.
     *
     * @param event  The mouse release event
     */
    public void OnMouseReleased(MouseEvent event){
        splineManager.onMouseReleased(); // Notify spline manager of mouse release
        actionPointManager.onMouseReleased(); // Notify action point manager of mouse release
    }
}

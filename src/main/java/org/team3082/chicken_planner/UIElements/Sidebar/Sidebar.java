package org.team3082.chicken_planner.UIElements.Sidebar;

import org.team3082.chicken_planner.ChickenPlannerApplication;
import org.team3082.chicken_planner.Constants;
import org.team3082.chicken_planner.AppState.AppState;
import org.w3c.dom.events.MouseEvent;

import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * The Sidebar class represents the UI sidebar component for the Chicken Planner application.
 * It includes a toolbar with tools for setting the application's drawing state.
 */
public class Sidebar {
    private final ChickenPlannerApplication application;
    private VBox root;

    /**
     * Constructor to initialize the Sidebar.
     *
     * @param application The main application instance.
     */
    public Sidebar(ChickenPlannerApplication application) {
        this.application = application;
        setUpRoot();
        setUpToolbar();
    }

    /**
     * Sets up the root VBox for the sidebar and attaches it to the application's root layout.
     */
    private void setUpRoot() {
        root = new VBox();
        root.setPrefSize(Constants.SIDEBAR_WIDTH, Constants.APP_PREFERRED_HEIGHT);
        root.setAlignment(Pos.TOP_CENTER);
        root.setId("sidebar");
        application.getRoot().setLeft(root);
    }

    /**
     * Sets up the toolbar with tools for the sidebar.
     */
    private void setUpToolbar() {
        HBox toolbar = createToolbar();
        ImageView penTool = createToolIcon("/PenIcon.png", 50, 50);
        ImageView eventTool = createToolIcon("/EventIcon.png", 50, 50);

        configureToolClickHandlers(penTool, eventTool);

        toolbar.getChildren().addAll(penTool, createSpacer(10), eventTool);

        // Highlight the pen tool as the default selected tool
        updateToolSelection(penTool, eventTool);

        root.getChildren().add(toolbar);
    }

    /**
     * Creates an HBox for the toolbar.
     *
     * @return A configured HBox.
     */
    private HBox createToolbar() {
        HBox toolbar = new HBox();
        toolbar.setPrefHeight(100);
        toolbar.setPrefWidth(Constants.SIDEBAR_WIDTH);
        toolbar.setAlignment(Pos.CENTER);
        return toolbar;
    }

    /**
     * Creates a spacer region with a specified width.
     *
     * @param width The preferred width of the spacer.
     * @return A Region configured as a spacer.
     */
    private Region createSpacer(double width) {
        Region spacer = new Region();
        spacer.setPrefWidth(width);
        return spacer;
    }

    /**
     * Creates an ImageView for a toolbar tool.
     *
     * @param resourcePath The path to the image resource.
     * @param width        The initial width of the tool icon.
     * @param height       The initial height of the tool icon.
     * @return A configured ImageView for the tool icon.
     */
    private ImageView createToolIcon(String resourcePath, double width, double height) {
        ImageView toolIcon = new ImageView(Sidebar.class.getResource(resourcePath).toExternalForm());
        toolIcon.setFitWidth(width);
        toolIcon.setFitHeight(height);
        return toolIcon;
    }

    /**
     * Configures click handlers for the toolbar tools to update the drawing state.
     *
     * @param penTool   The pen tool ImageView.
     * @param eventTool The event tool ImageView.
     */
    @SuppressWarnings("unused")
    private void configureToolClickHandlers(ImageView penTool, ImageView eventTool) {
        penTool.setOnMouseClicked(e -> {
            application.getAppState().setDrawingState(AppState.DrawingState.CONTROL_POINT);
            updateToolSelection(penTool, eventTool);
        });

        eventTool.setOnMouseClicked(e -> {
            application.getAppState().setDrawingState(AppState.DrawingState.ACTION_POINT);
            updateToolSelection(eventTool, penTool);
        });

        application.getScene().setOnKeyPressed(event -> {
            if (event.getCode().toString().equals("Q")) {
                application.getAppState().setDrawingState(AppState.DrawingState.CONTROL_POINT);
                updateToolSelection(penTool, eventTool);
            }

            if (event.getCode().toString().equals("W")) {
                application.getAppState().setDrawingState(AppState.DrawingState.ACTION_POINT);
                updateToolSelection(eventTool, penTool);
            }
        });
    }

    /**
     * Updates the visual state of the selected and unselected tools.
     *
     * @param selectedTool   The ImageView of the selected tool.
     * @param unselectedTool The ImageView of the unselected tool.
     */
    private void updateToolSelection(ImageView selectedTool, ImageView unselectedTool) {
        selectedTool.setFitWidth(60);
        selectedTool.setFitHeight(60);
        unselectedTool.setFitWidth(50);
        unselectedTool.setFitHeight(50);
        application.getTrajectoryManager().getInputManager().OnMouseReleased();
    }
}

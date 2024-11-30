package org.team3082.chicken_planner.UIElements.HiddenMenus.LoadMenuUI;

import java.io.File;
import java.util.ArrayList;

import org.team3082.chicken_planner.ChickenPlannerApplication;
import org.team3082.chicken_planner.AutoPlanning.AutoRoutine.AutoRoutine;
import org.team3082.chicken_planner.FileManagment.ProjectLoader;
import org.team3082.chicken_planner.UIElements.Menubar;
import org.team3082.chicken_planner.UIElements.HiddenMenus.HiddenMenu;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;

/**
 * Represents the "Load" menu in the Chicken Planner application.
 * This menu allows the user to load a WPLIB project.
 */
public class LoadMenu extends HiddenMenu {
    
    private VBox content;

    /**
     * Constructs a new LoadMenu instance.
     *
     * @param application The main application instance.
     * @param menubar     The menubar to which this menu belongs.
     */
    public LoadMenu(ChickenPlannerApplication application, Menubar menubar) {
        super("Load", application, menubar);
        setUpLoadMenu();
    }

    /**
     * Sets up the content for the "Load" menu.
     */
    private void setUpLoadMenu() {
        // Initialize the main content container
        content = new VBox();
        content.setSpacing(10);
        content.setPadding(new Insets(10));
        content.setPrefWidth(450); // Adjusted width for the layout
        content.setPrefHeight(305); // Adjusted height for the layout
        content.setAlignment(Pos.TOP_CENTER);
        content.setStyle("-fx-background-color: #706fd3;");

        // Add the "Load WPLIB Project" button
        Button openButton = createLoadButton();
        openButton.setStyle("-fx-background-color: #474787;" +
                            "-fx-font-size: 14;" +
                            "-fx-font-weight: bold;" +
                            "-fx-text-fill: #cfcfff;");
        content.getChildren().add(openButton);

        // Wrap content in a ScrollPane
        ScrollPane scrollPane = new ScrollPane(content);
        scrollPane.setStyle("-fx-background-color: #706fd3;");
        scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
        scrollPane.setPannable(false);

        // Prevent horizontal scrolling
        scrollPane.addEventFilter(javafx.scene.input.ScrollEvent.SCROLL, event -> {
            if (event.getDeltaX() != 0) {
                event.consume();
            }
        });

        // Add the ScrollPane to the menu's layout
        borderPane.setCenter(scrollPane);
    }

    /**
     * Creates the "Load WPLIB Project" button.
     *
     * @return The configured Button instance.
     */
    private Button createLoadButton() {
        Button loadButton = new Button("Load WPLIB Project");
        loadButton.setOnAction(e -> loadAction());
        return loadButton;
    }

    /**
     * Handles the action of loading a WPLIB project.
     * Opens a directory chooser and loads the selected directory.
     */
    private void loadAction() {
        ProjectLoader projectLoader = application.getProjectLoader();

        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Load WPLIB Project");
        directoryChooser.setInitialDirectory(new File(System.getProperty("user.home") + "/Documents"));

        File selectedDirectory = directoryChooser.showDialog(application.getStage());
        if (selectedDirectory != null) {
            boolean success = projectLoader.loadWPLIBFolder(selectedDirectory);
            if (success) {
                // Logic for successful load (e.g., update UI or display a message)
            } else {
                // Logic for failed load (e.g., show an error dialog)
            }
        }
    }

    /**
     * Displays a list of AutoRoutines in the menu.
     *
     * @param autoRoutines The list of AutoRoutines to display.
     */
    public void showRoutines(ArrayList<AutoRoutine> autoRoutines) {
        // Clear existing routine previews, retaining the load button
        while (content.getChildren().size() > 1) {
            content.getChildren().remove(content.getChildren().size() - 1);
        }

        // Add a default AutoRoutine at the start of the list
        autoRoutines.add(0, new AutoRoutine());

        // Create and display RoutinePreview elements for each AutoRoutine
        for (AutoRoutine autoRoutine : autoRoutines) {
            RoutinePreview routinePreview = new RoutinePreview(autoRoutine, 400, 0);
            routinePreview.setOnMouseClicked(e -> {
                // Set the active routine and update the UI
                application.getRoot().setCenter(application.getField().getRoot());
                application.getAppState().setCurrentAutoRoutine(autoRoutine);
                application.getTrajectoryManager().getSplineDrawingManager().resetAndPopulateCanvas();
                application.getStage().setTitle("ChickenPlanner 2024 - " +
                        new File(application.getAppState().getProjectPath()).getName() +
                        " - " + autoRoutine.getRoutineName());
                menuStage.close();
            });
            content.getChildren().add(routinePreview);
        }
    }
}

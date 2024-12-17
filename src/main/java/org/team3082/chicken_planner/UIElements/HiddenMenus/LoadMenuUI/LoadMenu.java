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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.stage.StageStyle;
import javafx.scene.control.Label;
import javafx.scene.control.ButtonBar;

public class LoadMenu extends HiddenMenu {
    
    private VBox content;

    public LoadMenu(ChickenPlannerApplication application, Menubar menubar) {
        super("Load", application, menubar);
        setUpLoadMenu();
    }

    private void setUpLoadMenu() {
        // Initialize the main content container
        content = new VBox();
        content.setSpacing(10);
        content.setPadding(new Insets(10));
        content.setPrefWidth(450);
        content.setPrefHeight(305);
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

        scrollPane.addEventFilter(javafx.scene.input.ScrollEvent.SCROLL, event -> {
            if (event.getDeltaX() != 0) {
                event.consume();
            }
        });

        borderPane.setCenter(scrollPane);
    }

    private Button createLoadButton() {
        Button loadButton = new Button("Load WPLIB Project");
        loadButton.setOnAction(e -> loadAction());
        return loadButton;
    }

    private void loadAction() {
        if (!application.getAppState().getRoutineSaved()) {
            promptUserToSaveChanges(application.getAppState().getCurrentAutoRoutine());
        }
        loadWPLIBProject();
    }

    private void loadWPLIBProject() {
        ProjectLoader projectLoader = application.getProjectLoader();

        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Load WPLIB Project");
        directoryChooser.setInitialDirectory(new File(System.getProperty("user.home") + "/Documents"));

        File selectedDirectory = directoryChooser.showDialog(application.getStage());
        if (selectedDirectory != null) {
            boolean success = projectLoader.loadWPLIBFolder(selectedDirectory);
            if(success) application.setSplash();
        }
    }

    public void showRoutines(ArrayList<AutoRoutine> autoRoutines) {
        while (content.getChildren().size() > 1) {
            content.getChildren().remove(content.getChildren().size() - 1);
        }

        AutoRoutine newAuto = new AutoRoutine();
        RoutinePreview routinePreview = new RoutinePreview(newAuto, 400, 0, application);
        routinePreview.setOnClick(e -> {
            if (!application.getAppState().getRoutineSaved()) {
                promptUserToSaveChanges(newAuto);
            } else {
                setCurrentAutoRoutine(newAuto);
            }
            menuStage.close();
        });
        content.getChildren().add(routinePreview);

        for (AutoRoutine autoRoutine : autoRoutines) {
            routinePreview = new RoutinePreview(autoRoutine, 400, 0, application);
            routinePreview.setOnClick(e -> {
                if (!application.getAppState().getRoutineSaved()) {
                    promptUserToSaveChanges(autoRoutine);
                } else {
                    setCurrentAutoRoutine(autoRoutine);
                }
                menuStage.close();
            });
            content.getChildren().add(routinePreview);
        }
    }

    private void setCurrentAutoRoutine(AutoRoutine autoRoutine) {
        application.getRoot().setCenter(application.getField().getRoot());
        application.getAppState().setCurrentAutoRoutine(autoRoutine);
        application.getTrajectoryManager().getSplineDrawingManager().resetAndPopulateCanvas();
        application.getStage().setTitle("ChickenPlanner 2024 - " +
                new File(application.getAppState().getProjectPath()).getName() +
                " - " + autoRoutine.getRoutineName());
        menuStage.close();
        application.getAppState().setRoutineSaved(true);
    }

    private void promptUserToSaveChanges(AutoRoutine autoRoutine) {
        if(application.getAppState().getRoutineSaved()) return;

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Warning");
        dialog.getDialogPane().setStyle("-fx-background-color: #706fd3; -fx-border-color: #474787; -fx-border-width: 2px;");

        Label contentText = new Label("You have unsaved changes. Do you wish to save?");
        contentText.setStyle("-fx-text-fill:rgb(56, 56, 110); -fx-font-size: 14; -fx-font-weight: bold;");
        dialog.getDialogPane().setContent(contentText);

        ButtonType save = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        ButtonType discard = new ButtonType("Discard", ButtonBar.ButtonData.CANCEL_CLOSE);
        ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.BACK_PREVIOUS);
        dialog.getDialogPane().getButtonTypes().addAll(save, discard, cancel);

        dialog.getDialogPane().lookupButton(save).setStyle("-fx-background-color: #474787; -fx-text-fill: #cfcfff; -fx-font-size: 14; -fx-font-weight: bold;");
        dialog.getDialogPane().lookupButton(discard).setStyle("-fx-background-color: #474787; -fx-text-fill: #cfcfff; -fx-font-size: 14; -fx-font-weight: bold;");
        dialog.getDialogPane().lookupButton(cancel).setStyle("-fx-background-color: #474787; -fx-text-fill: #cfcfff; -fx-font-size: 14; -fx-font-weight: bold;");
        dialog.initStyle(StageStyle.UNDECORATED);

        dialog.showAndWait().ifPresent(result -> {
            if (result == save) {
                application.getProjectLoader().saveCurrentRoutine();
                application.getMenubar().getLoadMenu().showRoutines(application.getAppState().getLoadedRoutines());
                setCurrentAutoRoutine(autoRoutine);
            } else if(result == discard){
                setCurrentAutoRoutine(autoRoutine);
                
            } else {

            }
        });
    }

}

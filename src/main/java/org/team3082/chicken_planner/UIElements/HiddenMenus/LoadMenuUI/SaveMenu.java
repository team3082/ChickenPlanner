package org.team3082.chicken_planner.UIElements.HiddenMenus.LoadMenuUI;

import java.io.File;

import org.team3082.chicken_planner.ChickenPlannerApplication;
import org.team3082.chicken_planner.AutoPlanning.AutoRoutine.AutoRoutine;
import org.team3082.chicken_planner.UIElements.Menubar;
import org.team3082.chicken_planner.UIElements.HiddenMenus.HiddenMenu;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class SaveMenu extends HiddenMenu{
    VBox content;
    RoutineSaveBox routineSaveBox; 
    Button oldSave;
    HBox hBox;

    public SaveMenu(ChickenPlannerApplication application, Menubar menubar) {
        super("Save", application, menubar);
        // Pane emptyPane = new Pane();
        // emptyPane.setMaxSize(0, 0);
        // borderPane.setBottom(emptyPane);
        
        content = new VBox();
        content.setAlignment(Pos.CENTER);
        TextField autoName = new TextField("Example Routine Name");
        autoName.setMaxWidth(200);
        Button newSave = new Button("New Save");
        oldSave = new Button("Overwrite Save");
        HBox.setMargin(newSave, new Insets(0, 4, 0, 4));
        HBox.setMargin(oldSave, new Insets(0, 4, 0, 4));
        newSave.setStyle("-fx-background-color: #474787;" +
        "-fx-font-size: 14;" +
        "-fx-font-weight: bold;" +
        "-fx-text-fill: #cfcfff;");
        oldSave.setStyle("-fx-background-color: #474787;" +
        "-fx-font-size: 14;" +
        "-fx-font-weight: bold;" +
        "-fx-text-fill: #cfcfff;");

        newSave.setOnAction(e -> {
            AutoRoutine newRoutine = new AutoRoutine(application.getAppState().getCurrentAutoRoutine());
            newRoutine.setRoutineName(routineSaveBox.textBar.getText());
            application.getAppState().getLoadedRoutines().add(newRoutine);
            application.getAppState().setCurrentAutoRoutine(newRoutine);
            application.getRoot().setCenter(application.getField().getRoot());
            application.getTrajectoryManager().getSplineDrawingManager().resetAndPopulateCanvas();
            application.getStage().setTitle("ChickenPlanner 2024 - " +
                    new File(application.getAppState().getProjectPath()).getName() +
                    " - " + newRoutine.getRoutineName());
            menuStage.close();
            application.getAppState().setRoutineSaved(true);
            application.getProjectLoader().saveCurrentRoutine();
            application.getMenubar().getLoadMenu().showRoutines(application.getAppState().getLoadedRoutines());
            menuStage.close();
        });
        oldSave.setOnAction(e -> {
            application.getProjectLoader().saveCurrentRoutine();
            application.getMenubar().getLoadMenu().showRoutines(application.getAppState().getLoadedRoutines());
            application.getAppState().setRoutineSaved(true);
            
            menuStage.close();
        });
        hBox = new HBox(newSave, oldSave);
        hBox.setAlignment(Pos.CENTER);
        VBox.setMargin(hBox,  new Insets(5, 0, 5, 0));
        content.getChildren().addAll(hBox, autoName);
        
        borderPane.setCenter(content);
    }

    @Override
    public void openWindow() {
        super.openWindow();
        while (content.getChildren().size() > 1) {
            content.getChildren().remove(content.getChildren().size() - 1);
        }
        if(hBox.getChildren().size() > 1){
            hBox.getChildren().removeLast();
        }

        if(application.getAppState().getCurrentAutoRoutine().getRoutineName() != null){
            hBox.getChildren().add(oldSave);
        }
        routineSaveBox = new RoutineSaveBox(application.getAppState().getCurrentAutoRoutine(), 400, 0, application);
        content.getChildren().add(routineSaveBox);
    }
}

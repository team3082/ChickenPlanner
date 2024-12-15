package org.team3082.chicken_planner.UIElements.HiddenMenus;

import org.team3082.chicken_planner.ChickenPlannerApplication;
import org.team3082.chicken_planner.AutoPlanning.AutoRoutine.AutoRoutine;
import org.team3082.chicken_planner.UIElements.Menubar;
import org.team3082.chicken_planner.UIElements.HiddenMenus.LoadMenuUI.RoutinePreview;
import org.team3082.chicken_planner.UIElements.HiddenMenus.LoadMenuUI.RoutineSaveBox;

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
        Button oldSave = new Button("Save");
        newSave.setOnAction(e -> {
            application.getProjectLoader().saveCurrentRoutine();
        });
        oldSave.setOnAction(e -> {
            application.getProjectLoader().saveCurrentRoutine();
        });
        HBox hBox = new HBox(newSave, oldSave);
        hBox.setAlignment(Pos.CENTER);
        content.getChildren().addAll(hBox, autoName);
        
        borderPane.setCenter(content);
    }

    @Override
    public void openWindow() {
        super.openWindow();
        content.getChildren().removeLast();
        content.getChildren().add(new RoutineSaveBox(application.getAppState().getCurrentAutoRoutine(), 400, 0));
    }
}

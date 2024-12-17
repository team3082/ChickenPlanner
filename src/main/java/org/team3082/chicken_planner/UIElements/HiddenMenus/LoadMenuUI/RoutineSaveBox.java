package org.team3082.chicken_planner.UIElements.HiddenMenus.LoadMenuUI;

import org.team3082.chicken_planner.ChickenPlannerApplication;
import org.team3082.chicken_planner.AutoPlanning.AutoRoutine.AutoRoutine;

import javafx.geometry.Insets;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class RoutineSaveBox extends RoutinePreview {
    public TextField textBar;
    public RoutineSaveBox(AutoRoutine autoRoutine, double width, double height, ChickenPlannerApplication application){
        super(autoRoutine, width, height, application);

        while (topBar.getChildren().size()>0) topBar.getChildren().remove(0);
        textBar = new TextField(autoRoutine.getRoutineName());
        HBox.setMargin(textBar, new Insets(0, 0, 0, 10));
        textBar.setMinHeight(25);
        textBar.setMaxHeight(25);
        topBar.getChildren().add(textBar);

        String style = "-fx-background-color: transparent; "
             + "-fx-border-style: solid; "
             + "-fx-border-width: 0 0 2px 0; "
             + "-fx-border-color: #cfcfff;"
             + "-fx-padding: 5px 0 5px 0;"
             + "-fx-font-weight: bold;"
             + "-fx-text-fill: #cfcfff;"
             + "-fx-font-size: 14;";

        textBar.setStyle(style);
    }
}

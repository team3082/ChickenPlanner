package org.team3082.chicken_planner.UI.Main;

import org.team3082.chicken_planner.State.EventBus;
import org.team3082.chicken_planner.State.Events.PageSwitchEvent;

import javafx.stage.Stage;

public class UIManager {
    private Stage primaryStage;

    public UIManager(Stage primaryStage) {
        this.primaryStage = primaryStage;
        

        EventBus.register(event -> {
            switch (event) {
                case PageSwitchEvent pageSwitchEvent -> switchScene(pageSwitchEvent.getTargetPage());
                default -> {}
            }
        });
    }

    public void switchScene(Page page){

    }

    
    public void initUI() {
        
    }
}

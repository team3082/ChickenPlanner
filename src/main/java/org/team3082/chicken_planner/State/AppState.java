package org.team3082.chicken_planner.State;

import org.team3082.chicken_planner.Core.Model.Project;

public class AppState {
    private static AppState instance;
    
    private Project currentProject;

    private AppState() { 
    } 
    
    public static AppState getInstance() {
        if (instance == null){
            instance = new AppState();
        }

        return instance;
    }


    public Project getCurrentProject() { return currentProject; }
}

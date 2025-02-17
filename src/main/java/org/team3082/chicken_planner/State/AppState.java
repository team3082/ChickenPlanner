package org.team3082.chicken_planner.State;

import org.team3082.chicken_planner.Core.Model.Project;

public class AppState {
    private static AppState instance;
    
    private Project currentProject;
    private PathManager pathManager;
    private AutoShellManager autoShellManager;

    private AppState() { } // Private constructor for Singleton pattern

    public static AppState getInstance() {
        ; }

    public void loadProject(Project project) {
        this.currentProject = project;
        EventBus.fireEvent(new ProjectLoadedEvent(project));
    }

    public Project getCurrentProject() { return currentProject; }
}

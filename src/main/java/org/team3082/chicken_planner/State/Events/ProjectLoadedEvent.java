package org.team3082.chicken_planner.State.Events;

import org.team3082.chicken_planner.Core.Model.Project;

public class ProjectLoadedEvent implements Event {
    private final Project project;

    public ProjectLoadedEvent(Project project) {
        this.project = project;
    }

    public Project getProject() {
        return project;
    }
}


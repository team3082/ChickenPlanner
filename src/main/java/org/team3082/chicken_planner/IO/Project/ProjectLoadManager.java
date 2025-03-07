package org.team3082.chicken_planner.IO.Project;

import java.io.File;

import org.team3082.chicken_planner.Core.Model.Project;

public interface ProjectLoadManager {
    Project loadProject(File file);
    void saveProject(Project Project);
}
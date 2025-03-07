package org.team3082.chicken_planner.IO.Project;

import java.io.File;
import java.util.ArrayList;

import org.team3082.chicken_planner.Core.Model.AutoRoutine;
import org.team3082.chicken_planner.Core.Model.Project;

public class Team2025Loader implements ProjectLoadManager {

    @Override
    public Project loadProject(File file) {
        return new Project();
    }

    @Override
    public void saveProject(Project project) {
        ArrayList<AutoRoutine> autoRoutines = project.getRoutines();

        // for(AutoRoutine routine : autoRoutines){
        //     for(ManagedSpline<?> managedSpline : autoRoutines.getSplineList()){
        //         if (!CubicBezierCurve.class.isInstance(managedSpline.getSpline())) {
        //             throw new IllegalArgumentException("2025 Loader does not support non-BezierCurve splines.");
        //     }
        // }
    }
}
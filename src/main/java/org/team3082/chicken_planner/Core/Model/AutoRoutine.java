package org.team3082.chicken_planner.Core.Model;

import org.team3082.chicken_planner.Core.Paths.Splines.SplineList;

public class AutoRoutine {
    private String name;
    private SplineList splines;

    public AutoRoutine(){
        splines = new SplineList();
    }
}


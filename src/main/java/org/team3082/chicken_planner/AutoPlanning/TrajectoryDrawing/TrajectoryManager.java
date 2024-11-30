package org.team3082.chicken_planner.AutoPlanning.TrajectoryDrawing;

import org.team3082.chicken_planner.ChickenPlannerApplication;
import org.team3082.chicken_planner.AutoPlanning.TrajectoryDrawing.DrawingManagers.RobotDrawingManager;
import org.team3082.chicken_planner.AutoPlanning.TrajectoryDrawing.DrawingManagers.SplineDrawingManager;
import org.team3082.chicken_planner.AutoPlanning.TrajectoryDrawing.InputManagment.InputManager;

public class TrajectoryManager {
    private SplineDrawingManager splineDrawingManager;
    private RobotDrawingManager robotDrawingManager;
    private InputManager inputManager;
    
    public TrajectoryManager(ChickenPlannerApplication application){
        this.splineDrawingManager = new SplineDrawingManager(application);
        this.robotDrawingManager = new RobotDrawingManager(application);
        inputManager = new InputManager(application, this);
    }

    public SplineDrawingManager getSplineDrawingManager() {
        return splineDrawingManager;
    }
}

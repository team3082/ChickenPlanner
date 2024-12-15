package org.team3082.chicken_planner.AutoPlanning.AutoRoutine;

import java.util.ArrayList;

import org.team3082.chicken_planner.FileManagment.AutoRoutineJSON;
import org.team3082.chicken_planner.MathUtils.BezierSpline;
import org.team3082.chicken_planner.MathUtils.CubicBezierCurve;
import org.team3082.chicken_planner.MathUtils.ExampleCurves;
import org.team3082.chicken_planner.MathUtils.Vector2;

public class AutoRoutine {
    
    // Name of the routine, likely used for identification purposes.
    private String routineName;
    
    // Bezier spline representing the trajectory for the auto routine.
    private BezierSpline spline;
    
    // List of action points that might be executed during the routine.
    private ArrayList<ActionPoint> actionPoints;

    // Default constructor initializes the spline with a default curve and an empty list of action points.
    public AutoRoutine(){
        spline = ExampleCurves.getDefualtSpline(); // Assigning a default spline from ExampleCurves.
        actionPoints = new ArrayList<>(); // Initializing an empty list for action points.
    }

    public AutoRoutine(BezierSpline spline, ArrayList<ActionPoint> actionPoints){
        spline = ExampleCurves.getDefualtSpline(); // Assigning a default spline from ExampleCurves.
        actionPoints = new ArrayList<>(); // Initializing an empty list for action points.
    }
    
    public AutoRoutine(AutoRoutineJSON autoRoutineJSON){
        this.routineName = autoRoutineJSON.routineName;
        this.actionPoints = new ArrayList<>(); 
        for(int index = 0; index<autoRoutineJSON.actionPoints.length; index++){
            actionPoints.add(autoRoutineJSON.actionPoints[index]);
        }
        System.out.println(actionPoints.size());
        Vector2[] controlPoints = autoRoutineJSON.controlPoints;
        this.spline = new BezierSpline(new CubicBezierCurve(controlPoints[0], controlPoints[1], controlPoints[2], controlPoints[3]));
        for(int index = 3; index<controlPoints.length-1; index+=3){
            System.out.println(controlPoints.length + " "+ index);
            spline.addCurve(new CubicBezierCurve(controlPoints[index], controlPoints[index+1], controlPoints[index+2], controlPoints[index+3]), spline.getCurveCount());
        }
    }

     // Clone constructor for deep copying the AutoRoutine object
    public AutoRoutine(AutoRoutine other) {
        // Perform a deep copy to ensure no shared references
        this.routineName = other.routineName;
        this.spline = new BezierSpline(other.getSpline());
        this.actionPoints = new ArrayList<>(); // Create a new ArrayList
        for (int i = 0; i < other.actionPoints.size(); i++) {
            this.actionPoints.add(new ActionPoint(other.getActionPoints().get(i)));
        }
    }

    // Getter for the BezierSpline object, which represents the trajectory of the routine.
    public BezierSpline getSpline(){
        return spline;
    }

    // Getter for the list of action points in the routine.
    public ArrayList<ActionPoint> getActionPoints(){
        return actionPoints;
    }

    // Getter for the name of the routine.
    public String getRoutineName(){
        return routineName;
    }

    public void setRoutineName(String name) {
        routineName = name;
    }
}

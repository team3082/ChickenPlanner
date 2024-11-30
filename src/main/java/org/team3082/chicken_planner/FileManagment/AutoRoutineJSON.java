package org.team3082.chicken_planner.FileManagment;

import org.team3082.chicken_planner.AutoPlanning.AutoRoutine.ActionPoint;
import org.team3082.chicken_planner.AutoPlanning.AutoRoutine.AutoRoutine;
import org.team3082.chicken_planner.MathUtils.Vector2;

import com.google.gson.Gson;

/**
 * Utility class for converting AutoRoutine objects to JSON.
 */
@SuppressWarnings("unused")
public class AutoRoutineJSON {

    public String routineName;          // Name of the routine
    public Vector2[] controlPoints;     // Array of control points in the routine's spline
    public ActionPoint[] actionPoints;  // Array of action points in the routine

    /**
     * Private constructor to initialize the JSON representation of an AutoRoutine.
     * This constructor is only used internally by the routineToJson method.
     *
     * @param autoRoutine The AutoRoutine to be converted into JSON format.
     */
    public AutoRoutineJSON(AutoRoutine autoRoutine) {
        this.routineName = autoRoutine.getRoutineName();
        this.controlPoints = autoRoutine.getSpline().getAllControlPoints();
        this.actionPoints = autoRoutine.getActionPoints().toArray(new ActionPoint[autoRoutine.getActionPoints().size()]);
    }

    /**
     * Converts an AutoRoutine object to its JSON representation.
     *
     * @param autoRoutine The AutoRoutine to convert.
     * @return A JSON string representing the AutoRoutine.
     */
    public static String routineToJson(AutoRoutine autoRoutine) {
        Gson jsonManager = new Gson();
        return jsonManager.toJson(new AutoRoutineJSON(autoRoutine));
    }
}

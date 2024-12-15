package org.team3082.chicken_planner.AppState;

import java.util.ArrayList;
import java.util.List;

import org.team3082.chicken_planner.AutoPlanning.AutoRoutine.AutoRoutine;

import javafx.scene.Node;

/**
 * Represents the state of the application, including the current project path
 * and the drawing mode (control point or action point).
 */
public class AppState {
    private String projectPath = "NoProjectPath"; // The path to the current project
    private DrawingState drawingState; // The current drawing state

    private AutoRoutine currentRoutine; //The current routine
    private ArrayList<AutoRoutine> loadedRoutines;
    private boolean routineSaved;

    public boolean getRoutineSaved(){
        return routineSaved;
    }

    public void setRoutineSaved(boolean saved){
        routineSaved = saved;
    }

    /**
     * Enum to represent the current drawing mode.
     */
    public enum DrawingState {
        CONTROL_POINT, // Drawing mode for control points
        ACTION_POINT   // Drawing mode for action points
    }

    /**
     * Default constructor initializes the application state
     * with the drawing mode set to CONTROL_POINT.
     */
    public AppState() {
        this.drawingState = DrawingState.CONTROL_POINT;
        this.currentRoutine = new AutoRoutine();
        this.loadedRoutines = new ArrayList<>();
        routineSaved = true;
    }

    /**
     * Gets the current project path.
     *
     * @return The current project path as a String.
     */
    public String getProjectPath() {
        return projectPath;
    }

    /**
     * Sets the project path.
     *
     * @param projectPath The new project path as a String.
     */
    public void setProjectPath(String projectPath) {
        this.projectPath = projectPath;
    }

    /**
     * Gets the current drawing state.
     *
     * @return The current DrawingState.
     */
    public DrawingState getDrawingState() {
        return drawingState;
    }

    
    /**
     * Gets the current auto routine.
     *
     * @return The currentRoutine.
     */
    public AutoRoutine getCurrentAutoRoutine() {
        return currentRoutine;
    }

    
    public void setCurrentAutoRoutine(AutoRoutine autoRoutine){
        currentRoutine = autoRoutine;
    }   


    /**
     * Sets the current drawing state.
     *
     * @param drawingState The new DrawingState.
     */
    public void setDrawingState(DrawingState drawingState) {
        this.drawingState = drawingState;
    }

    public void setLoadedRoutines(ArrayList<AutoRoutine> autoRoutines) {
        loadedRoutines = autoRoutines;
    }

    public ArrayList<AutoRoutine> getLoadedRoutines() {
        return loadedRoutines;
    }
}


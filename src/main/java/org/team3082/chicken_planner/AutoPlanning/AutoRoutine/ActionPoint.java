package org.team3082.chicken_planner.AutoPlanning.AutoRoutine;

public class ActionPoint {
    private double t;               // Time at which the action point occurs
    private double rotationRadians;  // Rotation in radians at this action point
    private boolean isStopped;       // Indicates whether the action point represents a stop

    // Constructor with all parameters
    public ActionPoint(double t, double rotationRadians, boolean isStopped) {
        this.t = t;
        this.rotationRadians = rotationRadians;
        this.isStopped = isStopped;
    }

    // Constructor with t and rotation (default: isStopped = false)
    public ActionPoint(double t, double rotationRadians) {
        this(t, rotationRadians, false);
    }

    // Constructor with t and stopped state (default: rotationRadians = -1)
    public ActionPoint(double t, boolean isStopped) {
        this(t, -1, isStopped);
    }

    // Constructor with only t (defaults: rotationRadians = -1, isStopped = false)
    public ActionPoint(double t) {
        this(t, -1, true);
    }

    public ActionPoint(ActionPoint other) {
        this.t = other.t;
        this.rotationRadians = other.rotationRadians;
        this.isStopped = other.isStopped;
    }
    

    // Getter and Setter Methods
    public double getT() {
        return t;
    }

    public void setT(double t) {
        this.t = t;
    }

    public double getRotationRadians() {
        return rotationRadians;
    }

    public boolean isStopped() {
        return isStopped;
    }
}

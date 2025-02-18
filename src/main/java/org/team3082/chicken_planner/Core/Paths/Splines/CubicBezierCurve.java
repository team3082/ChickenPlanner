package org.team3082.chicken_planner.Core.Paths.Splines;

import javafx.scene.effect.Light.Point;

public class CubicBezierCurve implements Spline {
    private Point[] controlPoints; // Array to hold the four control points

    // Constructor to initialize the control points
    public CubicBezierCurve(Point a, Point b, Point c, Point d) {
        this.controlPoints = new Point[]{a, b, c, d};
    }

    @Override
    public int getNumberOfControlPoints() {
        return controlPoints.length;
    }

    @Override
    public void updateControlPoint(int index, Point newPoint) {
        if (index < 0 || index >= controlPoints.length) {
            throw new IllegalArgumentException("Invalid control point index");
        }
        controlPoints[index] = newPoint;
    }
}

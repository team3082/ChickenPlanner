package org.team3082.chicken_planner.Core.Paths.Splines.BezierCurve;

import org.team3082.chicken_planner.Core.Paths.Splines.Spline;

import javafx.scene.effect.Light.Point;

public class BezierCurve implements Spline {
    private Point[] controlPoints;
    private Point[] cachedPoints;
    
    public BezierCurve(Point a, Point b, Point c, Point d) {
        this.controlPoints = new Point[]{a, b, c, d};
    }

    @Override
    public int getNumberOfControlPoints() {
        return 4;
    }

    @Override
    public void updateControlPoint(int index, Point newPoint) {
        if (index < 0 || index >= controlPoints.length) {
            throw new IllegalArgumentException("Invalid control point index");
        }
        controlPoints[index] = newPoint;
    }
}

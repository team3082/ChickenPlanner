package org.team3082.chicken_planner.Core.Paths.Splines.BezierCurve;

import org.team3082.chicken_planner.Core.Paths.Splines.Spline;
import org.team3082.chicken_planner.Utils.Point;

public class CubicBezierCurve implements Spline {
    private Point[] controlPoints;

    private static final int resolution = 100;
    private Point[] cachedPoints;
    private double length;
    
    public CubicBezierCurve(Point a, Point b, Point c, Point d) {
        this.controlPoints = new Point[]{a, b, c, d};
        calculatePointsAndCurveLength();
    }

    @Override
    public int getNumberOfControlPoints() {
        return 4;
    }

    /**
     * Evaluates the Bezier curve at a given parameter t.
     *
     * @param t A parameter value between 0 and 1.
     * @return The point on the curve corresponding to the given t value.
     * @throws IllegalArgumentException if t is outside the range [0, 1].
     */
    public Point getPointAtT(double t) {
        if (t < 0 || t > 1) {
            throw new IllegalArgumentException("t must be between 0 and 1");
        }

        double x = (Math.pow(1 - t,3) * controlPoints[0].getX()) +
        (3 * Math.pow(1 - t,2) * t * controlPoints[1].getX()) +
        (3 * (1 - t) * Math.pow(t,2) *  controlPoints[2].getX()) +
        (Math.pow(t,3) * controlPoints[3].getX());

        double y = (Math.pow(1 - t,3) * controlPoints[0].getY()) +
        (3 * Math.pow(1 - t,2) * t * controlPoints[1].getY()) +
        (3 * (1 - t) * Math.pow(t,2) *  controlPoints[2].getY()) +
        (Math.pow(t,3) * controlPoints[3].getY());

        return new Point(x, y);
    }

    /**
     * Caches the points of the Bezier curve and finds the length
     *
     * @param resolution Number of steps to divide the curve into for length calculation.
     */
    private void calculatePointsAndCurveLength() {
        length = 0;
        cachedPoints = new Point[resolution];
        cachedPoints[0] = getPointAtT(0);

        for (int index = 1; index < resolution; index++) {
            double t = (double) index / (resolution - 1);
            cachedPoints[0] = getPointAtT(t);

            length += cachedPoints[index-1].distance(cachedPoints[index]);
        }
    }

    @Override
    public double getLength() {
        return length;

    }

    @Override
    public void updateControlPoint(int index, Point newPoint) {
        if(index < 0 || index > 4){
            throw new IllegalArgumentException("Invalid Index");
        }
        
        cachedPoints[index] = newPoint;
        calculatePointsAndCurveLength();
    }

    @Override
    public Point[] getCachedPoints() {
        return cachedPoints;
    }

}

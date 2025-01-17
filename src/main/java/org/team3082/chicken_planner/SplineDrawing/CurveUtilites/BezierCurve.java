package org.team3082.chicken_planner.SplineDrawing.CurveUtilites;


import org.team3082.chicken_planner.Constants;

/**
 * Represents a cubic Bezier curve defined by four control points.
 * This curve is computed based on the four control points using 
 * linear interpolation, providing methods to get points along 
 * the curve, the curve length, and other properties.
 */
public class BezierCurve {
    
    private Vector2 controlPointOne;
    private Vector2 controlPointTwo; 
    private Vector2 controlPointThree;
    private Vector2 controlPointFour;

    private static int numberOfPoints = Constants.DEFUALT_CURVE_POINTS;
    private double curveLength;
    private CurvePoint[] points;

    /**
     * Constructs a BezierCurve with the given control points.
     *
     * @param controlPointOne First control point.
     * @param controlPointTwo Second control point
     * @param controlPointThree Third control point
     * @param controlPointFour Fourth control point.
     */
    public BezierCurve(Vector2 controlPointOne, Vector2 controlPointTwo, Vector2 controlPointThree, Vector2 controlPointFour) {
        this.controlPointOne = controlPointOne;
        this.controlPointTwo = controlPointTwo;
        this.controlPointThree = controlPointThree;
        this.controlPointFour = controlPointFour;
        
        calculatePointsAndCurveLength(numberOfPoints);
    }

    public BezierCurve(BezierCurve other) {
        this(
            new Vector2(other.getControlPointOne()),
            new Vector2(other.getControlPointTwo()),
            new Vector2(other.getControlPointThree()),
            new Vector2(other.getControlPointFour())
        );
    }
    

    /**
     * Returns the precomputed length of the Bezier curve.
     *
     * @return The length of the curve.
     */
    public double getCurveLength() {
        return curveLength;
    }

    /**
     * Evaluates the Bezier curve at a given parameter t.
     *
     * @param t A parameter value between 0 and 1.
     * @return The point on the curve corresponding to the given t value.
     * @throws IllegalArgumentException if t is outside the range [0, 1].
     */
    public Vector2 getPointAtT(double t) {
        if (t < 0 || t > 1) {
            throw new IllegalArgumentException("t must be between 0 and 1");
        }

        Vector2 pointOne = Vector2.lerp(controlPointOne, controlPointTwo, t);
        Vector2 pointTwo = Vector2.lerp(controlPointTwo, controlPointThree, t);
        Vector2 pointThree = Vector2.lerp(controlPointThree, controlPointFour, t);

        pointOne = Vector2.lerp(pointOne, pointTwo, t);
        pointTwo = Vector2.lerp(pointTwo, pointThree, t);

        return Vector2.lerp(pointOne, pointTwo, t);
    }

    /**
     * Finds the nearest t value on the Bezier curve to a given point.
     *
     * @param point The point to find the closest t value for.
     * @param steps Number of segments to sample along the curve.
     * @return The t value corresponding to the nearest point on the curve.
     */
    public double getNearestT(Vector2 point, int steps) {
        double nearestT = 0.0;
        double closestDistance = Double.MAX_VALUE;

        for (int i = 0; i <= steps; i++) {
            double t = (double) i / steps;
            Vector2 bezierPoint = getPointAtT(t);
            double distance = point.distance(bezierPoint);
            if (distance < closestDistance) {
                closestDistance = distance;
                nearestT = t;
            }
        }

        return nearestT;
    }

    /**
     * Calculates the length of the Bezier curve using numerical approximation and saves points used.
     *
     * @param resolution Number of steps to divide the curve into for length calculation.
     */
    private void calculatePointsAndCurveLength(int resolution) {
        curveLength = 0;
        points = new CurvePoint[resolution];
        points[0] = new CurvePoint(this, 0.0);
        Vector2 previousPoint = points[0].getPosition();

        for (int index = 1; index < resolution; index++) {
            double t = (double) index / (resolution - 1);
            Vector2 currentPoint = getPointAtT(t);
            curveLength += previousPoint.distance(currentPoint);
            points[index] = new CurvePoint(currentPoint, t);
            previousPoint = currentPoint;
        }
    }

    /**
     * Gets the first control point.
     *
     * @return The first control point.
     */
    public Vector2 getControlPointOne() {
        return controlPointOne;
    }

    /**
     * Gets the second control point relative to the first control point.
     *
     * @return The actual position of the second control point.
     */
    public Vector2 getControlPointTwo() {
        return controlPointTwo;
    }

    /**
     * Gets the second control point relative to the first control point.
     *
     * @return The relative position of the second control point.
     */
    public Vector2 getRelativeControlPointTwo() {
        return controlPointTwo.subtract(controlPointOne);
    }

    /**
     * Gets the third control point relative to the fourth control point.
     *
     * @return The actual position of the third control point.
     */
    public Vector2 getControlPointThree() {
        return controlPointThree;
    }

    /**
     * Gets the third control point relative to the fourth control point.
     *
     * @return The relative position of the third control point.
     */
    public Vector2 getRelativeControlPointThree() {
        return controlPointThree.subtract(controlPointFour);
    }

    /**
     * Gets the fourth control point.
     *
     * @return The fourth control point.
     */
    public Vector2 getControlPointFour() {
        return controlPointFour;
    }

    /**
     * Gets all computed points on the curve.
     *
     * @return An array of points sampled along the curve.
     */
    public CurvePoint[] getPoints() {
        return points;
    }

    /**
     * Adjusts the second control point along with the first control point and recalculates the curve.
     *
     * @param controlPointOne The new first control point.
     */
    public void setControlPointOne(Vector2 controlPointOne) {
        this.controlPointTwo = this.controlPointTwo.add(this.controlPointOne.subtract(controlPointOne));
        this.controlPointOne = controlPointOne;
        calculatePointsAndCurveLength(numberOfPoints);
    }

    /**
     * Sets the second control and recalculates the curve.
     *
     * @param controlPointTwo The new second control point.
     */
    public void setControlPointTwo(Vector2 controlPointTwo) {
        this.controlPointTwo = controlPointTwo;
        calculatePointsAndCurveLength(numberOfPoints);
    }

    /**
     * Sets the third control point and recalculates the curve.
     *
     * @param controlPointThree The new third control point.
     */
    public void setControlPointThree(Vector2 controlPointThree) {
        this.controlPointThree = controlPointThree;
        calculatePointsAndCurveLength(numberOfPoints);
    }

    /**
     * Adjusts the third control point along with the fourth control point and recalculates the curve.
     *
     * @param controlPointFour The new fourth control point.
     */
    public void setControlPointFour(Vector2 controlPointFour) {
        this.controlPointThree = this.controlPointThree.add(this.controlPointFour.subtract(controlPointFour));
        this.controlPointFour = controlPointFour;
        calculatePointsAndCurveLength(numberOfPoints);
    }

    /**
     * Sets the first control point and recalculates the curve.
     *
     * @param controlPointOne The new first control point.
     */
    public void setPointOneOnly(Vector2 controlPointOne) {
        this.controlPointOne = controlPointOne;
        calculatePointsAndCurveLength(numberOfPoints);
    }

    /**
     * Sets the fourth control point and recalculates the curve.
     *
     * @param controlPointFour The new fourth control point.
     */
    public void setPointFourOnly(Vector2 controlPointFour) {
        this.controlPointFour = controlPointfour;
        calculatePointsAndCurveLength(numberOfPoints);
    }

    /**
     * Sets the number of points used to sample the curve.
     *
     * @param numberOfPoints The number of points for curve sampling.
     */
    public static void setNumberOfPoints(int numberOfPoints) {
        BezierCurve.numberOfPoints = numberOfPoints;
    }
}


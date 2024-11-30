package org.team3082.chicken_planner.MathUtils;

import java.util.ArrayList;

/**
 * Represents a spline composed of multiple cubic Bezier curves.
 * Provides methods to manipluate the spline and get points on it.
 */
public class BezierSpline {
    private final ArrayList<CubicBezierCurve> curveList;

    /**
     * Constructs a BezierSpline with an initial CubicBezierCurves.
     *
     * @param startingCurve The initial curve in the spline.
     * @throws IllegalArgumentException if startingCurve is null.
     */
    public BezierSpline(CubicBezierCurve... curves) {
        if (curves == null) {
            throw new IllegalArgumentException("Curve array cannot be null");
        }

        curveList = new ArrayList<>();
        curveList.add(curves[0]);

        for(int index = 1; index < curves.length; index++){
            addCurve(curves[index], index);
            System.out.println(curveList.size() + " " + curveList.get(index));
        }
    }

    /**
     * Gets the cubic Bezier curve at a specific index.
     *
     * @param index The index of the curve.
     * @return The curve at the specified index.
     * @throws IllegalArgumentException if the index is out of bounds.
     */
    public CubicBezierCurve getCurveAt(int index) {
        if (index < 0 || index >= curveList.size()) {
            throw new IllegalArgumentException("Curve index out of bounds. Valid range is 0 to " + (curveList.size() - 1));
        }
        return curveList.get(index);
    }

    /**
     * Finds the nearest cached t value
     * 
     * @param point A point in field coordinates
     * @return The nearest cached t value to point
     */
    public double findNearestT(Vector2 point) {
        double nearestT = 0.0;
        double closestDistance = Double.MAX_VALUE;

        for (int curveIndex = 0; curveIndex < curveList.size(); curveIndex++) {
            CubicBezierCurve cubicBezierCurve = curveList.get(curveIndex);
            CurvePoint[] points = cubicBezierCurve.getPoints();
            for(int pointIndex = 0; pointIndex < points.length; pointIndex++){
                CurvePoint curvePoint = points[pointIndex];
                double distance = curvePoint.getPosition().distance(point);
                if(distance < closestDistance){
                    nearestT = curvePoint.getT()+curveIndex;
                    closestDistance = distance;
                }
            }
        }

        return nearestT;
    } 
    

    /**
     * Adds a CubicBezierCurve to the spline and adjusts control points if necessary.
     *
     * @param curve The curve to add to the spline.
     * @param index The index of the curve to add, for instance
     * @throws IllegalArgumentException if the curve is null.
     */
    public void addCurve(CubicBezierCurve curve, int index) {
        if (curve == null) {
            throw new IllegalArgumentException("Curve cannot be null.");
        }
        if(index < 0 || index > curveList.size()){
            throw new IllegalArgumentException("Index out of range");
        }

        if(index == 0){
            curveList.add(0, curve);
            CubicBezierCurve rightCurve = curveList.get(1);
            if(!curve.getControlPointFour().equals(rightCurve.getControlPointOne())){
                curve.setControlPointFour(rightCurve.getControlPointOne());
            }
        } else if (index == curveList.size()){
            curveList.add(curve);
            CubicBezierCurve leftCurve = curveList.get(curveList.size()-2);
            if(!curve.getControlPointOne().equals(leftCurve.getControlPointFour())){
                curve.setControlPointOne(leftCurve.getControlPointFour());
            }
        } else {
            curveList.add(index, curve);
            CubicBezierCurve rightCurve = curveList.get(index+1);
            if(!curve.getControlPointFour().equals(rightCurve.getControlPointOne())){
                curve.setControlPointFour(rightCurve.getControlPointOne());
            }
            CubicBezierCurve leftCurve = curveList.get(index-1);
            if(!curve.getControlPointOne().equals(leftCurve.getControlPointFour())){
                curve.setControlPointOne(leftCurve.getControlPointFour());
            }
        }
        
    }

    /**
     * Evaluates the Bezier spline at a given parameter t.
     *
     * @param t A parameter value between 0 and 1.
     * @return The point on the curve corresponding to the given parameter t.
     * @throws IllegalArgumentException if t is outside the range [0, 1].
     */
    public Vector2 getPoint(double t) {
        if (t < 0 || t > curveList.size()) {
            throw new IllegalArgumentException("Parameter t must be between 0 and 1.");
        }

        int curveIndex = (int)t;

        return curveList.get(curveIndex).getPointAtT(t-curveIndex);
    }

    /**
     * Returns all control points in the spline.
     *
     * @return A Vector2 array of control points in the spline.
     */
    public Vector2[] getAllControlPoints() {
        // Array size is 3 times number of curves + 1 for the final control point
        Vector2[] controlPoints = new Vector2[curveList.size() * 3 + 1];
        
        // Populate control points array by iterating over each curve
        for (int index = 0; index < curveList.size(); index++) {
            controlPoints[index * 3] = curveList.get(index).getControlPointOne();
            controlPoints[index * 3 + 1] = curveList.get(index).getControlPointTwo();
            controlPoints[index * 3 + 2] = curveList.get(index).getControlPointThree();
            
            // Add the final control point of the spline on the last curve
            if (index == curveList.size() - 1) {
                controlPoints[index * 3 + 3] = curveList.get(index).getControlPointFour();
            }
        }
        return controlPoints;
    }

    /**
     * Sets a specific control point in the spline and adjusts related control points if necessary.
     *
     * @param index The index of the control point within the entire spline.
     * @param point The new position for the control point.
     * @throws IllegalArgumentException if the index is out of range or if the point is null.
     */
    public void setControlPoint(int index, Vector2 point) {
        if (point == null) {
            throw new IllegalArgumentException("Control point cannot be null.");
        }

        // Validate that the index is within the bounds of all possible control points in the spline
        if (index < 0 || index > (curveList.size() * 3)) {
            throw new IllegalArgumentException("Control point index out of bounds.");
        }

        // Case 1: Last control point in the spline, which is the fourth point of the last curve
        if (index == (curveList.size() * 3)) {
            curveList.get(curveList.size() - 1).setControlPointFour(point);
            return;
        }

        // Case 2: First control point in the spline, which is the first point of the first curve
        if (index == 0) {
            curveList.get(0).setControlPointOne(point);
            return;
        }

        // Identify the curve that the specified control point belongs to
        int curveIndex = index / 3;  // Determine the curve number
        boolean indexAtSharedPoint = index % 3 == 0;  // Check if this is a shared point between two curves

        if (indexAtSharedPoint) {
            // Case 3: Shared control point between two curves.
            // Update both the last point of the previous curve and the first point of the current curve
            curveList.get(curveIndex).setControlPointOne(point);
            curveList.get(curveIndex - 1).setControlPointFour(point);
            return;
        }

        // Case 4: The specified control point is the second or third point of the curve, with no sharing
        if (index % 3 == 1) {
            // Set the second control point of the curve
            curveList.get(curveIndex).setControlPointTwo(point);
            return;
        }

        curveList.get(curveIndex).setControlPointThree(point);
    }

    /**
     * Removes a curve from the spline, adjusting neighboring curves if necessary.
     *
     * @param curveIndex The index of the curve to remove based on shared control points
     * @throws IllegalArgumentException if attempting to remove the only curve or if the index is out of bounds.
     */
    public void removeCurve(int curveIndex) {
        if (curveList.size() == 1) {
            throw new IllegalArgumentException("Cannot delete a curve in a spline with only one curve");
        }
        if (curveIndex < 0 || curveIndex > curveList.size()+1) {
            throw new IllegalArgumentException("Curve index out of bounds.");
        }

        if(curveIndex == curveList.size()){
            curveList.removeLast();
        } else if(curveIndex == 0){
            curveList.removeFirst();
        } else {
            CubicBezierCurve curveLeft = curveList.remove(curveIndex-1);
            CubicBezierCurve curveRight = curveList.remove(curveIndex-1);
            CubicBezierCurve mergedCurve = new CubicBezierCurve(
                curveLeft.getControlPointOne(), 
                curveLeft.getControlPointTwo(), 
                curveRight.getControlPointThree(), 
                curveRight.getControlPointFour()
            );
    
            curveList.add(curveIndex-1, mergedCurve);
        }
    }

    /**
     * Returns the number of curves in the spline.
     *
     * @return The number of curves in the spline.
     */
    public int getCurveCount() {
        return curveList.size();
    }

    /**
     * Gets the curve array.
     *
     * @return The ArrayList of CubicBezierCurve in the spline.
     */
    public ArrayList<CubicBezierCurve> getCurveArray() {
        return curveList;
    }
}

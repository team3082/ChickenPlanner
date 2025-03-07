package org.team3082.chicken_planner.Core.Paths.Splines;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.team3082.chicken_planner.Core.Paths.Splines.BezierCurve.BezierCurveManipulator;
import org.team3082.chicken_planner.Core.Paths.Splines.BezierCurve.BezierDrawer;
import org.team3082.chicken_planner.Core.Paths.Splines.BezierCurve.CubicBezierCurve;
import org.team3082.chicken_planner.Utils.Point;

import javafx.scene.canvas.GraphicsContext;

/**
 * A class representing a list of managed splines. This class provides methods to add, remove,
 * retrieve, and manipulate splines, as well as draw them and retrieve their cached points.
 */
public class SplineList {
    private final List<ManagedSpline<?>> splineList;

    /**
     * Constructs an empty SplineList.
     */
    public SplineList() {
        this.splineList = new ArrayList<>();
        CubicBezierCurve curve = new CubicBezierCurve(new Point(0, 0), new Point(0, 0), new Point(0, 0), null);

        BezierCurveManipulator curveManipulator = new BezierCurveManipulator();
        BezierDrawer bezierDrawer = new BezierDrawer();
        ManagedSpline<CubicBezierCurve> spline = new ManagedSpline<>(curve, curveManipulator, bezierDrawer);
        
        splineList.add(spline);
    }

    /**
     * Adds a managed spline at the specified index. The surrounding splines are automatically
     * rebound to maintain continuity.
     *
     * @param index         the index at which the spline should be added.
     * @param managedSpline the managed spline to add.
     * @throws IndexOutOfBoundsException if the index is out of bounds.
     */
    public void addSplineAt(int index, ManagedSpline<Spline> managedSpline) {
        if (index < 0 || index > splineList.size()) {
            throw new IndexOutOfBoundsException("Index out of bounds for adding spline.");
        }

        if (index > 0) {
            splineList.get(index - 1).boundToRightSpline(managedSpline.getSpline());
        }

        if (index < splineList.size()) {
            managedSpline.boundToRightSpline(splineList.get(index).getSpline());
        }

        splineList.add(index, managedSpline);
    }

    /**
     * Removes the managed spline at the specified index. The surrounding splines are automatically
     * rebound to maintain continuity.
     *
     * @param index the index of the spline to remove.
     * @throws IndexOutOfBoundsException if the index is out of bounds.
     */
    public void removeSplineAt(int index) {
        if (index < 0 || index >= splineList.size()) {
            throw new IndexOutOfBoundsException("Index out of bounds for removing spline.");
        }

        if (index > 0 && index < splineList.size() - 1) {
            splineList.get(index - 1).boundToRightSpline(splineList.get(index + 1).getSpline());
        }

        splineList.remove(index);
    }

    /**
     * Retrieves the managed spline at the specified index.
     *
     * @param index the index of the spline to retrieve.
     * @return the managed spline at the specified index.
     * @throws IndexOutOfBoundsException if the index is out of bounds.
     */
    public ManagedSpline<?> getSplineAt(int index) {
        if (index < 0 || index >= splineList.size()) {
            throw new IndexOutOfBoundsException("Index out of bounds for getting spline.");
        }

        return splineList.get(index);
    }

    /**
     * Returns the number of managed splines in the list.
     *
     * @return the number of managed splines.
     */
    public int size() {
        return splineList.size();
    }

    /**
     * Clears all managed splines from the list.
     */
    public void clear() {
        splineList.clear();
    }

    /**
     * Draws all managed splines using the provided GraphicsContext.
     *
     * @param gc the GraphicsContext used to draw the splines.
     */
    public void draw(GraphicsContext gc) {
        for (ManagedSpline<?> managedSpline : splineList) {
            managedSpline.draw(gc);
        }
    }

    /**
     * Retrieves all cached points from all managed splines in the list.
     *
     * @return a list of all cached points.
     */
    public ArrayList<Point> getAllCachedPoints() {
        ArrayList<Point> cachedPoints = new ArrayList<>();
        for (ManagedSpline<?> managedSpline : splineList) {
            Point[] points = managedSpline.getCachedPoints();
            cachedPoints.addAll(Arrays.asList(points));
        }
        return cachedPoints;
    }

    /**
     * Retrieves all underlying splines from the managed splines in the list.
     *
     * @return a list of all underlying splines.
     */
    public ArrayList<Spline> getSplines() {
        ArrayList<Spline> splines = new ArrayList<>();
        for (ManagedSpline<?> managedSpline : splineList) {
            splines.add(managedSpline.getSpline());
        }
        return splines;
    }
}
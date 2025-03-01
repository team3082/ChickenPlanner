package org.team3082.chicken_planner.Core.Paths.Splines;

import javafx.scene.canvas.GraphicsContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.team3082.chicken_planner.Utils.Point;

public class SplineList {
    private final List<ManagedSpline<Spline>> splineList;

    public SplineList() {
        this.splineList = new ArrayList<>();
    }


    // Add a spline at a specific index
    public void addSplineAt(int index, ManagedSpline<Spline> managedSpline) {
        if (index < 0 || index > splineList.size()) {
            throw new IndexOutOfBoundsException("Index out of bounds for adding spline.");
        }

        if (index > 0) {
            splineList.get(index - 1).boundToRightSpline(managedSpline.getSpline());
        }

        if (index < splineList.size()) {
            managedSplinelkm.boundToRightSpline(splineList.get(index).getSpline());
        }

        splineList.add(index, managedSpline);
    }

    // Remove a spline at a given index and rebind surrounding splines
    public void removeSplineAt(int index) {
        if (index < 0 || index >= splineList.size()) {
            throw new IndexOutOfBoundsException("Index out of bounds for removing spline.");
        }
        if (index > 0 && index < splineList.size() - 1) {
            splineList.get(index - 1).getManipulator().boundToRightSpline(splineList.get(index + 1).getSpline());
        }
        splineList.remove(index);
    }

    // Get a spline at a given index
    public ManagedSpline<Spline> getSplineAt(int index) {
        if (index < 0 || index >= splineList.size()) {
            throw new IndexOutOfBoundsException("Index out of bounds for getting spline.");
        }
        return splineList.get(index);
    }

    // Get the number of splines
    public int size() {
        return splineList.size();
    }

    // Clear all splines
    public void clear() {
        splineList.clear();
    }

    // Get the full list of managed splines
    public List<ManagedSpline<Spline>> getAllSplines() {
        return new ArrayList<>(splineList);
    }

    // Draw all splines using their respective drawers
    public void draw(GraphicsContext gc) {
        for (ManagedSpline<Spline> managedSpline : splineList) {
            managedSpline.draw(gc);
        }
    }

    public ArrayList<Point>getAllCachedPoints() {
        ArrayList<Point> cachedPoints = new ArrayList<>();
        for (ManagedSpline<Spline> managedSpline : splineList) {
            Point[] points = managedSpline.getCachedPoints();
            cachedPoints.addAll(Arrays.asList(points));
        }
        return cachedPoints;
    }   
}

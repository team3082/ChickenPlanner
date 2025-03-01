package org.team3082.chicken_planner.Core.Paths.Splines;

import org.team3082.chicken_planner.Utils.Point;

public interface Spline {
    int getNumberOfControlPoints();
    double getLength();

    void updateControlPoint(int index, Point newPoint);

    Point[] getCachedPoints();
}

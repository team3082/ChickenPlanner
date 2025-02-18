package org.team3082.chicken_planner.Core.Paths.Splines;

import javafx.scene.effect.Light.Point;

public interface Spline {
    int getNumberOfControlPoints();
    void updateControlPoint(int index, Point newPoint);
}

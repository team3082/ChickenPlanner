package org.team3082.chicken_planner.Core.Paths.Splines;

import javafx.scene.effect.Light.Point;

public interface SplineManipulator<T extends Spline> {
    void moveControlPoint(T spline, int index, Point newPoint);
    void boundToRightSpline(Spline spline);
}

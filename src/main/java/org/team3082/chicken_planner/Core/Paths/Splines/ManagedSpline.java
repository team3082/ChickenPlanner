package org.team3082.chicken_planner.Core.Paths.Splines;

import org.team3082.chicken_planner.UI.Drawing.SplineDrawer;
import org.team3082.chicken_planner.Utils.Point;

import javafx.scene.canvas.GraphicsContext;

public class ManagedSpline<T extends Spline> {
    private final T spline;
    private final SplineManipulator<T> manipulator;
    private final SplineDrawer<T> drawer;

    public ManagedSpline(T spline, SplineManipulator<T> manipulator, SplineDrawer<T> drawer) {
        this.spline = spline;
        this.manipulator = manipulator;
        this.drawer = drawer;
    }

    public T getSpline() {
        return spline;
    }

    public Point[] getCachedPoints(){
        return spline.getCachedPoints();
    }

    public void draw(GraphicsContext gc) {
        drawer.draw(gc, spline);
    }

    public Object getManipulator() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getManipulator'");
    }

    public void boundToRightSpline(Spline spline2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'boundToRightSpline'");
    }
}

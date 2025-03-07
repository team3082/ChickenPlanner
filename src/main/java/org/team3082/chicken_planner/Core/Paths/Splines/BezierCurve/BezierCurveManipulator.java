package org.team3082.chicken_planner.Core.Paths.Splines.BezierCurve;

import org.team3082.chicken_planner.Core.Paths.Splines.Spline;
import org.team3082.chicken_planner.Core.Paths.Splines.SplineManipulator;
import org.team3082.chicken_planner.Utils.Point;

public class BezierCurveManipulator implements SplineManipulator<CubicBezierCurve> {

    public void moveControlPoint(CubicBezierCurve spline, int index, Point newPoint) {
        
    }

    @Override
    public void moveControlPoint(CubicBezierCurve spline, int index, javafx.scene.effect.Light.Point newPoint) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'moveControlPoint'");
    }

    @Override
    public void boundToRightSpline(Spline spline) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'boundToRightSpline'");
    }


}

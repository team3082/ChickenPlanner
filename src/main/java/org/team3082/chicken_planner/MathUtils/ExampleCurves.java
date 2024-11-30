package org.team3082.chicken_planner.MathUtils;

// The ExampleCurves class provides utility methods for generating predefined spline curves.
public class ExampleCurves {

    /**
     * Creates and returns a default BezierSpline consisting of two cubic Bezier curves.
     * This can be used as a sample or default spline in applications requiring path generation.
     *
     * @return a BezierSpline object composed of two cubic Bezier curves.
     */
    public static BezierSpline getDefualtSpline() {
        return new BezierSpline(
            // First cubic Bezier curve
            new CubicBezierCurve(
                new Vector2(0, 0), 
                new Vector2(2, 4), 
                new Vector2(4, 3), 
                new Vector2(6, 4)  
            ),
            // Second cubic Bezier curve
            new CubicBezierCurve(
                new Vector2(6, 4),  
                new Vector2(8.5, 5.5),
                new Vector2(9, 2),  
                new Vector2(12, 2)
            )
        );
    }
}

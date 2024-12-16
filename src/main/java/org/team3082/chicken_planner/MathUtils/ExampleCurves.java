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
                new Vector2(1.3374793698424616, 5.522308119011599), 
                new Vector2(3.3374793698424567, 7.522308119011596), 
                new Vector2(5.7835783945986545, 7.598220877458395), 
                new Vector2(7.783578394598649, 5.598220877458397)  
            )
        );
    }
}

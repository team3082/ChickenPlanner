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
                new Vector2(356.35695275701363,  250.42272923816984),
                new Vector2(405.9550521104958, 165.9768734967435), 
                new Vector2(447.46650483025803, 315.06748777126177), 
                new Vector2(513.2378974511801, 213.7324608815501)  
            )
        );
    }
}

package org.team3082.chicken_planner.MathUtils;

/**
 * Represents a point on a curve with an associated parameter t.
 */
public class CurvePoint {

    // The position of the point on the curve
    private final Vector2 position;

    // The parameter value t that corresponds to this point on the curve
    private final double t;

    /**
     * Constructs a CurvePoint from a cubic Bezier curve and a parameter t.
     *
     * @param spline The cubic Bezier curve from which the point is derived.
     * @param t      The parameter value (0 <= t <= 1) that determines the position on the curve.
     * @throws IllegalArgumentException if t is outside the range [0, 1].
     */
    public CurvePoint(CubicBezierCurve spline, double t) {
        if (t < 0 || t > 1) {
            throw new IllegalArgumentException("t must be between 0 and 1");
        }

        this.position = new Vector2(spline.getPointAtT(t));
        this.t = t;
    }

    /**
     * Constructs a CurvePoint from a given position and parameter t.
     *
     * @param point The position of the point.
     * @param t     The parameter value (typically associated with a curve).
     * @throws IllegalArgumentException if t is outside the range [0, 1].
     */
    public CurvePoint(Vector2 point, double t) {
        if (t < 0 || t > 1) {
            throw new IllegalArgumentException("t must be between 0 and 1");
        }
        
        this.position = point;
        this.t = t;
    }

    /**
     * Returns the position of the point.
     *
     * @return The position as a Vector2.
     */
    public Vector2 getPosition() {
        return position;
    }

    /**
     * Returns the parameter value t associated with this point.
     *
     * @return The parameter value t.
     */
    public double getT() {
        return t;
    }
}

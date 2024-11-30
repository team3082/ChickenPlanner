package frc.robot.MathUtils;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.math.trajectory.Trajectory.State;

/**
 * Represents a cubic Bezier curve defined by four control points.
 * This curve is computed based on the four control points using 
 * linear interpolation, providing methods to get points along 
 * the curve, the curve length, and other properties.
 */
public class CubicBezierCurve {

    private Vector2 controlPointOne;
    private Vector2 controlPointTwo; // Relative to controlPointOne
    private Vector2 controlPointThree; // Relative to controlPointFour
    private Vector2 controlPointFour;

    /**
     * Constructs a CubicBezierCurve with the given control points.
     *
     * @param controlPointOne First control point.
     * @param controlPointTwo Second control point
     * @param controlPointThree Third control point
     * @param controlPointFour Fourth control point.
     */
    public CubicBezierCurve(Vector2 controlPointOne, Vector2 controlPointTwo, Vector2 controlPointThree, Vector2 controlPointFour) {
        this.controlPointOne = controlPointOne;
        this.controlPointTwo = controlPointTwo.subtract(controlPointOne);
        this.controlPointThree = controlPointThree.subtract(controlPointFour);
        this.controlPointFour = controlPointFour;
    }

    /**
     * Evaluates the Bezier curve at a given parameter t.
     *
     * @param t A parameter value between 0 and 1.
     * @return The point on the curve corresponding to the given t value.
     * @throws IllegalArgumentException if t is outside the range [0, 1].
     */
    public Vector2 getPointAtT(double t) {
        if (t < 0 || t > 1) {
            throw new IllegalArgumentException("t must be between 0 and 1");
        }

        Vector2 realControlTwo = controlPointTwo.add(controlPointOne);
        Vector2 realControlThree = controlPointThree.add(controlPointFour);

        Vector2 pointOne = Vector2.lerp(controlPointOne, realControlTwo, t);
        Vector2 pointTwo = Vector2.lerp(realControlTwo, realControlThree, t);
        Vector2 pointThree = Vector2.lerp(realControlThree, controlPointFour, t);

        pointOne = Vector2.lerp(pointOne, pointTwo, t);
        pointTwo = Vector2.lerp(pointTwo, pointThree, t);

        return Vector2.lerp(pointOne, pointTwo, t);
    }

    /**
     * Gets the velocity vector at a given t value.
     */
    public Vector2 getVelocityAtT(double t) {
        Vector2 realControlTwo = controlPointTwo.add(controlPointOne);
        Vector2 realControlThree = controlPointThree.add(controlPointFour);

        Vector2 term1 = realControlTwo.subtract(controlPointOne).multiply(3 * Math.pow(1 - t, 2));
        Vector2 term2 = realControlThree.subtract(realControlTwo).multiply(6 * (1 - t) * t);
        Vector2 term3 = controlPointFour.subtract(realControlThree).multiply(3 * t * t);
        return term1.add(term2).add(term3);
    }

    /**
     * Gets the acceleration vector at a given t value.
     */
    public Vector2 getAccelerationAtT(double t) {
        Vector2 realControlTwo = controlPointTwo.add(controlPointOne);
        Vector2 realControlThree = controlPointThree.add(controlPointFour);

        Vector2 term1 = realControlTwo.subtract(controlPointOne).multiply(6 * (1 - t));
        Vector2 term2 = realControlThree.subtract(realControlTwo).multiply(6 * t);
        return term1.add(term2);
    }

    /**
     * Gets the curvature at a given t value.
     */
    public double getCurvatureAtT(double t) {
        Vector2 velocity = getVelocityAtT(t);
        Vector2 acceleration = getAccelerationAtT(t);

        double crossProduct = velocity.getX() * acceleration.getY() - velocity.getY() * acceleration.getX();
        double velocityMagnitude = velocity.magnitude();
        return Math.abs(crossProduct) / Math.pow(velocityMagnitude, 3);
    }

    /**
     * Gets the rotation (heading) at a given t value.
     */
    public Rotation2d getRotationAtT(double t) {
        Vector2 velocity = getVelocityAtT(t);
        return new Rotation2d(Math.atan2(velocity.getY(), velocity.getX()));
    }

    /**
     * Generates a trajectory with velocity, acceleration, curvature, and rotation.
     */
    public Trajectory getTrajectory(int points) {
        TrajectoryConfig config = new TrajectoryConfig(2.0, 2.0);
        List<Pose2d> poses = new ArrayList<>();

        for (int i = 0; i <= points; i++) {
            double t = (double) i / points; // Normalize t between 0 and 1.
            Vector2 point = getPointAtT(t);
            Rotation2d rotation = getRotationAtT(t);

            poses.add(new Pose2d(point.getX(), point.getY(), rotation));
        }

        return TrajectoryGenerator.generateTrajectory(poses, config);
    }
}

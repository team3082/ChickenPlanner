// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

import javax.swing.Action;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class ChickenPlannerLib {
    private static final int POINTS_PER_TRAJECTORY = 100;

    public static SequentialCommandGroup followChickenPathCommand(
        String trajectoryName,
        TrajectoryConfig trajectoryConfig,
        RamseteController controller,
        Supplier<Pose2d> poseSupplier,
        Consumer<Pose2d> resetOdometry,
        BiConsumer<Double, Double> arcadeOutput,
        Command... actionPointCommands) {

        String trajectoryPath = Filesystem.getDeployDirectory() + "/ChickenPlanner/" + trajectoryName + ".json";
        List<CubicBezierCurve> bezierCurves;
        List<Double> actionPointsT;

        try {
            // Parse the Bezier curves and action point times from the path
            bezierCurves = getBezierCurves(trajectoryPath);
            actionPointsT = getStopPoints(trajectoryPath);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Trajectory Path: " + trajectoryPath + " does not exist");
            return null;
        }

       // Prepare the list of trajectories by segmenting the Bezier curves based on the action points
        List<Trajectory> trajectories = new ArrayList<>();
        if (actionPointsT.isEmpty()) {
            // If no action points, just create a single trajectory covering all curves
            trajectories.add(getTrajectoryInRange(bezierCurves, trajectoryConfig, 0, bezierCurves.size()));
        } else {
            // Create trajectories for each segment between action points
            for (int index = 0; index <= actionPointsT.size(); index++) {
                double startT = (index == 0) ? 0 : actionPointsT.get(index - 1);
                double endT = (index == actionPointsT.size()) ? bezierCurves.size() : actionPointsT.get(index);

                // Add the trajectory for this segment
                trajectories.add(getTrajectoryInRange(bezierCurves, trajectoryConfig, startT, endT));
            }
        }

        // Create a SequentialCommandGroup to hold all the commands
        SequentialCommandGroup commandGroup = new SequentialCommandGroup();
        commandGroup.addCommands(new InstantCommand(()-> resetOdometry.accept(trajectories.get(0).getInitialPose())));

        // Loop through the created trajectories and add RamseteCommand along with any action commands
        for (int i = 0; i < trajectories.size(); i++) {
            Trajectory trajectory = trajectories.get(i);
            System.out.println(trajectory);
            // Create a RamseteCommand for the current trajectory
            FollowTrajectoryCommandRamsete ramseteCommand = new FollowTrajectoryCommandRamsete(
                trajectory,
                poseSupplier,
                arcadeOutput,
                controller,
                true
            );

            // Add the RamseteCommand and corresponding action point command (if available)
            if (i < actionPointCommands.length) {
                commandGroup.addCommands(ramseteCommand, actionPointCommands[i]);
            } else {
                commandGroup.addCommands(ramseteCommand);
            }
        }

        return commandGroup; // Return the created command group
    }


    private static Trajectory getTrajectoryInRange(List<CubicBezierCurve> bezierCurves, TrajectoryConfig config, double minT, double maxT) {
        // Validate that minT and maxT are within a valid range
        if (minT < 0 || maxT > bezierCurves.size() || minT >= maxT) {
            throw new IllegalArgumentException("Invalid range: minT = " + minT + ", maxT = " + maxT);
        }
    
        // Calculate tChange (make sure it's positive and valid)
        double tChange = (maxT - minT) / (double) POINTS_PER_TRAJECTORY;
        if (tChange <= 0) {
            throw new IllegalArgumentException("tChange must be positive: " + tChange);
        }
    
        List<Pose2d> poses = new ArrayList<>();
    
        // Iterate over the t range, ensuring curveIndex is within bounds
        for (double t = minT; t <= maxT; t += tChange) {
            int curveIndex = (int) Math.floor(t); // Ensure we're using the floor value of t
    
            if (curveIndex < 0 || curveIndex >= bezierCurves.size()) {
                throw new IndexOutOfBoundsException("curveIndex out of bounds: " + curveIndex);
            }
    
            CubicBezierCurve curve = bezierCurves.get(curveIndex);
            double localT = t % 1; // Ensure the local t is within [0, 1]
    
            Vector2 point = curve.getPointAtT(localT);
            Rotation2d rotation = curve.getRotationAtT(localT);
    
            poses.add(new Pose2d(point.getX(), point.getY(), rotation));
        }
    
        // Generate and return the trajectory from the collected poses
        return TrajectoryGenerator.generateTrajectory(poses, config);
    }
    
    

  private static List<Double> getStopPoints(String trajectoryPath) throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    JsonNode root = mapper.readTree(new File(trajectoryPath));

    JsonNode actionPoints = root.get("actionPoints");
    List<Double> actionPointsT = new ArrayList<>();

    for (JsonNode actionPoint : actionPoints) {
      actionPointsT.add(actionPoint.get("t").asDouble());
    }
    Collections.sort(actionPointsT);
    return actionPointsT;
  }

  private static List<CubicBezierCurve> getBezierCurves(String trajectoryPath) throws IOException{
    ObjectMapper mapper = new ObjectMapper();
    JsonNode root = mapper.readTree(new File(trajectoryPath));
    
    JsonNode controlPoints = root.get("controlPoints");

    int numCurves = (controlPoints.size() - 1) / 3;
    List<CubicBezierCurve> bezierCurves = new ArrayList<>();
    for (int i = 0; i < numCurves; i++) {
        JsonNode a = controlPoints.get((i * 3));
        JsonNode b = controlPoints.get((i * 3) + 1);
        JsonNode c = controlPoints.get((i * 3) + 2);
        JsonNode d = controlPoints.get((i * 3) + 3);

        bezierCurves.add(new CubicBezierCurve (
            new Vector2(a.get("x").asDouble(), a.get("y").asDouble()),
            new Vector2(b.get("x").asDouble(), b.get("y").asDouble()),
            new Vector2(c.get("x").asDouble(), c.get("y").asDouble()),
            new Vector2(d.get("x").asDouble(), d.get("y").asDouble())
        ));
    }

    return bezierCurves;
  }

  /**
   * Represents a cubic Bezier curve defined by four control points.
   * This curve is computed based on the four control points using 
   * linear interpolation, providing methods to get points along 
   * the curve, the curve length, and other properties.
   */
  public static class CubicBezierCurve {
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
       if (t < 0 || t > 1) {
            throw new IllegalArgumentException("t must be between 0 and 1");
        }

        Vector2 realControlTwo = controlPointTwo.add(controlPointOne);
        Vector2 realControlThree = controlPointThree.add(controlPointFour);

        Vector2 term1 = realControlTwo.subtract(controlPointOne).multiply(3 * Math.pow(1 - t, 2));
        Vector2 term2 = realControlThree.subtract(realControlTwo).multiply(6 * (1 - t) * t);
        Vector2 term3 = controlPointFour.subtract(realControlThree).multiply(3 * t * t);
        return term1.add(term2).add(term3);
    }

    /**
     * Gets the rotation (heading) at a given t value.
     */
    public Rotation2d getRotationAtT(double t) {
        if (t < 0 || t > 1) {
          throw new IllegalArgumentException("t must be between 0 and 1");
        }

        Vector2 velocity = getVelocityAtT(t);
        return new Rotation2d(Math.atan2(velocity.getY(), velocity.getX()));
    }

    /**
     * Evaluates the Bezier curve pose at a given parameter t.
     *
     * @param t A parameter value between 0 and 1.
     * @return The pose on the curve corresponding to the given t value.
     * @throws IllegalArgumentException if t is outside the range [0, 1].
     */
    public Pose2d getPoseAtPoint(double t){
      Vector2 point = getPointAtT(t);
      Rotation2d rotation = getRotationAtT(t);

      return new Pose2d(point.getX(), point.getY(), rotation);
    }
  }

  /**
   * Represents a 2D point in space with basic vector operations.
   */
  public static class Vector2 {
      private double x;
      private double y;
      /**
       * Constructs a Vector2 with specified x and y coordinates.
       *
       * @param x The x-coordinate.
       * @param y The y-coordinate.
       */
      public Vector2(double x, double y) {
          this.x = x;
          this.y = y;
      }

      /**
       * Copy constructor creates a new Vector2 from an existing one.
       *
       * @param otherVector The vector to copy.
       */
      public Vector2(Vector2 otherVector) {
          this(otherVector.x, otherVector.y);
      }

      /**
       * Adds another vector to this vector.
       *
       * @param otherVector The vector to add.
       * @return A new Vector2 representing the result of the addition.
       */
      public Vector2 add(Vector2 otherVector) {
          return new Vector2(this.x + otherVector.x, this.y + otherVector.y);
      }

      /**
       * Subtracts another vector from this vector.
       *
       * @param otherVector The vector to subtract.
       * @return A new Vector2 representing the result of the subtraction.
       */
      public Vector2 subtract(Vector2 otherVector) {
          return new Vector2(this.x - otherVector.x, this.y - otherVector.y);
      }

      /**
       * Multiplies this vector by a scalar.
       *
       * @param scalar The scalar value to multiply by.
       * @return A new Vector2 representing the scaled vector.
       */
      public Vector2 multiply(double scalar) {
          return new Vector2(this.x * scalar, this.y * scalar);
      }

      /**
       * Performs linear interpolation (lerp) between two vectors.
       *
       * @param pointOne The starting vector.
       * @param pointTwo The ending vector.
       * @param t The interpolation factor (0 ≤ t ≤ 1).
       * @return The interpolated vector.
       */
      public static Vector2 lerp(Vector2 pointOne, Vector2 pointTwo, double t) {
          return pointOne.multiply(1.0 - t).add(pointTwo.multiply(t));
      }

      /**
       * Gets the x value of the vector.
       *
       * @return The x value.
       */
      public double getX() {
          return x;
      }

      /**
       * Gets the y value of the vector.
       *
       * @return The y value.
       */
      public double getY() {
          return y;
      }

      /**
       * Sets the x value of the vector.
       *
       * @param x The new x value.
       */
      public void setX(double x) {
          this.x = x;
      }

      /**
       * Sets the y value of the vector.
       *
       * @param y The new y value.
       */
      public void setY(double y) {
          this.y = y;
      }

      @Override
      public String toString() {
          return "(" + x + ", " + y + ")";
      }
  }

}

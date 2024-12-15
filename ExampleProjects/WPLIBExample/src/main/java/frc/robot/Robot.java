// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.SwerveControllerCommand;
import frc.robot.MathUtils.ChickenParser;
import frc.robot.MathUtils.CubicBezierCurve;

import java.io.IOException;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class Robot extends TimedRobot {
  private final XboxController m_controller = new XboxController(0);

  // Slew rate limiters to make joystick inputs more gentle; 1/3 sec from 0
  // to 1.
  private final SlewRateLimiter m_speedLimiter = new SlewRateLimiter(3);
  private final SlewRateLimiter m_rotLimiter = new SlewRateLimiter(3);

  private final Drivetrain m_drive = new Drivetrain();
  private final RamseteController m_ramsete = new RamseteController();


  @Override
  public void robotInit() {

  }

  @Override
  public void robotPeriodic() {
    m_drive.periodic();
  }

  @Override
  public void autonomousInit() {
    // Create the TrajectoryConfig (you can modify the velocity and acceleration constraints as needed)
    TrajectoryConfig trajectoryConfig = new TrajectoryConfig(
        Drivetrain.kMaxSpeed, // Maximum speed
        Drivetrain.kMaxSpeed // Maximum acceleration (you can adjust this as needed)
    );

    trajectoryConfig.setKinematics(m_drive.getKinematics());  // Using the getKinematics() from Drivetrain

    // Example parameters for getFollowChickenPathCommand method
    String trajectoryPath = "/path/to/trajectory.json";  // Path to the trajectory
    Supplier<Pose2d> poseSupplier = () -> m_drive.getPose();  // Pose supplier, assumes your Drivetrain class has a getPose method
    RamseteController controller = m_ramsete;  // Using the RamseteController instance already in the Robot class
    SimpleMotorFeedforward feedforward = new SimpleMotorFeedforward(1.0, 3.0, 0.2);  // Example feedforward values
    DifferentialDriveKinematics kinematics = m_drive.getKinematics();  // Get kinematics from Drivetrain
    Supplier<DifferentialDriveWheelSpeeds> wheelSpeeds = () -> m_drive.getWheelSpeeds();  // Assume m_drive has a getWheelSpeeds method
    PIDController leftController = new PIDController(1.0, 0.0, 0.0);  // Example PID values for left side
    PIDController rightController = new PIDController(1.0, 0.0, 0.0);  // Example PID values for right side
    BiConsumer<Double, Double> outputVolts = (leftVolts, rightVolts) -> m_drive.setOutput(leftVolts, rightVolts);  // Assuming m_drive has a setOutput method
    Command[] actionPointCommands = new Command[0];  // No action commands for this example

    // Call the method with the missing TrajectoryConfig parameter
    SequentialCommandGroup commandGroup = ChickenPlannerLib.getFollowChickenPathCommand(
        trajectoryPath, 
        trajectoryConfig, 
        poseSupplier, 
        controller, 
        feedforward, 
        kinematics, 
        wheelSpeeds, 
        leftController, 
        rightController, 
        outputVolts, 
        actionPointCommands
    );

    // Run the command group (this might be done differently depending on your robot framework)
    if (commandGroup != null) {
        commandGroup.schedule();
    }
  }



  @Override
  public void autonomousPeriodic() {
  
  }

  @Override
  public void teleopPeriodic() {
    double xSpeed = -m_speedLimiter.calculate(m_controller.getLeftY()) * Drivetrain.kMaxSpeed;
    double rot = -m_rotLimiter.calculate(m_controller.getRightX()) * Drivetrain.kMaxAngularSpeed;
    m_drive.drive(xSpeed, rot);
  }

  @Override
  public void simulationPeriodic() {
    m_drive.simulationPeriodic();
  }
}

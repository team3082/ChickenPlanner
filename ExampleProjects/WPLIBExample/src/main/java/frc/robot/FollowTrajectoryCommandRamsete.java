// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.security.cert.TrustAnchor;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.Trajectory.State;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RamseteCommand;

public class FollowTrajectoryCommandRamsete extends Command {
  private Timer m_timer = new Timer();
  private RamseteController m_ramseteController;
  private Trajectory m_trajectory;
  private Supplier<Pose2d> poseSupplier;
  private BiConsumer<Double, Double> arcadeDrive;
  private boolean stopAtEnd = false;


  public FollowTrajectoryCommandRamsete(
    Trajectory trajectory,
    Supplier<Pose2d> poseSupplier,
    BiConsumer<Double, Double> arcadeDrive,
    RamseteController controller,
    boolean stopAtEnd) {
    // Initialize trajectory
    this.m_trajectory = trajectory;

    // Assign pose supplier
    this.poseSupplier = poseSupplier;

    // Assign arcade drive consumer
    this.arcadeDrive = arcadeDrive;

    // Set whether to stop at the end of the trajectory
    this.stopAtEnd = stopAtEnd;

    // Initialize the Ramsete Controller with default gains
    this.m_ramseteController = controller;

    // Start the timer
    this.m_timer = new Timer();
}

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
      // Reset and start the timer
      m_timer.reset();
      m_timer.start();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    
      // Get the desired pose from the trajectory.
      var desiredPose = m_trajectory.sample(m_timer.get());

      // Get the reference chassis speeds from the Ramsete controller.
      var refChassisSpeeds = m_ramseteController.calculate(poseSupplier.get(), desiredPose);

      // Set the linear and angular speeds.
      arcadeDrive.accept(refChassisSpeeds.vxMetersPerSecond, refChassisSpeeds.omegaRadiansPerSecond);
    
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    // Stop the timer
    m_timer.stop();

    // Stop the robot if the stopAtEnd flag is set
    if (stopAtEnd) {
        arcadeDrive.accept(0.0, 0.0);
    }
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (m_timer.get() > m_trajectory.getTotalTimeSeconds()) return true;
    return false;
  }
}

// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.auto.AutoSelector;
import frc.robot.auto.CommandAuto;
import frc.robot.subsystems.swerve.Pigeon;
import frc.robot.subsystems.swerve.SwerveManager;
import frc.robot.subsystems.swerve.SwervePID;
import frc.robot.subsystems.swerve.SwervePosition;
import frc.robot.utils.RTime;
import frc.robot.utils.Vector2;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    try {
      Thread.sleep(5000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    SwerveManager.init();
    SwervePosition.init();
    SwervePID.init();
    Telemetry.init();
    AutoSelector.setup();
  }

  @Override
  public void robotPeriodic() {
    try {
      Pigeon.update();
      RTime.updateAbsolute();
      RTime.update();
      Telemetry.update(false);
      } catch (Exception e) {
        System.out.println("oopsies" + e.toString());
        e.printStackTrace();
      }
  }

  @Override
  public void autonomousInit() {
    RTime.init();
    Pigeon.setYaw(90);
	  CommandScheduler.getInstance().enable();
    AutoSelector.run();
  }

  @Override
  public void autonomousPeriodic() {
    SwervePosition.update();
    CommandAuto.update();
  }

  @Override
  public void teleopInit() {
    OI.init();
    SwervePosition.setPosition(
        new Vector2(56.78 * (DriverStation.getAlliance().isPresent() && DriverStation.getAlliance().get() == Alliance.Red ? 1 : -1), -275));
  }

  @Override
  public void teleopPeriodic() {
    try {
      SwervePosition.update();
      OI.userInput();
    } catch (Exception e) {
      System.out.println("oopsies" + e.toString());
      e.printStackTrace();
    }
  }

  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  @Override
  public void testInit() {}

  @Override
  public void testPeriodic() {}

  @Override
  public void simulationInit() {}

  @Override
  public void simulationPeriodic() {}
}

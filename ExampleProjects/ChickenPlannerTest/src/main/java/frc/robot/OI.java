package frc.robot;

import static edu.wpi.first.units.Units.RPM;
import static edu.wpi.first.units.Units.Radians;
import static frc.robot.configs.Constants.ShooterConstants.speakerPos;
import static frc.robot.configs.Tuning.OI.*;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import frc.controllermaps.LogitechF310;
import frc.robot.subsystems.swerve.Pigeon;
import frc.robot.subsystems.swerve.SwerveManager;
import frc.robot.subsystems.swerve.SwervePosition;
import frc.robot.configs.ShooterSettings;
import frc.robot.utils.Vector2;
import frc.robot.utils.RMath;

public class OI {
    public static Joystick driverStick, operatorStick;

    // Driver Controls

    // Movement
    static final int moveX         = LogitechF310.AXIS_LEFT_X;
    static final int moveY         = LogitechF310.AXIS_LEFT_Y;
    static final int rotateX       = LogitechF310.AXIS_RIGHT_X;
    static final int boost         = LogitechF310.AXIS_RIGHT_TRIGGER;

    // Shooter
    /** Pre-rev the shooter to a velocity for somewhere in the field */
    static final int revShooter    = LogitechF310.BUTTON_LEFT_BUMPER;
    /** Automatically adjusts angle & velocity for both amp and speaker or goes to manual angle/velocity if in manual mode */
    static final int fireShooter   = LogitechF310.BUTTON_RIGHT_BUMPER;
    /** Ejects the gamepiece without regard for our field position */
    static final int eject         = LogitechF310.BUTTON_B;

    // Intake
    static final int intake        = LogitechF310.AXIS_LEFT_TRIGGER;

    // Others
    static final int pass          = LogitechF310.BUTTON_A;
    static final int zero          = LogitechF310.BUTTON_Y;

    /*-------------------------------------------------------------------*/

    // Operator Controls

    // Shooter
    static final int switchAmpMode      = LogitechF310.BUTTON_LEFT_BUMPER;
    static final int switchShooterMode  = LogitechF310.BUTTON_RIGHT_BUMPER;
    static final int setManualShoot     = LogitechF310.BUTTON_Y;
    static final int calibrate          = LogitechF310.BUTTON_B;

    // Climber
    static final int zeroClimber        = LogitechF310.BUTTON_X;
    static final int setManualClimb     = LogitechF310.BUTTON_A;
    static final int climberUp          = LogitechF310.DPAD_UP;
    static final int climberDown        = LogitechF310.DPAD_DOWN;

    static public enum ShooterMode {
        SPEAKER,
        SPEAKER_MANUAL,
        PASSING,
        AMP
    }

    public static ShooterMode currentShooterMode = ShooterMode.SPEAKER;
    public static boolean manualFireSet = true;
    public static boolean manualClimbSet = true;
    public static boolean aligning = false;
    public static boolean ejecting = false;

    public static int lastPOV = -1;

    static boolean isGround = false;

    public static double topVector = 260;
    public static double bottomVector = 900;

    public static double shooterDistance = 0.0;
    public static boolean shooterPassing = false;

    /**
     * Initialize OI with preset joystick ports.
     */
    public static void init() {
        driverStick = new Joystick(0);
    }

    public static void userInput() {
        driverInput();
    }

    /**
     * Instruct the robot to follow instructions from joysticks.
     * One call from this equals one frame of robot instruction.
     * Because we used TimedRobot, this runs 50 times a second,
     * so this lives in the teleopPeriodic() function.
     */
    public static void driverInput() {
        // INPUT

        if (driverStick.getRawButton(zero)) Pigeon.zero();

        double boostStrength = driverStick.getRawAxis(boost);
        if(boostStrength < 0.1) boostStrength = 0;

        double kBoostCoefficient = NORMALSPEED + boostStrength * (1.0 - NORMALSPEED);
        
        /*--------------------------------------------------------------------------------------------------------*/
        // SETUP

        Vector2 drive = new Vector2(driverStick.getRawAxis(moveX), -driverStick.getRawAxis(moveY));
        double rotate;
        if (currentShooterMode == ShooterMode.SPEAKER && driverStick.getRawButton(fireShooter)) {
            // Face AWAY from speaker (Pigeon's POV) due to shooter being behind the robot
            rotate = speakerPos.add(new Vector2(0,5)).sub(SwervePosition.getPosition()).norm().mul(-1.0).atan2();
        } else {
            rotate = RMath.smoothJoystick1(driverStick.getRawAxis(rotateX)) * -ROTSPEED;
        }

        double manualRPM = 3800.0;
        double manualAngle = 54.0;

        if (drive.mag() < 0.125) 
            drive = new Vector2();
        else
            drive = RMath.smoothJoystick2(drive).mul(kBoostCoefficient);

        if (Math.abs(rotate) < 0.005) 
            rotate = 0;

        /*--------------------------------------------------------------------------------------------------------*/
        // SWERVE

        // if (driverStick.getRawButton(lock)) {
        //     for (SwerveModule module: SwerveManager.mods) {
        //         module.rotateToRad((module.pos.atan2()));
        //     }
        // }

        if (currentShooterMode == ShooterMode.SPEAKER && driverStick.getRawButton(fireShooter)) {
            aligning = true;
            SwerveManager.moveAndRotateTo(drive, rotate);
        } else {
            // Swervin' and a steerin! Zoom!
            SwerveManager.rotateAndDrive(rotate, drive);
            aligning = false;
        }
    }
}
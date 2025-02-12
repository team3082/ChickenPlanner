package frc.robot.configs;

import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.SensorDirectionValue;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import frc.robot.utils.Vector2;

public final class Constants {

    // The distance from the speaker's backing wall to the front of the subwoofer.
    public static final double kSpeakerOffset = 36.0 + 13.0;
    public static final double offset = -10.0;

    public static final class Climber {
        public static final int RIGHT_MOTOR_ID = 21;
        public static final int RIGHT_HALL_ID = 1;

        public static final int LEFT_MOTOR_ID = 20;
        public static final int LEFT_HALL_ID = 0;

        public static final boolean LEFT_MOTOR_INVERTED = true;
        public static final boolean RIGHT_MOTOR_INVERTED = false;

        public static final double INCHES_PER_ROTATION = 0.0;
        public static final double TICKS_PER_INCH = 0.0;

        public static final double MAX_EXTENSION_TICKS = 200000;
    }

    public static final class ShooterConstants {

        // Talon IDs: Flywheel
        public static final int TOPFLYWHEEL_ID = 10;
        public static final int BOTTOMFLYWHEEL_ID = 11;

        // Talon ID: Pivot
        public static final int FLYWHEELPIVOT_ID = 9;

        // CANCoder offset
        public static final double PIVOT_OFFSET = 345.674 - 17.0;

        public static final double TARGET_OFFSET = 0.0; // safety factor, if needed
        
        //COMposition
        public static final Vector2 SHOOTER_COM_POS = new Vector2(0,0); //position of the com relative to the pivot point. the angle is what really matters

        // Falcon : Wheel(s)
        public static final double shooterBeltRatio = 1;
        // Pivot gear ratio
        public static final double shooterGearRatio = 50;

        // Converting from RPM to Phoenix native sensor units (1u/100ms)
        public static final double RPMToVel = 2048.0 * shooterBeltRatio / 60.0 / 10.0;
        // Converting from Phoenix native sensor units (1u/100ms) to RPMs
        public static final double VelToRPM = 10.0 * 60.0 / 2048.0 / shooterBeltRatio;

        //all positions for the red alliance in inches
        public static final double targetX = 56.7;
        public static final double targetY = -327.13;
        public static final double TARGETHEIGHT = 78.0;

        // Not the exact pos of the speaker. Rather, the position of the robot's
        // center of rotation when pressed up against the subwoofer.
        // Y was previously -327.0.
        public static final Vector2 speakerPos = new Vector2(56.78 * (DriverStation.getAlliance().isPresent() && DriverStation.getAlliance().get() == Alliance.Red ? 1 : -1), -327.0);

        public static final double SHOOTERPOSZ = 0.0;//the height the the end of the shooter lies on average. I don't feel like calculating this as a function of shooter angle
    }

    public static final class Intake {

        // Motor IDs: Belt motors
        public static final int INDEXER_ID = 25;
        public static final int TOPINTAKE_ID = 0;
        public static final int BOTTOMINTAKE_ID = 24;

        // Motor ID: Pivot
        public static final int INTAKEPIVOT_ID = 22;

        // CAN ID: Beambreak
        public static final int LASER_ID = 0;
        public static final double LASER_BREAK_DIST= 200.0;

        // CANCoder offset
        public static final double INTAKE_OFFSET = 0;

        // Motor : belt(s)
        // Pivot gear ratio
        public static final double INTAKERATIO = 48.0;

        public static final double HANDOFF_SPEED = 0.5;
        public static final double INTAKE_HANDOFF_SPEED = 0.5;

        //intake and note width in millimeters, used for lasercan detection
        public static final int INTAKE_WIDTH_mm = 635;
        public static final int NOTE_WIDTH_mm = 355;

        public static final double INROBOT_INTAKE_ANGLE = -1200;//in ticks
        public static final double SOURCE_INTAKE_ANGLE = -1200;
        public static final double GROUND_INTAKE_ANGLE = -63000;
        public static final double FEED_INTAKE_ANGLE = -1200;

        public static final double FLIPUP_TIME = 0.1;
    }

    public static final class Swerve {

        public static final int DRIVEID0 = 5;
        public static final int DRIVEID1 = 7;
        public static final int DRIVEID2 = 1;
        public static final int DRIVEID3 = 3;

        public static final int STEERID0 = 6;
        public static final int STEERID1 = 8;
        public static final int STEERID2 = 2;
        public static final int STEERID3 = 4;

        public static final double MODOFFSET0 = 286.348;
        public static final double MODOFFSET1 = 221.309;
        public static final double MODOFFSET2 = 174.551;
        public static final double MODOFFSET3 = 134.736;

        public static final double WIDTH = 26;
        public static final double LENGTH = 26;
        public static final double MODULEOFFSET = 2.625;

        public static final double SWERVEMODX0 = (WIDTH / 2) - MODULEOFFSET;
        public static final double SWERVEMODX1 = -1 * (WIDTH / 2) + MODULEOFFSET;
        public static final double SWERVEMODX2 = -1 * (WIDTH / 2) + MODULEOFFSET;
        public static final double SWERVEMODX3 = (WIDTH / 2) - MODULEOFFSET;

        public static final double SWERVEMODY0 = -1 * (LENGTH / 2) + MODULEOFFSET;
        public static final double SWERVEMODY1 = -1 * (LENGTH / 2) + MODULEOFFSET;
        public static final double SWERVEMODY2 = (LENGTH / 2) - MODULEOFFSET;
        public static final double SWERVEMODY3 = (LENGTH / 2) - MODULEOFFSET;

        public static final double driveTrackwidth = 0.0;
        public static final double driveWheelbase = 0.0;

        public static final double shootWhileMoveSpeed = 0.24;
    
        // The unadjusted maximum velocity of the robot, in inches per second.
        public static final double maxChassisVelocity = 6380.0 / 60.0 * 6.12 * (4.0 * Math.PI);
        // The unadjusted theoretical maximum angular velocity of the robot, in radians per second.
        public static final double maxAngularVelocity = (maxChassisVelocity / Math.hypot(driveTrackwidth / 2.0, driveWheelbase / 2.0));

        // Rotations to Radians
        public static final double rotConversionFactor = 2 * Math.PI;

        public static final class MK4iDriveRatios {
            // SDS MK4i - (8.14 : 1)
            public static final double L1 = (8.14 / 1.0);
            // SDS MK4i - (6.75 : 1)
            public static final double L2 = (6.75 / 1.0);
            // SDS MK4i - (6.12 : 1)
            public static final double L3 = (6.12 / 1.0);
        }

        public static final class MK4DriveRatios {
            // SDS MK4 - (8.14 : 1)
            public static final double L1 = (8.14 / 1.0);
            // SDS MK4 - (6.75 : 1)
            public static final double L2 = (6.75 / 1.0);
            // SDS MK4 - (6.12 : 1)
            public static final double L3 = (6.12 / 1.0);
            // SDS MK4 - (5.14 : 1)
            public static final double L4 = (5.14 / 1.0);
        }

        public static final class MK4iConstants {

            // Swerve module's wheel diameter
            public static final double wheelDiameter = 4;
            // The gear ratio of the steering motor
            public static final double angleGearRatio = ((150.0 / 7.0) / 1.0);

            // Whether or not, based on our module, the motors should be inverted
            public static final InvertedValue driveMotorInvert = InvertedValue.CounterClockwise_Positive;
            public static final InvertedValue angleMotorInvert = InvertedValue.Clockwise_Positive;

            // Should the CANCoder's magnet be inverted as well?
            public static final SensorDirectionValue cancoderInvert = SensorDirectionValue.CounterClockwise_Positive;

        }

        public static final class MK4Constants {
        
            // Swerve module's wheel diameter
            public static final double wheelDiameter = 4;

            // The gear ratio of the steering motor
            public static final double angleGearRatio = (12.8 / 1.0);

            // Whether or not, based on our module, the motors should be inverted
            public static final InvertedValue driveMotorInvert = InvertedValue.CounterClockwise_Positive;
            public static final InvertedValue angleMotorInvert = InvertedValue.CounterClockwise_Positive;

            // Should the CANCoder's magnet be inverted as well?
            public static final SensorDirectionValue cancoderInvert = SensorDirectionValue.CounterClockwise_Positive;

        }

    }

    
    public static final double METERSTOINCHES = 39.3701;
    public static final double FIELD_HEIGHT = 323.25; // inches
    public static final double FIELD_WIDTH = 651.25; // inches
}

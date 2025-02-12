package frc.robot.subsystems.swerve;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import frc.robot.subsystems.swerve.Pigeon;
import frc.robot.utils.Vector2;
import frc.robot.utils.swerve.SwerveInstruction;
import frc.robot.utils.swerve.SwerveMath;

import static frc.robot.configs.Constants.Swerve.*;
import static frc.robot.configs.Tuning.OI.KDYAW;

public final class SwerveManager {
    
    public static SwerveModule[] mods;
    public static Vector2 movement = new Vector2();

    public static void init() {
        mods = new SwerveModule[] {
            // Back Right
            new SwerveModule(STEERID0, DRIVEID0, MODOFFSET0, SWERVEMODX0, SWERVEMODY0),
            // Back Left
            new SwerveModule(STEERID1, DRIVEID1, MODOFFSET1, SWERVEMODX1, SWERVEMODY1),
            // Front Left
            new SwerveModule(STEERID2, DRIVEID2, MODOFFSET2, SWERVEMODX2, SWERVEMODY2),
            // Front Right
            new SwerveModule(STEERID3, DRIVEID3, MODOFFSET3, SWERVEMODX3, SWERVEMODY3)
        };
    }

    public static void resetSensors() {
        for (SwerveModule mod : mods) {
            mod.resetSteerSensor();
        }
    }

    public static void rotateAndDrive(SwerveInstruction si) {
        rotateAndDrive(si.rotation, si.movement);
    }

    /**
     * Only call this method from OI
     * @param rotSpeed
     * @param move
     */
    public static void rotateAndDriveWithYawRateControl(double rotSpeed, Vector2 move) {
        double yawRate = Pigeon.getDeltaRotRad();
        double correctedRotSpeed = rotSpeed + yawRate * KDYAW;
        rotateAndDrive(correctedRotSpeed, move);
    }

    
    /** 
     * Locks the robot's angle to a specific angle, but allows free translation.
     */
    public static void moveAndRotateTo(Vector2 move, double toAngle) {
        SwervePID.rotPID.setDest(toAngle);
        double rotation = SwervePID.rotPID.updateOutput(Pigeon.getRotationRad());
        rotateAndDrive(rotation, move.mul(shootWhileMoveSpeed));
    }

    public static void rotateAndDrive(double rotSpeed, Vector2 move) {
        movement = move;
        double heading = Pigeon.getRotationRad();
        
        // Array containing the unclamped movement vectors of each module
        Vector2[] vectors = new Vector2[mods.length];

        // Multiply the movement vector by a rotation matrix to compensate for the pigeon's heading
        Vector2 relMove = move.rotate(-(heading - Math.PI / 2));

        // The greatest magnitude of any module's distance from the center of rotation
        double maxModPosMagnitude = 0;
        for (int i = 0; i < mods.length; i++) {
            maxModPosMagnitude = Math.max(maxModPosMagnitude,
                mods[i].pos.mag());
        }

        // The greatest speed of the modules. If any one module's speed is
        // greater than 1.0, all the speeds are scaled down.
        double maxSpeed = 1.0;

        // Calculate unclamped movement vectors
        for (int i = 0; i < mods.length; i++) {
            // The vector representing the direction the module should move to achieve the
            // desired rotation. Calculated by taking the derivative of the module's
            // position on the circle around the center of rotation, normalizing the
            // resulting vector according to maxModPosMagnitude (such that the magnitude of
            // the largest vector is 1), and scaling it by a factor of rotSpeed.

            Vector2 rotate = new Vector2(
                (-1 * mods[i].pos.y / maxModPosMagnitude) * rotSpeed,
                (     mods[i].pos.x / maxModPosMagnitude) * rotSpeed);

            // The final movement vector, calculated by summing movement and rotation
            // vectors

            Vector2 rotMove = relMove.add(rotate);

            vectors[i] = rotMove;
            maxSpeed = Math.max(maxSpeed, rotMove.mag());
        }

        for (int i = 0; i < mods.length; i++) {
            // Convert the movement vectors to a directions and magnitudes, clamping the
            // magnitudes based on 'max'. 
            double direction = vectors[i].atan2();
            double power = vectors[i].mag() / maxSpeed;

            // Drive the swerve modules
            if(power != 0)
                mods[i].rotateToRad(direction);
            mods[i].drive(power);
        }
    }

    /**
     * Gets the raw encoder position of a specified SwerveMod's drive motor
     * @param id the ID of the SwerveModule to check
     * @return the raw encoder position, in ticks
     */
    public static double getEncoderPos(int id){
        return mods[id].drive.getSelectedSensorPosition();
    }

    /**
     * Gets the velocity a given SwerveModule is driving at
     * @param id the ID of the SwerveModule to check
     * @return the drive velocity of the SwerveModule, in inches/second
     */
    public static double getDriveVelocity(int id) {
        return mods[id].getDriveVelocity();
    }

    /**
     * Returns the angle a given SwerveModule's wheel is pointed toward
     * @param id the ID of the SwerveModule to check
     * @return the angle of the SwerveModule, in radians
     */
    public static double getSteerAngle(int id) {
        return mods[id].getSteerAngle();
    }

    /**
     * Returns the overall drive velocity of the robot, based on the average of the velocities of the wheels. Not
     * adjusted for the rotation of the robot on the field.
     * @return the robot's drive velocity, in inches/second
     */
    public static Vector2 getRobotDriveVelocity() {
        Vector2 velSum = new Vector2();
        for (SwerveModule mod : mods) {
            velSum = velSum.add(Vector2.fromPolar(mod.getSteerAngle(), mod.getDriveVelocity()));
        }

        return velSum.div(mods.length);
    }

    /**
     * finds the robots velocity by performing pose exponentiation on each of the swerve mods,
     * and returning the average change
     * @return
     */
    public static Vector2 getRobotDriveVelocityModExponentiation(){
        Vector2 velSum = new Vector2();
        for (SwerveModule mod : mods){
            double dtheta = mod.getSteerDelta();
            double thetaf = mod.getSteerAngle();
            //divided by 50 to get the change per frame instead of seconds
            double dpos = mod.getDriveVelocity() / 50;
            velSum.add(SwerveMath.poseExponentiation(dpos, thetaf - dtheta, dtheta));
        }
        // multiplied by 50 to return to inches per second
        return velSum.div(mods.length).mul(50);
    }

    /**
     * Returns the overall rotational velocity of the robot, based on the rotations and velocities of each of the
     * wheels. Primarily meant for applications where the Pigeon is unavailable, such as simulation.
     * @return the rotational velocity of the robot, in radians/second
     */
    public static double getRotationalVelocity() {
        // We only need to check the first swerve module
        Vector2 moduleVel = Vector2.fromPolar(getSteerAngle(0), getDriveVelocity(0));
        Vector2 rotVector = moduleVel.sub(getRobotDriveVelocity());

        // Get the one-dimensional rotation velocity in inches/sec, moving around the circle
        double rotVelInches = rotVector.rotate(-mods[0].pos.atan2()).y;

        // Divide by the radius to get the rotation velocity in radians/sec
        double radius = mods[0].pos.mag();
        return rotVelInches / radius;
    }

    public static void pointWheels(double radians) {
        for (SwerveModule mSwerveMod : mods) mSwerveMod.rotateToRad(radians);
    }

    /**
     * Locks the robot in position by rotating all wheels towards the center of the robot
     */
    public static void lockWheels(){
        for(SwerveModule mSwerveMod : mods){
            mSwerveMod.rotateToRad(mSwerveMod.pos.atan2());
            mSwerveMod.drive.neutralOutput();
        }
    }

    public static SwerveModuleState[] returnStates() {
        return new SwerveModuleState[] {
            new SwerveModuleState(mods[0].getDriveVelocity(), Rotation2d.fromRadians(mods[0].getSteerAngle())),
            new SwerveModuleState(mods[1].getDriveVelocity(), Rotation2d.fromRadians(mods[1].getSteerAngle())),
            new SwerveModuleState(mods[2].getDriveVelocity(), Rotation2d.fromRadians(mods[2].getSteerAngle())),
            new SwerveModuleState(mods[3].getDriveVelocity(), Rotation2d.fromRadians(mods[3].getSteerAngle()))
        };
    }
}

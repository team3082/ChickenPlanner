package frc.robot.subsystems.swerve;

import com.ctre.phoenix.sensors.Pigeon2;

import edu.wpi.first.wpilibj.RobotBase;
import frc.robot.utils.RTime;

@SuppressWarnings("removal")
public class Pigeon {

    public static Pigeon2 pigeon;
    private static double lastRot;
    private static double deltaRot;

    public static double simulatedRot = 0;

    public static void init() {
        pigeon = new Pigeon2(0, "CANivore");
        pigeon.configFactoryDefault();
    }

    public static void update() {
        if (RobotBase.isSimulation()) {
            simulatedRot += SwerveManager.getRotationalVelocity() * RTime.deltaTime();
        }
        deltaRot = (getRotationRad() - lastRot) / RTime.deltaTime();
        lastRot = getRotationRad();
    }

    public static void setSimulatedRot(double rad) {
        simulatedRot = rad;
    }

    public static void zero(){
        pigeon.setYaw(90);
    }

    public static void setYaw(double deg) {
        if (RobotBase.isSimulation()) simulatedRot = deg * Math.PI / 180;
        else pigeon.setYaw(deg);
        
    }

    public static void setYawRad(double rad) {
        setYaw(rad * 180.0 / Math.PI);
        simulatedRot = rad;
    }

    
    /**
     * Local to the robot, not the world
     * Pitch, rotates around the X, left to right, axis
     * Tilts forward and backward
     * @return Pitch in radians
     */
    public static double getPitchRad() {
        return Math.PI * pigeon.getPitch() / 180;
    }

    /**
     * Local to the robot, not the world
     * Yaw, rotates around the Y, up and down, axis
     * @return Yaw in radians
     */
    public static double getRotationRad() {
        if (RobotBase.isSimulation()) {
            return simulatedRot;
        }
        return Math.PI * pigeon.getYaw() / 180;
    }

    /**
     * Local to the robot, not the world
     * Roll, rotates around the Z, forward and backward, axis
     * Tilts left and right
     * @return Roll in radians
     */
    public static double getRollRad() {
        return Math.PI * pigeon.getRoll() / 180;
    }

    /**
     * Gets the rotation speed (yaw) of the robot in radians per second
     * @return Yaw in radians
     */
    public static double getDeltaRotRad() {
        return deltaRot;
    }
}

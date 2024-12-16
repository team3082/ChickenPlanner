package frc.robot.auto;

import java.util.List;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import frc.robot.auto.commands.FollowRobotPath;

import frc.robot.subsystems.swerve.SwerveManager;
import frc.robot.subsystems.swerve.SwervePosition;
import frc.robot.utils.Vector2;
import frc.robot.utils.trajectories.ChickenParser;
import frc.robot.utils.trajectories.RobotPath;

public class CommandAuto {
  public static void init(Command command){
    new SequentialCommandGroup(command,stop()).schedule();
  }
  
  public static void update(){
    CommandScheduler.getInstance().run();
  }

  public static Command stop(){
    return Commands.runOnce(() -> SwerveManager.rotateAndDrive(0, new Vector2()));
  }

  public static Command chickenPlannerTest() {
    ChickenParser parser = new ChickenParser("src/main/deploy/ChickenPlanner/Silly Path.json");
    List<RobotPath> paths = parser.getPaths();
    Vector2 startingPos = paths.get(0).getStartPos();
    SwervePosition.setPosition(startingPos);

    FollowRobotPath[] commandArray = new FollowRobotPath[paths.size()];
    for(int index = 0; index<commandArray.length; index++){
      commandArray[index] = new FollowRobotPath(paths.get(index));
    }
  
    return new SequentialCommandGroup(
      commandArray
    );
  }
}
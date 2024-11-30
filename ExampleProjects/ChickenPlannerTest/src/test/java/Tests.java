import org.junit.jupiter.api.Test;

import frc.robot.utils.trajectories.ChickenParser;
import frc.robot.utils.trajectories.RobotPath;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

public class Tests {
    @Test
    public void chickenParserTest() {
        System.out.println("testing chicken parser...");
        ChickenParser parser = new ChickenParser("src/main/deploy/ChickenPlannerPathFollowTest.json");
        List<RobotPath> paths = parser.getPaths();
        assertEquals(2, paths.size());
        System.out.println("curve points 1: " + paths.get(0).points.size());
        System.out.println("curve points 2: " + paths.get(1).points.size());
    }
}
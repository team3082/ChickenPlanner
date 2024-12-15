package frc.robot.MathUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import frc.robot.ChickenPlannerLib.Vector2;

public class ChickenParser {
    String pathJSON;

    public ChickenParser(String pathJSON) {
        this.pathJSON = pathJSON;
    }

    public Trajectory loadTrajectory() throws IOException {
        // load json and read bezier curve data to generate new bezier curve class
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(new File(pathJSON));
        
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

        TrajectoryConfig config = new TrajectoryConfig(10.0, 0.5);
        List<Pose2d> poses = new ArrayList<>();

        int points = 10;
        for(CubicBezierCurve cubicBezierCurve : bezierCurves){
            for (int i = 0; i <= points; i++) {
                double t = (double) i / points;
                Vector2 point = cubicBezierCurve.getPointAtT(t);
                Rotation2d rotation = cubicBezierCurve.getRotationAtT(t);
    
                poses.add(new Pose2d(point.getX(), point.getY(), rotation));
            }
        }

        return TrajectoryGenerator.generateTrajectory(poses, config);
    }
}

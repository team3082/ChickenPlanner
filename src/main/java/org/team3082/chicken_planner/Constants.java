package org.team3082.chicken_planner;

import org.team3082.chicken_planner.SplineDrawing.CurveUtilites.Vector2;

public class Constants {
    public static class UI {
        public static final int WINDOW_WIDTH = 800;
        public static final int WINDOW_HEIGHT = 600;
    }

    public static class Math {
        public static final double EPSILON = 1e-6;
        public static final int CURVE_SAMPLES = 300;
        public static final int TRAJECTORY_SAMPLES_PER_METER = 10;
    }

    public static final int DEFUALT_CURVE_POINTS = 0;
    public static final Vector2 ORIGIN_POINT = null;
    
}

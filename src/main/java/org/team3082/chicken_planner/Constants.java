package org.team3082.chicken_planner;

import org.team3082.chicken_planner.MathUtils.Vector2;

import javafx.scene.paint.Color;

public class Constants {
    //App layout constants
    public static final double APP_PREFERRED_WIDTH = 600*1.5;
    public static final double APP_PREFERRED_HEIGHT = 400*1.5;
    public static final double SIDEBAR_WIDTH = 160;
    public static  final double TOPBAR_HEIGHT = 60;
    public static final double FIELD_PADDING = 20;

    //Trajectory Drawing Constants
    public static final int DEFUALT_CURVE_POINTS = 500;
    public static final double LINE_WIDTH = 0.007;
    public static final Color LINE_COLOR = new Color(247.0/256.0, 247.0/256.0, 247.0/256.0, 1);
   
    public static final double INNER_CONTROL_POINT_SCALE = 0.016/1.1;
    public static final Color INNER_CONTROL_POINT_COLOR =  new Color(198.0/256.0, 190.0/256.0, 252.0/256.0, .5);
    public static final double INNER_CONTROL_LINE_WIDTH = 0.005;
    
    public static final double OUTER_CONTROL_POINT_SCALE = 0.02/1.1;
    public static final Color OUTER_CONTROL_POINT_COLOR = new Color(198.0/256.0, 190.0/256.0, 252.0/256.0, .7); 
    public static final Color FINAL_CONTROL_POINT_COLOR = new Color(198.0/256.0, 190.0/256.0, 252.0/256.0, 1);
    

    //Real dimensions in meters of the Field
    public static final double FIELD_WIDTH = 16.542;
    public static final double FIELD_HEIGHT = 8.211;

    //Field scaling constants  
    public static final Vector2 ORIGIN_POINT = new Vector2(103.33333333333331, 532.6666666666666);
    public static final Vector2 END_POINT = new Vector2(992.0, 92.0);

    public static final double X_SCALE_FACTOR = (END_POINT.getX() - ORIGIN_POINT.getX()) / Constants.FIELD_WIDTH;
    public static final double Y_SCALE_FACTOR = (END_POINT.getY() - ORIGIN_POINT.getY()) / Constants.FIELD_HEIGHT;
    
    public static final double REFERENCE_CANVAS_WIDTH = 1100.0;
    public static final double REFERENCE_CANVAS_HEIGHT = 625.3002401921536;

    //Action points
    public static final double ACTION_POINT_SCALE = 0.016/1.2;
    public static final Color ACTION_POINT_COLOR = new Color(113/256.0, 111/256.0, 211/256.0, 0.9);
}

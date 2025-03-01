package org.team3082.chicken_planner.Utils;

import java.util.Vector;

public class Point {
    private final double x;
    private final double y;

    public Point(double x, double y){
        this.x = x;
        this.y = y;
    }
    
    public Point setX(double x){
        return new Point(x, y);
    }

    public Point setY(double y){
        return new Point(x, y);
    }

    public double getX(){
        return x;
    }

    public double getY(){
        return y;
    }

    
}

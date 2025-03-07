package org.team3082.chicken_planner.Utils;

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


    public Point add(Point point){
        return new Point(x + point.x, y + point.y);
    }

    public Point sub(Point point){
        return new Point(x - point.x, y - point.y);
    }


    public Point mul(double scale){
        return new Point(x * scale, y * scale);
    }

    public Point div(double scale){
        return new Point(x / scale, y / scale);
    }

    public double mag(){
        return Math.hypot(x, y);
    }

    public double distance(Point point){
        return this.sub(point).mag();
    }

    public Point norm(){
        double m = mag();
        return div(m);
    }

    public Point rotate(double angle){
        double rotateX = x * Math.cos(angle) - y * Math.sin(angle);
        double rotateY = x * Math.sin(angle) + y * Math.cos(angle);
        return new Point(rotateX, rotateY);
    }

    public double dot(Point dotPoint){
        return x*dotPoint.x + y*dotPoint.y;
    }

    public double atan2(){
        return Math.atan2(y, x);
    }

    public boolean isGreater(Point otherVector){
        return x > otherVector.x && y > otherVector.y;
    }
    
    public boolean equals(Point point){
        return x == point.x && y == point.y;
    }
}

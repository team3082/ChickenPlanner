package org.team3082.chicken_planner.State.Events;

public class DrawCircle implements Event{
    public double pixelX;
    public double pixelY;

    public DrawCircle(double a , double c){
        pixelX = a;
        pixelY = c;
    }
}

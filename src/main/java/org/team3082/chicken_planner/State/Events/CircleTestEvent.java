package org.team3082.chicken_planner.State.Events;

public class CircleTestEvent implements Event {
    public double pixelX;
    public double pixelY;

    public CircleTestEvent(double a , double c){
        pixelX = a;
        pixelY = c;
    }
}

package org.team3082.chicken_planner.AutoPlanning.TrajectoryDrawing.InputManagment;

import java.util.ArrayList;

import org.team3082.chicken_planner.AutoPlanning.AutoRoutine.ActionPoint;
import org.team3082.chicken_planner.AutoPlanning.AutoRoutine.AutoRoutine;
import org.team3082.chicken_planner.AutoPlanning.TrajectoryDrawing.DrawingManagers.SplineDrawingManager;
import org.team3082.chicken_planner.ChickenPlannerApplication;
import org.team3082.chicken_planner.Constants;
import org.team3082.chicken_planner.MathUtils.BezierSpline;
import org.team3082.chicken_planner.MathUtils.Vector2;

import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;

public class ActionPointManager {
    private ChickenPlannerApplication application;
    private SplineDrawingManager splineDrawingManager;
    private Canvas canvas;

    public int actionPointSelected = 0;

    public ActionPointManager(ChickenPlannerApplication application, SplineDrawingManager splineDrawingManager) {
       this.application = application;
       this.splineDrawingManager = splineDrawingManager;
       this.canvas = application.getField().getSplineCanvas();

        application.getAppState().autoT.addListener((_, _, _)->{
            if(application.getAppState().getCurrentAutoRoutine().getActionPoints().size() > 0){
                application.getAppState()
                .getCurrentAutoRoutine()
                .getActionPoints()
                .get(actionPointSelected)
                .setT(application.getAppState().autoT.doubleValue());
            }
        });
    }

    public void onMousePressed(MouseEvent event){
        AutoRoutine currentRoutine = application.getAppState().getCurrentAutoRoutine();
        Vector2 mousePosition = new Vector2(event.getX(), event.getY());
        switch (event.getButton()) {
            case PRIMARY:
                actionPointSelected = getActionPointIndex(mousePosition);
                application.getTrajectoryManager().getSplineDrawingManager().resetAndPopulateCanvas();
                if(actionPointSelected != -1){
                    application.getAppState().setRoutineSaved(false);
                    application.getAppState().autoT.set(currentRoutine.getActionPoints().get(actionPointSelected).getT());
                    
                }
              
                break;
            case SECONDARY:
                int pointIndex = getActionPointIndex(mousePosition);
                if(pointIndex != -1) return;

                double t = currentRoutine.getSpline().findNearestT(mousePosition.pixelToField(canvas));
                if(t == (double)((int) t)) t -= 0.001;
                if(t <= 0) t = 0.0001;
                if(currentRoutine.getSpline().getPoint(t).fieldToPixel(canvas).distance(mousePosition)<canvas.getHeight()*0.01){
                    currentRoutine.getActionPoints().add(new ActionPoint(t));
                    actionPointSelected = currentRoutine.getActionPoints().size()-1;
                    splineDrawingManager.resetAndPopulateCanvas();
                    application.getAppState().autoT.set(currentRoutine.getActionPoints().get(actionPointSelected).getT());
                }
                break;
            case MIDDLE:
                actionPointSelected =  getActionPointIndex(mousePosition);
                if(actionPointSelected == -1) return;
                currentRoutine.getActionPoints().remove(actionPointSelected);
                actionPointSelected = -1;
                splineDrawingManager.resetAndPopulateCanvas();
                break;
            default:
                break;
        }
    }

    public void onMouseDragged(MouseEvent event){
        AutoRoutine currentRoutine = application.getAppState().getCurrentAutoRoutine();
        Vector2 mousePosition = new Vector2(event.getX(), event.getY()).clamp(canvas.getWidth(), canvas.getHeight());
        mousePosition = mousePosition.pixelToField(canvas.getWidth(), canvas.getHeight());
        if(actionPointSelected == -1) return;
        double t = currentRoutine.getSpline().findNearestT(mousePosition);
        if(t == (double)((int) t)) t -= 0.001;
        if(t <= 0) t = 0.0001;
        currentRoutine.getActionPoints().get(actionPointSelected).setT(t);
         application.getAppState().autoT.set(t);
        splineDrawingManager.resetAndPopulateCanvas();
    }

    public void onMouseReleased(){
        actionPointSelected = -1;
    }
    
    private int getActionPointIndex(Vector2 mouseVector){
        ArrayList<ActionPoint> actionPoints = application.getAppState().getCurrentAutoRoutine().getActionPoints();
        BezierSpline spline = application.getAppState().getCurrentAutoRoutine().getSpline();

        for(int index = 0; index < actionPoints.size(); index++){
            double t = actionPoints.get(index).getT();
            Vector2 curvePixelPoint = spline.getPoint(t).fieldToPixel(canvas);
            double distanceThreshold = Constants.ACTION_POINT_SCALE*canvas.getHeight();
            if(curvePixelPoint.distance(mouseVector) < distanceThreshold){
                return index;
            }
        }

        return -1;
    }
}

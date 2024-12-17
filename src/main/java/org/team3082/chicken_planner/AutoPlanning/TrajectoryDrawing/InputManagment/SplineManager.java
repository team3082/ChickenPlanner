package org.team3082.chicken_planner.AutoPlanning.TrajectoryDrawing.InputManagment;

import java.util.ArrayList;

import org.team3082.chicken_planner.ChickenPlannerApplication;
import org.team3082.chicken_planner.Constants;
import org.team3082.chicken_planner.AutoPlanning.AutoRoutine.ActionPoint;
import org.team3082.chicken_planner.AutoPlanning.TrajectoryDrawing.DrawingManagers.SplineDrawingManager;
import org.team3082.chicken_planner.MathUtils.BezierSpline;
import org.team3082.chicken_planner.MathUtils.CubicBezierCurve;
import org.team3082.chicken_planner.MathUtils.Vector2;

import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;

/**
 * Manages the user input for modifying and handling Bezier splines on the canvas.
 * This class processes mouse events such as clicking, dragging, and releasing to manipulate
 * the control points of Bezier splines in the application.
 */
public class SplineManager {
    private ChickenPlannerApplication application;
    private SplineDrawingManager drawingManager;
    private Canvas canvas;

    private int pointSelectedIndex = -1; // Index of the currently selected point in the spline

    /**
     * Constructor to initialize the SplineManager.
     * 
     * @param application The main application instance.
     * @param drawingManager The manager responsible for drawing the splines.
     */
    public SplineManager(ChickenPlannerApplication application, SplineDrawingManager drawingManager) {
        this.application = application;
        this.drawingManager = drawingManager;
        this.canvas = application.getField().getSplineCanvas();
    }

    /**
     * Handles the adjustment of control points based on the selected point index.
     * 
     * @param spline The Bezier spline to modify.
     */
    private void handleControlPoints(BezierSpline spline) {
        int curveIndex = pointSelectedIndex / 3;
        boolean isSharedControlPoint = pointSelectedIndex % 3 == 0;
    
        if (isSharedControlPoint) {
            handleSharedControlPoint(spline, curveIndex);
        } else {
            handleNonSharedControlPoint(spline, curveIndex);
        }
    }

    /**
     * Handles logic when a shared control point is selected.
     * Shared control points influence two adjacent curves.
     * 
     * @param spline The Bezier spline.
     * @param curveIndex The index of the curve affected by the control point.
     */
    private void handleSharedControlPoint(BezierSpline spline, int curveIndex) {
        if (pointSelectedIndex == 0) {
            clampFirstControlPoint(spline, curveIndex);
        } else if (pointSelectedIndex == spline.getCurveCount() * 3) {
            clampLastControlPoint(spline, curveIndex);
        } else {
            clampIntermediateSharedControlPoint(spline, curveIndex);
        }
    }
    
    /**
     * Handles logic when a non-shared control point (handle) is selected.
     * 
     * @param spline The Bezier spline.
     * @param curveIndex The index of the curve affected by the control point.
     */
    private void handleNonSharedControlPoint(BezierSpline spline, int curveIndex) {
        if (pointSelectedIndex % 3 == 1) {
            // First handle (before clamping)
            clampFirstHandle(spline, curveIndex);
        } else if (pointSelectedIndex % 3 == 2) {
            // Second handle (after clamping)
            clampSecondHandle(spline, curveIndex);
        }
    }

    /**
     * Clamps the first control point of the specified curve within valid bounds.
     * 
     * @param spline The Bezier spline.
     * @param curveIndex The index of the curve.
     */
    private void clampFirstControlPoint(BezierSpline spline, int curveIndex) {
        drawingManager.resetAndPopulateCanvas();
        Vector2 controlPointTwo = clampControlPointPosition(spline.getCurveAt(curveIndex).getControlPointTwo());
        spline.getCurveAt(curveIndex).setControlPointTwo(controlPointTwo);
    }

    /**
     * Clamps the last control point of the specified curve within valid bounds.
     * 
     * @param spline The Bezier spline.
     * @param curveIndex The index of the curve.
     */
    private void clampLastControlPoint(BezierSpline spline, int curveIndex) {
        drawingManager.resetAndPopulateCanvas();
        Vector2 controlPointThree = clampControlPointPosition(spline.getCurveAt(curveIndex - 1).getControlPointThree());
        spline.getCurveAt(curveIndex - 1).setControlPointThree(controlPointThree);
    }

    /**
     * Clamps the intermediate shared control point of the spline and adjusts surrounding curves.
     * 
     * @param spline The Bezier spline.
     * @param curveIndex The index of the curve affected by the control point.
     */
    private void clampIntermediateSharedControlPoint(BezierSpline spline, int curveIndex) {
        CubicBezierCurve curve = spline.getCurveAt(curveIndex);
        Vector2 controlTwo = clampControlPointPosition(curve.getControlPointTwo());
        curve.setControlPointTwo(controlTwo);
    
        CubicBezierCurve prevCurve = spline.getCurveAt(curveIndex - 1);
        double magnitude = prevCurve.getRelativeControlPointThree().magnitude();
        prevCurve.setRelativeControlPointThree(curve.getRelativeControlPointTwo().normalize().multiply(-magnitude));
    
        Vector2 controlThree = clampControlPointPosition(prevCurve.getControlPointThree());
        prevCurve.setControlPointThree(controlThree);
    
        double nextMagnitude = curve.getRelativeControlPointTwo().magnitude();
        curve.setRelativeControlPointTwo(prevCurve.getRelativeControlPointThree().normalize().multiply(-nextMagnitude));
    }

    /**
     * Clamps the first handle of the spline and adjusts the previous curve if necessary.
     * 
     * @param spline The Bezier spline.
     * @param curveIndex The index of the curve.
     */
    private void clampFirstHandle(BezierSpline spline, int curveIndex) {
        if (curveIndex == 0) return;
        CubicBezierCurve previousCurve = spline.getCurveAt(curveIndex - 1);
        double magnitude = previousCurve.getRelativeControlPointThree().magnitude();
        previousCurve.setRelativeControlPointThree(spline.getCurveAt(curveIndex).getRelativeControlPointTwo().normalize().multiply(-magnitude));
    
        Vector2 controlPointThree = clampControlPointPosition(previousCurve.getControlPointThree());
        previousCurve.setControlPointThree(controlPointThree);
    }

    /**
     * Clamps the second handle of the spline and adjusts the next curve if necessary.
     * 
     * @param spline The Bezier spline.
     * @param curveIndex The index of the curve.
     */
    private void clampSecondHandle(BezierSpline spline, int curveIndex) {
        if (curveIndex == spline.getCurveCount() - 1) return;
        CubicBezierCurve nextCurve = spline.getCurveAt(curveIndex + 1);
        double magnitude = nextCurve.getRelativeControlPointTwo().magnitude();
        Vector2 controlPointTwo = spline.getCurveAt(curveIndex).getRelativeControlPointThree().normalize().multiply(-magnitude);
        nextCurve.setRelativeControlPointTwo(controlPointTwo);
    
        Vector2 controlPointTwoNext = clampControlPointPosition(nextCurve.getControlPointTwo());
        nextCurve.setControlPointTwo(controlPointTwoNext);
    }

    /**
     * Clamps the position of a control point to stay within the bounds of the canvas.
     * 
     * @param controlPoint The control point to clamp.
     * @return The clamped control point.
     */
    private Vector2 clampControlPointPosition(Vector2 controlPoint) {
        Vector2 pixelControlPoint = controlPoint.fieldToPixel(canvas.getWidth(), canvas.getHeight());
        pixelControlPoint.setX(Math.clamp(pixelControlPoint.getX(), 0, canvas.getWidth()));
        pixelControlPoint.setY(Math.clamp(pixelControlPoint.getY(), 0, canvas.getHeight()));
        return pixelControlPoint.pixelToField(canvas.getWidth(), canvas.getHeight());
    }

    /**
     * Handles mouse press events. Determines which control point to select based on the mouse position.
     * 
     * @param event The mouse event triggered on the canvas.
     */
    public void onMousePressed(MouseEvent event) {
        Vector2 mousePosition = new Vector2(event.getX(), event.getY());
        switch (event.getButton()){
            case PRIMARY:
                handlePrimaryClick(mousePosition);
                break;
            case SECONDARY:
                handleSecondaryClick(mousePosition);
                break;
            case MIDDLE:
                handleMiddleClick(mousePosition);
                break;
            default:
                break;
        }
    }

    /**
     * Handles primary mouse button click to select a control point.
     * 
     * @param mousePosition The mouse position when clicked.
     */
    private void handlePrimaryClick(Vector2 mousePosition) {
        pointSelectedIndex = getCurveIndexInThreshold(mousePosition);
        if(pointSelectedIndex != -1) application.getAppState().setRoutineSaved(false);
    }

    private void handleSecondaryClick(Vector2 mousePosition) {
        int pointIndex = getCurveIndexInThreshold(mousePosition);
        BezierSpline currentSpline = application.getAppState().getCurrentAutoRoutine().getSpline();

        if(pointIndex != currentSpline.getCurveArray().size()*3 && pointIndex != 0) return; 

        if(pointIndex == 0){
            addCurveAtStart(mousePosition);
            drawingManager.resetAndPopulateCanvas();
            return;
        }

        int arraySize = currentSpline.getCurveArray().size();
        CubicBezierCurve curve = new CubicBezierCurve(
            new Vector2(0, 0),
            new Vector2(0, 0),
            new Vector2(0, 0),
            new Vector2(0, 0)
        );
        ArrayList<CubicBezierCurve> curveList = currentSpline.getCurveArray();
        currentSpline.addCurve(curve, curveList.size());
        curve.setControlPointTwo(curve.getControlPointOne().add(curveList.get(arraySize-1).getRelativeControlPointThree().multiply(new Vector2(-1, -1))));
        curve.setControlPointFour(new Vector2(0, 0));
        curve.setControlPointThree(curveList.get(arraySize-1).getRelativeControlPointThree().multiply(new Vector2(1, -1)));
        
        mousePosition = mousePosition.pixelToField(canvas.getWidth(), canvas.getHeight());
        curve.setControlPointFour(mousePosition);
        pointSelectedIndex = currentSpline.getCurveArray().size()*3;
        drawingManager.resetAndPopulateCanvas();

        application.getAppState().setRoutineSaved(false);
    }

    public void addCurveAtStart(Vector2 mousePosition){
        BezierSpline currentSpline = application.getAppState().getCurrentAutoRoutine().getSpline();

        CubicBezierCurve curve = new CubicBezierCurve(
            new Vector2(0, 0),
            new Vector2(0, 0),
            new Vector2(0, 0),
            new Vector2(0, 0)
        );
        ArrayList<CubicBezierCurve> curveList = currentSpline.getCurveArray();

        currentSpline.addCurve(curve, 0);
        curve.setControlPointThree(curve.getControlPointFour().add(curveList.get(1).getRelativeControlPointTwo().multiply(new Vector2(-1, -1))));
        curve.setControlPointOne(new Vector2(0, 0));
        curve.setControlPointTwo(curveList.get(1).getRelativeControlPointTwo().multiply(new Vector2(1, -1)));
        
        mousePosition = mousePosition.pixelToField(canvas);
        curve.setControlPointOne(mousePosition);

        pointSelectedIndex = 0;
        drawingManager.resetAndPopulateCanvas();
    }

    private void handleMiddleClick(Vector2 mousePosition) {
        int pointIndex = getCurveIndexInThreshold(mousePosition);
        if(pointIndex == -1) return;
        
        int curveIndex = (pointIndex)/3;
    
        int curveSize = application.getAppState().getCurrentAutoRoutine().getSpline().getCurveCount();
        if(curveIndex < 0 || curveSize == 1){
            return;
        }

        ArrayList<ActionPoint> actionPoints = application.getAppState().getCurrentAutoRoutine().getActionPoints();
        for(int index = 0; index<actionPoints.size(); index++){
            double t = actionPoints.get(index).getT();
            if(t > curveIndex-1 && t < curveIndex){
                actionPoints.remove(index);
                index--;
            } 
        }

        application.getAppState().getCurrentAutoRoutine().getSpline().removeCurve(curveIndex);
        drawingManager.resetAndPopulateCanvas();
    }

    /**
     * Handles mouse drag events to move a selected control point.
     * 
     * @param event The mouse drag event triggered on the canvas.
     */
    public void onMouseDragged(MouseEvent event) {
        if (pointSelectedIndex == -1) return;

        Vector2 mousePosition = new Vector2(event.getX(), event.getY()).clamp(canvas.getWidth(), canvas.getHeight());
        mousePosition = mousePosition.pixelToField(canvas);
        
        BezierSpline spline = application.getAppState().getCurrentAutoRoutine().getSpline();
        spline.setControlPoint(pointSelectedIndex, mousePosition);
    
        handleControlPoints(spline);
        drawingManager.resetAndPopulateCanvas();
    }

    /**
     * Resets the selected point when the mouse is released.
     * 
     * @param event The mouse release event triggered on the canvas.
     */
    public void onMouseReleased() {
        pointSelectedIndex = -1;
    }

    /**
     * Determines the index of the control point that is within the mouse click threshold.
     * 
     * @param mousePosition The mouse position when clicked.
     * @return The index of the closest control point, or -1 if none are found.
     */
    private int getCurveIndexInThreshold(Vector2 mousePos) {
        BezierSpline currentSpline = application.getAppState().getCurrentAutoRoutine().getSpline();
        ArrayList<CubicBezierCurve> list = currentSpline.getCurveArray();
        double outer_distance = canvas.getHeight() * Constants.OUTER_CONTROL_POINT_SCALE;
        double inner_distance = canvas.getHeight() * Constants.INNER_CONTROL_POINT_SCALE;
        for(int check = 0; check<2; check++){
            for (int index = list.size()-1; index > -1; index--) {
                if (pointInDistance(list.get(index).getControlPointOne(), mousePos, outer_distance) && index == 0 && check == 1) {
                    return index * 3;
                }
                if (pointInDistance(list.get(index).getControlPointTwo(), mousePos, inner_distance)) {
                    return index * 3 + 1;
                }
                if (pointInDistance(list.get(index).getControlPointThree(), mousePos, inner_distance)) {
                    return index * 3 + 2;
                }
                if (pointInDistance(list.get(index).getControlPointFour(), mousePos, outer_distance) && check == 1) {
                    return index * 3 + 3;
                }
            }
        }
        return -1;
    }

    /**
     * Checks if a point is within a distance of another point
     * @param point The point to check
     * @param mousePosition The mouse position
     * @param threshold The max distance between the two
     * @return A boolean that is True if the point are in distance
     */
    private boolean pointInDistance(Vector2 point, Vector2 mousePosition, double threshold){
        point = point.fieldToPixel(canvas);
        double distance = point.distance(mousePosition);
        return distance < threshold;
    }

}

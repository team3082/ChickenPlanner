package org.team3082.chicken_planner.AutoPlanning.TrajectoryDrawing.DrawingManagers;

import java.util.ArrayList;

import org.team3082.chicken_planner.ChickenPlannerApplication;
import org.team3082.chicken_planner.Constants;
import org.team3082.chicken_planner.AutoPlanning.AutoRoutine.ActionPoint;
import org.team3082.chicken_planner.MathUtils.BezierSpline;
import org.team3082.chicken_planner.MathUtils.CubicBezierCurve;
import org.team3082.chicken_planner.MathUtils.CurvePoint;
import org.team3082.chicken_planner.MathUtils.Vector2;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Manages the drawing operations on a canvas, specifically for rendering
 * bezier splines and control points associated with a trajectory.
 */
public class SplineDrawingManager {
    private ChickenPlannerApplication application;
    private Canvas canvas;
    private GraphicsContext gc;
   
    /**
     * Initializes the DrawingManager with a TrajectoryManager and application context.
     * Sets up the canvas and adds listeners to adjust drawing based on canvas resizing.
     * 
     * @param trajectoryManager the manager responsible for trajectory drawing
     * @param application       the main application instance
     */
    @SuppressWarnings("unused")
    public SplineDrawingManager(ChickenPlannerApplication application) {
        this.application = application;
        this.canvas = application.getField().getSplineCanvas();
        this.gc = canvas.getGraphicsContext2D();

        canvas.widthProperty().addListener((obs, oldVal, newVal) -> resetAndPopulateCanvas());
        canvas.heightProperty().addListener((obs, oldVal, newVal) -> resetAndPopulateCanvas());

        resetAndPopulateCanvas();
    }

    /**
     * Draws the bezier spline on the canvas.
     * 
     * @param spline the bezier spline to draw
     */
    private void drawSpline(BezierSpline spline) {
        double canvasWidth = canvas.getWidth();
        double canvasHeight = canvas.getHeight();
        double width = canvasHeight * Constants.LINE_WIDTH;

        gc.setLineWidth(width);
        gc.setStroke(Constants.LINE_COLOR);

        Vector2 firstPoint = spline.getCurveAt(0).getControlPointOne().pixelToField(canvasWidth, canvasHeight);
        gc.moveTo(firstPoint.getX(), firstPoint.getY());
        gc.beginPath();

        boolean isOutsideBounds = false;

        for (int index = 0; index < spline.getCurveCount(); index++) {
            CubicBezierCurve curve = spline.getCurveAt(index);

            for (CurvePoint point : curve.getPoints()) {
                Vector2 movePoint = point.getPosition().fieldToPixel(canvasWidth, canvasHeight);
                boolean outsideBounds = point.getPosition().clamp(16.542, 8.211).equals(point.getPosition());
                if(!outsideBounds){
                    if(!isOutsideBounds){
                        gc.lineTo(movePoint.getX(), movePoint.getY());
                        gc.closePath();
                        gc.stroke();
                        gc.setStroke(Color.CRIMSON);
                        gc.beginPath();
                        isOutsideBounds = true;
                    }
                } else {
                    if(isOutsideBounds){
                        gc.lineTo(movePoint.getX(), movePoint.getY());
                        gc.closePath();
                        gc.stroke();
                        gc.setStroke(Constants.LINE_COLOR);
                        gc.beginPath();
                        isOutsideBounds = false;
                    }
                }
                gc.lineTo(movePoint.getX(), movePoint.getY());
                gc.moveTo(movePoint.getX(), movePoint.getY());
            }
        }

        gc.closePath();
        gc.stroke();
    }

    /**
     * Draws the control points and lines for each segment of the bezier spline.
     * 
     * @param spline the bezier spline containing control points
     */
    private void drawControlPoints(BezierSpline spline) {
        double canvasWidth = canvas.getWidth();
        double canvasHeight = canvas.getHeight();

        for (int index = 0; index < spline.getCurveCount(); index++) {
            CubicBezierCurve curve = spline.getCurveAt(index);

            // Draw inner control lines
            drawLine(
                curve.getControlPointOne().fieldToPixel(canvasWidth, canvasHeight), 
                curve.getControlPointTwo().fieldToPixel(canvasWidth, canvasHeight), 
                Constants.INNER_CONTROL_LINE_WIDTH, 
                Constants.INNER_CONTROL_POINT_COLOR
            );

            drawLine(
                curve.getControlPointThree().fieldToPixel(canvasWidth, canvasHeight),
                curve.getControlPointFour().fieldToPixel(canvasWidth, canvasHeight), 
                Constants.INNER_CONTROL_LINE_WIDTH, 
                Constants.INNER_CONTROL_POINT_COLOR
            );

            // Draw control points
            drawCircle(
                Constants.OUTER_CONTROL_POINT_SCALE, 
                curve.getControlPointOne().fieldToPixel(canvasWidth, canvasHeight),
                Constants.OUTER_CONTROL_POINT_COLOR
            );

            drawCircle(
                Constants.INNER_CONTROL_POINT_SCALE,
                curve.getControlPointTwo().fieldToPixel(canvasWidth, canvasHeight), 
                Constants.INNER_CONTROL_POINT_COLOR
            );

            drawCircle(
                Constants.INNER_CONTROL_POINT_SCALE,
                curve.getControlPointThree().fieldToPixel(canvasWidth, canvasHeight), 
                Constants.INNER_CONTROL_POINT_COLOR
            );

            if (index == spline.getCurveCount() - 1) {
                drawCircle(
                    Constants.OUTER_CONTROL_POINT_SCALE, 
                    curve.getControlPointFour().fieldToPixel(canvasWidth, canvasHeight),
                    Constants.FINAL_CONTROL_POINT_COLOR
                );
            }
        }
    }

    /**
     * Draws a circle at a specified location on the canvas.
     * 
     * @param sizeFactor the scale factor to determine the circle size
     * @param pixelCoords the coordinates to center the circle at
     * @param color the color to fill the circle with
     */
    private void drawCircle(double sizeFactor, Vector2 pixelCoords, Color color) {
        double radius = canvas.getHeight() * sizeFactor;

        gc.setFill(color);
        gc.fillOval(pixelCoords.getX() - radius, pixelCoords.getY() - radius, radius * 2, radius * 2);
    }

    /**
     * Draws a line between two points on the canvas.
     * 
     * @param pointOne the starting point of the line
     * @param pointTwo the ending point of the line
     * @param widthScale the width scale factor of the line
     * @param color the color to draw the line with
     */
    private void drawLine(Vector2 pointOne, Vector2 pointTwo, double widthScale, Color color) {
        double width = canvas.getHeight() * widthScale;
        gc.setLineWidth(width);
        gc.setStroke(color);

        gc.beginPath();
        gc.moveTo(pointOne.getX(), pointOne.getY());
        gc.lineTo(pointTwo.getX(), pointTwo.getY());
        gc.closePath();

        gc.stroke();
    }

    /**
     * Draws the action points on the curve
     * 
     * @param spline A BezierSpline to draw the control points on
     * @param actionPoints An arraylist of action points to draw
     */
    private void drawActionPoints(BezierSpline spline, ArrayList<ActionPoint> actionPoints) {
        for(int index = 0; index<actionPoints.size(); index++){
            Vector2 pixelVector = spline.getPoint(actionPoints.get(index).getT()).fieldToPixel(canvas.getWidth(), canvas.getHeight());
            drawCircle(Constants.ACTION_POINT_SCALE, pixelVector, Constants.ACTION_POINT_COLOR);
        }
    }

    /**
     * Clears the canvas and redraws the current bezier spline, control points, robot, and action points.
     */
    public void resetAndPopulateCanvas() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        BezierSpline currentSpline = application.getAppState().getCurrentAutoRoutine().getSpline();
        drawSpline(currentSpline);
        drawControlPoints(currentSpline);
        drawActionPoints(currentSpline, application.getAppState().getCurrentAutoRoutine().getActionPoints());
    }
}

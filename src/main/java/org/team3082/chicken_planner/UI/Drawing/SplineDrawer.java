package org.team3082.chicken_planner.UI.Drawing;

import org.team3082.chicken_planner.Core.Paths.Splines.Spline;

import javafx.scene.canvas.GraphicsContext;

public interface SplineDrawer<T extends Spline> {
    void draw(GraphicsContext gc, T spline);
}
  
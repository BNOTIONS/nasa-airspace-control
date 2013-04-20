package com.bnotions.airspacecontrol.view.airport;

import android.graphics.Paint;
import android.graphics.Point;

public abstract class Route {

    public String label;
    public Point start;
    public Point end;

    public abstract Paint getPaint();

}

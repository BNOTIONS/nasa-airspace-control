package com.bnotions.airspacecontrol.entity;

import android.graphics.Paint;

public abstract class RouteConfig {

    public double[] start1;
    public double[] start2;
    public String label1;
    public String label2;

    public abstract Paint getPaint();

}

package com.bnotions.airspacecontrol.view.airport;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.View;

public class Airport extends View {

    private Config config;
    private int width;
    private int height;

    public class Config {

        public Rect area;
        public Rect apron;
        public Point[] terminals;
        public Point tower;
        public Route[] taxiways;
        public Route[] runways;

    }

    public Airport(Context context, Config config) {
        super(context);

        this.config = config;

    }

    public void setConfig(Config config) {

        this.config = config;

    }

    public Config getConfig() {

        return config;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);



    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {

        width = w;
        height = h;

    }

}

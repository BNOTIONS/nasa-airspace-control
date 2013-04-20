package com.bnotions.airspacecontrol.view.airport;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;
import com.bnotions.airspacecontrol.entity.AirportConfig;

public class Airport extends View {

    private AirportConfig config;
    private int width;
    private int height;

    public Airport(Context context, AirportConfig config) {
        super(context);

        this.config = config;

    }

    public void setConfig(AirportConfig config) {

        this.config = config;

    }

    public AirportConfig getConfig() {

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

package com.bnotions.airspacecontrol.view.airport;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;
import com.bnotions.airspacecontrol.entity.AirportConfig;

public class AirportView extends View {

    private static final int COLOR_GRASS = 0xff729a3b;

    private AirportConfig config;
    private int width;
    private int height;

    public AirportView(Context context, AirportConfig config) {
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

        canvas.drawColor(COLOR_GRASS);

        if (config == null) return;



    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {

        width = w;
        height = h;

    }

}

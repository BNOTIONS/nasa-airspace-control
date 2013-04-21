package com.bnotions.airspacecontrol.view.airport;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;
import com.bnotions.airspacecontrol.R;
import com.bnotions.airspacecontrol.entity.AirportConfig;

public class AirportView extends View {

    private static final int COLOR_GRASS = 0xff729a3b;
    private static final int COLOR_APRON = 0xff696969;
    private static final int COLOR_RUNWAY = 0xff000000;

    private static final int RUNWAY_WIDTH = 20;

    private AirportConfig config;
    private int width;
    private int height;

    private Bitmap bm_terminal;
    private Bitmap bm_tower;

    private Paint paint_apron;
    private Paint paint_runway;

    public AirportView(Context context, AirportConfig config) {
        super(context);

        this.config = config;

        initBitmaps(context);
        initPaints();

    }

    private void initBitmaps(Context context) {

        bm_terminal = BitmapFactory.decodeResource(context.getResources(), R.drawable.icon);
        bm_tower = BitmapFactory.decodeResource(context.getResources(), R.drawable.icon);

    }

    private void initPaints() {

        paint_apron = new Paint();
        paint_apron.setColor(COLOR_APRON);
        paint_apron.setStyle(Paint.Style.FILL);

        paint_runway = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint_runway.setColor(COLOR_RUNWAY);
        paint_runway.setStyle(Paint.Style.STROKE);
        paint_runway.setStrokeWidth(RUNWAY_WIDTH);

    }

    public void setConfig(AirportConfig config) {

        this.config = config;
        invalidate();

    }

    public AirportConfig getConfig() {

        return config;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(COLOR_GRASS);

        if (config == null) return;

        drawApron(canvas);
        //drawTower(canvas);
        //drawTerminals(canvas);
        //drawRunways(canvas);

    }

    private void drawApron(Canvas canvas) {

        canvas.drawPath(config.apron, paint_apron);

    }

    private void drawTower(Canvas canvas) {

        canvas.drawBitmap(bm_tower, config.tower[0], config.tower[1], null);

    }

    private void drawTerminals(Canvas canvas) {

        for (int i = 0; i < config.terminals.length; i++) {
            int[] coordinates = config.terminals[i];
            canvas.drawBitmap(bm_terminal, coordinates[0], coordinates[0], null);
        }

    }

    private void drawRunways(Canvas canvas) {

        for (int i = 0; i < config.runways.length; i++) {
            AirportConfig.Runway runway = config.runways[i];
            canvas.drawLine(runway.latitude1, runway.longitude1,
                    runway.latitude2, runway.longitude2, paint_runway);
        }

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {

        width = w;
        height = h;

    }

}

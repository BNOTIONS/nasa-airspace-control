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

    private AirportConfig config;
    private int width;
    private int height;

    private Bitmap bm_terminal;
    private Bitmap bm_tower;

    private Paint paint_apron;

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

        drawApron(canvas);
        drawTower(canvas);
        drawTerminals(canvas);

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

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {

        width = w;
        height = h;

    }

}

package com.bnotions.airspacecontrol.view.airport;

import android.content.Context;
import android.graphics.*;
import android.view.View;
import com.bnotions.airspacecontrol.R;
import com.bnotions.airspacecontrol.entity.AirportConfig;

import java.util.ArrayList;

public class AirportView extends View {

    private static final int COLOR_GRASS = 0xff729a3b;
    private static final int COLOR_APRON = 0xff696969;
    private static final int COLOR_RUNWAY = 0xff000000;
    private static final int COLOR_TAXIWAY = 0xff696969;
    private static final int COLOR_LABEL = 0xff000000;

    private static final int RUNWAY_WIDTH = 50;
    private static final int TAXIWAY_WIDTH = 15;
    private static final float LABEL_SIZE = 28.0f;

    private AirportConfig config;
    private int width;
    private int height;

    private Bitmap bm_terminal;
    private Bitmap bm_tower;
    private Bitmap bm_aircraft;

    private Paint paint_apron;
    private Paint paint_runway;
    private Paint paint_taxiway;
    private Paint paint_label;

    private ArrayList<Aircraft> planes;

    public AirportView(Context context, AirportConfig config) {
        super(context);

        this.config = config;

        initBitmaps(context);
        initPaints();
        initObjects();

    }

    private void initBitmaps(Context context) {

        bm_terminal = BitmapFactory.decodeResource(context.getResources(), R.drawable.icon);
        bm_tower = BitmapFactory.decodeResource(context.getResources(), R.drawable.img_tower);
        bm_aircraft = BitmapFactory.decodeResource(context.getResources(), R.drawable.img_small_plane);

    }

    private void initPaints() {

        paint_apron = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint_apron.setColor(COLOR_APRON);
        paint_apron.setStyle(Paint.Style.FILL);

        paint_runway = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint_runway.setColor(COLOR_RUNWAY);
        paint_runway.setStyle(Paint.Style.STROKE);
        paint_runway.setStrokeWidth(RUNWAY_WIDTH);

        paint_taxiway = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint_taxiway.setColor(COLOR_TAXIWAY);
        paint_taxiway.setStyle(Paint.Style.STROKE);
        paint_taxiway.setStrokeWidth(TAXIWAY_WIDTH);

        paint_label = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint_label.setColor(COLOR_LABEL);
        paint_label.setTextSize(LABEL_SIZE);

    }

    private void initObjects() {

        planes = new ArrayList<Aircraft>();
        planes.add(new Aircraft());

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
        canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.img_grass_texture), 0, 0, new Paint());

        if (config == null) return;

        drawApron(canvas);
        drawTower(canvas);
        //drawTerminals(canvas);
        drawRunways(canvas);
        drawTaxiways(canvas);

        drawAircraft(canvas);

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
            canvas.drawLine(runway.longitude1, runway.latitude1,
                    runway.longitude2, runway.latitude2, paint_runway);
            canvas.drawText(runway.name1, runway.longitude1 - 20, runway.latitude1 - 25, paint_label);
            canvas.drawText(runway.name2, runway.longitude2 - 20, runway.latitude2 + 35, paint_label);
        }

    }

    private void drawTaxiways(Canvas canvas) {

        for (int i = 0; i < config.taxiways.length; i++) {
            AirportConfig.Taxiway taxiway = config.taxiways[i];
            Path path = taxiway.points;
            canvas.drawPath(path, paint_taxiway);
        }

    }

    private void drawAircraft(Canvas canvas) {

        if (planes == null) return;

        for (int i = 0; i < planes.size(); i++) {
            Aircraft aircraft = planes.get(i);
            canvas.drawBitmap(bm_aircraft, aircraft.x, aircraft.y, null);
        }

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {

        width = w;
        height = h;

    }

}

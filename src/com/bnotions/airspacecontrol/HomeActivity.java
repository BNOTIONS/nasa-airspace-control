package com.bnotions.airspacecontrol;

import android.app.Activity;
import android.graphics.Path;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.bnotions.airspacecontrol.adapter.AlertsAdapter;
import com.bnotions.airspacecontrol.entity.AirportConfig;
import com.bnotions.airspacecontrol.entity.Alert;
import com.bnotions.airspacecontrol.view.airport.AirportView;

import java.util.ArrayList;


public class HomeActivity extends Activity implements View.OnClickListener {

    private ListView listview_alerts;
    private AlertsAdapter adapter_alerts;
    private FrameLayout layout_canvas;
    private AirportView airport;
    private ImageButton btn_weather;
    private RelativeLayout layout_weather_dialog;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.activity_main);
        
        initializeUi();
    }

    private void initializeUi() {
        
        adapter_alerts = new AlertsAdapter(this, getTestAlerts());
        listview_alerts = (ListView) findViewById(R.id.listview_alerts);
        listview_alerts.setAdapter(adapter_alerts);
        layout_canvas = (FrameLayout) findViewById(R.id.layout_canvas);

        airport = new AirportView(this, null);
        layout_canvas.addView(airport, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        airport.post(new Runnable() {

            @Override
            public void run() {

                AirportConfig config = new AirportConfig();
                Path path = new Path();
                path.moveTo(0, 0);
                path.lineTo(0, 600);
                path.lineTo(600, 600);
                path.lineTo(600, 0);
                path.lineTo(0, 0);
                config.apron = path;

                config.runways = new AirportConfig.Runway[2];

                AirportConfig.Runway runway1 = config.new Runway();
                runway1.latitude1 = 200;
                runway1.longitude1 = 1000;
                runway1.latitude2 = 1000;
                runway1.longitude2 = 1000;
                config.runways[0] = runway1;

                AirportConfig.Runway runway2 = config.new Runway();
                runway2.latitude1 = 200;
                runway2.longitude1 = 1500;
                runway2.latitude2 = 1000;
                runway2.longitude2 = 1500;
                config.runways[1] = runway2;

                config.taxiways = new AirportConfig.Taxiway[4];

                AirportConfig.Taxiway taxiway1 = config.new Taxiway();
                taxiway1.points = new Path();
                taxiway1.points.moveTo(600, 200);
                taxiway1.points.lineTo(1000, 200);
                config.taxiways[0] = taxiway1;

                AirportConfig.Taxiway taxiway2 = config.new Taxiway();
                taxiway2.points = new Path();
                taxiway2.points.moveTo(1000, 200);
                taxiway2.points.lineTo(1500, 200);
                config.taxiways[1] = taxiway2;

                AirportConfig.Taxiway taxiway3 = config.new Taxiway();
                taxiway3.points = new Path();
                taxiway3.points.moveTo(600, 600);
                taxiway3.points.lineTo(1000, 1000);
                config.taxiways[2] = taxiway3;

                AirportConfig.Taxiway taxiway4 = config.new Taxiway();
                taxiway4.points = new Path();
                taxiway4.points.moveTo(1000, 1000);
                taxiway4.points.lineTo(1500, 1000);
                config.taxiways[3] = taxiway4;

                airport.setConfig(config);
            }
        });

        btn_weather = (ImageButton) findViewById(R.id.btn_weather);
        btn_weather.setOnClickListener(this);

        layout_weather_dialog = (RelativeLayout) findViewById(R.id.layout_weather_dialog);
    }

    private ArrayList<Alert> getTestAlerts() {

        ArrayList<Alert> list_alerts = new ArrayList<Alert>();
        list_alerts.add(new Alert(12, "When the lights are off, get under the desk.", Alert.STATUS_ORANGE));
        list_alerts.add(new Alert(12, "When the desks are flipped, hide behind it.", Alert.STATUS_RED));
        list_alerts.add(new Alert(12, "When there's a hole in the desk, panic.", Alert.STATUS_RED));
        list_alerts.add(new Alert(12, "If there's a black desk, it's better to hide there.", Alert.STATUS_GREEN));
        list_alerts.add(new Alert(12, "Turn off your cell phone or any electronic device.", Alert.STATUS_ORANGE));
        list_alerts.add(new Alert(12, "Do not Scream.", Alert.STATUS_ORANGE));
        list_alerts.add(new Alert(12, "When the lights are off, get under the desk.", Alert.STATUS_ORANGE));
        list_alerts.add(new Alert(12, "When the desks are flipped, hide behind it.", Alert.STATUS_RED));
        list_alerts.add(new Alert(12, "When there's a hole in the desk, panic.", Alert.STATUS_RED));
        list_alerts.add(new Alert(12, "If there's a black desk, it's better to hide there.", Alert.STATUS_GREEN));
        list_alerts.add(new Alert(12, "Turn off your cell phone or any electronic device.", Alert.STATUS_ORANGE));
        list_alerts.add(new Alert(12, "Do not Scream.", Alert.STATUS_ORANGE));
        list_alerts.add(new Alert(12, "When the lights are off, get under the desk.", Alert.STATUS_ORANGE));
        list_alerts.add(new Alert(12, "When the desks are flipped, hide behind it.", Alert.STATUS_RED));
        list_alerts.add(new Alert(12, "When there's a hole in the desk, panic.", Alert.STATUS_RED));
        list_alerts.add(new Alert(12, "If there's a black desk, it's better to hide there.", Alert.STATUS_GREEN));
        list_alerts.add(new Alert(12, "Turn off your cell phone or any electronic device.", Alert.STATUS_ORANGE));
        list_alerts.add(new Alert(12, "Do not Scream.", Alert.STATUS_ORANGE));

        return list_alerts;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btn_weather:

                ShowWeatherDialog();
                break;
        }
    }

    private void ShowWeatherDialog() {

        layout_weather_dialog.setVisibility(View.VISIBLE);
    }
}

package com.bnotions.airspacecontrol;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;
import com.bnotions.airspacecontrol.adapter.AlertsAdapter;
import com.bnotions.airspacecontrol.entity.AirportConfig;
import com.bnotions.airspacecontrol.entity.AirportConfigBuilder;
import com.bnotions.airspacecontrol.entity.Alert;
import com.bnotions.airspacecontrol.view.airport.AirportView;

import java.util.ArrayList;


public class HomeActivity extends Activity {

    private ListView listview_alerts;
    private AlertsAdapter adapter_alerts;
    private FrameLayout layout_canvas;
    private AirportView airport;

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
                AirportConfigBuilder builder = new AirportConfigBuilder(airport.getWidth(), airport.getHeight());
                double[][] apron = new double[4][2];
                apron[0][0] = 43.674577;
                apron[0][1] = -79.611912;
                apron[1][0] = 43.682026;
                apron[1][1] = -79.598694;
                apron[2][0] = 43.698038;
                apron[2][1] = -79.619808;
                apron[3][0] = 43.690467;
                apron[3][1] = -79.633198;
                double[] lats = new double[4];
                lats[0] = 43.673707;
                lats[1] = 43.660855;
                lats[2] = 43.679108;
                lats[3] = 43.698038;
                double[] longs = new double[4];
                longs[0] = -79.664183;
                longs[1] = -79.623585;
                longs[2] = -79.596634;
                longs[3] = -79.619808;
                AirportConfig config = builder.build(lats, longs).setApron(apron).create();
                airport.setConfig(config);
            }
        });

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
}

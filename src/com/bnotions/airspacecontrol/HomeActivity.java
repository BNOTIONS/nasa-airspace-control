package com.bnotions.airspacecontrol;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.bnotions.airspacecontrol.adapter.AlertsAdapter;
import com.bnotions.airspacecontrol.adapter.ScrollingStatusAdapter;
import com.bnotions.airspacecontrol.entity.Alert;
import com.bnotions.airspacecontrol.view.airport.AirportView;

import java.util.ArrayList;


public class HomeActivity extends Activity {

    private ListView listview_alerts;
    private AlertsAdapter adapter_alerts;
    private FrameLayout layout_canvas;
    private AirportView airport;
    private ScrollingStatusAdapter adapter_scrolling_stats;

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


        TextView txt_stat1_bottom = (TextView) findViewById(R.id.txt_stat1_bottom);
        TextView txt_stat2_bottom = (TextView) findViewById(R.id.txt_stat2_bottom);
        ArrayList<String> list_texts = getTestStatTexts();
        adapter_scrolling_stats = new ScrollingStatusAdapter(this, txt_stat1_bottom, txt_stat2_bottom);
        adapter_scrolling_stats.setListTexts(list_texts);
        adapter_scrolling_stats.startAnimation();

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

    private ArrayList<String> getTestStatTexts() {

        ArrayList<String> list_texts = new ArrayList<String>();
        list_texts.add("This a really awesome text that is scrolling.");
        list_texts.add("When it's scrolling, it looks good.");
        return list_texts;
    }
}

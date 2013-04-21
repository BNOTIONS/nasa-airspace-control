package com.bnotions.airspacecontrol.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.bnotions.airspacecontrol.R;
import com.bnotions.airspacecontrol.entity.Alert;

import java.util.ArrayList;


/**
 * Created with IntelliJ IDEA.
 * User: pirdod
 * Date: 4/20/13
 * Time: 6:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class AlertsAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Alert> list_alerts;


    public AlertsAdapter(Context context, ArrayList<Alert> list_alert) {

        this.context = context;
        this.list_alerts = list_alert;
    }

    @Override
    public int getCount() {

        return list_alerts.size();
    }

    @Override
    public Object getItem(int position) {

        if (list_alerts != null) return list_alerts.get(position);
        else return null;
    }

    @Override
    public long getItemId(int position) {

        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View root_view = convertView;
        if (root_view == null) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            root_view = (View) inflater.inflate(R.layout.item_alert, null);
        }

        updateRowView(root_view, position);

        return root_view;
    }

    private void updateRowView(View root_view, int position) {

        View view_status_color = root_view.findViewById(R.id.view_status_color);
        TextView txt_alert = (TextView) root_view.findViewById(R.id.txt_alert);

        Alert alert = list_alerts.get(position);

        txt_alert.setText(alert.getText());
        int status_color = alert.getStatusColor();
        switch (status_color) {

            case Alert.STATUS_GREEN:
                view_status_color.setBackgroundResource(R.color.alert_green_stat);
                break;
            case Alert.STATUS_ORANGE:
                view_status_color.setBackgroundResource(R.color.alert_orange_stat);
                break;
            case Alert.STATUS_RED:
                view_status_color.setBackgroundResource(R.color.alert_red_stat);
                break;
        }
    }
}

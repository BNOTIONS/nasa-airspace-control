package com.bnotions.airspacecontrol.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
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
    private Alert selected_alert;
    private View selected_view;
    private int selected_position;

    private int default_text_paint_flags;


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
            TextView txt_alert = (TextView) root_view.findViewById(R.id.txt_alert);
            default_text_paint_flags = txt_alert.getPaint().getFlags();
        }

        resetRow(root_view);
        updateRowView(root_view, position);

        return root_view;
    }

    private void resetRow(View root_view) {

        View view_status_color = root_view.findViewById(R.id.view_status_color);
        TextView txt_alert = (TextView) root_view.findViewById(R.id.txt_alert);

        txt_alert.getPaint().setFlags(default_text_paint_flags);
        txt_alert.setTextColor(context.getResources().getColor(android.R.color.white));
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

        if (alert.isCompleted()) setStriked(root_view);
    }

    private void setStriked(View root_view) {

        TextView txt_alert = (TextView) root_view.findViewById(R.id.txt_alert);
        txt_alert.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        txt_alert.setTextColor(R.color.txt_striked_through);
    }

    public void showActionPanelWithAlert(RelativeLayout layout_panel_bottom, View view, int position) {

        selected_alert = list_alerts.get(position);
        selected_view = view;
        selected_position = position;
        if (!selected_alert.isCompleted()) layout_panel_bottom.setVisibility(View.VISIBLE);
    }

    public void confirmButtonClicked(RelativeLayout layout_panel_bottom) {

        layout_panel_bottom.setVisibility(View.GONE);
        TextView txt_alert = (TextView) selected_view.findViewById(R.id.txt_alert);
        txt_alert.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        txt_alert.setTextColor(R.color.txt_striked_through);
        selected_alert.setCompleted(true);
        list_alerts.remove(selected_position);
        list_alerts.add(selected_alert);
        notifyDataSetChanged();
    }

    public void denyButtonClicked(RelativeLayout layout_panel_bottom) {

        layout_panel_bottom.setVisibility(View.GONE);
    }
}

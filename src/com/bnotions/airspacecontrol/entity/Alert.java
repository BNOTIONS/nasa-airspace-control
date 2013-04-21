package com.bnotions.airspacecontrol.entity;

import android.os.Handler;


/**
 * Created with IntelliJ IDEA.
 * User: pirdod
 * Date: 4/20/13
 * Time: 6:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class Alert {

    public static final int STATUS_GREEN = 223356;
    public static final int STATUS_ORANGE = 223367;
    public static final int STATUS_RED = 223378;

    public static final int DEFAULT_ORANGE_DELAY = 10000;
    public static final int DEFAULT_RED_DELAY = 8000;

    private int id;
    private String text;
    private int status_color;
    private boolean completed;
    private int orange_delay;
    private int red_delay;

    private AlertTimerListener time_listener;

    public Alert(int id, String text, int status_color, int orange_delay, int red_delay) {

        this.id = id;
        this.text = text;
        this.status_color = status_color;
        this.orange_delay = orange_delay;
        this.red_delay = red_delay;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {

        this.id = id;
    }

    public String getText() {

        return text;
    }

    public void setText(String text) {

        this.text = text;
    }

    public int getStatusColor() {

        return status_color;
    }

    public void setStatusColor(int status_color) {

        this.status_color = status_color;
    }

    public int getOrangeDelay() {

        return orange_delay;
    }

    public void setOrangeDelay(int orange_delay) {

        this.orange_delay = orange_delay;
    }

    public int getRedDelay() {

        return red_delay;
    }

    public void setRedDelay(int red_delay) {

        this.red_delay = red_delay;
    }

    public boolean isCompleted() {

        return completed;
    }

    public void setCompleted(boolean completed) {

        this.completed = completed;
    }

    public void setTimerListener(AlertTimerListener time_listener) {

        this.time_listener = time_listener;
        startTimer();
    }

    public AlertTimerListener getTimeListener() {

        return time_listener;
    }

    private void startTimer() {

        final Handler handler = new Handler();
        final Runnable runnable_red = new Runnable() {

            @Override
            public void run() {

                setStatusColor(STATUS_RED);
                if (time_listener != null) time_listener.onColorChange();
            }
        };
        final Runnable runnable_orange = new Runnable() {

            @Override
            public void run() {

                setStatusColor(STATUS_ORANGE);
                if (time_listener != null) time_listener.onColorChange();
                handler.postDelayed(runnable_red, getRedDelay());
            }
        };

        handler.postDelayed(runnable_orange, getOrangeDelay());
    }

    public interface AlertTimerListener {

        public void onColorChange();
    }
}

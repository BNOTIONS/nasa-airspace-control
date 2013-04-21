package com.bnotions.airspacecontrol.entity;

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

    private int id;
    private String text;
    private int status_color;
    private boolean completed;

    public Alert(int id, String text, int status_color) {

        this.id = id;
        this.text = text;
        this.status_color = status_color;
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

    public boolean isCompleted() {

        return completed;
    }

    public void setCompleted(boolean completed) {

        this.completed = completed;
    }
}

package com.bnotions.airspacecontrol.api;

public interface ParseCallback<T> {

    public void onComplete(T data);
    public void onFailure();

}

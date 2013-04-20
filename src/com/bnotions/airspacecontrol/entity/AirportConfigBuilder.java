package com.bnotions.airspacecontrol.entity;

import android.graphics.Point;

public class AirportConfigBuilder {

    public AirportConfig config;

    public AirportConfigBuilder() {

    }

    public AirportConfigBuilder build() {

        config = new AirportConfig();

        return this;
    }

    public AirportConfigBuilder setCenter(double latitude, double longitude) {

        return this;
    }

    public AirportConfigBuilder setApron(double[] apron) {

        return this;
    }

    public AirportConfigBuilder setTower(double latitude, double longitude) {

        return this;
    }

    public AirportConfigBuilder setTerminals(double[] terminals) {

        return this;
    }

    public AirportConfigBuilder setRunways(String[] labels, Point[][] starts) {

        return this;
    }

    public AirportConfigBuilder setTaxiways(String[] labels, Point[][] starts) {

        return this;
    }

    public AirportConfig create() {

        return config;
    }

}

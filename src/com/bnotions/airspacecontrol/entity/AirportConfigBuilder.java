package com.bnotions.airspacecontrol.entity;

import android.graphics.Path;

public class AirportConfigBuilder {

    private static final boolean DEBUG = true;

    private AirportConfig config;
    private double width = 0;
    private double height = 0;
    private double ratio;
    private double min_lat;
    private double min_long;

    public AirportConfigBuilder(double width, double height) {

        this.width = width;
        this.height = height;

    }

    /**
     * @param lats All latitudes of airport components
     * @param longs All longitudes of airport components
     * @return
     */
    public AirportConfigBuilder build(double[] lats, double[] longs) {

        config = new AirportConfig();
        config.center = new int[] {(int) (width / 2), (int) (height / 2)};

        double[] lat_minmax = getMinMax(lats);
        double[] long_minmax = getMinMax(longs);

        double lat_delta = lat_minmax[1] - lat_minmax[0];
        double long_delta = long_minmax[1] - long_minmax[0];
        if (lat_delta > long_delta) {
            ratio = width / lat_delta;
            min_lat = lat_minmax[0];
            min_long = long_minmax[0] - (((height - (long_delta * ratio)) / 2.0d) / ratio);
        } else {
            ratio = height / long_delta;
            min_lat = lat_minmax[0] - (((width - (lat_delta * ratio)) / 2.0d) / ratio);
            min_long = long_minmax[0];
        }

        return this;
    }

    /**
     * @param coordinates Set of coordinates to use, which must be on only one axis
     * @return An array with a minimum at index 0 and a maximum at index 1
     */
    private double[] getMinMax(double[] coordinates) {

        double[] min_max = new double[2];
        min_max[0] = coordinates[0];
        min_max[1] = coordinates[0];
        for (int i = 0; i < coordinates.length; i++) {
            if (coordinates[i] < min_max[0]) {
                min_max[0] = coordinates[i];
            }
            if (coordinates[i] > min_max[1]) {
                min_max[1] = coordinates[i];
            }
        }

        return min_max;
    }

    public AirportConfigBuilder setApron(double[][] apron) {

        int count = apron.length;
        config.apron = new Path();
        for (int i = 0; i < count; i++) {
            int x = (int) ((apron[i][0] - min_lat) * ratio);
            int y = (int) ((apron[i][1] - min_long) * ratio);
            config.apron.lineTo(x, y);
        }
        int x = (int) ((apron[0][0] - min_lat) * ratio);
        int y = (int) ((apron[0][1] - min_long) * ratio);
        config.apron.lineTo(x, y);

        return this;
    }

    public AirportConfigBuilder setTower(double latitude, double longitude) {

        config.tower = new int[] {
                (int) ((latitude - min_lat) * ratio),
                (int) ((longitude - min_long) * ratio)
        };

        return this;
    }

    public AirportConfigBuilder setTerminals(double[][] terminals) {

        int count = terminals.length;
        config.terminals = new int[count][2];
        for (int i = 0; i < count; i++) {
            config.terminals[i][0] = (int) (terminals[i][0] * ratio);
            config.terminals[i][1] = (int) (terminals[i][1] * ratio);
        }

        return this;
    }

    public AirportConfigBuilder setRunways(String[][] labels, double[][][] starts) {

        int count = labels.length;
        config.runways = new AirportConfig.Runway[count];
        for (int i = 0; i < count; i++) {
            AirportConfig.Runway runway = config.new Runway();
            runway.name1 = labels[i][0];
            double[] start1 = starts[i][0];
            runway.latitude1 = (int) (start1[0] * ratio);
            runway.longitude1 = (int) (start1[1] * ratio);

            runway.name2 = labels[i][1];
            double[] start2 = starts[i][1];
            runway.latitude2 = (int) (start2[0] * ratio);
            runway.longitude2 = (int) (start2[1] * ratio);

            config.runways[i] = runway;
        }

        return this;
    }

    public AirportConfigBuilder setTaxiways(String[] labels, double[][][] boundaries) {

        int count = labels.length;
        config.taxiways = new AirportConfig.Taxiway[count];
        for (int i = 0; i < count; i++) {
            AirportConfig.Taxiway taxiway = config.new Taxiway();
            taxiway.name = labels[i];
            double[][] taxiway_boundaries = boundaries[i];
            for (int x = 0; x < taxiway_boundaries.length; x++) {
                double[] coordinates = taxiway_boundaries[x];
//                taxiway.boundary[x] = new int[] {
//                        (int) (coordinates[0] * ratio),
//                        (int) (coordinates[1] * ratio)
//                    };
            }
            config.taxiways[i] = taxiway;
        }

        return this;
    }

    public AirportConfigBuilder setTaxiRoutes(int[] runway_ids, String[] runway_names,
                                              double[][][] points, boolean[][] holds, int[][] path_runway_ids) {

        int count = runway_ids.length;
        config.taxiroutes = new AirportConfig.TaxiRoute[count];
        for (int i = 0; i < count; i++) {
            AirportConfig.TaxiRoute taxiroute = config.new TaxiRoute();
            taxiroute.runway_id = runway_ids[i];
            taxiroute.runway_name = runway_names[i];
            double[][] taxiroute_points = points[i];
            for (int x = 0; x < taxiroute_points.length; x++) {
                double[] coordinates = taxiroute_points[x];
                AirportConfig.TaxiRoute.Path path = taxiroute.new Path();
                path.hold = holds[i][x];
                path.runway_id = path_runway_ids[x][i];
                path.latitude = (int) (coordinates[0] * ratio);
                path.longitude = (int) (coordinates[1] * ratio);
                taxiroute.path[x] = path;
            }
            config.taxiroutes[i] = taxiroute;
        }

        return this;
    }

    public AirportConfig create() {

        return config;
    }

}

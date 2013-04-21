package com.bnotions.airspacecontrol.entity;

import android.graphics.Path;

public class AirportConfig {

    public int[] center;
    public Path apron;
    public int[] tower;
    public int[][] terminals;
    public Runway[] runways;
    public Taxiway[] taxiways;
    public TaxiRoute[] taxiroutes;

    public class Taxiway {

        public Taxiway() {

        }

        public String name;
        public int[][] boundary;

    }

    public class Runway {

        public Runway() {

        }

        public int id;
        public String name1;
        public int latitude1;
        public int longitude1;
        public String name2;
        public int latitude2;
        public int longitude2;

    }

    public class TaxiRoute {

        public TaxiRoute() {

        }

        public class Path {

            public double latitude;
            public double longitude;
            public boolean hold;
            public int runway_id;

        }

        public int runway_id;
        public String runway_name;
        public Path[] path;

    }

}

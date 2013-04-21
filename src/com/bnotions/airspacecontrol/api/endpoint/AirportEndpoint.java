package com.bnotions.airspacecontrol.api.endpoint;

import android.util.Pair;
import com.bnotions.airspacecontrol.api.RequestInfo;
import com.bnotions.airspacecontrol.api.RequestInfoBuilder;
import com.bnotions.airspacecontrol.util.Environment;

public class AirportEndpoint {

    private static final String ENDPOINT_AIRPORTS = "airports/";

    public static RequestInfo createAirportRequest(int airport) {

        return new RequestInfoBuilder()
                .setServer(Environment.BASE_URL)
                .setEndpoint(ENDPOINT_AIRPORTS + airport)
                .setMethod(RequestInfoBuilder.GET)
                .build();

    }

}

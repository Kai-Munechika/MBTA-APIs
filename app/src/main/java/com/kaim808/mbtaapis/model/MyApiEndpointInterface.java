package com.kaim808.mbtaapis.model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by KaiM on 3/25/17.
 */

public interface MyApiEndpointInterface {


    String mApiKey = "lASN3s3xhEC_0_BGeXgURQ";

    //http://realtime.mbta.com/developer/api/v2/routes?api_key=lASN3s3xhEC_0_BGeXgURQ&format=json

    @GET("routes?api_key=" + mApiKey)
    Call<RoutesRoot> getRoutesRoot(@Query("format") String format);

    //http://realtime.mbta.com/developer/api/v2/stopsbyroute?api_key=wX9NwuHnZU2ToO7GmGR9uw&format=json&route=57

    @GET("stopsbyroute?api_key=" + mApiKey + "&format=json")
    Call<StopsByRouteRoot> getStopsByRouteRoot(@Query("route") String route);

    //http://realtime.mbta.com/developer/api/v2/schedulebyroutes?api_key=wX9NwuHnZU2ToO7GmGR9uw&format=json&max_time=1440&max_trips=1&routes=116

    @GET("schedulebyroutes?api_key=" + mApiKey + "&format=json&max_time=1440&max_trips=1")
    Call<HeadsignRoot> getHeadsignRoot(@Query("routes") String route);

}

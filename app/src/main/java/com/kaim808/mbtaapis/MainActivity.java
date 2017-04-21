package com.kaim808.mbtaapis;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.kaim808.mbtaapis.model.Direction;
import com.kaim808.mbtaapis.model.HeadsignRoot;
import com.kaim808.mbtaapis.model.Mode;
import com.kaim808.mbtaapis.model.MyApiEndpointInterface;
import com.kaim808.mbtaapis.model.Route;
import com.kaim808.mbtaapis.model.RoutesRoot;
import com.kaim808.mbtaapis.model.Stop;
import com.kaim808.mbtaapis.model.StopsByRouteRoot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "KAIKAI";
    public static final String mBASE_URL = "http://realtime.mbta.com/developer/api/v2/";
    public static final String mApiKey = "lASN3s3xhEC_0_BGeXgURQ";

    public RoutesRoot mRouteRoot;
    public StopsByRouteRoot mStopsByRouteRoot;
    public HeadsignRoot mHeadsignRoot;

    @BindView(R.id.routes_spinner)
    Spinner mRoutesSpinner;
    @BindView(R.id.direction_spinner)
    Spinner mDirectionSpinner;
    @BindView(R.id.starting_stop)
    Spinner mStartingStopSpinner;
    @BindView(R.id.destination_stop)
    Spinner mDestinationStopSpinner;
    @BindView(R.id.modes_spinner)
    Spinner mModesSpinner;


    private ArrayList<String> mModeNames;
    private ArrayList<String> mModeIds;
    private ArrayList<String> mRouteNames;
    private ArrayList<String> mRouteIds;
    private ArrayList<String> mDirectionNames;
    private ArrayList<String> mDirectionIds;
    private ArrayList<String> mStops0;
    private ArrayList<String> mStops1;
    private ArrayList<String> mDirectionWithHeadSignNames;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Retrofit retrofit = new Retrofit.Builder().baseUrl(mBASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        final MyApiEndpointInterface apiService = retrofit.create(MyApiEndpointInterface.class);

        // nested within routeEnqueue is every other API call to populate the rest of the spinners
        routeEnqueue(apiService);

    }


    // makes route call and updates route spinner
    private void routeEnqueue(MyApiEndpointInterface apiService) {
        String format = "json";

        Call<RoutesRoot> routesCall = apiService.getRoutesRoot(format);
        routesCall.enqueue(new Callback<RoutesRoot>() {
            @Override
            public void onResponse(Call<RoutesRoot> call, Response<RoutesRoot> response) {
                int statusCode = response.code();

                mRouteRoot = response.body();
                mModeNames = new ArrayList<>();
                mModeIds = new ArrayList<>();

                List<Mode> modes = mRouteRoot.getMode();
                for (Mode mode : modes) {
                    if (mModeNames.contains(mode.getModeName())) {
                        mModeNames.add(mode.getModeName() + " " + (Collections.frequency(mModeNames, mode.getModeName()) + 1));
                    }
                    else {
                        mModeNames.add(mode.getModeName());
                    }
                    mModeIds.add(mode.getRouteType());
                }
                ArrayAdapter<String> modesSpinnerAdapter = new ArrayAdapter<String>(MainActivity.this, R.layout.support_simple_spinner_dropdown_item, mModeNames);
                mModesSpinner.setAdapter(modesSpinnerAdapter);

                mModesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        List<Route> routes = mRouteRoot.getMode().get(i).getRoute();

                        mRouteNames = new ArrayList<>(routes.size());
                        mRouteIds = new ArrayList<>(routes.size());

                        for (Route route : routes) {
                            mRouteNames.add(route.getRouteName());
                            mRouteIds.add(route.getRouteId());
                        }

                        ArrayAdapter<String> routeSpinnerAdapter = new ArrayAdapter<>(MainActivity.this, R.layout.support_simple_spinner_dropdown_item, mRouteNames);
                        mRoutesSpinner.setAdapter(routeSpinnerAdapter);

                        mRoutesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                directionAndStopsEnqueue(apiService, mRouteIds.get(i));
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {
                            }
                        });
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {}
                });



            }

            @Override
            public void onFailure(Call<RoutesRoot> call, Throwable t) {
                Log.e(TAG, "onFailure called in routeEnqueue in " + MainActivity.this.getClass().getSimpleName());
            }
        });
    }

    private void directionAndStopsEnqueue(MyApiEndpointInterface apiService, String routeId) {
        Call<StopsByRouteRoot> stopsByRouteRootCall = apiService.getStopsByRouteRoot(routeId);
        stopsByRouteRootCall.enqueue(new Callback<StopsByRouteRoot>() {
            @Override
            public void onResponse(Call<StopsByRouteRoot> call, Response<StopsByRouteRoot> response) {
                int statuscode = response.code();

                mStopsByRouteRoot = response.body();

                mDirectionNames = new ArrayList<>();
                mDirectionIds = new ArrayList<>();
                mStops0 = new ArrayList<>();
                mStops1 = new ArrayList<>();

                List<Direction> directions = mStopsByRouteRoot.getDirection();

                for (Direction direction : directions) {
                    mDirectionNames.add(direction.getDirectionName());
                    mDirectionIds.add(direction.getDirectionId());

                    List<Stop> stops = direction.getStop();

                    if (direction.getDirectionId().equals("0")) {
                        for (Stop stop : stops) {
                            mStops0.add(stop.getStopName());
                        }
                    } else {
                        for (Stop stop : stops) {
                            mStops1.add(stop.getStopName());
                        }
                    }
                }

                // at this point, our arraylists are populated, so we connect them with our spinners

                ArrayAdapter<String> directionAdapter = new ArrayAdapter<>(MainActivity.this,
                        R.layout.support_simple_spinner_dropdown_item, mDirectionNames);
                mDirectionSpinner.setAdapter(directionAdapter);

                mDirectionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        String directionId = mDirectionIds.get(i);
                        ArrayAdapter<String> arrayAdapter;
                        if (directionId.equals("0")) {
                            arrayAdapter = new ArrayAdapter<String>(MainActivity.this,
                                    R.layout.support_simple_spinner_dropdown_item, mStops0);
                        } else {
                            arrayAdapter = new ArrayAdapter<String>(MainActivity.this,
                                    R.layout.support_simple_spinner_dropdown_item, mStops1);
                        }
                        mStartingStopSpinner.setAdapter(arrayAdapter);
                        mDestinationStopSpinner.setAdapter(arrayAdapter);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }
                });

                // after connecting adapters to spinners, we can (try to) check for headsigns
                headsignsEnqueue(apiService, routeId);
            }

            @Override
            public void onFailure(Call<StopsByRouteRoot> call, Throwable t) {
                Log.e(TAG, "onFailure called in directionAndStopsEnqueue in " + MainActivity.this.getClass().getSimpleName());
            }
        });
    }

    private void headsignsEnqueue(MyApiEndpointInterface apiService, String routeId) {
        Call<HeadsignRoot> headsignRootCall = apiService.getHeadsignRoot(routeId);
        headsignRootCall.enqueue(new Callback<HeadsignRoot>() {
            @Override
            public void onResponse(Call<HeadsignRoot> call, Response<HeadsignRoot> response) {
                try {
                    mHeadsignRoot = response.body();

                    // get the first mode(always the first mode since we're running 1 route at a time) and the first route's directions
                    List<Direction> directions = mHeadsignRoot.getMode().get(0).getRoute().get(0).getDirection();
                    mDirectionWithHeadSignNames = new ArrayList<String>();

                    for (Direction direction : directions) {
                        String directionName = direction.getDirectionName();

                        // remove (Express) and (Limited Stops) substring from end if included in tripname
                        String tripName = direction.getTrip().get(0).getTripHeadsign()
                                .replace("(Express)", "")
                                .replace("(Limited Stops)", "");
                        mDirectionWithHeadSignNames.add(directionName + " to " + tripName);
                    }


                    ArrayAdapter<String> directionAdapter = new ArrayAdapter<>(MainActivity.this,
                            R.layout.support_simple_spinner_dropdown_item, mDirectionWithHeadSignNames);
                    mDirectionSpinner.setAdapter(directionAdapter);
                }
                catch (Exception e) {
                    Log.e(TAG, "exception in headsignsEnqueue, no headsign data available");
                }

            }

            @Override
            public void onFailure(Call<HeadsignRoot> call, Throwable t) {
                Log.e(TAG, "onFailure called in headsignsEnqueue in " + MainActivity.this.getClass().getSimpleName());
            }
        });
    }


}

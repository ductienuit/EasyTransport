package com.transport.easytransport.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.transport.easytransport.R;
import com.transport.easytransport.SDKApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import transportapisdk.JourneyBodyOptions;
import transportapisdk.TransportApiClient;
import transportapisdk.TransportApiClientSettings;
import transportapisdk.TransportApiResult;
import transportapisdk.models.Itinerary;
import transportapisdk.models.Journey;

public class WaysActivity extends Activity {
    private final Executor mExecutor = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ways);
    }

    public void onClick(View view) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                String clientId = SDKApplication.getContext().getString(R.string.transportApiClientId);
                String clientSecret = SDKApplication.getContext().getString(R.string.transportApiClientSecret);

                TransportApiClient tapiClient = new TransportApiClient(new TransportApiClientSettings(clientId, clientSecret));

                double startLongitude = 106.685284;
                double startLatitude = 10.788872;
                double endLongitude = 106.705149;
                double endLatitude = 10.789289;

                // Let's restrict our Journey call to only some Modes.
                List<String> onlyModes = new ArrayList<>();
                //onlyModes.add("ShareTaxi");
                onlyModes.add("Bus");
                //onlyModes.add("Rail");
                // onlyModes.add("Ferry");
                // onlyModes.add("Coach");
                // onlyModes.add("Subway");
                // onlyModes.add("Rail");

                // Request only one Itinerary for now.
                int numItineraries = 5;

                JourneyBodyOptions journeyBodyOptions = new JourneyBodyOptions(
                        null,
                        null,
                        onlyModes,  // Đường đi bằng gì
                        null,
                        numItineraries, //Số đường chỉ dẫn ra
                        null);

                TransportApiResult<Journey> journeyResult = tapiClient.postJourney(
                        journeyBodyOptions,
                        startLatitude,
                        startLongitude,
                        endLatitude,
                        endLongitude,
                        null);


                ///STOPS REQUEST
//                StopQueryOptions stopQuery = StopQueryOptions.defaultQueryOptions();
//                TransportApiResult<List<Stop>> getStopsNearby = tapiClient.getStopsNearby(stopQuery,10.752070,106.663795,100);
//                Log.i("HRLLLLLLL", "ádjlk");

                List<Itinerary> itineraries = journeyResult.data.getItineraries();

                //mItinerariesLiveData.postValue(itineraries);
            }
        });
    }
}

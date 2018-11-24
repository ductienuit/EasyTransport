package com.transport.easytransport.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.transport.easytransport.R;
import com.transport.easytransport.SDKApplication;
import com.yalantis.jellytoolbar.widget.JellyToolbar;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import androidx.appcompat.widget.AppCompatEditText;
import transportapisdk.JourneyBodyOptions;
import transportapisdk.TransportApiClient;
import transportapisdk.TransportApiClientSettings;
import transportapisdk.TransportApiResult;
import transportapisdk.models.Itinerary;
import transportapisdk.models.Journey;

public class WaysActivity extends Activity {
    private final Executor mExecutor = Executors.newSingleThreadExecutor();
    MarkerOptions startMK = new MarkerOptions();
    MarkerOptions desMK = new MarkerOptions();

    List<Itinerary> itineraries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ways);

        Intent intent  = getIntent();
        Bundle bundle = intent.getBundleExtra("coordinate");

        LatLng start = new LatLng();
        LatLng des= new LatLng();

        start.setLatitude(bundle.getDouble("lat1", 1));
        start.setLongitude((bundle.getDouble("long1", 1)));
        String title1 = bundle.getString("name1","Current location");
        startMK.setTitle(title1);
        startMK.setPosition(start);

        des.setLatitude(bundle.getDouble("lat2", 1));
        des.setLongitude(bundle.getDouble("long2", 1));
        String title2 = bundle.getString("name2","World");
        desMK.setTitle(title2);
        desMK.setPosition(des);

    }

    public void onClick(View view) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                String clientId = SDKApplication.getContext().getString(R.string.transportApiClientId);
                String clientSecret = SDKApplication.getContext().getString(R.string.transportApiClientSecret);

                TransportApiClient tapiClient = new TransportApiClient(new TransportApiClientSettings(clientId, clientSecret));

                // Let's restrict our Journey call to only some Modes.
                List<String> onlyModes = new ArrayList<>();
                //onlyModes.add("ShareTaxi");
                onlyModes.add("Bus");
                onlyModes.add("Rail");
                 onlyModes.add("Ferry");
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
                        startMK.getPosition().getLatitude(),
                        startMK.getPosition().getLongitude(),
                        desMK.getPosition().getLatitude(),
                        desMK.getPosition().getLongitude(),
                        null);

                itineraries = journeyResult.data.getItineraries();
            }
        });
    }
}

package com.transport.easytransport.plan;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.transport.easytransport.R;
import com.transport.easytransport.SDKApplication;
import com.transport.easytransport.adapter.PlanAdapter;
import com.transport.easytransport.ui.main.MainFragment;
import com.transport.easytransport.ui.main.MainViewModel;
import com.yalantis.jellytoolbar.widget.JellyToolbar;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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

    ArrayList<Itinerary> itineraries = new ArrayList<>();

    private RecyclerView mSuggestRecyclerView;
    private PlanAdapter mSuggestAdapter;

    private RecyclerView mBusOnlyRecyclerView;
    private PlanAdapter mBusOnlyAdapter;

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

        //getItinerariesLiveData(start,des);

        //addSuggestRecyclerView();

        //addBusOnlyRecyclerView();
    }

    private void addBusOnlyRecyclerView() {
        mBusOnlyRecyclerView = findViewById(R.id.busOnlyList);
        mBusOnlyRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mBusOnlyRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mBusOnlyRecyclerView.setHasFixedSize(true);

        mBusOnlyAdapter = new PlanAdapter(itineraries, R.layout.planitem, this);
        mBusOnlyRecyclerView.setAdapter(mBusOnlyAdapter);
    }

    private void addSuggestRecyclerView() {
        mSuggestRecyclerView = findViewById(R.id.suggestList);
        mSuggestRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mSuggestRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mSuggestRecyclerView.setHasFixedSize(true);

        mSuggestAdapter = new PlanAdapter(itineraries, R.layout.planitem, this);
        mSuggestRecyclerView.setAdapter(mSuggestAdapter);
    }

    public void getItinerariesLiveData(LatLng start, LatLng des) {

        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                String clientId = SDKApplication.getContext().getString(R.string.transportApiClientId);
                String clientSecret = SDKApplication.getContext().getString(R.string.transportApiClientSecret);

                TransportApiClient tapiClient = new TransportApiClient(new TransportApiClientSettings(clientId, clientSecret));

                double startLongitude = start.getLongitude();
                double startLatitude = start.getLatitude();
                double endLongitude = des.getLongitude();
                double endLatitude = des.getLatitude();

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
                itineraries.clear();
                itineraries.addAll(journeyResult.data.getItineraries());
                mBusOnlyAdapter = new PlanAdapter(itineraries, R.layout.planitem, getApplicationContext());
                mBusOnlyRecyclerView.setAdapter(mBusOnlyAdapter);

                mSuggestAdapter = new PlanAdapter(itineraries, R.layout.planitem, getApplicationContext());
                mSuggestRecyclerView.setAdapter(mSuggestAdapter);
            }
        });
    }
}

package com.transport.easytransport.ui.main;

import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import transportapisdk.models.Itinerary;

import android.os.Vibrator;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.transport.easytransport.BitmapHelper;
import com.transport.easytransport.MapboxHelper;
import com.transport.easytransport.R;

import java.util.List;
import java.util.Objects;

import static androidx.constraintlayout.motion.widget.MotionScene.TAG;

public class MainFragment extends Fragment {
    private static final String LOG_TAG = "MainFragment";

    private static final int PERMISSIONS_REQUEST_CODE = 1;

    private MainViewModel mViewModel;

    private MapView mMapView;
    private MapboxMap mMap;

    private FloatingActionButton mCenterLocationButton;

    private PlaceAutocompleteFragment txtCurrent;
    private PlaceAutocompleteFragment txtArrive;

    private MarkerOptions mOriginMarkerOptions;
    private Marker mOriginMarker;

    private MarkerOptions mDestinationMarkerOptions;
    private Marker mDestinationMarker;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment, container, false);

        // Initialise the Mapbox instance.
        Mapbox.getInstance(getContext(), getString(R.string.mapBoxAccessToken));

        mMapView = view.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mCenterLocationButton = view.findViewById(R.id.centerLocationButton);
        mCenterLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOriginMarker != null) {
                    CameraPosition.Builder camPositionBuilder = new CameraPosition.Builder();
                    camPositionBuilder.target(mOriginMarker.getPosition());
                    camPositionBuilder.zoom(12.0);

                    mMap.setCameraPosition(camPositionBuilder.build());
                }
            }
        });

        txtCurrent = (PlaceAutocompleteFragment)
                getActivity().getFragmentManager().findFragmentById(R.id.txtcurrent);
        txtCurrent.setHint("Current location");
        ImageView searchCurrentIcon = (ImageView)((LinearLayout) Objects.requireNonNull(txtCurrent.getView())).getChildAt(0);
        searchCurrentIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_my_location_black_24dp));
        searchCurrentIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOriginMarker != null) {
                    CameraPosition.Builder camPositionBuilder = new CameraPosition.Builder();
                    camPositionBuilder.target(mOriginMarker.getPosition());
                    camPositionBuilder.zoom(12.0);

                    mMap.setCameraPosition(camPositionBuilder.build());
                }
            }
        });

        txtArrive = (PlaceAutocompleteFragment)
                getActivity().getFragmentManager().findFragmentById(R.id.txtarrive);
        txtArrive.setHint("Arrive address");
        ImageView searchArriveIcon = (ImageView)((LinearLayout) Objects.requireNonNull(txtArrive.getView())).getChildAt(0);
        searchArriveIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_subdirectory_arrow_right_black_24dp));


        txtCurrent.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Log.i(TAG, "Place: " + place.getName());
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: " + status);
            }
        });


        txtArrive.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Log.i(TAG, "Place: " + place.getName());
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: " + status);
            }
        });

        CardView btnFindway = view.findViewById(R.id.btnFindway);
        btnFindway.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                        getActivity().getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);


                autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
                    @Override
                    public void onPlaceSelected(Place place) {
                        // TODO: Get info about the selected place.
                        Log.i(TAG, "Place: " + place.getName());
                    }

                    @Override
                    public void onError(Status status) {
                        // TODO: Handle the error.
                        Log.i(TAG, "An error occurred: " + status);
                    }
                });
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // This ViewModel is scoped to this Fragment's parent Activity.
        mViewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);
    }

    @Override
    public void onStart() {
        super.onStart();

        mMapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();

        mMapView.onResume();

        // Check permission to get location.
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST_CODE);
        } else {
            mMapView.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(MapboxMap mapboxMap) {
                    mMap = mapboxMap;

                    setupViewModelConnections();
                }
            });
        }
    }

    // Observe LiveData from the MainViewModel.
    private void setupViewModelConnections() {
        mViewModel.getLocation(getContext()).observe(this, new Observer<Location>() {
            @Override
            public void onChanged(Location location) {
                Log.i(LOG_TAG, "Location update!");
            }
        });

        mViewModel.getStartLocation().observe(this, new Observer<Location>() {
            @Override
            public void onChanged(Location location) {
                if (mOriginMarker == null) {
                    final int markerWidth = getContext().getResources().getDimensionPixelSize(R.dimen.waypoint_end_map_marker_width);
                    final int markerHeight = getContext().getResources().getDimensionPixelSize(R.dimen.waypoint_end_map_marker_height);

                    // Icon icon = BitmapHelper.getVectorAsMapBoxIcon(getContext(), R.drawable.ic_map_pin_a, markerWidth, markerHeight);
                    Icon icon = BitmapHelper.getVectorAsMapBoxIcon(getContext(), R.drawable.maker, markerWidth, markerHeight);

                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.setIcon(icon);

                    markerOptions.setPosition(new LatLng(location));
                    mOriginMarker = mMap.addMarker(markerOptions);
                    mOriginMarkerOptions = markerOptions;
                } else {
                    mOriginMarker.setPosition(new LatLng(location));
                    mOriginMarkerOptions.setPosition(new LatLng(location));
                }

                CameraPosition.Builder camPositionBuilder = new CameraPosition.Builder();
                camPositionBuilder.target(mOriginMarker.getPosition());
                camPositionBuilder.zoom(12.0);

                mMap.setCameraPosition(camPositionBuilder.build());
            }
        });

        mViewModel.getEndLocation().observe(this, new Observer<LatLng>() {
            @Override
            public void onChanged(LatLng location) {
                if (mDestinationMarker == null) {
                    final int markerWidth = getContext().getResources().getDimensionPixelSize(R.dimen.waypoint_end_map_marker_width);
                    final int markerHeight = getContext().getResources().getDimensionPixelSize(R.dimen.waypoint_end_map_marker_height);

                    Icon icon = BitmapHelper.getVectorAsMapBoxIcon(getContext(), R.drawable.maker, markerWidth, markerHeight);
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.setIcon(icon);
                    markerOptions.setPosition(location);
                    mDestinationMarker = mMap.addMarker(markerOptions);
                    mDestinationMarkerOptions = markerOptions;
                } else {
                    mDestinationMarker.setPosition(location);
                    mDestinationMarkerOptions.setPosition(location);
                }
            }
        });

        mMap.addOnMapLongClickListener((@NonNull LatLng point) -> {
            try {
                Vibrator vibrator = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
                vibrator.vibrate(50L);
            } catch (SecurityException e) {
                // Gotta have permission ¯\_(ツ)_/¯
            }

            mViewModel.setEndLocation(point);
        });

        mViewModel.getItineraries().observe(this, new Observer<List<Itinerary>>() {

            @Override
            public void onChanged(List<Itinerary> itineraries) {
                mMap.clear();
                MapboxHelper.drawItineraryOnMap(getContext(), mMap, itineraries.get(0));
                mMap.addMarker(mOriginMarkerOptions);
                mMap.addMarker(mDestinationMarkerOptions);
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();

        mMapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();

        mMapView.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();

        mMapView.onLowMemory();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        mMapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        mMapView.onSaveInstanceState(outState);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                if (!ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), permissions[0])) {
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
                    intent.setData(uri);
                    startActivity(intent);
                }
            }
        }
    }

}

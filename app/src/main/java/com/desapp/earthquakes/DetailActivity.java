package com.desapp.earthquakes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.desapp.Earthquake;
import com.desapp.earthquakes.databinding.ActivityDetailBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class DetailActivity extends AppCompatActivity implements OnMapReadyCallback {
    public static final String EARTHQUAKE_KEY = "earthquake_key";

    ActivityDetailBinding binding;

    private MapView mMapView;
    private GoogleMap mMap;
    private LatLng earthquakeCoordinates;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Bundle extras = getIntent().getExtras();
        Earthquake earthquake = extras.getParcelable(EARTHQUAKE_KEY);

        earthquakeCoordinates = new LatLng(earthquake.getLatitude(), earthquake.getLongitude());

        mMapView = binding.mapView;
        mMapView.onCreate(savedInstanceState);
        mMapView.getMapAsync(this);

        binding.setEarthquake(earthquake);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        if (mMapView != null) {
            MarkerOptions marker = new MarkerOptions().position(earthquakeCoordinates).title("Earthquake");
            mMap.addMarker(marker);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(earthquakeCoordinates, 4));
        }
    }
}
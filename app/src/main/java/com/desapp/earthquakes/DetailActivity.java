package com.desapp.earthquakes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.desapp.Earthquake;
import com.desapp.earthquakes.databinding.ActivityDetailBinding;

public class DetailActivity extends AppCompatActivity {
    public static final String EARTHQUAKE_KEY = "earthquake_key";

    ActivityDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Bundle extras = getIntent().getExtras();
        Earthquake earthquake = extras.getParcelable(EARTHQUAKE_KEY);

        binding.setEarthquake(earthquake);
    }
}
package com.desapp.earthquakes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;

import com.desapp.earthquakes.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    //ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_main);

        MainViewModel viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        viewModel.getEqList().observe(this, eqList -> {
            for (Earthquake eq: eqList) {
                Log.d("eq", eq.getMagnitude() + " " + eq.getPlace());
            }
        });
        viewModel.getEarthquakes();
    }
}
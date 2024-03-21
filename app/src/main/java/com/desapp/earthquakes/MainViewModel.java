package com.desapp.earthquakes;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.desapp.Earthquake;
import com.desapp.database.EarthquakeDatabase;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private final MainRepository repository;

    public MainViewModel(@NonNull Application application) {
        super(application);
        EarthquakeDatabase database = EarthquakeDatabase.getDatabase(application);
        repository = new MainRepository(database);
    }

    public LiveData<List<Earthquake>> getEqList() {
        return repository.getEqList();
    }

    public void downloadEarthquakes() {
        repository.downloadAndSaveEarthquakes();
    }
}

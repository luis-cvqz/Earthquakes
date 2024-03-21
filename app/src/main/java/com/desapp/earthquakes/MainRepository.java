package com.desapp.earthquakes;

import androidx.lifecycle.LiveData;

import com.desapp.Earthquake;
import com.desapp.api.ApiClient;
import com.desapp.api.EarthquakeJSONResponse;
import com.desapp.api.Feature;
import com.desapp.database.EarthquakeDatabase;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainRepository {
    private final EarthquakeDatabase database;

    public MainRepository(EarthquakeDatabase database) {
        this.database = database;
    }

    public LiveData<List<Earthquake>> getEqList() {
        return database.earthquakeDAO().getEarthquakes();
    }

    public void downloadAndSaveEarthquakes() {
        ApiClient.Service service = ApiClient.getInstance().getService();

        service.getEarthquakes().enqueue(new Callback<EarthquakeJSONResponse>() {
            @Override
            public void onResponse(Call<EarthquakeJSONResponse> call, Response<EarthquakeJSONResponse> response) {
                List<Earthquake> earthquakeList = getEarthquakesWithMoshi(response.body());
                EarthquakeDatabase.databaseWriteExecutor.execute(() -> {
                    database.earthquakeDAO().insertAll(earthquakeList);
                });
            }

            @Override
            public void onFailure(Call<EarthquakeJSONResponse> call, Throwable t) {

            }
        });
    }

    private List<Earthquake> getEarthquakesWithMoshi(EarthquakeJSONResponse body) {
        ArrayList<Earthquake> eqList = new ArrayList<>();

        List<Feature> features = body.getFeatures();
        for (Feature feature: features) {
            String id = feature.getId();
            double magnitude = feature.getProperties().getMagnitude();
            String place = feature.getProperties().getPlace();
            long time = feature.getProperties().getTime();

            double longitude = feature.getGeometry().getLongitude();
            double latitude = feature.getGeometry().getLatitude();

            Earthquake earthquake = new Earthquake(id, place, magnitude, time, latitude, longitude);

            eqList.add(earthquake);
        }

        return eqList;
    }
}

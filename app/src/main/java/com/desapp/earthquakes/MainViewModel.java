package com.desapp.earthquakes;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.desapp.Earthquake;
import com.desapp.database.EarthquakeDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private final MainRepository repository;

    private final MutableLiveData<List<Earthquake>> eqList = new MutableLiveData<>();

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

    public void getEarthquakes() {
        repository.getEarthquakes(earthquakeList -> {
            eqList.setValue(earthquakeList);
        });
    }

    /*
    public void  getEarthquakes() {
        ApiClient.Service service = ApiClient.getInstance().getService();

        service.getEarthquakes().enqueue(new Callback<EarthquakeJSONResponse>() {
            @Override
            public void onResponse(Call<EarthquakeJSONResponse> call, Response<EarthquakeJSONResponse> response) {
                List<Earthquake> eql = getEarthquakesWithMosh(response.body());
                eqList.setValue(eql);
            }

            @Override
            public void onFailure(Call<EarthquakeJSONResponse> call, Throwable t) { }
        });
    }*/

    private List<Earthquake> parseEarthquake(String body) {
        ArrayList<Earthquake> eql = new ArrayList<>();
        try {
            JSONObject jsonResponse = new JSONObject(body);
            JSONArray features = jsonResponse.getJSONArray("features");
            for (int i = 0; i < features.length(); i++) {
                JSONObject jsonFeature = features.getJSONObject(i);
                String id = jsonFeature.getString("id");

                JSONObject jsonProperties = jsonFeature.getJSONObject("properties");
                double mag = jsonProperties.getDouble("mag");
                String place = jsonProperties.getString("place");
                long time = jsonProperties.getLong("time");

                JSONObject jsonGeometry = jsonFeature.getJSONObject("geometry");
                JSONArray coords = jsonGeometry.getJSONArray("coordinates");
                double longitude = coords.getDouble(0);
                double latitude = coords.getDouble(1);

                eql.add(new Earthquake(id, place, mag, time, longitude, latitude));
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return eql;
    }
}

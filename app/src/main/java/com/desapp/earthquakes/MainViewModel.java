package com.desapp.earthquakes;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.desapp.api.EarthquakeJSONResponse;
import com.desapp.api.Feature;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainViewModel extends ViewModel {
    private final MutableLiveData<List<Earthquake>> eqList = new MutableLiveData<>();

    public LiveData<List<Earthquake>> getEqList() {
        return eqList;
    }

    private MainRepository repository = new MainRepository();

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

            Earthquake earthquake = new Earthquake(id, place, magnitude, time, latitude,longitude);
            eqList.add(earthquake);
        }

        return eqList;
    }

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

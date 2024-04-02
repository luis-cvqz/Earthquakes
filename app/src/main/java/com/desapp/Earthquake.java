package com.desapp;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

import java.util.Objects;

@Entity (tableName = "earthquakes")
public class Earthquake implements Parcelable {
    @PrimaryKey
    @NonNull
    private String id;
    private String place;
    private double magnitude;
    private long time;
    private double latitude;
    private double longitude;

    public Earthquake(@NonNull String id, String place, double magnitude, long time, double latitude, double longitude) {
        this.id = id;
        this.place = place;
        this.magnitude = magnitude;
        this.time = time;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    protected Earthquake(Parcel in) {
        id = Objects.requireNonNull(in.readString());
        place = in.readString();
        magnitude = in.readDouble();
        time = in.readLong();
        longitude = in.readDouble();
        latitude = in.readDouble();
    }

    public static final Creator<Earthquake> CREATOR = new Creator<Earthquake>() {
        @Override
        public Earthquake createFromParcel(Parcel in) {
            return new Earthquake(in);
        }

        @Override
        public Earthquake[] newArray(int size) {
            return new Earthquake[size];
        }
    };

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public double getMagnitude() {
        return magnitude;
    }

    public String getStringMagnitude() {
        return String.valueOf(magnitude);
    }

    public void setMagnitude(double magnitude) {
        this.magnitude = magnitude;
    }

    public long getTime() {
        return time;
    }

    public String getStringTime() {
        return String.valueOf(time);
    }

    public void setTime(long time) {
        this.time = time;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getStringLongitude() {
        return String.valueOf(longitude);
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public String getStringLatitude() {
        return String.valueOf(latitude);
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Earthquake that = (Earthquake) o;
        return Double.compare(magnitude, that.magnitude) == 0
                && time == that.time
                && Double.compare(longitude, that.longitude) == 0
                && Double.compare(latitude, that.latitude) == 0
                && Objects.equals(id, that.id)
                && Objects.equals(place, that.place);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, place, magnitude, time, longitude, latitude);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int flags) {
        parcel.writeString(id);
        parcel.writeString(place);
        parcel.writeDouble(magnitude);
        parcel.writeLong(time);
        parcel.writeDouble(longitude);
        parcel.writeDouble(latitude);
    }
}


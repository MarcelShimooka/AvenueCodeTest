package com.avenuecode.test.models.google;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Marcel on 24/04/2015 22:03
 */
public class Geocoding implements Parcelable {

    @SerializedName("formatted_address")
    private String formattedAddress;

    private Geometry geometry;

    public String getFormattedAddress() {
        return formattedAddress;
    }

    public void setFormattedAddress(String formattedAddress) {
        this.formattedAddress = formattedAddress;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    protected Geocoding(Parcel in) {
        formattedAddress = in.readString();
        geometry = (Geometry) in.readValue(Geometry.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(formattedAddress);
        dest.writeValue(geometry);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Geocoding> CREATOR = new Parcelable.Creator<Geocoding>() {
        @Override
        public Geocoding createFromParcel(Parcel in) {
            return new Geocoding(in);
        }

        @Override
        public Geocoding[] newArray(int size) {
            return new Geocoding[size];
        }
    };
}
package com.avenuecode.test.models.google;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marcel on 24/04/2015 22:20
 */
public class GeocodingResult implements Parcelable {

    private List<Geocoding> results;

    public List<Geocoding> getResults() {
        return results;
    }

    public void setResults(List<Geocoding> results) {
        this.results = results;
    }

    protected GeocodingResult(Parcel in) {
        if (in.readByte() == 0x01) {
            results = new ArrayList<Geocoding>();
            in.readList(results, Geocoding.class.getClassLoader());
        } else {
            results = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (results == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(results);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<GeocodingResult> CREATOR = new Parcelable.Creator<GeocodingResult>() {
        @Override
        public GeocodingResult createFromParcel(Parcel in) {
            return new GeocodingResult(in);
        }

        @Override
        public GeocodingResult[] newArray(int size) {
            return new GeocodingResult[size];
        }
    };
}
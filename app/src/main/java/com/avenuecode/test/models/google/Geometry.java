package com.avenuecode.test.models.google;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Marcel on 24/04/2015 22:25
 */
public class Geometry implements Parcelable {

    private Location location;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    protected Geometry(Parcel in) {
        location = (Location) in.readValue(Location.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(location);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Geometry> CREATOR = new Parcelable.Creator<Geometry>() {
        @Override
        public Geometry createFromParcel(Parcel in) {
            return new Geometry(in);
        }

        @Override
        public Geometry[] newArray(int size) {
            return new Geometry[size];
        }
    };
}
package com.avenuecode.test.rest.services;

import com.avenuecode.test.models.google.GeocodingResult;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by Marcel on 24/04/2015 21:57
 * Interface that contains methods to request Google Maps Apis
 */
public interface GoogleMapsService {

    @GET("/maps/api/geocode/json")
    void getGeocoding(@Query("address") String address, @Query("sensor") boolean sensor, Callback<GeocodingResult> cb);

}
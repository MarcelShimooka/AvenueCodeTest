package com.avenuecode.test.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.avenuecode.test.R;
import com.avenuecode.test.models.google.Geocoding;
import com.avenuecode.test.models.google.GeocodingResult;
import com.avenuecode.test.models.google.Location;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends ActionBarActivity {

    private GoogleMap map;
    private GeocodingResult geocodingResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.activity_map_googlemap)).getMap();

        addMarkers();
    }

    /**
     * Retrieve the results of the search on the previous screen and show the markers on the map
     */
    private void addMarkers() {
        geocodingResult = getIntent().getParcelableExtra(MainActivity.RESULT_KEY);

        if (geocodingResult != null) {
            //Loop for add each marker
            for (Geocoding geocoding : geocodingResult.getResults()) {
                map.addMarker(new MarkerOptions()
                        .position(new LatLng(geocoding.getGeometry().getLocation().getLat(), geocoding.getGeometry().getLocation().getLng()))
                        .icon(BitmapDescriptorFactory.defaultMarker())
                        .title(geocoding.getFormattedAddress()) //location name that will be shown when click the marker
                        .snippet(String.valueOf(geocoding.getGeometry().getLocation().getLat())
                                + ", "
                                + String.valueOf(geocoding.getGeometry().getLocation().getLng())) //coordinates that will be shown when click the marker
                );
            }

            //Centralize the selected item
            Location selectedLocation = geocodingResult.getResults().get(getIntent().getIntExtra(MainActivity.SELECTED_POSITION_KEY, 0)).getGeometry().getLocation();
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(selectedLocation.getLat(), selectedLocation.getLng()), 4));
        }
    }
}
package com.avenuecode.test.activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.avenuecode.test.R;
import com.avenuecode.test.adapters.AddressAdapter;
import com.avenuecode.test.models.google.GeocodingResult;
import com.avenuecode.test.rest.ServiceGenerator;
import com.avenuecode.test.rest.services.GoogleMapsService;
import com.avenuecode.test.utils.CommomUtils;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends ActionBarActivity {

    //Key names for persist data
    public final static String RESULT_KEY = "listGeocoding";
    public final static String SELECTED_POSITION_KEY = "selectedPosition";

    private EditText etAddress;
    private Button btnSearch;
    private ListView lvResult;
    private GeocodingResult geocodingResult;
    private Dialog dialogProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etAddress = (EditText) findViewById(R.id.activity_main_et_address);
        btnSearch = (Button) findViewById(R.id.activity_main_btn_search);
        lvResult = (ListView) findViewById(R.id.activity_main_lv_result);

        setListeners();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(RESULT_KEY, geocodingResult);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        geocodingResult = savedInstanceState.getParcelable(RESULT_KEY);

        if (geocodingResult != null && geocodingResult.getResults().size() > 0) {
            loadResult();
        }
    }

    /**
     * Method to set the listeners of the activity
     */
    private void setListeners() {
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchAddress();
            }
        });

        lvResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, MapActivity.class);
                intent.putExtra(RESULT_KEY, geocodingResult);
                intent.putExtra(SELECTED_POSITION_KEY, position);
                startActivity(intent);
            }
        });
    }

    /**
     * Performs the process to search the address
     */
    private void searchAddress() {
        CommomUtils.hideKeyboard(MainActivity.this, etAddress);

        //Check if the address was informed
        if (etAddress.getText().toString().equals("")) {
            Toast.makeText(MainActivity.this, R.string.activity_main_search_address_validation, Toast.LENGTH_SHORT).show();
            return;
        }

        lvResult.setVisibility(View.GONE);
        showProgress();

        //Call the api for searching the address
        GoogleMapsService service = ServiceGenerator.createService(GoogleMapsService.class, getString(R.string.google_maps_api_base_url));
        service.getGeocoding(etAddress.getText().toString(), true, new Callback<GeocodingResult>() {
            @Override
            public void success(GeocodingResult googleGeocodingResult, Response response) {
                hideProgress();
                geocodingResult = googleGeocodingResult;

                if (geocodingResult.getResults().size() > 0) {
                    loadResult();
                } else {
                    Toast.makeText(MainActivity.this, R.string.activity_main_search_no_results_found, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                hideProgress();
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Show the result in the listview
     */
    private void loadResult() {
        lvResult.setVisibility(View.VISIBLE);
        lvResult.setAdapter(new AddressAdapter(MainActivity.this, geocodingResult.getResults()));
    }

    /**
     * Show a progress while searching
     */
    private void showProgress() {
        if (dialogProgress == null) {
            dialogProgress = new Dialog(this);
            dialogProgress.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialogProgress.setContentView(R.layout.custom_progress);
            dialogProgress.setCancelable(false);
        }

        dialogProgress.getWindow().getDecorView().getRootView().setBackgroundColor(getResources().getColor(android.R.color.transparent));
        dialogProgress.show();
    }

    /**
     * Hide the progress after the searching
     */
    private void hideProgress() {
        if (dialogProgress != null) {
            dialogProgress.dismiss();
            dialogProgress = null;
        }
    }
}
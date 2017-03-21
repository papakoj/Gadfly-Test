package com.example.papak.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;


/**
 * Created by papak on 3/20/2017.
 */


public class WebPage extends AppCompatActivity {

    public static final String ADDRESS = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webpage_view);
        String name = getIntent().getStringExtra(ADDRESS);

        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_ADDRESS)
                .build();

        autocompleteFragment.setFilter(typeFilter);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                final Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                Bundle b = new Bundle();
                b.putDouble("lat", place.getLatLng().latitude);
                b.putDouble("lng", place.getLatLng().longitude);
                intent.putExtras(b);
                startActivity(intent);
                Log.i("a", "Place: " + place.getName());
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i("a", "An error occurred: " + status);
            }
        });
    }

}

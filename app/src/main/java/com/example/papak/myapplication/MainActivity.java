package com.example.papak.myapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.location.*;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.vision.barcode.Barcode;

import java.io.IOException;
import java.util.List;



public class MainActivity extends AppCompatActivity {

    double LatAdd = 0;
    double LngAdd = 0;

    EditText edit;
    Button submitbtn;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        submitbtn = (Button) findViewById(R.id.submitButton);
        edit = (EditText) findViewById(R.id.addressfield);

//        System.out.println(toastt);
//        submitbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//
//        });

    }

    public void getLocationFromAddress(String strAddress){

        Geocoder coder = new Geocoder(this);
        List<Address> address = null;
        Barcode.GeoPoint p1 = null;

        try {
            address = coder.getFromLocationName(strAddress,5);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Address location = null;
        if (address != null) {
            location = address.get(0);
        }
        LatAdd = location.getLatitude();
        LngAdd = location.getLongitude();
    }

    public void clickAction(View v) {
        String address = edit.getText().toString();
        final Toast toast = Toast.makeText(getApplicationContext(), address, Toast.LENGTH_LONG);
        getLocationFromAddress(address);
        toast.show();
        final Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
        Bundle b = new Bundle();
        b.putDouble("lat", LatAdd);
        b.putDouble("lng", LngAdd);
        intent.putExtras(b);
        startActivity(intent);
    }



}

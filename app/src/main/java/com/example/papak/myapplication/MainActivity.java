package com.example.papak.myapplication;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

        edit.setOnKeyListener(new View.OnKeyListener() {

            public boolean onKey(View v, int keyCode, KeyEvent keyEvent) {
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    clickAction(v);
                    Log.e("TRUE", "Works?");
                    return true;
                }
                Log.d("FALSE", "FALSE");
                return false;
            }

        });

    }

    public void getLocationFromAddress(String strAddress){
        //Creates new geocoder object
        Geocoder coder = new Geocoder(this);

        List<Address> address = null;

        // Attempt to get Location from entered address
        try {
            address = coder.getFromLocationName(strAddress,5);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Address location = null;

        //Checks if address is valid and gets coordinates
        if (address.size() != 0) {
            location = address.get(0);
            LatAdd = location.getLatitude();
            LngAdd = location.getLongitude();
        } else {
            String fail = "Invalid Address.";
            final Toast toast = Toast.makeText(getApplicationContext(), fail, Toast.LENGTH_LONG);
            toast.show();
        }
    }

    public void clickAction(View v) {
        // Get entered address from text field
        String address = edit.getText().toString();
        //Store Application Context for easy referencing later
        Context context = getApplicationContext();

        //Check if the user has entered any text
        if (!address.isEmpty()) {
            Toast toast = Toast.makeText(context, address, Toast.LENGTH_LONG);

            // Checks to see whether we have an active internet connection
            ConnectivityManager cm =
                    (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            boolean isConnected = activeNetwork != null &&
                    activeNetwork.isConnectedOrConnecting();

            // Searches for entered location if there is an internet connection
            if (isConnected) {
                getLocationFromAddress(address);
                toast.show();
                final Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                // Create a bundle containing the longitude and latitude to be passed to WebPage
                Bundle b = new Bundle();
                b.putDouble("lat", LatAdd);
                b.putDouble("lng", LngAdd);
                intent.putExtras(b);
                startActivity(intent);
            } else {
                toast = Toast.makeText(context, "No Internet Connection", Toast.LENGTH_LONG);
                toast.show();
            }
        } else {
            final Toast toast = Toast.makeText(getApplicationContext(), "Please enter an address.", Toast.LENGTH_LONG);
            toast.show();
        }
    }

    public void clickWeb(View v) {
        final Intent intent = new Intent(getApplicationContext(), WebPage.class);
        String address = edit.getText().toString();
        intent.putExtra(WebPage.ADDRESS, address);
//        Bundle b = new Bundle();
//        b.putDouble("lat", LatAdd);
//        b.putDouble("lng", LngAdd);
//        intent.putExtras(b);
        startActivity(intent);
    }



}

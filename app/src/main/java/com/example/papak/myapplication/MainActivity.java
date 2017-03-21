package com.example.papak.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;





public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;
    private ActionBarDrawerToggle drawerToggle;

    private double LatAdd = 0;
    private double LngAdd = 0;

    private EditText edit;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set a Toolbar to replace the ActionBar.
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Find our drawer view
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = setupDrawerToggle();

        // Tie DrawerLayout events to the ActionBarToggle
        mDrawer.addDrawerListener(drawerToggle);

        // Find our drawer view
        nvDrawer = (NavigationView) findViewById(R.id.nvView);
        // Setup drawer view
        setupDrawerContent(nvDrawer);

        edit = (EditText) findViewById(R.id.addressfield);

    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        // NOTE: Make sure you pass in a valid toolbar reference.  ActionBarDrawToggle() does not require it
        // and will not render the hamburger icon without it.
        return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open,  R.string.drawer_close);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
        View headerLayout = navigationView.getHeaderView(0);
    }


    // `onPostCreate` called when activity start-up is complete after `onStart()`
    // NOTE 1: Make sure to override the method with only a single `Bundle` argument
    // Note 2: Make sure you implement the correct `onPostCreate(Bundle savedInstanceState)` method.
    // There are 2 signatures and only `onPostCreate(Bundle state)` shows the hamburger icon.
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        drawerToggle.onConfigurationChanged(newConfig);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
        Fragment fragment = null;
        Class fragmentClass;
        switch(menuItem.getItemId()) {
            case R.id.nav_first_fragment:
                fragmentClass = MainActivity.class;
                break;
            case R.id.nav_second_fragment:
                fragmentClass = MapsActivity.class;
                break;
            case R.id.nav_third_fragment:
                fragmentClass = RepresentativesDisplay.class;
                break;
            default:
                fragmentClass = MainActivity.class;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        // Set action bar title
        setTitle(menuItem.getTitle());
        // Close the navigation drawer
        mDrawer.closeDrawers();
    }


    private void getLocationFromAddress(String strAddress){
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
            final Toast toast = Toast.makeText(getApplicationContext(), R.string.invalid_address, Toast.LENGTH_LONG);
            toast.show();
        }
    }

    public boolean isConnected() {
        ConnectivityManager cm =
                (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    public void clickAction(View v) {
        // Get entered address from text field
        String address = edit.getText().toString();
        //Store Application Context for easy referencing later
        Context context = getApplicationContext();

        //Check if the user has entered any text
        if (!address.isEmpty()) {
            Toast toast = Toast.makeText(context, address, Toast.LENGTH_LONG);

            // Searches for entered location if there is an internet connection
            if (isConnected()) {
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
                toast = Toast.makeText(context, R.string.no_internet, Toast.LENGTH_LONG);
                toast.show();
            }
        } else {
            final Toast toast = Toast.makeText(getApplicationContext(), R.string.ask_for_address, Toast.LENGTH_LONG);
            toast.show();
        }
    }

    public void clickWeb(View v) {
        final Intent intent = new Intent(getApplicationContext(), WebPage.class);
        String address = edit.getText().toString();
        intent.putExtra(WebPage.ADDRESS, address);
//        Bundle b = new Bundle(); ///
//        b.putDouble("lat", LatAdd);
//        b.putDouble("lng", LngAdd);
//        intent.putExtras(b);
        startActivity(intent);
    }

    public void clickAdapt(View v) {
//        final Intent intent = new Intent(getApplicationContext(), RepresentativesDisplay.class);
//        startActivity(intent);
        new JsonTask().execute("https://openstates.org/api/v1/legislators/geo/?lat=44.76&long=-93.27");
    }

    ProgressDialog pd;

    private class JsonTask extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();

            pd = new ProgressDialog(MainActivity.this);
            pd.setMessage("Please wait");
            pd.setCancelable(false);
            pd.show();
        }

        protected String doInBackground(String... params) {


            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                if (isConnected()) {

                    connection.connect();

                    InputStream stream = connection.getInputStream();

                    reader = new BufferedReader(new InputStreamReader(stream));

                    StringBuffer buffer = new StringBuffer();
                    String line = "";
                    while ((line = reader.readLine()) != null) {
                        buffer.append(line);
//                    Log.d("Response: ", "> " + line);   //here u ll get whole response...... :-)

                    }
                    String result = buffer.toString();
                    result = result.substring(1);
                    result = result.substring(0, result.length() - 1);
                    return result;
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), R.string.no_internet, Toast.LENGTH_LONG);
                    toast.show();
                    return "";
                }


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        TextView json = (TextView) findViewById(R.id.jsontext);
        JSONObject jsonO;
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (pd.isShowing()){
                pd.dismiss();
            }
            if (!result.equals("")) {
                try {
                    jsonO = new JSONObject(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    json.setText(jsonO.get("email").toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Toast toast = Toast.makeText(getApplicationContext(), R.string.no_internet, Toast.LENGTH_LONG);
                toast.show();
            }
        }
    }

}

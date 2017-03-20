package com.example.papak.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by papak on 3/20/2017.
 */


public class MapView extends AppCompatActivity {

    public static final String ADDRESS = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_map);
        String name = getIntent().getStringExtra(ADDRESS);
        TextView txt = (TextView) findViewById(R.id.DISPLAYADD);
        txt.setText("Hi " + name);
    }

}

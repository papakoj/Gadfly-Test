package com.example.papak.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

public class RepresentativesDisplay extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view);

        // Construct the data source
        ArrayList<Representatives> arrayOfUsers = new ArrayList<Representatives>();
// Create the adapter to convert the array to views
        RepsAdapter adapter = new RepsAdapter(this, arrayOfUsers);
// Attach the adapter to a ListView
        Representatives newRep = new Representatives("Nathan Me", "5357268368", "emailhere", "district11",
                "state272", "url1", "party2");
        Representatives newRep1 = new Representatives("Alice Me", "5357268368", "emailhere", "district11",
                "state272", "url1", "party2");
        adapter.add(newRep);
        adapter.add(newRep1);
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);
    }
}

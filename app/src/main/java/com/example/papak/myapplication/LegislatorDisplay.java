package com.example.papak.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Linh Pham on 3/21/2017.
 */

public class LegislatorDisplay extends AppCompatActivity {
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view);

        ArrayList<Legislators> arrayOfLegislators = new ArrayList<Legislators>();
        LegislatorAdapter adapter = new LegislatorAdapter(this, arrayOfLegislators);

        Legislators newUser = new Legislators("Nathan", "Pham" , "dssdgs@grinnell.edu", "email");
        Legislators newUser2 = new Legislators("Linh", "Pham", "phamlinh@grinnell.edu", "email");
        adapter.add(newUser);
        adapter.add(newUser2);

        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);


    }
}

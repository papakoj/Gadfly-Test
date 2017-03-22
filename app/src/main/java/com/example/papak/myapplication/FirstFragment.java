package com.example.papak.myapplication;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class FirstFragment extends AppCompatActivity {
    View myView;

    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_first);
        TextView about = (TextView) findViewById(R.id.textAbout);
        about.setText("Why Gadfly?");

    }

   // public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
     //   myView = inflater.inflate(R.layout.fragment_first, null );
       // return myView;
    //}
}

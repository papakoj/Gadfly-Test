package com.example.papak.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Linh Pham on 3/21/2017.
 */

public class LegislatorAdapter extends ArrayAdapter<Legislators>{
    public LegislatorAdapter(Context context, ArrayList<Legislators> legislators) {
        super(context, 0, legislators);
    }

    @Override
    public View getView(int position, View contextView, ViewGroup parent) {
        Legislators legislators = getItem(position);
        if (contextView == null) {
            contextView = LayoutInflater.from(getContext()).inflate(R.layout.legislator, parent, false);
        }
        TextView name = (TextView) contextView.findViewById(R.id.name);
        TextView phone = (TextView) contextView.findViewById(R.id.phoneNumber);
        TextView email = (TextView) contextView.findViewById(R.id.emailAddress);

        name.setText(legislators.firstName + " " + legislators.lastName);
        phone.setText(legislators.phoneNumber);
        email.setText(legislators.emailAddress);

        return contextView;
    }
}

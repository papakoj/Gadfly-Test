package com.example.papak.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;
import android.widget.TextView;


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
        TextView txt = (TextView) findViewById(R.id.DISPLAYADD);
        txt.setText("Hi " + name);
        android.webkit.WebView myWebView = (android.webkit.WebView) findViewById(R.id.webView);
        myWebView.setWebViewClient(new WebViewClient());
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        myWebView.loadUrl("http://www.govtrack.us");
    }

}

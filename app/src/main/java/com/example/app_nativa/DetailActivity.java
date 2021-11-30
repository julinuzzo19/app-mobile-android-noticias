package com.example.app_nativa;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class DetailActivity extends AppCompatActivity {

    public WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String value = extras.getString("url");
            webView=findViewById(R.id.webview);
            webView.setWebViewClient(new WebViewClient());
            webView.loadUrl(value);
        }


    }
}
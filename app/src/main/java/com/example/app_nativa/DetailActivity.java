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
            String url = extras.getString("url");
            String title = extras.getString("url");
            String description = extras.getString("url");
            String image = extras.getString("url");
            String author = extras.getString("url");
            String source = extras.getString("url");
            String country = extras.getString("url");
            String category = extras.getString("url");
            String published_at = extras.getString("url");
            webView=findViewById(R.id.webview);
            webView.setWebViewClient(new WebViewClient());
            webView.loadUrl(url);
        }


    }
}
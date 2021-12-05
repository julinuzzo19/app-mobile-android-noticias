package com.example.app_nativa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DetailActivity extends AppCompatActivity {

    public TextView titleView, descriptionView,authorView,sourceView,published_atView;
    public ImageView imageView;
    public Button button_webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String url = extras.getString("url");
            String title = extras.getString("title");
            String description = extras.getString("description");
            String image = extras.getString("image");
            String author = extras.getString("author");
            String published_at = extras.getString("published_at");


            titleView = findViewById(R.id.title);
            descriptionView = findViewById(R.id.description);
            imageView = findViewById(R.id.image_detail);
            authorView= findViewById(R.id.author);
            published_atView= findViewById(R.id.published_at);

            DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
            DateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");
            Date date = null;
            String result = null;

            try {
                date = inputFormat.parse(published_at);
                result = outputFormat.format(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Glide.with(this).load(image).into(imageView);
            titleView.setText(title);
            descriptionView.setText(description);
            authorView.setText(author);
            published_atView.setText(result);

            button_webView = findViewById(R.id.btn_see_web);
            button_webView.setOnClickListener(v -> {

                Intent intent = new Intent(getApplicationContext(), WebViewActivity.class);
                intent.putExtra("url",url);

                startActivity(intent);
                overridePendingTransition(0,0);

            });


        }
    }


}
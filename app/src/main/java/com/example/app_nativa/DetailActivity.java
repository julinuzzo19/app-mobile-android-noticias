package com.example.app_nativa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.app_nativa.placeholder.PlaceholderContent;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DetailActivity extends AppCompatActivity {

    public TextView titleView, descriptionView,authorView,published_atView;
    public ImageView imageView;
    public Button button_webView,btn_add_favourites;
    DBHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        db = new DBHelper(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String url = extras.getString("url");
            String title = extras.getString("title");
            String description = extras.getString("description");
            String image = extras.getString("image");
            String author = extras.getString("author");
            String source = extras.getString("source");
            String published_at = extras.getString("published_at");
            String category = extras.getString("category");
            String language = extras.getString("language");
            String country = extras.getString("country");



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
            btn_add_favourites = findViewById(R.id.btn_add_favourites);

            PlaceholderContent.PlaceholderItem item = new PlaceholderContent.PlaceholderItem(title,author,description,image,country,url,language,source,category,published_at);

            btn_add_favourites.setOnClickListener(v -> {
               Boolean response =db.insertFavourite(item);
                if (response) Toast.makeText(getApplicationContext(), R.string.add_favourites, Toast.LENGTH_SHORT).show();
                else Toast.makeText(getApplicationContext(), R.string.favourites_exists, Toast.LENGTH_SHORT).show();
            });
        }
    }
}
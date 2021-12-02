package com.example.app_nativa;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.app_nativa.placeholder.PlaceholderContent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

public class LocalNewsActivity extends BaseActivity implements LocationListener {

    protected RecyclerView mRecyclerView;
    protected AdapterMasterDetail mAdapter;
    protected RequestQueue requestQueue;
    ArrayList<PlaceholderContent.PlaceholderItem> listaNoticias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.name_title_localsnews);
        listaNoticias = new ArrayList<>();
        requestQueue= Volley.newRequestQueue(this);

        mRecyclerView = findViewById(R.id.recyclerViewLocals);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        getLocationCountry();
    }

    public void getLocationCountry() {
        Double latitude;
        Double longitude;
        String coords = null;


        LocationManager locationManager = (LocationManager) getSystemService(this.LOCATION_SERVICE);
        //check the network permission
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
        }
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                0,
                0, this);

        if (locationManager != null) {
            Location location = locationManager
                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);

            if (location != null) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                coords = latitude + "+" + longitude;
            }

        }

        String url_geocode = "https://api.opencagedata.com/geocode/v1/json?q=" + coords + "&key=3813ad361a624db9acbf1ce7e921c3f8";

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, url_geocode, null,
                response -> {

                    try {
                        JSONArray mJsonArray = response.getJSONArray("results");
                        JSONObject objectJson = mJsonArray.getJSONObject(0);

                        JSONObject itemJson=objectJson.getJSONObject("components");

                        String country_code = itemJson.getString("country_code");

                        getLocalNews(country_code);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                },
                error -> {
                    Toast.makeText(getApplicationContext(), "err", Toast.LENGTH_SHORT).show();
                });
        requestQueue.add(objectRequest);

    }

    public void getLocalNews(String country_code)
    {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String language = prefs.getString("language", "es");

        String url_api = "http://api.mediastack.com/v1/news?access_key=d67e5f39b3825efab82f83e260ae52ca"+"&countries="+country_code;

        JsonObjectRequest objectRequest = new JsonObjectRequest (Request.Method.GET, url_api,null,
                response -> {
                    try {
                        JSONArray mJsonArray = response.getJSONArray("data");

                        for (int i=0; i<=mJsonArray.length();i++)
                        {
                            JSONObject itemJson = mJsonArray.getJSONObject(i);
                            String image = itemJson.getString("image");
                            if (image != "null") {
                                String title = itemJson.getString("title");
                                String description = itemJson.getString("description");
                                if (title != description) {
                                    String author = itemJson.getString("author");
                                    String country = itemJson.getString("country");
                                    String category = itemJson.getString("category");
                                    String lang = itemJson.getString("language");
                                    String published_at = itemJson.getString("published_at");
                                    String source = itemJson.getString("source");
                                    String url = itemJson.getString("url");

                                    PlaceholderContent.PlaceholderItem item = new PlaceholderContent.PlaceholderItem(title, author, description, image, country, url, lang, source, category, published_at);

                                    listaNoticias.add(item);

                                    mAdapter=new AdapterMasterDetail(listaNoticias);

                                    mAdapter.setOnClickListener(v -> {
                                        Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                                        intent.putExtra("url", listaNoticias.get(mRecyclerView.getChildAdapterPosition(v)).getUrl());
                                        startActivity(intent);
                                    });

                                    mRecyclerView.setAdapter(mAdapter);
                                }
                            }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }, error -> {
            Toast.makeText(getApplicationContext(), "err", Toast.LENGTH_SHORT).show();
        });


        requestQueue.add(objectRequest);
    }


    @Override
    int getContentViewId() {
        return R.layout.activity_local_news;
    }

    // action you want to selected - eg. i want home btn to get selected
    @Override
    int getNavigationMenuItemId() {
        return R.id.LocalNewsActivity;
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {

    }
}
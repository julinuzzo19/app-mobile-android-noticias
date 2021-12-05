package com.example.app_nativa;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.app_nativa.placeholder.PlaceholderContent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MasterDetailActivity extends BaseActivity {

    private RecyclerView mRecyclerView;
    private AdapterMasterDetail mAdapter;
    private RequestQueue requestQueue;
    ArrayList<PlaceholderContent.PlaceholderItem> listaNoticias;
    DBHelper db;
    NotificationCompat.Builder builder;
    Boolean notificacionState;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.name_title_explore);

        createNotificationChannel();
        notificacionState = getNotificationState();

        listaNoticias = new ArrayList<>();
        requestQueue=Volley.newRequestQueue(this);

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = new DBHelper(this);
        try {
            getNoticias();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void getNoticias() throws JSONException {


        requestQueue= Volley.newRequestQueue(this);

         SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
         String language = prefs.getString("language", "es");

        String url_api = "http://api.mediastack.com/v1/news?access_key=98fa36b39718213b60d8c657c3a47f96&limit=50&sources="+language;

        JsonObjectRequest stringRequest = new JsonObjectRequest (Request.Method.GET, url_api,null,
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
                                    intent.putExtra("title", listaNoticias.get(mRecyclerView.getChildAdapterPosition(v)).getTitle());
                                    intent.putExtra("description", listaNoticias.get(mRecyclerView.getChildAdapterPosition(v)).getDescription());
                                    intent.putExtra("image", listaNoticias.get(mRecyclerView.getChildAdapterPosition(v)).getImage());
                                    intent.putExtra("author", listaNoticias.get(mRecyclerView.getChildAdapterPosition(v)).getAuthor());
                                    intent.putExtra("source", listaNoticias.get(mRecyclerView.getChildAdapterPosition(v)).getSource());
                                    intent.putExtra("country", listaNoticias.get(mRecyclerView.getChildAdapterPosition(v)).getCountry());
                                    intent.putExtra("category", listaNoticias.get(mRecyclerView.getChildAdapterPosition(v)).getCategory());
                                    intent.putExtra("published_at", listaNoticias.get(mRecyclerView.getChildAdapterPosition(v)).getPublished_at());
                                    intent.putExtra("url", listaNoticias.get(mRecyclerView.getChildAdapterPosition(v)).getUrl());
                                    intent.putExtra("language", listaNoticias.get(mRecyclerView.getChildAdapterPosition(v)).getLanguage());
                                    startActivity(intent);
                                });

                                mRecyclerView.setAdapter(mAdapter);

                                if (notificacionState)
                                {
                                    builder=createNotification(item);
                                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
                                    notificationManager.notify(1, builder.build());
                                }
                            }
                        }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }, error -> {
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                });

        requestQueue.add(stringRequest);

    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
         super.onContextItemSelected(item);
        switch (item.getItemId())
        {
            case 101:
               PlaceholderContent.PlaceholderItem favourite= mAdapter.getItemByPosition(item.getGroupId());
               Boolean result = db.insertFavourite(favourite);
               if (result)
               {
                   Toast.makeText(getApplicationContext(), R.string.add_favourites, Toast.LENGTH_SHORT).show();
                   return true;
               }
               else
               {
                   Toast.makeText(getApplicationContext(), R.string.favourites_exists, Toast.LENGTH_SHORT).show();
                   return false;
               }


            case 102:
                shareNoticia(mAdapter.getItemByPosition(item.getGroupId()));
                return true;
        }
        return false;
    }

    @Override
    int getContentViewId() {
        return R.layout.activity_master_detail;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.MasterDetailActivity;
    }

    @Override
    AdapterMasterDetail getAdapter() {
        return mAdapter;
    }


    public NotificationCompat.Builder createNotification(PlaceholderContent.PlaceholderItem item){
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("url",item.getUrl() );
        intent.putExtra("title", item.getTitle());
        intent.putExtra("description", item.getDescription());
        intent.putExtra("image", item.getImage());
        intent.putExtra("author", item.getAuthor());
        intent.putExtra("source", item.getSource());
        intent.putExtra("country", item.getCountry());
        intent.putExtra("category", item.getCategory());
        intent.putExtra("published_at", item.getPublished_at());
        intent.putExtra("language", item.getLanguage());


        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        builder = new NotificationCompat.Builder(this, "channel")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getApplicationContext().getResources(),R.mipmap.ic_launcher))
                .setStyle(new NotificationCompat.BigTextStyle().bigText(item.getDescription()))
                .setContentTitle(item.getTitle())
                .setContentText(item.getDescription())
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);
        return builder;

    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "name";
            String description = "channel_description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("channel", name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    protected Boolean getNotificationState(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        Boolean notificationState = prefs.getBoolean("notifications", true);

        return notificationState;

    }

}
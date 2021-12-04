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
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;;
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
/*
        RequestQueue queue = Volley.newRequestQueue(this);

         SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
         String language = prefs.getString("language", "es");

        String url_api = "http://api.mediastack.com/v1/news?access_key=d67e5f39b3825efab82f83e260ae52ca";

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
                                String language = itemJson.getString("language");
                                String published_at = itemJson.getString("published_at");
                                String source = itemJson.getString("source");
                                String url = itemJson.getString("url");

                                PlaceholderContent.PlaceholderItem item = new PlaceholderContent.PlaceholderItem(title, author, description, image, country, url, language, source, category, published_at);

                                listaNoticias.add(item);

                                mAdapter=new AdapterMasterDetail(listaNoticias);

                                mAdapter.setOnClickListener(v -> {
                                    Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                                    intent.putExtra("url", listaNoticias.get(mRecyclerView.getChildAdapterPosition(v)).getUrl());
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
                    Toast.makeText(getApplicationContext(), "err", Toast.LENGTH_SHORT).show();
                });


        queue.add(stringRequest);
*/



        JSONArray arrayJson= new JSONArray();

        JSONObject objetoJson1 = new JSONObject();
        objetoJson1.put("title", "Ingresó a una casa y manoseó a una joven");
        objetoJson1.put("author", "Redacción LA");
        objetoJson1.put("description", "\"Un hombre de 55 años denunció que un joven ingresó de forma subrepticia a su vivienda, manoseó a su hija adolescente. El sospechoso tiene 25 años y fue retenido por vecinos cuando quiso escapar.La entrada Ingresó a una casa y manoseó a una joven se publicó primero en Primera Edición.");
        objetoJson1.put("image", "https://www.lavoz.com.ar/resizer/bg8i6cXExKS0a8PgAiTpRSF0gBY=/cloudfront-us-east-1.images.arcpublishing.com/grupoclarin/VIANZC57W5B7PDO7ABXTMDXBXY.jpg");
        objetoJson1.put("country", "ar");
        objetoJson1.put("category", "general");
        objetoJson1.put("language", "es");
        objetoJson1.put("published_at", "2021-08-01T01:16:09+00:00");
        objetoJson1.put("source", "losandes");
        objetoJson1.put("url", "https://www.losandes.com.ar/politica/alberto-fernandez-propuso-discutir-la-duracion-de-los-jueces-en-sus-cargos");

        JSONObject objetoJson2= new JSONObject();
        objetoJson2.put("title", "Lo que dejó la natación: las consagraciones de Dressel y McKeon");
        objetoJson2.put("author", "Redacción LA");
        objetoJson2.put("description", "EE.UU. dominó entre los hombres y Australia entre las mujeres");
        objetoJson2.put("image", "https://images.pagina12.com.ar/styles/width960/public/2021-08/182414-000-9hd8y4.jpg?itok=jbcngzNx");
        objetoJson2.put("country", "ar");
        objetoJson2.put("category", "general");
        objetoJson2.put("language", "es");
        objetoJson2.put("published_at", "2021-09-01T01:16:09+00:00");
        objetoJson2.put("source", "cronica");
        objetoJson2.put("url", "https://www.losandes.com.ar/politica/alberto-fernandez-propuso-discutir-la-duracion-de-los-jueces-en-sus-cargos");

        JSONObject objetoJson3 = new JSONObject();
        objetoJson3.put("title", "Ingresó a una casa y manoseó a una joven");
        objetoJson3.put("author", "Redacción LA");
        objetoJson3.put("description", "\"Un hombre de 55 años denunció que un joven ingresó de forma subrepticia a su vivienda, manoseó a su hija adolescente. El sospechoso tiene 25 años y fue retenido por vecinos cuando quiso escapar.La entrada Ingresó a una casa y manoseó a una joven se publicó primero en Primera Edición.");
        objetoJson3.put("image", "https://www.lavoz.com.ar/resizer/bg8i6cXExKS0a8PgAiTpRSF0gBY=/cloudfront-us-east-1.images.arcpublishing.com/grupoclarin/VIANZC57W5B7PDO7ABXTMDXBXY.jpg");
        objetoJson3.put("country", "ar");
        objetoJson3.put("category", "general");
        objetoJson3.put("language", "es");
        objetoJson3.put("published_at", "2021-08-01T01:16:09+00:00");
        objetoJson3.put("source", "losandes");
        objetoJson3.put("url", "https://www.losandes.com.ar/politica/alberto-fernandez-propuso-discutir-la-duracion-de-los-jueces-en-sus-cargos");

        //guardo en arreglo los elementos
        arrayJson.put(objetoJson1);
        arrayJson.put(objetoJson2);
        arrayJson.put(objetoJson3);

        for (int i=0; i<=arrayJson.length();i++)
        {
            JSONObject mJsonObject =  arrayJson.getJSONObject(i);
            String title = mJsonObject.getString("title");
            String author = mJsonObject.getString("author");
            String description = mJsonObject.getString("description");
            String image = mJsonObject.getString("image");
            String country = mJsonObject.getString("country");
            String category = mJsonObject.getString("category");
            String language = mJsonObject.getString("language");
            String published_at = mJsonObject.getString("published_at");
            String source = mJsonObject.getString("source");
            String url = mJsonObject.getString("url");

            PlaceholderContent.PlaceholderItem item = new PlaceholderContent.PlaceholderItem(title,author,description,image,country,url,language,source,category,published_at);

            listaNoticias.add(item);
            mAdapter = new AdapterMasterDetail(listaNoticias);
            mAdapter.setOnClickListener(v -> {
                Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                intent.putExtra("url", listaNoticias.get(mRecyclerView.getChildAdapterPosition(v)).getUrl());
                startActivity(intent);
                });
            mRecyclerView.setAdapter(mAdapter);

            //set notification for 1st new
            if (notificacionState)
            {
                builder=createNotification(item);
                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
                notificationManager.notify(1, builder.build());
            }

        }
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
         super.onContextItemSelected(item);
        switch (item.getItemId())
        {
            case 101:
               PlaceholderContent.PlaceholderItem favourite= mAdapter.getItemByPosition(item.getGroupId());
                db.insertFavourite(favourite);
                Toast.makeText(getApplicationContext(), R.string.add_favourites, Toast.LENGTH_SHORT).show();
                return true;

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
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        builder = new NotificationCompat.Builder(this, "channel")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(item.getTitle())
                .setContentText(item.getDescription())
                .setSmallIcon(R.mipmap.ic_launcher)
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
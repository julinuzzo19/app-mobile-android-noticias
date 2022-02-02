package com.example.app_nativa;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.app_nativa.placeholder.PlaceholderContent;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FavouritesActivity extends BaseActivity {

    private RecyclerView mRecyclerView;
    private AdapterMasterDetail mAdapter;
    ArrayList<PlaceholderContent.PlaceholderItem> listaNoticias;
    private RequestQueue requestQueue;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(R.string.name_title_favourite);
        db = new DBHelper(this);

        listaNoticias = new ArrayList<>();
        requestQueue=Volley.newRequestQueue(this);

        mRecyclerView = findViewById(R.id.recyclerViewFavourites);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        listaNoticias=db.getFavourites();

        mAdapter=new AdapterMasterDetail(listaNoticias);

        mAdapter.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
            intent.putExtra("url", listaNoticias.get(mRecyclerView.getChildAdapterPosition(v)).getUrl());
            intent.putExtra("title", listaNoticias.get(mRecyclerView.getChildAdapterPosition(v)).getTitle());
            intent.putExtra("description", listaNoticias.get(mRecyclerView.getChildAdapterPosition(v)).getDescription());
            intent.putExtra("image", listaNoticias.get(mRecyclerView.getChildAdapterPosition(v)).getImage());
            intent.putExtra("author", listaNoticias.get(mRecyclerView.getChildAdapterPosition(v)).getAuthor());
            intent.putExtra("published_at", listaNoticias.get(mRecyclerView.getChildAdapterPosition(v)).getPublished_at());
            startActivity(intent);
        });

        mRecyclerView.setAdapter(mAdapter);

    }


    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        this.closeContextMenu();

        switch (item.getItemId())
        {
            case 102:
                shareNoticia(mAdapter.getItemByPosition(item.getGroupId()));
                return true;
            case 103:
                PlaceholderContent.PlaceholderItem favourite= mAdapter.getItemByPosition(item.getGroupId());
                Boolean result =db.removeFavourite(favourite.getTitle());
                if (result)
                {
                    listaNoticias.remove(item.getGroupId());
                    mAdapter.notifyItemRemoved(item.getGroupId());



                }
                else{return false;}


                return true;
        }
        return false;

    }


    @Override
    int getContentViewId() {
        return R.layout.activity_favourites;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.favouritesActivity;
    }

    @Override
    AdapterMasterDetail getAdapter() {
        return mAdapter;

    }

    public void getNoticiasonSubmit(String query) {


        requestQueue= Volley.newRequestQueue(this);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String language = prefs.getString("language", "es");

        String url_api = "http://api.mediastack.com/v1/news?access_key=98fa36b39718213b60d8c657c3a47f96&keywords="+query+"&limit=50&sort=published_desc&sources="+language;

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


}
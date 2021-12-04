package com.example.app_nativa;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.app_nativa.placeholder.PlaceholderContent;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;

import java.util.ArrayList;

public class FavouritesActivity extends BaseActivity {

    private RecyclerView mRecyclerView;
    private AdapterMasterDetail mAdapter;
    ArrayList<PlaceholderContent.PlaceholderItem> listaNoticias;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(R.string.name_title_favourite);
        db = new DBHelper(this);

        mRecyclerView = findViewById(R.id.recyclerViewFavourites);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        listaNoticias=db.getFavourites();

        mAdapter=new AdapterMasterDetail(listaNoticias);

        mAdapter.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
            intent.putExtra("url", listaNoticias.get(mRecyclerView.getChildAdapterPosition(v)).getUrl());
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
                Boolean result =db.removeFavourite(favourite.getId());
                //listaNoticias=db.getFavourites();

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


}
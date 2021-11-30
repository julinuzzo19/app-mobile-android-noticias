package com.example.app_nativa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class FavouritesActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_favourites);
        setTitle("Favoritos");
    }

    @Override
    int getContentViewId() {
        return R.layout.activity_favourites;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.favouritesActivity;
    }



}
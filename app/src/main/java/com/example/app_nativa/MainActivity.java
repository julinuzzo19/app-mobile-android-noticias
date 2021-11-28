package com.example.app_nativa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    ItemListFragment itemListFragment= new ItemListFragment();
    LocalNewsFragment localNewsFragment= new LocalNewsFragment();
    FavouritesFragment favouritesFragment= new FavouritesFragment();

    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelected = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){

                case R.id.itemListFragment:
                    getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,itemListFragment).commit();

                    //Intent intent  = new Intent(getApplicationContext(), ItemDetailHostActivity.class);
                    //startActivity(intent);


                    return true;

                case R.id.favouritesFragment:
                    getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,favouritesFragment).commit();
                    return true;
                case R.id.localNewsFragment:
                    getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,localNewsFragment).commit();
                    return true;

            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = findViewById(R.id.bottomNavigationView);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelected);

    }
}
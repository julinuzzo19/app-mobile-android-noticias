package com.example.app_nativa;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Locale;

public abstract class BaseActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {


    protected BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String lang= getLanguagePrefs();
        setLanguageForApp(lang);

        setContentView(getContentViewId());

        navigationView = findViewById(R.id.navigation);
        navigationView.setOnNavigationItemSelectedListener(this);





    }

    protected String getLanguagePrefs(){
      SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
         String language = prefs.getString("language", "es");
       return language;
    }
    private void setLanguageForApp(String languageToLoad){
        Locale locale;

        SharedPreferences prefs = getSharedPreferences("prefs", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("language", languageToLoad).apply();

        if(languageToLoad.equals("not-set")){ //use any value for default
            locale = Locale.getDefault();
        }
        else {
            locale = new Locale(languageToLoad);
        }
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
    }




    @Override
    protected void onStart() {
        super.onStart();
        updateNavigationBarState();
    }

    @Override
    public void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.MasterDetailActivity:{
                startActivity(new Intent(this, MasterDetailActivity.class));
                break;
            }
            case R.id.LocalNewsActivity:{
                startActivity(new Intent(this, LocalNewsActivity.class));
                break;
            }
            case R.id.favouritesActivity:{
                startActivity(new Intent(this, FavouritesActivity.class));
                break;
            }
        }
        return true;
    }



    private void updateNavigationBarState(){
        int actionId = getNavigationMenuItemId();
        selectBottomNavigationBarItem(actionId);
    }

    void selectBottomNavigationBarItem(int itemId) {
        MenuItem item = navigationView.getMenu().findItem(itemId);
        item.setChecked(true);
    }

    abstract int getContentViewId();

    abstract int getNavigationMenuItemId();


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.search:
                Toast.makeText(getApplicationContext(), "Search", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.settings:
                Intent intent= new Intent(this, SettingsActivity.class);
                //intent.putExtra("activity",getLocalClassName());
                startActivity(intent);
                return true;
            case R.id.logout:
                LogOut();
                return true;

        }

        return false;
    }

    protected void LogOut()
    {
        SharedPreferences prefs = getSharedPreferences("prefs", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = prefs.edit();
        editor.remove("email");
        editor.remove("password");
        editor.apply();

        Intent intent = new Intent(this, FirstActivity.class);
        startActivity(intent);
        finish();
    }
}

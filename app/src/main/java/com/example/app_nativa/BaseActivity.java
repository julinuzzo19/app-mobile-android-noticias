package com.example.app_nativa;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.DisplayMetrics;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.preference.PreferenceManager;

import com.example.app_nativa.placeholder.PlaceholderContent;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;

public abstract class BaseActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {


    protected BottomNavigationView navigationView;
    protected AdapterMasterDetail mAdapter;
    SearchView searchView;


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

    abstract AdapterMasterDetail getAdapter();

    public void shareNoticia(PlaceholderContent.PlaceholderItem item)
    {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, item.getTitle());
        intent.putExtra(Intent.EXTRA_TEXT,item.getUrl());
        startActivity(Intent.createChooser(intent, "Share Via"));

        Toast.makeText(getApplicationContext(), R.string.shared, Toast.LENGTH_SHORT).show();
    }

 @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar,menu);
        MenuItem menuItem = menu.findItem(R.id.search);
        searchView= (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Buscar una noticia");


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;

            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public boolean onQueryTextChange(String newText) {
            if (mAdapter==null){mAdapter=  getAdapter();}
                mAdapter.filter(newText);
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.search:
                return true;

            case R.id.search_voice:
                getVoice();
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

    protected void getVoice()
    {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,"es-ES");

        startActivityForResult(intent, 200);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == 200 && resultCode == RESULT_OK)
            {
                ArrayList<String> array = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                String voice = array.get(0);
                Toast.makeText(this, voice, Toast.LENGTH_SHORT).show();

                searchView.setIconified(false);
                searchView.setQuery(voice,true);

            }
            else {
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
            }
    }

}

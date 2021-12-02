package com.example.app_nativa;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebViewClient;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceGroup;
import androidx.preference.PreferenceManager;

public class SettingsActivity extends AppCompatActivity {

    //private String lastActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings, new SettingsFragment())
                    .commit();
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        /*Bundle extras = getIntent().getExtras();
        if (extras != null) {
            lastActivity = extras.getString("activity");

        }*/


            }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);


    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
        }
    }



    /*
  @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
      Intent intent = null;
      try {
          intent = new Intent(this, Class.forName(lastActivity));
          startActivity(intent);
      } catch (ClassNotFoundException e) {
          e.printStackTrace();
      }

    }*/
}
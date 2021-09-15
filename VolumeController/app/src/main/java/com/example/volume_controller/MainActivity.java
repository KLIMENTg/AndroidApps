package com.example.volume_controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("onCreate", "onCreate Method Entered");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startService(new Intent(this, PlayerService.class));
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("onPause", "On pause engaged.");
    }

    @Override
    protected void onResume() {
        Log.d("onResume", "Always ran.");
        super.onResume();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        boolean previouslyStarted = prefs.getBoolean( getString( R.string.pref_previously_started ), false );

        String ok = getString( R.string.pref_previously_started );
        //boolean test = prefs.getBoolean("pref_previously_started", false);
        //boolean dude = prefs.getBoolean( getString( R.string.pref_previously_started ), false );

        if( ! Boolean.parseBoolean(ok)  ) {
            Log.d("onResume", "Only during start-up - ran once.");
            SharedPreferences.Editor edit = prefs.edit();
            edit.putBoolean( getString(R.string.pref_previously_started), Boolean.TRUE );
            edit.commit();

//            Intent it = new Intent();
//            it.setAction( Intent.ACTION_MAIN );
//            it.addCategory(Intent.CATEGORY_HOME);
//            MainActivity.super.startActivity(it);
        }
    }

}

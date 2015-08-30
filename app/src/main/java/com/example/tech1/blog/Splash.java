package com.example.tech1.blog;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Handler;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class Splash extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ActionBar ab = getActionBar();
        ab.hide();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(Splash.this, Home.class);
                startActivity(mainIntent);
                finish();
            }
        }, 2000);
    }
}




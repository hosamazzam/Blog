package com.example.tech1.blog;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TabActivity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TabHost;

/**
 * Created by tech 1 on 7/5/2015.
 */
public class Private extends TabActivity {

    static Private object;
    static int index=0;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private);
        // create the TabHost that will contain the Tabs
        TabHost tabHost = (TabHost) findViewById(android.R.id.tabhost);
        // TabHost tabHost = getTabHost();
        object = this;

        ActionBar ab = getActionBar();
        ab.hide();
         tabHost.setBackground(getResources().getDrawable(R.drawable.university_background));
        TabHost.TabSpec tab1 = tabHost.newTabSpec("Notes");
        TabHost.TabSpec tab2 = tabHost.newTabSpec("Alerts");

        tab1.setIndicator("Notes",getResources().getDrawable(R.drawable.add));
        tab1.setContent(new Intent(this, Tab_private_Note.class));

        tab2.setIndicator("Alerts",getResources().getDrawable(R.drawable.clock_icon));
        tab2.setContent(new Intent(this, Tab_private_Alerts.class));

        tabHost.addTab(tab1);
        tabHost.addTab(tab2);
        tabHost.setCurrentTab(index);


    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Intent myIntent = new Intent(getApplicationContext(),Home.class);
        //startActivityForResult(myIntent, 0);
        click();


        return true;
    }

*/

    public void click() {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View promptView = layoutInflater.inflate(R.layout.dialog_additem, null);

        final Dialog alertD = new Dialog(this);



        ImageButton note = (ImageButton) promptView.findViewById(R.id.note_button);

        ImageButton alert = (ImageButton) promptView.findViewById(R.id.alert_button);

        note.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent myIntent = new Intent(getApplicationContext(), add_new_note.class);
                alertD.dismiss();
                startActivity(myIntent);

            }
        });

        alert.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                alertD.dismiss();
                Intent myIntent = new Intent(getApplicationContext(), add_new_alert.class);
                startActivity(myIntent);


            }
        });

        alertD.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        alertD.setContentView(promptView);

        alertD.getWindow().setBackgroundDrawable(new ColorDrawable(R.drawable.round_corner));
    //    alertD.getWindow().setLayout(300,200);
        alertD.show();
    }


}

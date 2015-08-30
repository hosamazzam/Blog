package com.example.tech1.blog;

/**
 * Created by tech 1 on 8/5/2015.
 */

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context arg0, Intent arg1) {
     //   arg1.getIntExtra("title",0);
        Toast.makeText(arg0, "Alarm received! "+arg1.getStringExtra("title"), Toast.LENGTH_LONG).show();
        System.out.println(arg1.getStringExtra("title"));

        Intent mIntent = new Intent(arg0,ShowDialog.class); //Same as above two lines
        mIntent.putExtra("title",arg1.getStringExtra("title"));
        mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        arg0.startActivity(mIntent);




    }

}
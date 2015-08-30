package com.example.tech1.blog;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by tech 1 on 7/5/2015.
 */
public class Tab_private_Alerts extends Activity {
    LinearLayout listView;
    RelativeLayout note;
    ArrayList arr = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_private_alert);
        LayoutInflater layoutInflater = LayoutInflater.from(this);

        DB database = new DB(this);
        arr = database.getAllAlerts("private_alert");
        System.out.println("arrr " + arr);
        View alert_card = layoutInflater.inflate(R.layout.alert_card, null);
        listView = (LinearLayout) this.findViewById(R.id.listView);

        Button newalert = (Button) findViewById(R.id.add_alert_item);
        newalert.bringToFront();
        newalert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getApplicationContext(), add_new_alert.class);
                startActivity(myIntent);

            }
        });


        for (int i = 0; i < arr.size(); ) {

            alert_card.setId(i);


            final TextView title = (TextView) alert_card.findViewById(R.id.alert_title);
            final TextView time = (TextView) alert_card.findViewById(R.id.alert_time);
            final Switch turn = (Switch) alert_card.findViewById(R.id.alert_switch);
            final View finalAlert_card = alert_card;
            alert_card.setId(i);
            alert_card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), add_new_alert.class);
                    intent.putExtra("id", arr.get(finalAlert_card.getId()).toString());
                    intent.putExtra("title", title.getText());
                    intent.putExtra("time", time.getText());
                    intent.putExtra("turn", arr.get(Integer.valueOf(finalAlert_card.getId()) + 3).toString());

                    startActivity(intent);

                }
            });

            title.setText(arr.get(i + 1).toString());
            DatePicker date = new DatePicker(this);
            String Date[] = arr.get(i + 3).toString().split("/");

            date.init(Integer.valueOf(Date[2]), Integer.valueOf(Date[1])+1, Integer.valueOf(Date[0]),null);
            String date1 = date.getDayOfMonth() + "/"
                    + date.getMonth() + "/"
                    + date.getYear();
            time.setText(arr.get(i + 2) + " " + date1);
            turn.setChecked(Boolean.valueOf(arr.get(i+4).toString()));
                listView.addView(alert_card);
                alert_card = layoutInflater.inflate(R.layout.alert_card, null);

                i += 5;
            }

        }
    }

package com.example.tech1.blog;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.sql.Time;
import java.util.Calendar;

/**
 * Created by tech 1 on 7/6/2015.
 */
public class add_new_alert extends Activity {
    TimePicker time;
    DatePicker date;
    EditText title;
    Switch turn;
    Button save;
    TextView Show_time_date;
    boolean state;
    Bundle intentdata;
    boolean update = false;
    String day = "1", month = "8", year = "2015", hour = "12", min = "0";

    LayoutInflater layoutInflater;
    View date_dialog, time_dialog, dialog, activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_alerts);


        title = (EditText) findViewById(R.id.alert_title);

        turn = (Switch) findViewById(R.id.alert_switch);
        save = (Button) findViewById(R.id.alert_save);
        Show_time_date = (TextView) findViewById(R.id.date_time);

        ImageButton open_date = (ImageButton) findViewById(R.id.open_date);
        open_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Show_date_dialog();
                updatescreen();
            }
        });
        ImageButton open_time = (ImageButton) findViewById(R.id.open_time);
        open_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Show_time_dialog();
                updatescreen();
            }
        });
        intentdata = getIntent().getExtras();
        if (intentdata != null) {
            if (intentdata.containsKey("id")) {
                update = true;
                title.setText(intentdata.getString("title"));
                turn.setChecked(Boolean.valueOf(intentdata.getString("turn")));
                String total_time[] = intentdata.getString("time").split(" ");
                String Date[] = total_time[1].split("/");
                String Time[] = total_time[0].split(":");
                hour = Time[0];
                min = Time[1];
                year = Date[2];
                day = Date[0];
                month = String.valueOf(Integer.valueOf(String.valueOf(Date[1])) - 1);

                updatescreen();
            }
        }
        turn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    state = true;
                } else state = false;
                System.out.println("state =" + state);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DB database = new DB(getApplicationContext());
                database.createTables();
                database.setDATABASE_TABLE("private_alert");
                String Time = hour + ":" + min;
                String Date = day + "/"
                        + month + "/"
                        + year;
                Calendar calSet = Calendar.getInstance();

                calSet.set(Integer.valueOf(year),
                        Integer.valueOf(month),
                        Integer.valueOf(day),
                        Integer.valueOf(hour),
                        Integer.valueOf(min),
                        00);


                System.out.println("Date " + Date);
                if (IsVaild()) {
                    database.createTables();
                    if (update) {
                        database.updatealert(Integer.valueOf(intentdata.getString("id"))
                                , "private_alert", title.getText().toString(), Time, Date,
                                String.valueOf(state));
                        setTime(calSet, state, title.getText().toString());
                        Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_LONG).show();

                    } else { // add new

                        database.insert_alert("private_alert", title.getText().toString()
                                , Time, Date, String.valueOf(state));
                        setTime(calSet, state, title.getText().toString());
                    }
                    Intent intent = new Intent(getApplicationContext(), Private.class);
                    Private.object.finish();
                    Private.index = 1;
                    finish();
                    startActivity(intent);


                }
            }
        });

        updatescreen();
    }


    public void updatescreen() {
        Show_time_date.setText(hour + ":" + min + "   " +
                day + "/" + (String.valueOf(Integer.valueOf(month) + 1)) + "/" + year);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (update) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.delete, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        DB db = new DB(getApplicationContext());
        System.out.println("deleete");
        db.delete("private_alert", Integer.valueOf(intentdata.getString("id")));
        Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getApplicationContext(), Private.class);
        Private.object.finish();
        Private.index = 1;
        finish();
        startActivity(intent);
        return true;
    }

    public Boolean IsVaild() {
        System.out.println("title " + title.getText());
        if (title.getText().toString().equals("") || title.getText().toString().equals(null)) {
            title.setError("Please insert title", getResources().getDrawable(R.drawable.ic_action_error));
            return false;
        }

        return true;

    }

    public void Show_date_dialog() {

        final Dialog alertD = new Dialog(this);
        layoutInflater = LayoutInflater.from(this);
        date_dialog = layoutInflater.inflate(R.layout.date_card, null);

        alertD.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertD.setContentView(date_dialog);
        date = (DatePicker) date_dialog.findViewById(R.id.alert_date);
        date.init(Integer.valueOf(year), Integer.valueOf(month), Integer.valueOf(day), null); // yesr / month/day

        Button save = (Button) date_dialog.findViewById(R.id.save_button);
        save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                alertD.cancel();
                alertD.dismiss();
                day = String.valueOf(date.getDayOfMonth());
                month = String.valueOf(date.getMonth());
                year = String.valueOf(date.getYear());
                updatescreen();

            }
        });

        alertD.dismiss();
        alertD.getWindow().setBackgroundDrawable(new ColorDrawable(R.drawable.round_corner));
//        alertD.getWindow().setLayout(400, 390);
        alertD.show();
    }

    public void Show_time_dialog() {
        final Dialog alertD = new Dialog(this);
        layoutInflater = LayoutInflater.from(this);
        time_dialog = layoutInflater.inflate(R.layout.time_card, null);

        alertD.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertD.setContentView(time_dialog);
        time = (TimePicker) time_dialog.findViewById(R.id.alert_time);

        time.setCurrentHour(Integer.valueOf(hour));
        time.setCurrentMinute(Integer.valueOf(min));

        Button save = (Button) alertD.findViewById(R.id.save_button);
        save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                alertD.dismiss();
                alertD.cancel();

                hour = time.getCurrentHour().toString();
                min = time.getCurrentMinute().toString();
                updatescreen();
            }
        });

        alertD.dismiss();
        alertD.getWindow().setBackgroundDrawable(new ColorDrawable(R.drawable.round_corner));
        // alertD.getWindow().setLayout(400, 390);
        alertD.show();
    }


    public void setTime(Calendar calSet, Boolean state, String Title) {

        Calendar calNow = Calendar.getInstance();

        if (calSet.compareTo(calNow) <= 0) {
            //Today Set time passed, count to tomorrow
            calSet.add(Calendar.DATE, 0);
        }

        setAlarm(calSet, state, Title);
    }


    private void setAlarm(Calendar targetCal, Boolean state, String Title) {


        Intent intent = new Intent(getBaseContext(), AlarmReceiver.class);
        intent.putExtra("title", Title);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), 1, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(), pendingIntent);
        if (!state) {
            alarmManager.cancel(pendingIntent);
        }


    }

}

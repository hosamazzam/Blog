package com.example.tech1.blog;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by tech 1 on 7/29/2015.
 */
public class Admin extends Activity {
    LinearLayout listView;
    User_info user = User_info.getInstance();
    Bitmap bitmap;
    String arr[];
    //   String URL="http://10.0.3.2/Blog_db/User_image/";
    String URL = "http://hosamazzam.0fees.net/Blog_db_online/User_image/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);
        ActionBar ab = getActionBar();
        ab.hide();
        DB_remote database = new DB_remote("admin",getApplicationContext());
        database.admin = Admin.this;
        database.execute("1");
    }

    public void Status(String result) {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View report_card = layoutInflater.inflate(R.layout.report_card, null);

        TextView first = (TextView) findViewById(R.id.first_text);
        first.setVisibility(View.GONE);
        arr = result.split("/");
        listView = (LinearLayout) this.findViewById(R.id.listView);
        listView.removeAllViews();
        for (int i = 0; i < arr.length; ) {

            final Button delete = (Button) report_card.findViewById(R.id.remove);
            final TextView uname = (TextView) report_card.findViewById(R.id.User_name);
            final TextView fname = (TextView) report_card.findViewById(R.id.friend_name);
            ImageView uimg = (ImageView) report_card.findViewById(R.id.pic_user);
            ImageView fimg = (ImageView) report_card.findViewById(R.id.pic_friend);
            final TextView uid = (TextView) report_card.findViewById(R.id.User_id);
            final TextView fid = (TextView) report_card.findViewById(R.id.friend_id);
            final TextView reason = (TextView) report_card.findViewById(R.id.report_reason);
            report_card.setId(Integer.valueOf(arr[i]));
            reason.setText(arr[i + 7]);

            uname.setText(arr[i + 5]);
            uid.setText(arr[i + 4]);

            LoadImage load = new LoadImage();
            load.Image(uimg);
            load.execute(URL + arr[i + 6]);

            fname.setText(arr[i + 2]);
            fid.setText(arr[i + 1]);

            load = new LoadImage();
            load.Image(fimg);
            load.execute(URL + arr[i + 3]);
            final View finalReport_card = report_card;
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DB_remote database = new DB_remote("delete_user",getApplicationContext());
                    database.admin = Admin.this;
                    System.out.println("uid " + uid.getText() + " " + finalReport_card.getId());
                    database.execute(uid.getText().toString(), String.valueOf(finalReport_card.getId()));
                }
            });

            i += 8;


            listView.addView(report_card);
            report_card = layoutInflater.inflate(R.layout.report_card, null);


        }

    }


    private class LoadImage extends AsyncTask<String, String, Bitmap> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        ImageView img;

        public void Image(ImageView image) {
            img = image;
        }

        protected Bitmap doInBackground(String... args) {

            try {
                if (!args[0].equals("http://hosamazzam.0fees.net/Blog_db_online/User_image/none")) {
                    bitmap = BitmapFactory.decodeStream((InputStream) new URL(args[0]).getContent());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        protected void onPostExecute(Bitmap image) {


            if (image != null) {
                img.setImageBitmap(image);


            } else {
                img.setBackground(getResources().getDrawable(R.drawable.no_image));
            }


        }
    }


    public void doneremove() {
        DB_remote database = new DB_remote("admin",getApplicationContext());
        database.admin = Admin.this;
        database.execute("1");
    }

}

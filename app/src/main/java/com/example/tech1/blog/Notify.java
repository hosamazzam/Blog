package com.example.tech1.blog;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
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
import android.widget.Toast;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by tech 1 on 7/21/2015.
 */
public class Notify extends Activity {
    LinearLayout listView;
    User_info user = User_info.getInstance();
    Bitmap bitmap;
    String arr[];
    Button search;
//    String URL="http://10.0.3.2/Blog_db/User_image/";
    String URL="http://hosamazzam.0fees.net/Blog_db_online/User_image/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requests);
        ActionBar ab = getActionBar();
        ab.hide();
        DB_remote database = new DB_remote("notify",getApplicationContext());
        database.notify = Notify.this;
        database.execute(user.getID(), "sending");
        search = (Button) findViewById(R.id.search_friend_button);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Notify.this, Search_friend.class);
                startActivity(intent);

            }
        });


    }

    public void Status(String result) {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View friend_card = layoutInflater.inflate(R.layout.request_card, null);

        TextView first = (TextView) findViewById(R.id.first_text);
        first.setVisibility(View.GONE);
        arr = result.split("/");
        listView = (LinearLayout) this.findViewById(R.id.listView);

        listView.removeAllViews();

        if(result.equals("")){
            first.setVisibility(View.VISIBLE);
            return;
        }
        for (int i = 0; i < arr.length; i++) {

            final Button add = (Button) friend_card.findViewById(R.id.add_friend);
            final Button remove = (Button) friend_card.findViewById(R.id.remove_firend);
            final TextView name = (TextView) friend_card.findViewById(R.id.User_name);
            ImageView img = (ImageView) friend_card.findViewById(R.id.pic_friend);
            final TextView id = (TextView) friend_card.findViewById(R.id.User_id);
            String ID, NAME;
            System.out.println("ID " + arr[i + 1]);
            friend_card.setId(Integer.valueOf(arr[i]));

            final View finalFriend_card = friend_card;
            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DB_remote database = new DB_remote("accept",getApplicationContext());
                    database.notify = Notify.this;
                    database.execute(String.valueOf(finalFriend_card.getId()), "friend");
                }
            });

            remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DB_remote database = new DB_remote("remove",getApplicationContext());
                    database.notify = Notify.this;
                    database.execute(String.valueOf(finalFriend_card.getId()));
                }
            });
            if (!arr[i + 1].equals(user.getID())) {
                System.out.println("one");
                ID = arr[i + 1];
                NAME = arr[i + 2];
                System.out.println("img " + arr[i + 3]);
                LoadImage load = new LoadImage();
                load.Image(img);
                load.execute(URL + arr[i + 3]);


            } else {
                System.out.println("two");
                ID = arr[i + 4];
                NAME = arr[i + 5];
                System.out.println("img " + arr[i + 6]);

                LoadImage load = new LoadImage();
                load.Image(img);
                load.execute(URL + arr[i + 6]);

            }
            i += 7;
            try {
                Thread.sleep(500);                 //1000 milliseconds is one second.
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            name.setText(NAME);
            id.setText(ID);
            listView.addView(friend_card);
            friend_card = layoutInflater.inflate(R.layout.request_card, null);


        }

    }


    private class LoadImage extends AsyncTask<String, String, Bitmap> {
        ImageView img;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        public void Image(ImageView image) {
            img = image;
        }

        protected Bitmap doInBackground(String... args) {
            onPreExecute();
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

    public void request_result(String data) {
        if (data.equals("remove")) {
            Toast.makeText(getApplicationContext(), "request deleted", Toast.LENGTH_SHORT).show();
        }
        else if (data.equals("accept")) {
            Toast.makeText(getApplicationContext(), "you are now Friends", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(getApplicationContext(), "some thing wrong", Toast.LENGTH_SHORT).show();
        }

        DB_remote database = new DB_remote("notify",getApplicationContext());
        database.notify = Notify.this;
        database.execute(user.getID(), "sending");

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Notify.this, Public.class);
        finish();
        startActivity(intent);
    }
}

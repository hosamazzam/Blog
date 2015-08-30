package com.example.tech1.blog;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by tech 1 on 7/20/2015.
 */
public class Search_friend extends Activity {
    EditText id;
    Button search;
    Bitmap bitmap;
    ImageView img;
    User_info user = User_info.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_friend);
        ActionBar ab = getActionBar();
        ab.hide();

        id = (EditText) findViewById(R.id.sreach_box);
        search = (Button) findViewById(R.id.search_friend_button);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (id.getText().toString().equals(user.getID())) {
                    Toast.makeText(getApplicationContext(), "You can't add your self", Toast.LENGTH_SHORT).show();
                } else {
                    if (!id.getText().toString().equals("")) {
                        DB_remote datebase = new DB_remote("search",getApplicationContext());
                        datebase.search = Search_friend.this;
                        datebase.execute(id.getText().toString());
                    }
                }
            }
        });
    }

    public void CreateDialog(String result) {
        final String arr[] = result.split("/");
        System.out.println("result " + result);
        final Dialog alertD = new Dialog(this);
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View dialog = layoutInflater.inflate(R.layout.add_friend_card, null);

        alertD.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertD.setContentView(dialog);


        Button add = (Button) dialog.findViewById(R.id.search_friend_button);
        img = (ImageView) dialog.findViewById(R.id.pic_friend);
        final TextView uid = (TextView) dialog.findViewById(R.id.User_id);
        final TextView uname = (TextView) dialog.findViewById(R.id.User_name);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DB_remote database = new DB_remote("request",getApplicationContext());
                database.search = Search_friend.this;
                database.execute(user.getID(), uid.getText().toString(),
                        user.getFirst_Name() + " " + user.getLast_name(), arr[1] + " " + arr[2],
                        user.getPic(), arr[9], "sending");
                alertD.cancel();
                alertD.dismiss();
                Intent intent = new Intent(Search_friend.this, Public.class);
                finish();
                startActivity(intent);

            }
        });

        uid.setText(arr[0]);
        uname.setText(arr[1] + " " + arr[2]);
        new LoadImage().execute("http://hosamazzam.0fees.net/Blog_db_online/User_image/" + arr[9]);
        // alertD.getWindow().setLayout(300, 150);
        alertD.show();
    }

    public void Status(String result) {
        if (!result.equals("")) {
            CreateDialog(result);
        } else {
            Toast.makeText(getApplicationContext(), "no user found", Toast.LENGTH_SHORT).show();
        }

    }

    public void Status_request(String result){
        if(result.equals("sending")){
            Toast.makeText(getApplicationContext(), "your request send successfully ", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(getApplicationContext(), "you are sent request before ", Toast.LENGTH_SHORT).show();
        }
    }


    private class LoadImage extends AsyncTask<String, String, Bitmap> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected Bitmap doInBackground(String... args) {
            try {
                if (!args[0].toString().equals("http://hosamazzam.0fees.net/Blog_db_online/User_image/none")) {
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


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Search_friend.this, Friends.class);
        finish();
        startActivity(intent);
    }
}

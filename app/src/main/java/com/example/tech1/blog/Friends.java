package com.example.tech1.blog;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
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
public class Friends extends Activity {
    LinearLayout listView;
    User_info user = User_info.getInstance();
    Bitmap bitmap;
    String arr[];
    Button search;
 //   String URL="http://10.0.3.2/Blog_db/User_image/";
   String URL="http://hosamazzam.0fees.net/Blog_db_online/User_image/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
        ActionBar ab = getActionBar();
        ab.hide();
        DB_remote database = new DB_remote("friends",getApplicationContext());
        database.friend = Friends.this;
        database.execute(user.getID(), "friend");
        search = (Button) findViewById(R.id.search_friend_button);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Friends.this, Search_friend.class);
                finish();
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

            final Button report = (Button) friend_card.findViewById(R.id.add_friend);
            final Button delete = (Button) friend_card.findViewById(R.id.remove_firend);
            final TextView name = (TextView) friend_card.findViewById(R.id.User_name);
            ImageView img = (ImageView) friend_card.findViewById(R.id.pic_friend);
            final TextView id = (TextView) friend_card.findViewById(R.id.User_id);
            delete.setText("Delete");
            report.setText("Report");
            String imagename="";
            friend_card.setId(Integer.valueOf(arr[i]));
            // report.setId(Integer.valueOf(arr[i]));
            final View finalFriend_card = friend_card;
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DeleteDailog(String.valueOf(finalFriend_card.getId()), name.getText().toString());
                }
            });


            String ID, NAME;
            System.out.println("ID " + arr[i + 1]);
            if (!arr[i + 1].equals(user.getID())) {
                System.out.println("one");
                ID = arr[i + 1];
                NAME = arr[i + 2];
                System.out.println("img " + arr[i + 3]);
                imagename= arr[i + 3];
                LoadImage load = new LoadImage();
                load.Image(img);
                load.execute(URL + arr[i + 3]);


            } else {
                System.out.println("two");
                ID = arr[i + 4];
                NAME = arr[i + 5];
                System.out.println("img " + arr[i + 6]);
                imagename= arr[i + 6];
                LoadImage load = new LoadImage();
                load.Image(img);
                load.execute(URL + arr[i + 6]);

            }
            i += 7;

            final String finalImagename = imagename;
            report.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    reportDailog(id.getText().toString(), name.getText().toString()
                            , finalImagename);
                }
            });

            name.setText(NAME);
            id.setText(ID);
            listView.addView(friend_card);
            friend_card = layoutInflater.inflate(R.layout.request_card, null);


        }

    }

    private void DeleteDailog(final String id, String name) {
        System.out.println("id delete " + id);
        final Dialog alertD = new Dialog(this);
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View dialog = layoutInflater.inflate(R.layout.delete_dialog, null);

        alertD.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertD.setContentView(dialog);

        Button delete = (Button) dialog.findViewById(R.id.delete_friend);
        TextView username = (TextView) dialog.findViewById(R.id.User_name);
        username.setText(username.getText() + name);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DB_remote database = new DB_remote("delete_friend",getApplicationContext());
                database.friend = Friends.this;
                database.execute(id);
                database = new DB_remote("friends",getApplicationContext());
                database.friend = Friends.this;
                database.execute(user.getID(), "friend");
                alertD.cancel();
                alertD.dismiss();
            }
        });
       // alertD.getWindow().setLayout(250, 130);
        alertD.show();
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

    private void reportDailog(final String id, final String name,final String image) {
        System.out.println("id delete " + id);
        final Dialog alertD = new Dialog(this);
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View dialog = layoutInflater.inflate(R.layout.report_dialog, null);

        final String[] reason = {""};
        reason[0]="It is fake user";
        alertD.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertD.setContentView(dialog);
        final Button chech1 = (Button) dialog.findViewById(R.id.check1);
        final Button chech2 = (Button) dialog.findViewById(R.id.check2);
        final Button chech3 = (Button) dialog.findViewById(R.id.check3);
        final Button report = (Button) dialog.findViewById(R.id.report_button);

        chech1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chech1.setBackground(getResources().getDrawable(R.drawable.checked));
                chech2.setBackground(getResources().getDrawable(R.drawable.unchecked));
                chech3.setBackground(getResources().getDrawable(R.drawable.unchecked));
                reason[0] = "It is fake user";
            }
        });

        chech2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chech1.setBackground(getResources().getDrawable(R.drawable.unchecked));
                chech2.setBackground(getResources().getDrawable(R.drawable.checked));
                chech3.setBackground(getResources().getDrawable(R.drawable.unchecked));
                reason[0] = "Post impropriate content";
            }
        });

        chech3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chech1.setBackground(getResources().getDrawable(R.drawable.unchecked));
                chech2.setBackground(getResources().getDrawable(R.drawable.unchecked));
                chech3.setBackground(getResources().getDrawable(R.drawable.checked));
                reason[0] = "Post wrong data";
            }
        });

        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DB_remote database = new DB_remote("report",getApplicationContext());
                database.friend = Friends.this;
                database.execute(user.getID(), user.getFirst_Name() + " " +
                        user.getLast_name(),user.getPic(), id, name,image, reason[0]);

                database = new DB_remote("friends",getApplicationContext());
                database.friend = Friends.this;
                database.execute(user.getID(), "friend");
                alertD.cancel();
                alertD.dismiss();
                alertD.cancel();
                alertD.dismiss();
            }
        });

      //  alertD.getWindow().setLayout(300, 200);
        alertD.show();
    }

    public void reported(){
        Toast.makeText(getApplicationContext(),"Your report send successfully",Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Friends.this, Public.class);
        finish();
        startActivity(intent);
    }

}

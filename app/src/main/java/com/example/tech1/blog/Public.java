package com.example.tech1.blog;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by tech 1 on 7/16/2015.
 */
public class Public extends Activity {
    LinearLayout listView;
    ImageButton add_post, notify, friend;
    TextView count;
    Bitmap bitmap;
    User_info user = User_info.getInstance();
    private SwipeRefreshLayout swipeView;
    //    String URL="http://10.0.3.2/Blog_db/User_image/";
    //  String URL = "http://hosamazzam.0fees.net/Blog_db_online/User_image/";
    ArrayList post_ids = new ArrayList();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public);
        ActionBar ab = getActionBar();
        ab.hide();

        swipeView = (SwipeRefreshLayout) findViewById(R.id.swipe_view);
        swipeView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeView.setRefreshing(true);
                swipeView.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        //  swipeView.setRefreshing(false);
                        DB_remote database = new DB_remote("posts",getApplicationContext());
                        database.aPublic = Public.this;
                        database.execute(user.getID());

                        DB_remote database1 = new DB_remote("notify_count",getApplicationContext());
                        database1.aPublic = Public.this;
                        database1.execute(user.getID(), "sending");

                    }
                }, 500);
            }
        });
        swipeView.setColorSchemeColors(Color.GRAY, Color.GREEN, Color.BLUE,
                Color.RED, Color.BLACK);
        swipeView.setDistanceToTriggerSync(10);// in dips
        swipeView.setSize(SwipeRefreshLayout.DEFAULT);// LARGE also can be used


        add_post = (ImageButton) findViewById(R.id.add_Button);
        friend = (ImageButton) findViewById(R.id.friend_Button);
        notify = (ImageButton) findViewById(R.id.notify_Button);

        count = (TextView) findViewById(R.id.count);

        add_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Public.this, add_post.class);
                finish();
                startActivity(intent);
            }
        });
        friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Public.this, Friends.class);
                finish();
                startActivity(intent);
            }
        });
        notify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Public.this, Notify.class);
                finish();
                startActivity(intent);
            }
        });

        DB_remote database = new DB_remote("notify_count",getApplicationContext());
        database.aPublic = Public.this;
        database.execute(user.getID(), "sending");

        database = new DB_remote("posts",getApplicationContext());
        database.aPublic = Public.this;
        database.execute(user.getID());


    }


    public void notify(int num) {
        System.out.println("count " + num);
        if (num == 0) {
            count.setVisibility(View.GONE);

        } else {
            count.setVisibility(View.VISIBLE);
            count.setText(String.valueOf(num));
        }

    }

    public void Posts(String result) {
        if (swipeView.isRefreshing()) {
            System.out.println("false ");
            swipeView.setRefreshing(false);
        }

        if (result.equals("")) {
            return;
        }
        String arr[] = result.split("/");
        post_ids = new ArrayList();
        TextView show = (TextView) findViewById(R.id.first_text);
        show.setVisibility(View.GONE);
        int i = 0;

        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View post_card = layoutInflater.inflate(R.layout.post_card, null);
        listView = (LinearLayout) this.findViewById(R.id.listView);
        listView.removeAllViews();

        for (; i < arr.length; ) {

            if (!post_ids.contains(arr[i])) {
                post_ids.add(arr[i]);
                System.out.println("Arr " + post_ids);

                post_card.setId(Integer.valueOf(arr[i]));
                final ImageView user_img = (ImageView) post_card.findViewById(R.id.pic_user);
                final TextView User_name = (TextView) post_card.findViewById(R.id.User_name);
                final TextView post_time = (TextView) post_card.findViewById(R.id.post_time);
                final ImageView post_img = (ImageView) post_card.findViewById(R.id.pic_post);
                final TextView post_content = (TextView) post_card.findViewById(R.id.post_contnet);
                Time_Ago time = new Time_Ago();
                LoadImage load = new LoadImage();
                load.uimg = user_img;
                load.pimg = post_img;
                load.execute(arr[i + 3], arr[i + 4]);

                post_time.setText(time.Get_Time(arr[i + 9], arr[i + 8], arr[i + 10], arr[i + 6], arr[i + 7]));
                User_name.setText(arr[i + 2]);
                post_content.setText(arr[i + 5]);
                listView.addView(post_card);
                post_card = layoutInflater.inflate(R.layout.post_card, null);
                i += 11;
            } else {
                i += 11;
                System.out.println(i);
            }
        }

    }

    private class LoadImage extends AsyncTask<String, String, ArrayList<Bitmap>> {
        ImageView uimg;
        ImageView pimg;
        String type;

        @Override
        protected void onPreExecute() {

        }

        protected ArrayList<Bitmap> doInBackground(String... args) {
            ArrayList<Bitmap> bitmaps = new ArrayList<>();
            try {
                if (!args[0].equals("none")) {
                    String url =   URLEncoder.encode(args[0], "UTF-8") ;
                    bitmaps.add(BitmapFactory.decodeStream((InputStream)
                            new URL("http://hosamazzam.0fees.net/Blog_db_online/User_image/" + url).getContent()));
                }
                if (args[0].equals("none")) {
                    bitmaps.add(null);
                }
                if (!args[1].equals("none")) {
                  String url =   URLEncoder.encode(args[1], "UTF-8") ;

                    bitmaps.add(BitmapFactory.decodeStream((InputStream)
                            new URL("http://hosamazzam.0fees.net/Blog_db_online/Post_image/" + url).getContent()));
                }
                if (args[1].equals("none")) {
                    bitmaps.add(null);
                }
                System.out.println("image = " + args[0]);
            } catch (Exception e) {
                //    e.printStackTrace();
            }
            return bitmaps;
        }

        protected void onPostExecute(ArrayList<Bitmap> bitmaps) {

            if (bitmaps.get(0) != null) {
                uimg.setImageBitmap(bitmaps.get(0));
            } else {
                uimg.setVisibility(View.GONE);
            }

            if (bitmaps.get(1) != null) {
                pimg.setImageBitmap(bitmaps.get(1));
            } else {
                pimg.setVisibility(View.GONE);
            }
        }

    }

}

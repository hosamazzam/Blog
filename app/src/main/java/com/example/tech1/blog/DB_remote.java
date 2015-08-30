package com.example.tech1.blog;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.widget.Toast;


/**
 * Created by tech 1 on 7/13/2015.
 */
public class DB_remote extends AsyncTask<String, Void, String> {
    Context context;
    String URL;
    Admin admin;
    String type;
    Login_Signup login_signup;
    Search_friend search;
    Friends friend;
    Public aPublic;
    Notify notify;
    add_post post;

    public DB_remote(String type, Context con) {
        this.type = type;
        context = con;
        URL = "http://hosamazzam.0fees.net/Blog_db_online/";
        //URL = "http://10.0.3.2/Blog_db/";
    }
    protected void onPreExecute() {

    }


    @Override
    protected String doInBackground(String... arg0) {
        try {


            String link = null, data = null;
            System.out.println("type " + type);
            if (type == "login") {
                link = URL + "db_login.php";
                data = URLEncoder.encode("Email", "UTF-8") + "=" + URLEncoder.encode(arg0[0], "UTF-8");
                data += "&" + URLEncoder.encode("Password", "UTF-8") + "=" + URLEncoder.encode(arg0[1], "UTF-8");
            }
            if (type == "update_user") {
                link = URL + "db_add_image.php";
                data = URLEncoder.encode("filename", "UTF-8") + "=" + URLEncoder.encode(arg0[0], "UTF-8");
                data += "&" + URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(arg0[1], "UTF-8");
            }
            if (type == "admin") {
                link = URL + "db_admin.php";
                data = URLEncoder.encode("filename", "UTF-8") + "=" + URLEncoder.encode(arg0[0], "UTF-8");
            }
            if (type == "signup") {
                link = URL + "db_signup.php";
                data = URLEncoder.encode("First_Name", "UTF-8") + "=" + URLEncoder.encode(arg0[0], "UTF-8");
                data += "&" + URLEncoder.encode("Last_Name", "UTF-8") + "=" + URLEncoder.encode(arg0[1], "UTF-8");
                data += "&" + URLEncoder.encode("Password", "UTF-8") + "=" + URLEncoder.encode(arg0[2], "UTF-8");
                data += "&" + URLEncoder.encode("Email", "UTF-8") + "=" + URLEncoder.encode(arg0[3], "UTF-8");
                data += "&" + URLEncoder.encode("Gender", "UTF-8") + "=" + URLEncoder.encode(arg0[4], "UTF-8");
                data += "&" + URLEncoder.encode("Address", "UTF-8") + "=" + URLEncoder.encode(arg0[5], "UTF-8");
                data += "&" + URLEncoder.encode("Collage", "UTF-8") + "=" + URLEncoder.encode(arg0[6], "UTF-8");
                data += "&" + URLEncoder.encode("Study", "UTF-8") + "=" + URLEncoder.encode(arg0[7], "UTF-8");
                data += "&" + URLEncoder.encode("Image", "UTF-8") + "=" + URLEncoder.encode(arg0[8], "UTF-8");
                System.out.println("data " + data);
            }
            if (type == "add_post") {
                link = URL + "db_addpost.php";
                data = URLEncoder.encode("user_id", "UTF-8") + "=" + URLEncoder.encode(arg0[0], "UTF-8");
                data += "&" + URLEncoder.encode("user_name", "UTF-8") + "=" + URLEncoder.encode(arg0[1], "UTF-8");
                data += "&" + URLEncoder.encode("user_image", "UTF-8") + "=" + URLEncoder.encode(arg0[2], "UTF-8");
                data += "&" + URLEncoder.encode("content", "UTF-8") + "=" + URLEncoder.encode(arg0[3], "UTF-8");
                data += "&" + URLEncoder.encode("min", "UTF-8") + "=" + URLEncoder.encode(arg0[4], "UTF-8");
                data += "&" + URLEncoder.encode("hour", "UTF-8") + "=" + URLEncoder.encode(arg0[5], "UTF-8");
                data += "&" + URLEncoder.encode("day", "UTF-8") + "=" + URLEncoder.encode(arg0[6], "UTF-8");
                data += "&" + URLEncoder.encode("month", "UTF-8") + "=" + URLEncoder.encode(arg0[7], "UTF-8");
                data += "&" + URLEncoder.encode("year", "UTF-8") + "=" + URLEncoder.encode(arg0[8], "UTF-8");
                data += "&" + URLEncoder.encode("Image", "UTF-8") + "=" + URLEncoder.encode(arg0[9], "UTF-8");
                System.out.println("data " + data);
            }
            if (type == "search") {
                link = URL + "db_search.php";
                data = URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(arg0[0], "UTF-8");
            }
            if (type == "request") {
                link = URL + "db_send_request.php";
                data = URLEncoder.encode("user_id", "UTF-8") + "=" + URLEncoder.encode(arg0[0], "UTF-8");
                data += "&" + URLEncoder.encode("friend_id", "UTF-8") + "=" + URLEncoder.encode(arg0[1], "UTF-8");
                data += "&" + URLEncoder.encode("user_name", "UTF-8") + "=" + URLEncoder.encode(arg0[2], "UTF-8");
                data += "&" + URLEncoder.encode("friend_name", "UTF-8") + "=" + URLEncoder.encode(arg0[3], "UTF-8");
                data += "&" + URLEncoder.encode("user_image", "UTF-8") + "=" + URLEncoder.encode(arg0[4], "UTF-8");
                data += "&" + URLEncoder.encode("friend_image", "UTF-8") + "=" + URLEncoder.encode(arg0[5], "UTF-8");
                data += "&" + URLEncoder.encode("status", "UTF-8") + "=" + URLEncoder.encode(arg0[6], "UTF-8");
            }
            if (type == "friends") {
                link = URL + "db_friends.php";
                data = URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(arg0[0], "UTF-8");
                data += "&" + URLEncoder.encode("status", "UTF-8") + "=" + URLEncoder.encode(arg0[1], "UTF-8");
            }
            if (type == "notify") {
                link = URL + "db_requests.php";
                data = URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(arg0[0], "UTF-8");
                data += "&" + URLEncoder.encode("status", "UTF-8") + "=" + URLEncoder.encode(arg0[1], "UTF-8");
            }
            if (type == "notify_count") {
                link = URL + "db_notify.php";
                data = URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(arg0[0], "UTF-8");
                data += "&" + URLEncoder.encode("status", "UTF-8") + "=" + URLEncoder.encode(arg0[1], "UTF-8");
            }
            if (type == "delete_friend" || type == "remove") {
                link = URL + "db_delete_friends.php";
                data = URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(arg0[0], "UTF-8");
            }
            if (type == "delete_user") {
                link = URL + "db_delete_user.php";
                data = URLEncoder.encode("uid", "UTF-8") + "=" + URLEncoder.encode(arg0[0], "UTF-8");
                data += "&" + URLEncoder.encode("rid", "UTF-8") + "=" + URLEncoder.encode(arg0[1], "UTF-8");
            }
            if (type == "accept") {
                link = URL + "db_accept_friends.php";
                data = URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(arg0[0], "UTF-8");
            }
            if (type == "posts") {
                link = URL + "db_posts.php";
                data = URLEncoder.encode("ID", "UTF-8") + "=" + URLEncoder.encode(arg0[0], "UTF-8");
                System.out.println("data ; " + data);
            }
            if (type == "report") {
                link = URL + "db_report.php";
                data = URLEncoder.encode("user_id", "UTF-8") + "=" + URLEncoder.encode(arg0[0], "UTF-8");
                data += "&" + URLEncoder.encode("user_name", "UTF-8") + "=" + URLEncoder.encode(arg0[1], "UTF-8");
                data += "&" + URLEncoder.encode("user_image", "UTF-8") + "=" + URLEncoder.encode(arg0[2], "UTF-8");
                data += "&" + URLEncoder.encode("reported_id", "UTF-8") + "=" + URLEncoder.encode(arg0[3], "UTF-8");
                data += "&" + URLEncoder.encode("reported_name", "UTF-8") + "=" + URLEncoder.encode(arg0[4], "UTF-8");
                data += "&" + URLEncoder.encode("reported_image", "UTF-8") + "=" + URLEncoder.encode(arg0[5], "UTF-8");
                data += "&" + URLEncoder.encode("reason", "UTF-8") + "=" + URLEncoder.encode(arg0[6], "UTF-8");
                System.out.println("date : " + data);
            }

            URL url = new URL(link);
            URLConnection conn = url.openConnection();

            conn.setDoOutput(true);
            conn.setConnectTimeout(60000);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

            wr.write(data);
            wr.flush();

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            StringBuilder sb = new StringBuilder();
            String line = null;

            // Read Server Response
            while ((line = reader.readLine()) != null) {
                System.out.println("line = " + line);
                sb.append(line);
                break;
            }

            return sb.toString();
        } catch (Exception e) {
         //   cancel(true);
            return "no_internet_connection";
        }

    }

    @Override
    protected void onPostExecute(String result) {
        if (result == "no_internet_connection") {
            Toast.makeText(context, "No internet connection.", Toast.LENGTH_LONG).show();
        } else if (type == "signup") {
            login_signup.signup_status(result);
        } else if (type == "update_user") {
            if (result.equals("updated")) {
                System.out.println("image added !!");
            }
        } else if (type == "login") {
            System.out.println("result = " + result);
            if (result.equals("")) {
                login_signup.login_status(false, result);
            } else {
                login_signup.login_status(true, result);
            }
        } else if (type == "add_post") {
            post.post_status(result);
        } else if (type == "search") {
            search.Status(result);
        } else if (type == "request") {
            search.Status_request(result);
        } else if (type == "friends") {

            friend.Status(result);

        } else if (type == "notify") {

            notify.Status(result);

        } else if (type == "notify_count") {
            aPublic.notify(Integer.valueOf(result));
        } else if (type == "remove") {
            notify.request_result(result);
        } else if (type == "accept") {
            notify.request_result(result);
        } else if (type == "report") {
            if (result.equals("reported"))
                friend.reported();
        } else if (type == "posts") {
            aPublic.Posts(result);
        } else if (type == "admin") {
            if (!result.equals("")) {
                admin.Status(result);
            }
        } else if (type == "delete_user") {
            admin.doneremove();
        }
    }
}
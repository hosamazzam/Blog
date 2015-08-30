package com.example.tech1.blog;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by tech 1 on 7/16/2015.
 */
public class add_post extends Activity {
    EditText post_contnet;
    Button upload, post;
    ImageView img;
    User_info user = User_info.getInstance();
    String filename = "none";
    ProgressDialog prgDialog;
    int RESULT_LOAD_IMG;
    String imgDecodableString = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);
        ActionBar ab = getActionBar();
        ab.hide();
        prgDialog = new ProgressDialog(this);

        post_contnet = (EditText) findViewById(R.id.post_contnet);
        upload = (Button) findViewById(R.id.upload_image);
        post = (Button) findViewById(R.id.post_button);
        img = (ImageView) findViewById(R.id.Post_image);

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imgDecodableString != "") {
                    Upload image = new Upload();
                    prgDialog.setMessage("Uploading...");
                    prgDialog.setCancelable(false);
                    prgDialog.show();
                    image.user_id = user.getID();
                    image.imgPath = imgDecodableString;
                    image.post = add_post.this;
                    image.uploadImage();
                } else {
                    finish_uploading();
                }
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, RESULT_LOAD_IMG);

            }
        });
    }


    public void post_status(String result) {
        if (result.equals("post_done")) {
            Toast.makeText(getApplicationContext(), "your post added successfully ", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(getApplicationContext(), "some thing go wrong try again", Toast.LENGTH_SHORT).show();
        }

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data

                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                System.out.println("file " + filePathColumn);
                // Get the cursor
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imgDecodableString = cursor.getString(columnIndex);
                cursor.close();
                System.out.println("file " + imgDecodableString);

                // Set the Image in ImageView after decoding the String
                img.setBackground(getResources().getDrawable(R.drawable.no_image));


                img.setImageBitmap(BitmapFactory
                        .decodeFile(imgDecodableString));
                String fileNameSegments[] = imgDecodableString.split("/");
                filename = fileNameSegments[fileNameSegments.length - 1];


            }
        } catch (Exception e) {

        }
    }

    public void finish_uploading() {

        if (imgDecodableString != "") {
            prgDialog.hide();
            filename = user.getID() + filename;
        }
        System.out.println("file name "+filename);
        DB_remote datebase = new DB_remote("add_post",getApplicationContext());
        datebase.post = add_post.this;
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR);
        if (c.get(Calendar.AM_PM) == 1) {
            hour = 12 + c.get(Calendar.HOUR);
        }

        datebase.execute(user.getID(), user.getFirst_Name() + " " + user.getLast_name(), user.getPic(),
                post_contnet.getText().toString(), String.valueOf(c.get(Calendar.MINUTE)),
                String.valueOf(hour), String.valueOf(c.get(Calendar.DAY_OF_MONTH)),
                String.valueOf(c.get(Calendar.MONTH)+1), String.valueOf(c.get(Calendar.YEAR)), filename);

        Intent intent = new Intent(add_post.this, Public.class);
        finish();
        startActivity(intent);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(add_post.this, Public.class);
        finish();
        startActivity(intent);
    }

    public void failed(){
        Toast.makeText(getApplicationContext(),"upload image failed try again :v ",Toast.LENGTH_LONG).show();
    }

}

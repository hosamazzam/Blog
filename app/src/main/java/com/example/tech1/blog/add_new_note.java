package com.example.tech1.blog;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by tech 1 on 7/6/2015.
 */
public class add_new_note extends Activity {
    Button choose, save;
    int RESULT_LOAD_IMG;
    Bundle intentdata;
    ImageView imgView;
    String imgDecodableString = "none";
    EditText title, description;
    boolean update = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notes);
        imgView = (ImageView) findViewById(R.id.note_image);
        choose = (Button) findViewById(R.id.choose_button);
        save = (Button) findViewById(R.id.note_save);
        title = (EditText) findViewById(R.id.alert_title);
        description = (EditText) findViewById(R.id.note_description);
        intentdata = getIntent().getExtras();
        if (intentdata != null) {
            if (intentdata.containsKey("id")) {

                System.out.println("id = " + intentdata.getString("id"));
                update = true;
                title.setText(intentdata.getString("title"));
                description.setText(intentdata.getString("content"));
                try {
                    Bitmap myBitmap = BitmapFactory
                            .decodeFile(intentdata.getString("path").toString());
                    imgView.setImageBitmap(myBitmap);
                    imgDecodableString = intentdata.getString("path").toString();
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("no image found");

                }

            }
        }
        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DB database = new DB(getApplicationContext());
                database.setDATABASE_TABLE("private_note");
                if (IsVaild()) {
                    if (update) {
                        database.updatenote(Integer.valueOf(intentdata.getString("id"))
                                , "private_note", title.getText().toString(), description.getText().toString(),
                                imgDecodableString);

                        Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_LONG).show();

                    } else {
                        database.insert_note("private_note", title.getText().toString(),
                                description.getText().toString(), imgDecodableString);

                        Toast.makeText(getApplicationContext(), "Added", Toast.LENGTH_LONG).show();
                    }
                    Intent intent = new Intent(getApplicationContext(), Private.class);
                    Private.object.finish();
                    Private.index = 0;
                    finish();
                    startActivity(intent);
                }

            }
        });


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
        // Intent myIntent = new Intent(getApplicationContext(),Home.class);
        //startActivityForResult(myIntent, 0);
        DB db = new DB(getApplicationContext());
        System.out.println("deleete");
        db.delete("private_note", Integer.valueOf(intentdata.getString("id")));
        Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getApplicationContext(), Private.class);
        Private.object.finish();
        Private.index = 0;
        finish();
        startActivity(intent);
        return true;
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
                imgView.setBackground(getResources().getDrawable(R.drawable.no_image));

                RoundImage circle = new RoundImage();
                imgView.setImageBitmap(circle.getCircleBitmap(BitmapFactory
                        .decodeFile(imgDecodableString)));

            } else {

            }
        } catch (Exception e) {

        }

    }

    public Boolean IsVaild() {
        System.out.println("title " + title.getText());

        if (title.getText().toString().equals("") || title.getText().toString().equals(null)) {
            title.setError("Please insert title", getResources().getDrawable(R.drawable.ic_action_error));
            return false;
        }

        return true;

    }
}

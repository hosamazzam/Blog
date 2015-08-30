package com.example.tech1.blog;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by tech 1 on 7/13/2015.
 */
public class Signup_Upload extends Activity {

    int RESULT_LOAD_IMG,CAMERA_PIC_REQUEST;
    String imgDecodableString = "";
    ImageView pic;
    Button upload,skip;
    TextView ID;
    ProgressDialog prgDialog;
    Bundle bundle;
    User_info user = User_info.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        pic = (ImageView) findViewById(R.id.FacePic);
        bundle = getIntent().getExtras();
        ID = (TextView) findViewById(R.id.User_id);
        upload = (Button) findViewById(R.id.upload_image);
        skip = (Button) findViewById(R.id.skip_button);

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Signup_Upload.this, Public.class);
                finish();
                startActivity(intent);
            }
        });
        ActionBar ab = getActionBar();
        ab.hide();
        prgDialog = new ProgressDialog(this);

                ID.setText("Your ID: " + user.getID());

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("clicked ");
                CreateDailog();
            }
        });

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
                pic.setBackground(getResources().getDrawable(R.drawable.no_image));

                pic.setImageBitmap(BitmapFactory
                        .decodeFile(imgDecodableString));

                Upload image = new Upload();
                prgDialog.setMessage("Uploading...");
                prgDialog.setCancelable(false);
                prgDialog.show();
                image.user_id=user.getID();
                image.type="user";
                image.imgPath = imgDecodableString;
                image.up = Signup_Upload.this;
                image.uploadImage();

            } if (requestCode == CAMERA_PIC_REQUEST) {
                String[] projection = { MediaStore.Images.Media.DATA };
                Cursor cursor = managedQuery(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        projection, null, null, null);
                int column_index_data = cursor
                        .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToLast();

                String imagePath = cursor.getString(column_index_data);
                System.out.println("image "+imagePath);

            }
        } catch (Exception e) {

        }

    }

    public void CreateDailog() {

        final Dialog alertD = new Dialog(this);
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View dialog = layoutInflater.inflate(R.layout.image_dailog, null);

        alertD.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertD.setContentView(dialog);

        Button choose = (Button) dialog.findViewById(R.id.choose_photo);
        choose.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                System.out.println("enterd");
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
                alertD.cancel();
                alertD.dismiss();

            }
        });
        Button take = (Button) dialog.findViewById(R.id.take_photo);
        take.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                System.out.println("enterd");
                Intent galleryIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(galleryIntent, CAMERA_PIC_REQUEST);
                alertD.cancel();
                alertD.dismiss();
            }
        });


//        alertD.getWindow().setLayout(400, 390);
        alertD.show();

    }

    public void finish_uploading() {
        prgDialog.hide();
        DB_remote datebase= new DB_remote("update_user",getApplicationContext());
        System.out.println("finish upload "+imgDecodableString+" "+user.getID());
        String fileNameSegments[] = imgDecodableString.split("/");
        String fileName = fileNameSegments[fileNameSegments.length - 1];
        datebase.execute(user.getID()+fileName,user.getID());

        Intent intent = new Intent(Signup_Upload.this, Public.class);
        finish();
        startActivity(intent);
    }
    public void failed(){
        Toast.makeText(getApplicationContext(),"upload image failed try again :v ",Toast.LENGTH_LONG).show();
    }

}


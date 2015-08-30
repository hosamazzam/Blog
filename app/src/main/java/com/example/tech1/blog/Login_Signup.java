package com.example.tech1.blog;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 * Created by tech 1 on 7/5/2015.
 */
public class Login_Signup extends Activity {

    Button signup, login, sign_in;
    RelativeLayout list;
    String gender = "male";
    Button sign_up;
    ImageView logo;
    Button male, female, gender_button;
    EditText fname, lname, email, pass, year, address, collage, gender_text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_signup);
        ActionBar ab = getActionBar();
        ab.hide();
        logo = (ImageView) findViewById(R.id.logo);
        signup = (Button) findViewById(R.id.sign_up_button);
        sign_in = (Button) findViewById(R.id.sign_in_button);
        list = (RelativeLayout) findViewById(R.id.listView);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.removeAllViews();
                sign_in.setText("Login");
                logo.setBackground(getResources().getDrawable(R.drawable.logo_no_titel));
                signup.setBackground(getResources().getDrawable(R.drawable.tab_box));
                sign_in.setBackground(getResources().getDrawable(R.drawable.button_box));

                LayoutInflater layoutInflater = LayoutInflater.from(getApplicationContext());
                View tab_signup = layoutInflater.inflate(R.layout.tab_signup, null);
                list.addView(tab_signup);

                sign_up = (Button) tab_signup.findViewById(R.id.signup_button);
                male = (Button) tab_signup.findViewById(R.id.male_gender);
                female = (Button) tab_signup.findViewById(R.id.female_gender);
                gender_text = (EditText) tab_signup.findViewById(R.id.gender_box);
                gender_button = (Button) tab_signup.findViewById(R.id.gender_button);
                male.setVisibility(View.GONE);
                female.setVisibility(View.GONE);
                gender_button.bringToFront();
                fname = (EditText) tab_signup.findViewById(R.id.First_name_box);
                lname = (EditText) tab_signup.findViewById(R.id.Last_name_box);
                email = (EditText) tab_signup.findViewById(R.id.Email_box);
                pass = (EditText) tab_signup.findViewById(R.id.password_box);
                address = (EditText) tab_signup.findViewById(R.id.Address_box);
                year = (EditText) tab_signup.findViewById(R.id.Study_year_box);
                collage = (EditText) tab_signup.findViewById(R.id.Collage_box);

                gender_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        male.setVisibility(View.VISIBLE);
                        male.bringToFront();
                        female.bringToFront();
                        female.setVisibility(View.VISIBLE);
                    }
                });

                male.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (!hasFocus) {
                            male.setVisibility(View.GONE);
                            female.setVisibility(View.GONE);
                        }
                    }
                });
                male.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gender_text.setText("male");
                        gender = "male";
                        male.setVisibility(View.GONE);
                        female.setVisibility(View.GONE);
                    }
                });

                female.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gender_text.setText("female");
                        gender = "female";
                        male.setVisibility(View.GONE);
                        female.setVisibility(View.GONE);
                    }
                });


                sign_up.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        System.out.println("clicked ");
                        if (IsVaild()) {
                            DB_remote db = new DB_remote("signup",getApplicationContext());
                            db.login_signup = Login_Signup.this;
                            db.execute(fname.getText().toString(), lname.getText().toString(),
                                    pass.getText().toString(), email.getText().toString(), gender,
                                    address.getText().toString(), collage.getText().toString(),
                                    year.getText().toString(), "none");

                        } else {
                            Toast.makeText(getApplicationContext(), "Please fill all forms", Toast.LENGTH_LONG).show();
                        }
                    }
                });


            }
        });
        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.removeAllViews();
                sign_in.setText("Sign in");
                logo.setBackground(getResources().getDrawable(R.drawable.logo));
                signup.setBackground(getResources().getDrawable(R.drawable.button_box));
                sign_in.setBackground(getResources().getDrawable(R.drawable.tab_box));

                LayoutInflater layoutInflater = LayoutInflater.from(getApplicationContext());
                View tab_login = layoutInflater.inflate(R.layout.tab_login, null);
                list.addView(tab_login);

                Button forget = (Button) findViewById(R.id.forget);
                forget.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Login_Signup.this, ForgetPassword.class);
                        finish();
                        startActivity(intent);
                    }
                });
                login = (Button) findViewById(R.id.login_button);
                final EditText email = (EditText) tab_login.findViewById(R.id.email_login);
                final EditText pass = (EditText) tab_login.findViewById(R.id.password_login);

                login.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(email.getText().toString().equals("admin")&& pass.getText().toString().equals("admin")){
                            Intent intent = new Intent(Login_Signup.this, Admin.class);
                            finish();
                            startActivity(intent);
                        }
                        else {
                        DB_remote db = new DB_remote("login",getApplicationContext());
                        db.login_signup = Login_Signup.this;
                        db.execute(email.getText().toString(), pass.getText().toString());
                    }}
                });
            }
        });
    }


    public Boolean IsVaild() {

        if (fname.getText().toString().equals("") || fname.getText().toString().equals(null)) {
            return false;
        }
        if (lname.getText().toString().equals("") || lname.getText().toString().equals(null)) {
            return false;
        }
        if (pass.getText().toString().equals("") || pass.getText().toString().equals(null)) {
            return false;
        }
        if (email.getText().toString().equals("") || email.getText().toString().equals(null)) {
            return false;
        }
        if (collage.getText().toString().equals("") || collage.getText().toString().equals(null)) {
            return false;
        }
        if (year.getText().toString().equals("") || year.getText().toString().equals(null)) {
            return false;
        }
        if (address.getText().toString().equals("") || address.getText().toString().equals(null)) {
            return false;
        }
        if (gender_text.getText().toString().equals("") || gender_text.getText().toString().equals(null)) {
            return false;
        }
        return true;
    }

    public void login_status(boolean flag, String result) {
        if (flag) {
            User_info user = User_info.getInstance();
            String arr[]= result.split("/");
            user.setID(arr[0]);
            user.setFirst_Name(arr[1]);
            user.setLast_name(arr[2]);
            user.setPass(arr[3]);
            user.setEmail(arr[4]);
            user.setGender(arr[5]);
            user.setAddress(arr[6]);
            user.setYear(arr[7]);
            user.setCollage(arr[8]);
            user.setPic(arr[9]);

            Intent intent = new Intent(Login_Signup.this, Public.class);
            finish();
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), "Try again,user name or password wrong", Toast.LENGTH_LONG).show();
        }
    }

    public void signup_status(String result){
        String arr[] = result.split("/");
        Intent intent = new Intent(Login_Signup.this, Signup_Upload.class);
        User_info user = User_info.getInstance();
        user.setID(arr[0]);
        user.setFirst_Name(arr[1]);
        user.setLast_name(arr[2]);
        user.setPass(arr[3]);
        user.setEmail(arr[4]);
        user.setGender(arr[5]);
        user.setAddress(arr[6]);
        user.setYear(arr[7]);
        user.setCollage(arr[8]);
        user.setPic(arr[9]);

        Toast.makeText(getApplicationContext(), "congratulation just one step", Toast.LENGTH_LONG).show();
        finish();
        startActivity(intent);
    }

}

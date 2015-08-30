package com.example.tech1.blog;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by tech 1 on 7/5/2015.
 */
public class Home extends Activity {

    Button pri_but, pub_but;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        pri_but = (Button) findViewById(R.id.private_button);
        pub_but = (Button) findViewById(R.id.public_button);
        ActionBar ab = getActionBar();
        ab.hide();

        pri_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this,Private.class);
                startActivity(intent);
            }
        });
        pub_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this,Login_Signup.class);
                startActivity(intent);
               // Toast.makeText(getApplicationContext(),"coming soooon",Toast.LENGTH_LONG).show();
            }
        });
    }
}

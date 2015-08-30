package com.example.tech1.blog;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by tech 1 on 7/30/2015.
 */
public class ForgetPassword extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        ActionBar ab = getActionBar();
        ab.hide();
        EditText email = (EditText) findViewById(R.id.email_forget_box);
        Button send = (Button) findViewById(R.id.send_email_button);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    GmailSender sender = new GmailSender("hosam.azzam2@gmail.com", "1234567891011121314");
                    sender.sendMail("This is Subject",
                            "This is Body",
                            "hosam.azzam2@gmail.com",
                            "hosam_azzam2010@yahoo.com");
                    System.out.println("send");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


}

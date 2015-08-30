package com.example.tech1.blog;

import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.CharacterPickerDialog;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by tech 1 on 8/5/2015.
 */
public class ShowDialog extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alerm);

        Bundle bundle = getIntent().getExtras();
        TextView title = (TextView) findViewById(R.id.alerm_title);
        title.setText(bundle.get("title").toString());
        Button ok= (Button) findViewById(R.id.ok);
        Uri uriNotification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        final Ringtone ringTone = RingtoneManager
                .getRingtone(getApplicationContext(), uriNotification);
        ringTone.play();
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ringTone.stop();
                finish();
            }
        });
    }
}

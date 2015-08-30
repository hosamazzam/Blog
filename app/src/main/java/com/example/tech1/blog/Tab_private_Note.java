package com.example.tech1.blog;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * Created by tech 1 on 7/5/2015.
 */
public class Tab_private_Note extends Activity {
    LinearLayout listView;
    ArrayList arr = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_private_notes);
        LayoutInflater layoutInflater = LayoutInflater.from(this);

        DB database = new DB(this);
        arr = database.getAllNote("private_note");
        System.out.println("arrr " + arr);
        View note_card = layoutInflater.inflate(R.layout.note_card, null);
        listView = (LinearLayout) this.findViewById(R.id.listView);

        Button newnote = (Button) findViewById(R.id.add_note_item);
        newnote.bringToFront();
        newnote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getApplicationContext(), add_new_note.class);
                startActivity(myIntent);

            }
        });

        for (int i = 0; i < arr.size(); ) {

            note_card.setId(i);

            final ImageButton img = (ImageButton) note_card.findViewById(R.id.note_image);
            final TextView title = (TextView) note_card.findViewById(R.id.alert_title);
            final TextView content = (TextView) note_card.findViewById(R.id.note_description);
            img.setId(i);
            final View finalNote_card = note_card;
            note_card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), add_new_note.class);
                    intent.putExtra("id", arr.get(finalNote_card.getId()).toString());
                    intent.putExtra("title", title.getText());
                    intent.putExtra("content", content.getText());
                    intent.putExtra("path", arr.get(Integer.valueOf(finalNote_card.getId())+3).toString());

                    startActivity(intent);
                }
            });
            title.setText(arr.get(i + 1).toString());
            content.setText(arr.get(i + 2).toString());
            try {

                Bitmap myBitmap = BitmapFactory
                        .decodeFile(arr.get(i + 3).toString());
                img.setImageBitmap(myBitmap);
                System.out.println("out "+arr.get(i + 3).toString());
                if(arr.get(i + 3).toString().compareTo("none")==0){
                    img.setImageResource(com.example.tech1.blog.R.drawable.no_image);
                }
            } catch (Exception e) {
                e.printStackTrace();
                img.setBackground(null);
                img.setImageResource(com.example.tech1.blog.R.drawable.no_image);
                System.out.println("no image found");

            }
            // listView.removeView(note_card);
            listView.addView(note_card);
            note_card = layoutInflater.inflate(R.layout.note_card, null);

            i += 4;
        }
    }
}

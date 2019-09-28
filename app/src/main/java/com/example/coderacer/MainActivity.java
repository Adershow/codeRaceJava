package com.example.coderacer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        img = (ImageView) findViewById(R.id.image);

        img.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                redirect(Bage.class);
            }
        });
    }

    public void redirect(Class i){
        Intent ia = new Intent(this, i);
        startActivity(ia);
    }
}

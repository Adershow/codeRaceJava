package com.example.coderacer;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class Admin extends AppCompatActivity {

    TextView latit;
    TextView longit;
    private SQLiteDatabase db;
    private CoderacerBD banco;
    Button btn1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        latit = findViewById(R.id.lat);
        longit = findViewById(R.id.lo);
        btn1 =  findViewById(R.id.btn);
        banco = new CoderacerBD(getApplicationContext());

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ponto p = new Ponto();
                CoderacerBD dao = new CoderacerBD(getApplicationContext());
                p.setLatitude(latit.getText().toString());
                p.setLongitude(longit.getText().toString());

                Toast.makeText(Admin.this, "Inserido com sucesso"+ dao.save(p).toString(), Toast.LENGTH_SHORT).show();
            }
        });


    }



    public void redirect(Class i){
        Intent ia = new Intent(this, i);
        startActivity(ia);
    }



}

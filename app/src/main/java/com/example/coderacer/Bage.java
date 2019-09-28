package com.example.coderacer;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.*;

import java.util.*;

public class Bage extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private SQLiteDatabase db;
    private CoderacerBD banco;
    private AlertDialog alertDialog;
    private Ponto ponto;
    String b = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bage);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.maps);
        mapFragment.getMapAsync(this);
        ponto = new Ponto();
        banco = new CoderacerBD(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        if(banco.getAll().size()>0){
            Log.i("test","entrou");
            for (Ponto ponto:banco.getAll()){
                String tipo = ponto.getTipo();
                Bitmap b;
                Bitmap smallmarker;
                BitmapDescriptor smallmarkericon;
                LatLng pos = new LatLng(Double.parseDouble(ponto.getLatitude()),Double.parseDouble(ponto.getLongitude()));
                switch (tipo){

                    case "Obra":
                        b = BitmapFactory.decodeResource(getResources(), R.drawable.barrier);
                        smallmarker = Bitmap.createScaledBitmap(b, 100, 100, false);
                        smallmarkericon = BitmapDescriptorFactory.fromBitmap(smallmarker);
                        mMap.addMarker(new MarkerOptions().position(pos).title(ponto.getDescricao())).setIcon(smallmarkericon);
                        break;
                    case "Feira":
                        b = BitmapFactory.decodeResource(getResources(), R.drawable.grocery);
                        smallmarker = Bitmap.createScaledBitmap(b, 100, 100, false);
                        smallmarkericon = BitmapDescriptorFactory.fromBitmap(smallmarker);
                        mMap.addMarker(new MarkerOptions().position(pos).title(ponto.getDescricao())).setIcon(smallmarkericon);                        break;
                    case "Acidente":
                        b = BitmapFactory.decodeResource(getResources(), R.drawable.accident);
                        smallmarker = Bitmap.createScaledBitmap(b, 125, 125, false);
                        smallmarkericon = BitmapDescriptorFactory.fromBitmap(smallmarker);
                        mMap.addMarker(new MarkerOptions().position(pos).title(ponto.getDescricao())).setIcon(smallmarkericon);                        break;
                    case "Evento":
                        b = BitmapFactory.decodeResource(getResources(), R.drawable.balloons);
                        smallmarker = Bitmap.createScaledBitmap(b, 125, 125, false);
                        smallmarkericon = BitmapDescriptorFactory.fromBitmap(smallmarker);
                        mMap.addMarker(new MarkerOptions().position(pos).title(ponto.getDescricao())).setIcon(smallmarkericon);                        break;

                }

            }
        }

        LatLng pos1 = new LatLng(-31.32, -54.105);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pos1, 13.5f));

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(final LatLng latLng) {


                AlertDialog.Builder builder = new AlertDialog.Builder(Bage.this);
                builder.setTitle("Digite uma descricao");
                final EditText descricao = new EditText(Bage.this);
                final ListView list = new ListView(Bage.this);
                builder.setView(descricao);

                String[] a = {"Obra", "Feira", "Acidente", "Evento"};

                builder.setSingleChoiceItems(a, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0:
                                b = "Obra";
                                break;
                            case 1:
                                b = "Feira";
                                break;
                            case 2:
                                b = "Acidente";
                                break;
                            case 3:
                                b = "Evento";
                                break;
                        }
                    }
                });




                builder.setPositiveButton("Enviar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(Bage.this, "Inserido com sucesso" + descricao , Toast.LENGTH_SHORT).show();
                        ponto.setLatitude(String.valueOf(latLng.latitude));
                        ponto.setLongitude(String.valueOf(latLng.longitude));
                        ponto.setDescricao(descricao.getText().toString());
                        ponto.setTipo(b);
                        banco.save(ponto);
                        onMapReady(googleMap);
                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });

    }
}

package com.example.daysi.mobilicious;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class descripcionRestaurante extends AppCompatActivity  implements OnMapReadyCallback {
    private GoogleMap mMap;
    TextView res,nit,por,fon;
    String id,idA;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descripcion_restaurante);

        Bundle bun=getIntent().getExtras();
        id=bun.getString("idRestaurante");
        idA=bun.getString("idAdmin");

        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://192.168.43.238:3000/api/restaurantes", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
                try {
                    JSONArray jsonArray = timeline;
                    //Toast.makeText(getApplicationContext(),timeline+"entra",Toast.LENGTH_SHORT).show();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject r=jsonArray.getJSONObject(i);
                        if(id.equals(r.getString("_id"))){
                            res= (TextView) findViewById(R.id.nombreRestaurante);
                            res.setText(r.getString("nombre"));
                            nit= (TextView) findViewById(R.id.nitRestaurante);
                            nit.setText(r.getString("nit"));
                            por= (TextView) findViewById(R.id.nombrePropietario);
                            por.setText(r.getString("propietario"));
                            fon= (TextView) findViewById(R.id.telefonoRestaurante);
                            fon.setText(r.getString("telefono"));
                        }
                    }
                } catch (Exception e) {

                };
            }
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        if(idA.equals("")){
            Button btn= (Button) findViewById(R.id.verMenu);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent in = new Intent(getApplicationContext(), MenuRestaurante.class);
                    in.putExtra("id2",id);
                    startActivity(in);

                }
            });
        }else{
            Button btn= (Button) findViewById(R.id.verMenu);
            btn.setText("Ver pedidos");
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent in = new Intent(getApplicationContext(), MenuRestaurante.class);
                    //in.putExtra("id2",id);
                    startActivity(in);

                }
            });
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }
}

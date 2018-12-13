package com.example.daysi.mobilicious;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
public class ListaRestaurantes extends AppCompatActivity implements View.OnClickListener{
    static ArrayList<String> restaurantes=new ArrayList<>();
    static ArrayList<String> nombresRes=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_restaurantes);

        LinearLayout llBotonera = (LinearLayout) findViewById(R.id.llBotoneraAdmin);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT );

        Button agre=findViewById(R.id.AgregarRestauranteAdmin);
        agre.setOnClickListener(this);

        correr();
        //Toast.makeText(getApplicationContext(),timeline+"entra",Toast.LENGTH_LONG).show();
        for (int i = 0; i < nombresRes.size(); i++) {
            Button button = new Button(this);
            button.setId(i);
            button.setOnClickListener(this);
            //Toast.makeText(this,nombresRes.get(i)+"",Toast.LENGTH_SHORT).show();
            button.setLayoutParams(lp);
            button.setText("restaurantes "+nombresRes.get(i));
            llBotonera.addView(button);
        }
    }

    @Override
    public void onClick(View v) {
        for(int i=0;i<nombresRes.size();i++){
            if(v.getId() == i){
                Intent in =new Intent(getApplicationContext(),descripcionRestaurante.class);
                in.putExtra("idRestaurante",restaurantes.get(i));
                in.putExtra("idAdmin","noEstavacio");
                startActivity(in);
            }
        }
        if(v.getId() == R.id.AgregarRestauranteAdmin){
            Intent in =new Intent(getApplicationContext(),RegistrarRestaurante.class);
            startActivity(in);
        }
    }
    void correr(){
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://192.168.43.238:3000/api/restaurantes", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
                try {

                    JSONArray jsonArray = timeline;
                    //Toast.makeText(getApplicationContext(),timeline+"entra",Toast.LENGTH_LONG).show();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject r=jsonArray.getJSONObject(i);
                        restaurantes.add(r.getString("_id"));
                        nombresRes.add(r.getString("nombre"));

                    }
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(),"no entra al array",Toast.LENGTH_SHORT).show();
                };
            }
        });
    }
}


package com.example.daysi.mobilicious;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import cz.msebera.android.httpclient.Header;
import java.util.ArrayList;



public class MenuRestaurante extends AppCompatActivity implements View.OnClickListener{
    TextView precio;
    int cant=0;
    static ArrayList<String> descripcion=new ArrayList<>();
    static ArrayList<String> pre=new ArrayList<>();
    static ArrayList<String> nombre=new ArrayList<>();
    String id;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_restaurante);

        Bundle bun=getIntent().getExtras();
        id=bun.getString("id2");

        precio=findViewById(R.id.monto);
        LinearLayout llBotonera = (LinearLayout) findViewById(R.id.llBotoneraMenu);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT );

        Button reset=findViewById(R.id.Borrar);
        reset.setOnClickListener(this);
        Button pedir=findViewById(R.id.realizarPedido);
        pedir.setOnClickListener(this);
        rescatar();
        //llBotonera.clearAnimation();
        for (int i=0; i<nombre.size(); i++){
            Button button = new Button(this);
            button.setId(i);
            button.setOnClickListener(this);
            TextView tex=new TextView(this);
            tex.setText(nombre.get(i));
            TextView des=new TextView(this);
            //des.setTextAlignment(Ali);
            des.setText(descripcion.get(i));
            button.setLayoutParams(lp);
            button.setText(pre.get(i));
            llBotonera.addView(tex);
            llBotonera.addView(des);
            llBotonera.addView(button);
        }

    }
    void rescatar(){
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://192.168.43.238:3000/api/menus", null, new JsonHttpResponseHandler() {
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
                        if(id.equals(r.getString("id_restaurant"))) {
                            nombre.add(r.getString("nombre"));
                            pre.add(r.getString("precio"));
                            descripcion.add(r.getString("descripcion"));
                        }

                    }
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(),"no entra al array",Toast.LENGTH_SHORT).show();
                };
            }
        });
    }
    @Override
    public void onClick(View v){
        for(int i=0;i<pre.size();i++){
            if(v.getId() == i) {
                cant+=Integer.parseInt(pre.get(i));
            }
        }
        if(v.getId() == R.id.Borrar){
            cant=0;
        }
        if(v.getId() == R.id.realizarPedido){

        }
        setear(cant);
    }
    void setear(int i){
        precio.setText(i+" bs.");
    }
    private void Agregar() {
        AsyncHttpClient client = new AsyncHttpClient();
        final RequestParams params = new RequestParams();

        /*params.put("nombre", nombre.getText().toString());
        params.put("password", "");
        params.put("email", em.getText().toString());
        params.put("telefono", fono.getText().toString());
        params.put("ci", ci.getText().toString());
        params.put("avatar", tipo.getText().toString());//esto seria mejor con spinner
        //Toast.makeText(MainActivity.this, "entra", Toast.LENGTH_LONG).show();*/
        client.post("http://192.168.43.238:3000/orden", params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    String message = response.getString("message");
                    if (message != null) {
                        //Do stuff
                    } else {
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }
        });
    }
}

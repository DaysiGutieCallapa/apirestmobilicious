package com.example.daysi.mobilicious;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
public class RegistrarRestaurante extends AppCompatActivity {
    EditText nombre,nit,propietario,fono;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_restaurante);

        nombre = (EditText) findViewById(R.id.nomRestaurantRegistrar);
        nit= (EditText) findViewById(R.id.nitRestaurantRegistrar);
        propietario = (EditText) findViewById(R.id.proRestaurantRegistrar);
        fono = (EditText) findViewById(R.id.fonoRestaurantRegistrar);
        Button btn = (Button) findViewById(R.id.RegistrarRestaurant);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.subirUsuario:
                        Agregar();
                        break;
                }
                Intent intent =new Intent(getApplicationContext(),ListaRestaurantes.class);
                //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }
    private void Agregar() {
        AsyncHttpClient client = new AsyncHttpClient();
        final RequestParams params = new RequestParams();

        params.put("nombre", nombre.getText().toString());
        params.put("nit", nit.getText().toString());
        params.put("propietario", propietario.getText().toString());
        params.put("telefono", fono.getText().toString());
        //params.put("ci", ci.getText().toString());
        //params.put("tipo", tipo.getText().toString());//esto seria mejor con spinner
        //Toast.makeText(MainActivity.this, "entra", Toast.LENGTH_LONG).show();
        client.post("http://192.168.43.238:3000/api/restaurantes", params, new JsonHttpResponseHandler(){
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

package com.example.daysi.mobilicious;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cz.msebera.android.httpclient.Header;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

public class AgregarUsuario extends AppCompatActivity {
    EditText nombre,pass,em,fono,ci,tipo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_usuario);

        nombre = (EditText) findViewById(R.id.nomUsuario);
        em = (EditText) findViewById(R.id.emUsuario);
        fono = (EditText) findViewById(R.id.fonoUsuario);
        ci = (EditText) findViewById(R.id.ciUsuario);
        tipo = (EditText) findViewById(R.id.tipoUsuario);
        Button btn = (Button) findViewById(R.id.subirUsuario);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.subirUsuario:
                        Agregar();
                        break;
                }
                Intent intent =new Intent(getApplicationContext(),VistaUsuariosAdministrador.class);
                //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }
    private void Agregar() {
        AsyncHttpClient client = new AsyncHttpClient();
        final RequestParams params = new RequestParams();

        params.put("nombre", nombre.getText().toString());
        params.put("password", "loquesea");
        params.put("email", em.getText().toString());
        if(isNumber(fono.getText().toString())){
            params.put("telefono", fono.getText().toString());
        }else {
            Toast.makeText(getApplicationContext(), "no se ha insertado por q el telefono solo acepta numeros", Toast.LENGTH_LONG).show();
            return;
        }
        params.put("ci", ci.getText().toString());
        params.put("avatar", tipo.getText().toString());//esto seria mejor con spinner
        //Toast.makeText(MainActivity.this, "entra", Toast.LENGTH_LONG).show();
        client.post("http://192.168.43.238:3000/api/usuarios", params, new JsonHttpResponseHandler(){
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
    boolean isNumber(String x){
        try {
            Integer.parseInt(x);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}

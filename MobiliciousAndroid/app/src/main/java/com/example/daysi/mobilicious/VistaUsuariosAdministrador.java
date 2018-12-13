package com.example.daysi.mobilicious;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import cz.msebera.android.httpclient.Header;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class VistaUsuariosAdministrador extends AppCompatActivity implements View.OnClickListener{
    static ArrayList<String> idUsuario=new ArrayList<>();
    static ArrayList<String> nomre=new ArrayList<>();
    static ArrayList<String> fon=new ArrayList<>();
    static ArrayList<String> c=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_usuarios_administrador);


        Button verEs=findViewById(R.id.verRestaurantes);
        verEs.setOnClickListener(this);

        Button agre=findViewById(R.id.AgregarUsuarios);
        agre.setOnClickListener(this);
        correr();

        LinearLayout llBotonera = (LinearLayout) findViewById(R.id.llBotoneraAdminisrador);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT );
        //Toast.makeText(getApplicationContext(),timeline+"entra",Toast.LENGTH_LONG).show();
        for (int i = 0; i < nomre.size(); i++) {
            Button button = new Button(this);
            button.setId(i);
            button.setOnClickListener(this);
            //Toast.makeText(this,nomre.get(i)+"",Toast.LENGTH_SHORT).show();
            button.setLayoutParams(lp);
            button.setText("Usuario "+nomre.get(i));
            llBotonera.addView(button);
        }
    }

    @Override
    public void onClick(View v) {
        for(int i=-0;i<nomre.size();i++){
            if(v.getId() == i){
                Toast.makeText(getApplicationContext(),"nombre : "+nomre.get(i)+""
                        +" ci : "+c.get(i)
                        +" telefono : "+fon.get(i),Toast.LENGTH_SHORT).show();
            }
        }
        if(v.getId() == R.id.AgregarUsuarios){
            Intent in =new Intent(getApplicationContext(),AgregarUsuario.class);
            startActivity(in);
        }
        if(v.getId() == R.id.verRestaurantes){
            Intent in =new Intent(getApplicationContext(),ListaRestaurantes.class);
            startActivity(in);
        }
    }
    void correr(){
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://192.168.43.238:3000/api/usuarios", null, new JsonHttpResponseHandler() {
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
                        idUsuario.add(r.getString("_id"));
                        nomre.add(r.getString("nombre"));
                        fon.add(r.getString("telefono"));
                        c.add(r.getString("ci"));
                    }
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(),"no entra al array",Toast.LENGTH_SHORT).show();
                };
            }
        });
    }
}

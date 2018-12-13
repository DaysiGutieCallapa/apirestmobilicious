package com.example.daysi.mobilicious;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import cz.msebera.android.httpclient.Header;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener,View.OnClickListener{
    GoogleApiClient googleApiClient;
    static ArrayList<String> restaurantes=new ArrayList<>();
    static ArrayList<String> nombresRes=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, (GoogleApiClient.OnConnectionFailedListener) this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        /*LinearLayout llBotonera = (LinearLayout) findViewById(R.id.llBotonera);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT );

        for (int i=0; i<10; i++){
            Button button = new Button(this);
            button.setId(i);
            button.setOnClickListener(this);
            button.setLayoutParams(lp);
            button.setText("Boton "+(i+1));
            llBotonera.addView(button);

        }*/
        correr();
        LinearLayout llBotonera = (LinearLayout) findViewById(R.id.llBotonera);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT );
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
                @Override
                public void onResult(@NonNull Status status) {
                    if (status.isSuccess()) {
                        goLogInScreen();
                    } else {
                        Toast.makeText(getApplicationContext(), /*R.string.not_close_session*/"no se puede desloguear", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(googleApiClient);
        if (opr.isDone()) {
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(@NonNull GoogleSignInResult googleSignInResult) {
                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }
    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {

            GoogleSignInAccount account = result.getSignInAccount();

            //nameTextView.setText("Bienvenido: "+account.getDisplayName());
            //email=account.getEmail();
            //emailTextView.setText(account.getEmail());
            //idTextView.setText(account.getId());
            //Glide.with(getApplicationContext()).load(account.getPhotoUrl()).into(photoImageView);

        } else {
            goLogInScreen();
        }
    }
    private void goLogInScreen() {
        Intent intent = new Intent(this, lognInActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onClick(View v) {
        for(int i=0;i<restaurantes.size();i++){
            if(v.getId() == i){
                Intent in =new Intent(getApplicationContext(),descripcionRestaurante.class);
                in.putExtra("idRestaurante",restaurantes.get(i));
                in.putExtra("idAdmin","");
                startActivity(in);
            }
        }
    }
}

package tesi.unibo.it.apasapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import tesi.unibo.it.apasapp.Location.LocationTracker;
import tesi.unibo.it.apasapp.Location.ProviderLocationTracker;
import tesi.unibo.it.apasapp.model.Persona;


public class MainActivity extends AppCompatActivity implements LocationTracker.LocationUpdateListener{

    Button btnRegistrazione, btnLogInComeOspite, btnLogIn;
    String userName, password;
    EditText editTextUserName, editTextPassword;
    ProviderLocationTracker provider;
    ProgressDialog progressBar;
    long secondsInizio;
    Persona p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Calendar c = Calendar.getInstance();
        secondsInizio = System.currentTimeMillis() / 1000;



        editTextUserName = (EditText)findViewById(R.id.editTextUserName);
        editTextPassword = (EditText)findViewById(R.id.editTextPassword);

        btnLogInComeOspite = (Button)findViewById(R.id.btnOspite);
        btnLogInComeOspite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //vai all'activity seleziona Categoria
                Intent intent = new Intent(MainActivity.this, SelectCategoryActivity.class);
                startActivity(intent);
            }
        });


        btnLogIn = (Button)findViewById(R.id.btnLogIn);
        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               /* progressBar = new ProgressDialog(v.getContext());
                progressBar.setCancelable(true);
                progressBar.setMessage("GetLocation");
                progressBar.setIndeterminate(true);
                progressBar.show();

                provider = new ProviderLocationTracker(MainActivity.this, ProviderLocationTracker.ProviderType.GPS, MainActivity.this);
                provider.start();*/
                //crea progressbar


                if(Utility.isInternetAvailable(MainActivity.this) == true) {
                    userName = editTextUserName.toString();
                    password = editTextPassword.toString();
                    if(!userName.equals("") && !password.equals("")) {
                        final ProgressDialog pDialog = new ProgressDialog(MainActivity.this);
                        pDialog.setMessage("Loading...");
                        pDialog.show();
                        String url = "http://192.168.1.7/progettoTesi/GETandroid/getLogIn.php";
                        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            pDialog.dismiss();
                                            JSONObject jsonResponse = new JSONObject(response);
                                            boolean success = jsonResponse.getBoolean("result");
                                            if(success==true) {
                                                Toast.makeText(getApplicationContext(), jsonResponse.getString("data"), Toast.LENGTH_SHORT).show();
                                                //crea persona
                                                p = Persona.createPersonaFromJson(jsonResponse.getJSONObject("data"));
                                                Intent intent = new Intent(MainActivity.this, SelectCategoryActivity.class);
                                                intent.putExtra("persona", p);
                                                startActivity(intent);

                                            } else {
                                                Toast.makeText(getApplicationContext(), jsonResponse.getString("data"), Toast.LENGTH_SHORT).show();
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        pDialog.dismiss();
                                        error.printStackTrace();
                                    }
                                }
                        ){
                            @Override
                            protected Map<String, String> getParams()
                            {
                                Map<String, String> params = new HashMap<>();
                                // the POST parameters:
                                params.put("userName", editTextUserName.getText().toString());
                                params.put("password", editTextPassword.getText().toString());
                                return params;
                            }
                        };
                        Volley.newRequestQueue(getApplication()).add(postRequest);

                    } else {
                        Toast.makeText(getApplicationContext(), "Inserire userName e password", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "E' necessario essere connessi per effettuare il log-in", Toast.LENGTH_SHORT).show();
                }
            }
        });


        btnRegistrazione = (Button)findViewById(R.id.btnNuovaRegistrazione);
        btnRegistrazione.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToRegistrazioneActivity = new Intent(MainActivity.this, RegistrazioneActivity.class);
                startActivity(intentToRegistrazioneActivity);
            }
        });
    }

    @Override
    public void onUpdate(Location newLoc) {
        long secondsFine = System.currentTimeMillis() / 1000;
        //dismiss progressBar
        progressBar.dismiss();
        provider.stop();

        editTextUserName.setText("Tempo richiesto; " + (secondsFine - secondsInizio));
        if(newLoc!= null) {
            editTextPassword.setText("Latitudine: " + newLoc.getLatitude() + " Longitude: " + newLoc.getLongitude());
        }
    }
}

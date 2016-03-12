package tesi.unibo.it.apasapp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import tesi.unibo.it.apasapp.model.Persona;

/**
 * Created by Volpi on 15/02/2016.
 */
public class RegistrazioneActivity extends AppCompatActivity {


    String nome, cognome, sesso, indirizzo, telefono, email, userName, password, ripetiPassword, dataNascita;
    String stato,regione,castello,provincia;
    String[] elencoStati, elencoRegioni, elencoCastelli;
    HashMap<String, String[]> mapRegioneToProvincia;


    Button btnRegistrami;
    Spinner spinnerStato, spinnerRegioneOCastello, spinnerProvince;
    // Create an ArrayAdapter using the string array and a default spinner layout
    ArrayAdapter<String> adapterStato, adapterRegioneOCastello, adapterProvince;

    Persona p;


    TextView txtViewCastelloORegione;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrazione_layout);

        elencoStati = Utility.getStati(getResources());
        elencoRegioni = Utility.getRegioni(getResources());
        elencoCastelli = Utility.getCastelli(getResources());
        mapRegioneToProvincia = Utility.getRegioniProvince(getResources());

        txtViewCastelloORegione = (TextView)findViewById(R.id.txtViewRegioniOCastelloRegistrazione);

        spinnerStato = (Spinner) findViewById(R.id.spinnerStatoRegistrazione);
        spinnerRegioneOCastello = (Spinner)findViewById(R.id.spinnerRegioneOCastelloRegistrazione);
        spinnerProvince = (Spinner)findViewById(R.id.spinnerProvinciaRegistrazione);


        adapterStato =  new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, elencoStati);
        adapterStato.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStato.setAdapter(adapterStato);


        spinnerStato.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                stato = (String) parent.getItemAtPosition(position);
                if (stato.equals("Italia")) {
                    txtViewCastelloORegione.setText("Regione");
                    ((LinearLayout) findViewById(R.id.linearLayoutProvincia)).setVisibility(View.VISIBLE);
                    adapterRegioneOCastello = new ArrayAdapter<String>(RegistrazioneActivity.this, android.R.layout.simple_spinner_item, elencoRegioni);
                    adapterRegioneOCastello.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerRegioneOCastello.setAdapter(adapterRegioneOCastello);

                } else if (stato.equals("San Marino")) {
                    txtViewCastelloORegione.setText("Castello");
                    ((LinearLayout) findViewById(R.id.linearLayoutProvincia)).setVisibility(View.INVISIBLE);
                    adapterRegioneOCastello = new ArrayAdapter<String>(RegistrazioneActivity.this, android.R.layout.simple_spinner_item, elencoCastelli);
                    adapterRegioneOCastello.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerRegioneOCastello.setAdapter(adapterRegioneOCastello);

                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        spinnerRegioneOCastello.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (stato.equals("Italia")) {
                    adapterProvince = new ArrayAdapter<String>(RegistrazioneActivity.this, android.R.layout.simple_spinner_item, mapRegioneToProvincia.get(elencoRegioni[position]));
                    adapterProvince.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerProvince.setAdapter(adapterProvince);
                    castello = null;
                    regione = elencoRegioni[position];
                } else if (stato.equals("San Marino")) {
                    castello = elencoCastelli[position];
                    regione = null;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        spinnerProvince.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                provincia = mapRegioneToProvincia.get(regione)[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnRegistrami = (Button)findViewById(R.id.btnRegistrami);
        btnRegistrami.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check campi inseriti
                //if campi ok invia richiesta al server per controllo username disponibile
                //return json con risposta dal server, OK oppure devi inserire un diverso userName

                //TUTTO OK, il server ha inserito i dati nel database, e finish();
                //altrimenti resta in questa schermata Toast devi inserire un diverso userName


                if(stato.equals("San Marino")) {
                    Toast.makeText(getApplicationContext(), "San Marino, Castello: " + castello,Toast.LENGTH_SHORT).show();
                } else if(stato.equals("Italia")) {
                    Toast.makeText(getApplicationContext(), "Italia, Regione; " + regione + ", Provincia: " + provincia,Toast.LENGTH_SHORT).show();
                }


                boolean flag = checkCampiInseriti();
                if(flag == true) {
                    effettuaRegistrazione();



                    //invia al server (usa asyncTask, attendi risposta, se positiva Toast registrazione ok e torni al login
                    //altrimenti toast cambia username
                    //Toast
                    //finish();
                } else {
                    //Toast errore compilare tutti i campi
                }
            }
        });
    }

    public void effettuaRegistrazione() {
        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();
        String URL = "http://tesimobilewebdesign.altervista.org/INSERTandroid/inserisciPersona.php";
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, URL,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //controllare la risposta;
                        Toast.makeText(RegistrazioneActivity.this, "il server mi ha risposto", Toast.LENGTH_LONG).show();



                        pDialog.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(RegistrazioneActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                        pDialog.dismiss();
                    }
                }) {
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("nome", nome);
                /*params.put("cognome", cognome);
                params.put("sesso", sesso);
                params.put("dataNascita", dataNascita);
                params.put("email", email);
                params.put("telefono", telefono);
                params.put("stato", stato);
                params.put("regione", regione);
                params.put("provincia", provincia);
                params.put("castello", castello);
                params.put("indirizzo", indirizzo);
                params.put("userName", userName);
                params.put("password", password);*/
                return params;
            }
        };


      /*  StringRequest jsonRequest = new StringRequest(Request.Method.POST, URL,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(RegistrazioneActivity.this, "il server mi ha risposto", Toast.LENGTH_LONG).show();
                    pDialog.dismiss();
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(RegistrazioneActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    pDialog.dismiss();
                }
            }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("nome", "nome"); //Add the data you'd like to send to the server.
                return MyData;
            }
        };*/

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonRequest);




    }


    boolean checkCampiInseriti() {
        boolean flag = true;
        String errore = null;
        nome = ((EditText)findViewById(R.id.editTextNomeRegistrazione)).getText().toString();
        cognome = ((EditText)findViewById(R.id.editTextCognomeRegistrazione)).getText().toString();
        sesso = ((RadioButton)findViewById(R.id.rdbMaschio)).isChecked()?"maschio":"femmina";
        dataNascita = ((EditText)findViewById(R.id.editTextDataNascita)).getText().toString();
        indirizzo =((EditText)findViewById(R.id.editTextIndirizzoRegistrazione)).getText().toString();
        telefono = ((EditText)findViewById(R.id.editTextTelefonoRegistrazione)).getText().toString();
        email = ((EditText)findViewById(R.id.editTextEmailRegistrazione)).getText().toString();
        userName = ((EditText)findViewById(R.id.editTextUserNameRegistrazione)).getText().toString();
        password = ((EditText)findViewById(R.id.editTextPasswordRegistrazione)).getText().toString();
        ripetiPassword = ((EditText)findViewById(R.id.editTextRipetiPasswordRegistrazione)).getText().toString();





        boolean emailOK = isEmailValid(email);


        if(nome.equals("") || cognome.equals("") || indirizzo.equals("") || telefono.equals("") || email.equals("") || userName.equals("") || password.equals("") || ripetiPassword.equals("")) {
            errore = "Compilare tutti i campi";
            flag = false;
        } else if(emailOK == false) {
            errore = "Inserire una email valida";
            flag = false;
        } else if(password.equals(ripetiPassword) == false) {
            errore = "Controlla uguaglianza campi password";
            flag = false;
        }

        if(flag == false) {
            Toast.makeText(getApplicationContext(), "Errore: " + errore, Toast.LENGTH_SHORT).show();
        }

        return flag;
    }

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}

package tesi.unibo.it.apasapp;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import tesi.unibo.it.apasapp.model.Persona;

/**
 * Created by Volpi on 15/02/2016.
 */
public class RegistrazioneActivity extends AppCompatActivity {

    Persona p;
    String nome, cognome, sesso, indirizzo, telefono, email, userName, password, ripetiPassword, dataNascita;
    String stato,regione,castello,provincia;
    String[] elencoStati, elencoRegioni, elencoCastelli;
    HashMap<String, String[]> mapRegioneToProvincia;

    EditText editTextNome, editTextCognome, editTextDataNascita, editTextIndirizzo, editTextTelefono, editTextEmail, editTextUserName, editTextPassword, editTextRipetiPassword;
    RadioButton rdbSessoMaschio;

    Button btnRegistrami;
    Spinner spinnerStato, spinnerRegioneOCastello, spinnerProvince;
    // Create an ArrayAdapter using the string array and a default spinner layout
    ArrayAdapter<String> adapterStato, adapterRegioneOCastello, adapterProvince;


    TextView txtViewCastelloORegione;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrazione_layout);

        editTextNome = (EditText)findViewById(R.id.editTextNome);
        editTextCognome = (EditText)findViewById(R.id.editTextCognome);
        rdbSessoMaschio = (RadioButton)findViewById(R.id.rdbMaschio);
        editTextDataNascita = (EditText)findViewById(R.id.editTextDataNascita);
        editTextIndirizzo = (EditText)findViewById(R.id.editTextIndirizzo);
        editTextTelefono = (EditText)findViewById(R.id.editTextTelefono);
        editTextEmail = (EditText)findViewById(R.id.editTextEmail);
        editTextUserName = (EditText)findViewById(R.id.editTextUserName);
        editTextPassword = (EditText)findViewById(R.id.editTextPassword);
        editTextRipetiPassword = (EditText)findViewById(R.id.editTextRipetiPassword);

        elencoStati = Utility.getStati(getResources());
        elencoRegioni = Utility.getRegioni(getResources());
        elencoCastelli = Utility.getCastelli(getResources());
        mapRegioneToProvincia = Utility.getRegioniProvince(getResources());

        txtViewCastelloORegione = (TextView)findViewById(R.id.txtViewRegioneOCastello);

        spinnerStato = (Spinner) findViewById(R.id.spinnerStato);
        spinnerRegioneOCastello = (Spinner)findViewById(R.id.spinnerRegioneOCastello);
        spinnerProvince = (Spinner)findViewById(R.id.spinnerProvincia);


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
            public void onNothingSelected(AdapterView<?> parent) {
            }
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
            public void onNothingSelected(AdapterView<?> parent) {
            }
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

        editTextDataNascita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentDate=Calendar.getInstance();
                final int mYear=mcurrentDate.get(Calendar.YEAR);
                final int mMonth=mcurrentDate.get(Calendar.MONTH);
                final int mDay=mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker=new DatePickerDialog(RegistrazioneActivity.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        selectedmonth+=1;
                        editTextDataNascita.setText(selectedyear+"-"+selectedmonth+"-"+selectedday);
                    }
                },mYear, mMonth, mDay);
                mDatePicker.setTitle("Select date");
                mDatePicker.show();
            }
        });



        btnRegistrami = (Button)findViewById(R.id.btnRegistrami);
        btnRegistrami.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Utility.isInternetAvailable(RegistrazioneActivity.this) == true) {
                    boolean flag = checkCampiInseriti();
                    if (flag == true) {
                        if(stato.equals("Italia")) {
                            p = new Persona(0, nome, cognome, sesso, email, telefono,stato, regione, provincia, null, indirizzo, userName, password, "utente generico", dataNascita);
                        } else {
                            p = new Persona(0, nome, cognome, sesso, email, telefono,stato, null, null, castello, indirizzo, userName, password, "utente generico", dataNascita);
                        }
                        effettuaRegistrazione();
                    } else {
                        Toast.makeText(getApplicationContext(), "Compilare tutti i campi", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Attivare internet", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void effettuaRegistrazione() {
        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();
        String URL = " http://192.168.1.7/progettoTesi/MODIFYandroid/updatePersona.php";
        //String URL = "http://192.168.1.7/progettoTesi/INSERTandroid/inserisciPersona.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            pDialog.dismiss();
                            //controllare la risposta;
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("result");
                            if(success == true) {
                                Toast.makeText(getApplicationContext(), jsonResponse.getString("data"), Toast.LENGTH_SHORT).show();
                                finish();
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
                        Toast.makeText(RegistrazioneActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("nome", p.getNome());
                params.put("cognome", p.getCognome());
                params.put("sesso", p.getSesso());
                params.put("dataNascita", p.getDataNascita().toString());
                params.put("email", p.getEmail());
                params.put("telefono", p.getTelefono());
                params.put("stato", p.getStato());
                if(stato.equals("Italia")) {
                    params.put("regione", p.getRegione());
                    params.put("provincia", p.getProvincia());
                } else {
                    params.put("castello", p.getCastello());
                }
                params.put("indirizzo", p.getIndirizzo());
                params.put("userName", p.getUserName());
                params.put("password", p.getPassword());
                return params;
            }
        };
        Volley.newRequestQueue(getApplication()).add(postRequest);
    }


    boolean checkCampiInseriti() {
        boolean flag = true;
        String errore = null;

        nome = editTextNome.getText().toString();
        cognome = editTextCognome.getText().toString();
        sesso = rdbSessoMaschio.isChecked()?"maschio":"femmina";
        dataNascita = editTextDataNascita.getText().toString();
        indirizzo = editTextIndirizzo.getText().toString();
        telefono = editTextTelefono.getText().toString();
        email = editTextEmail.getText().toString();
        userName = editTextUserName.getText().toString();
        password = editTextPassword.getText().toString();
        ripetiPassword = editTextRipetiPassword.getText().toString();
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
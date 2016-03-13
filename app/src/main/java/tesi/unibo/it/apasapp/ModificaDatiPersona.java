package tesi.unibo.it.apasapp;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
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
import com.android.volley.Response;
import com.android.volley.VolleyError;
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
 * Created by Volpi on 13/03/2016.
 */
public class ModificaDatiPersona extends AppCompatActivity {

    Persona p;
    TextView txtViewCastelloORegione;
    EditText editTextNome, editTextCognome, editTextIndirizzo, editTextTelefono, editTextEmail, editTextDataNascita;
    RadioButton rdbMaschio, rdbFemmina;
    Spinner spinnerStato, spinnerRegioneOCastello, spinnerProvince;
    LinearLayout linearLayoutProvincia;

    Button btnModificaDatiPersona;

    boolean flagSpinnerRegioneCastello = true;
    boolean flagSpinnerProvincia = true;

    String[] elencoStati, elencoRegioni, elencoCastelli;
    HashMap<String, String[]> mapRegioneToProvincia;

    String nome, cognome, sesso, email, telefono, indirizzo, dataNascita;

    ArrayAdapter<String> adapterStato, adapterRegioneOCastello, adapterProvince;

    String tmpStato, tmpRegione, tmpProvincia, tmpCastello;


    Persona pNew = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modifica_dati_persona_activity);


        Intent intent = getIntent();
        p = (Persona)intent.getExtras().getSerializable("persona");
        if(p.getStato().equals("San Marino")) {
            flagSpinnerProvincia = false;
        }

        tmpStato = p.getStato();
        tmpCastello = p.getCastello();
        tmpProvincia = p.getProvincia();
        tmpRegione = p.getRegione();

        txtViewCastelloORegione = (TextView)findViewById(R.id.txtViewRegioneOCastello);
        editTextNome = (EditText)findViewById(R.id.editTextNome);
        editTextCognome = (EditText)findViewById(R.id.editTextCognome);
        editTextIndirizzo = (EditText)findViewById(R.id.editTextIndirizzo);
        editTextTelefono = (EditText)findViewById(R.id.editTextTelefono);
        editTextEmail = (EditText)findViewById(R.id.editTextEmail);
        editTextDataNascita = (EditText)findViewById(R.id.editTextDataNascita);
        rdbMaschio = (RadioButton)findViewById(R.id.rdbMaschio);
        rdbFemmina = (RadioButton)findViewById(R.id.rdbFemmina);
        spinnerStato = (Spinner)findViewById(R.id.spinnerStato);
        spinnerRegioneOCastello = (Spinner)findViewById(R.id.spinnerRegioneOCastello);
        spinnerProvince = (Spinner)findViewById(R.id.spinnerProvincia);
        linearLayoutProvincia = (LinearLayout)findViewById(R.id.linearLayoutProvincia);


        editTextNome.setText(p.getNome());
        editTextCognome.setText(p.getCognome());
        editTextEmail.setText(p.getEmail());
        editTextIndirizzo.setText(p.getIndirizzo());
        editTextTelefono.setText(p.getTelefono());
        String dataN = p.getDataNascita().toString();

        editTextDataNascita.setText(p.getDataNascita().toString());



        editTextDataNascita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentDate = Calendar.getInstance();
                final int mYear = mcurrentDate.get(Calendar.YEAR);
                final int mMonth = mcurrentDate.get(Calendar.MONTH);
                final int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker = new DatePickerDialog(ModificaDatiPersona.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        selectedmonth += 1;
                        editTextDataNascita.setText(selectedyear + "-" + selectedmonth + "-" + selectedday);
                    }
                }, mYear, mMonth, mDay);
                mDatePicker.setTitle("Select date");
                mDatePicker.show();
            }
        });


        elencoStati = Utility.getStati(getResources());
        elencoRegioni = Utility.getRegioni(getResources());
        elencoCastelli = Utility.getCastelli(getResources());
        mapRegioneToProvincia = Utility.getRegioniProvince(getResources());

        if(p.getSesso().equals("maschio")) {
            rdbMaschio.setChecked(true);
        } else {
            rdbFemmina.setChecked(true);
        }


        btnModificaDatiPersona = (Button)findViewById(R.id.btnModificaDatiPersona);
        btnModificaDatiPersona.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aggiornaPersona();
            }
        });

        riempiSpinner();
    }



    private void riempiSpinner() {
        adapterStato =  new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, elencoStati);
        adapterStato.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStato.setAdapter(adapterStato);

        spinnerStato.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(flagSpinnerRegioneCastello==true) {
                    tmpStato = p.getStato();
                } else {
                    tmpStato = (String) parent.getItemAtPosition(position);
                }

                if (tmpStato.equals("Italia")) {
                    txtViewCastelloORegione.setText("Regione");
                    ((LinearLayout) findViewById(R.id.linearLayoutProvincia)).setVisibility(View.VISIBLE);
                    adapterRegioneOCastello = new ArrayAdapter<String>(ModificaDatiPersona.this, android.R.layout.simple_spinner_item, elencoRegioni);
                    adapterRegioneOCastello.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerRegioneOCastello.setAdapter(adapterRegioneOCastello);

                    if(flagSpinnerRegioneCastello == true) {
                        spinnerStato.setSelection(getIndexSpinner(spinnerStato, p.getStato()));
                        spinnerRegioneOCastello.setSelection(getIndexSpinner(spinnerRegioneOCastello, p.getRegione()));
                    }


                } else if (tmpStato.equals("San Marino")) {
                    //NON FUNZIONA BENE VIENE CHIAMATO 2 VOLTE NON SO IL PERCHE'

                    txtViewCastelloORegione.setText("Castello");
                    ((LinearLayout) findViewById(R.id.linearLayoutProvincia)).setVisibility(View.INVISIBLE);
                    adapterRegioneOCastello = new ArrayAdapter<String>(ModificaDatiPersona.this, android.R.layout.simple_spinner_item, elencoCastelli);
                    adapterRegioneOCastello.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerRegioneOCastello.setAdapter(adapterRegioneOCastello);

                    if(flagSpinnerRegioneCastello == true) {
                        spinnerStato.setSelection(getIndexSpinner(spinnerStato, p.getStato()));
                        spinnerRegioneOCastello.setSelection(getIndexSpinner(spinnerRegioneOCastello, p.getCastello()));
                    }
                }

                flagSpinnerRegioneCastello = false;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        spinnerRegioneOCastello.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (tmpStato.equals("Italia")) {
                    adapterProvince = new ArrayAdapter<String>(ModificaDatiPersona.this, android.R.layout.simple_spinner_item, mapRegioneToProvincia.get(elencoRegioni[position]));
                    adapterProvince.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerProvince.setAdapter(adapterProvince);
                    tmpCastello = null;
                    tmpRegione = elencoRegioni[position];

                    if(flagSpinnerProvincia == true) {
                        spinnerProvince.setSelection(getIndexSpinner(spinnerProvince, p.getProvincia()));
                        flagSpinnerProvincia = false;
                    }
                } else if (tmpStato.equals("San Marino")) {
                    tmpCastello = elencoCastelli[position];
                    tmpRegione = null;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        spinnerProvince.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tmpProvincia = mapRegioneToProvincia.get(tmpRegione)[position];
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }



    private int getIndexSpinner(Spinner spinner, String myString) {
        int index = 0;
        int count = spinner.getCount();
        for(int i = 0; i < spinner.getCount(); i++) {
            String l = spinner.getItemAtPosition(i).toString();
            if(spinner.getItemAtPosition(i).toString().equals(myString)) {
                index = i;
                break;
            }
        }
        return index;
    }


    private void aggiornaPersona() {
        //CONTROLLO INTERNET


        if(Utility.isInternetAvailable(this) == true) {
            nome = editTextNome.getText().toString();
            cognome = editTextCognome.getText().toString();
            email = editTextEmail.getText().toString();
            sesso = rdbMaschio.isChecked() ? "maschio" : "femmina";
            telefono = editTextTelefono.getText().toString();
            indirizzo = editTextIndirizzo.getText().toString();
            dataNascita = editTextDataNascita.getText().toString();
            if (tmpStato.equals("Italia")) {
                pNew = new Persona(p.getIdPersona(), nome, cognome, sesso, email, telefono, tmpStato, tmpRegione, tmpProvincia, null, indirizzo, p.getUserName(), p.getPassword(), p.getPrivilegio(), dataNascita);
            } else {
                pNew = new Persona(p.getIdPersona(), nome, cognome, sesso, email, telefono, tmpStato, null, null, tmpCastello, indirizzo, p.getUserName(), p.getPassword(), p.getPrivilegio(), dataNascita);
            }



            final ProgressDialog pDialog = new ProgressDialog(this);
            pDialog.setMessage("Loading...");
            pDialog.show();
            String URL = "http://192.168.1.7/progettoTesi/MODIFYandroid/updatePersona.php";
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
                            Toast.makeText(ModificaDatiPersona.this, error.toString(), Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                protected Map<String,String> getParams() {
                    Map<String,String> params = new HashMap<String, String>();
                    params.put("idPersona", Integer.toString(pNew.getIdPersona()));
                    params.put("nome", pNew.getNome());
                    params.put("cognome", pNew.getCognome());
                    params.put("sesso", pNew.getSesso());
                    params.put("dataNascita", pNew.getDataNascita());
                    params.put("email", pNew.getEmail());
                    params.put("telefono", pNew.getTelefono());
                    params.put("stato", pNew.getStato());
                    if(pNew.getStato().equals("Italia")) {
                        params.put("regione", pNew.getRegione());
                        params.put("provincia", pNew.getProvincia());
                    } else {
                        params.put("castello", pNew.getCastello());
                    }
                    params.put("privilegio", pNew.getPrivilegio());
                    params.put("indirizzo", pNew.getIndirizzo());
                    return params;
                }
            };
            Volley.newRequestQueue(getApplication()).add(postRequest);




        }


        //controlla internet sia connesso
        //effettua chiamata al server
        //se OK torna al menu principale con finish, e toast tutto ok
        //altrimenti mostra toast errore...


    }
}

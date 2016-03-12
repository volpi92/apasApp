package tesi.unibo.it.apasapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

/**
 * Created by Volpi on 15/02/2016.
 */
public class InserisciAnimaleSmarritoActivity extends AppCompatActivity {

    String stato,regione,castello,provincia;
    String[] elencoStati, elencoRegioni, elencoCastelli;
    HashMap<String, String[]> mapRegioneToProvincia;
    private static final int REQUEST_CODE = 1;
    private Bitmap bitmap;
    private ImageView imageViewAnimaleSmarrito;
    Button btnCaricaFoto, btnInserisciNuovoSmarrimento;

    String[] tipo, elencoRazzeCani, elencoRazzeGatti;
    Spinner spinnerTipoAnimale, spinnerRazzaAnimale, spinnerStato, spinnerRegioneOCastello, spinnerProvince;
    String tipoScelto;
    ArrayAdapter<String> adapterTipo, adapterRazza, adapterStato, adapterRegioneOCastello, adapterProvince;

    TextView txtViewCastelloORegione;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.smarrimenti_aggiungi_nuovo_smarrimento);

        tipo = Utility.getTipo(getResources());
        elencoRazzeCani = Utility.getRazzeCani(getResources());
        elencoRazzeGatti = Utility.getRazzeGatti(getResources());
        elencoStati = Utility.getStati(getResources());
        elencoRegioni = Utility.getRegioni(getResources());
        elencoCastelli = Utility.getCastelli(getResources());
        mapRegioneToProvincia = Utility.getRegioniProvince(getResources());


        txtViewCastelloORegione = (TextView)findViewById(R.id.txtViewRegioneOCastelloSmarrimentoNuovo);

        imageViewAnimaleSmarrito = (ImageView)findViewById(R.id.imageViewInsertAnimaleSmarrito);
        spinnerTipoAnimale = (Spinner)findViewById(R.id.spinnerTipoAnimaleSmarrito);
        spinnerRazzaAnimale = (Spinner)findViewById(R.id.spinnerRazzaAnimaleSmarrito);
        spinnerStato = (Spinner)findViewById(R.id.spinnerStatoSmarrimento);
        spinnerRegioneOCastello = (Spinner)findViewById(R.id.spinnerRegioneOCastelloSmarrimento);
        spinnerProvince = (Spinner)findViewById(R.id.spinnerProvinciaSmarrimento);

        setAdapterinSpinner();

        btnCaricaFoto = (Button)findViewById(R.id.btnCaricaFotoAnimaleSmarrito);
        btnCaricaFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        btnInserisciNuovoSmarrimento = (Button)findViewById(R.id.btnInserisciSmarrimentoAnimale);
        btnInserisciNuovoSmarrimento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //INTERNET ATTIVO?
                boolean flag = checkCampiInseriti();
                if(flag == true) {
                    //invia al server (usa asyncTask, attendi risposta, se positiva Toast registrazione ok e torni al selectActivity
                    //altrimenti segnala errore Toast o AlertDialog
                } else {
                    //toast inserire tutti i campi
                }
            }
        });




    }


    public void setAdapterinSpinner() {
        adapterTipo =  new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tipo);
        adapterTipo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTipoAnimale.setAdapter(adapterTipo);

        spinnerTipoAnimale.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tipoScelto = (String) parent.getItemAtPosition(position);
                if (tipoScelto.equals("Cane")) {
                    adapterRazza = new ArrayAdapter<String>(InserisciAnimaleSmarritoActivity.this, android.R.layout.simple_spinner_item, elencoRazzeCani);
                    adapterRazza.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerRazzaAnimale.setAdapter(adapterRazza);

                } else if (tipoScelto.equals("Gatto")) {
                    adapterRazza = new ArrayAdapter<String>(InserisciAnimaleSmarritoActivity.this, android.R.layout.simple_spinner_item, elencoRazzeGatti);
                    adapterRazza.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerRazzaAnimale.setAdapter(adapterRazza);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        adapterStato =  new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, elencoStati);
        adapterStato.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStato.setAdapter(adapterStato);

        spinnerStato.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                stato = (String) parent.getItemAtPosition(position);
                if (stato.equals("Italia")) {
                    ((LinearLayout) findViewById(R.id.linearLayoutProvinciaSmarrimento)).setVisibility(View.VISIBLE);
                    txtViewCastelloORegione.setText("Regione");
                    adapterRegioneOCastello = new ArrayAdapter<String>(InserisciAnimaleSmarritoActivity.this, android.R.layout.simple_spinner_item, elencoRegioni);
                    adapterRegioneOCastello.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerRegioneOCastello.setAdapter(adapterRegioneOCastello);

                } else if (stato.equals("San Marino")) {
                    ((LinearLayout) findViewById(R.id.linearLayoutProvinciaSmarrimento)).setVisibility(View.INVISIBLE);
                    txtViewCastelloORegione.setText("Castello");
                    adapterRegioneOCastello = new ArrayAdapter<String>(InserisciAnimaleSmarritoActivity.this, android.R.layout.simple_spinner_item, elencoCastelli);
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
                    adapterProvince = new ArrayAdapter<String>(InserisciAnimaleSmarritoActivity.this, android.R.layout.simple_spinner_item, mapRegioneToProvincia.get(elencoRegioni[position]));
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
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK)
            try {
                // We need to recyle unused bitmaps
                if (bitmap != null) {
                    bitmap.recycle();
                }
                InputStream stream = getContentResolver().openInputStream(
                        data.getData());
                bitmap = BitmapFactory.decodeStream(stream);
                stream.close();
                imageViewAnimaleSmarrito.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        super.onActivityResult(requestCode, resultCode, data);
    }




    public boolean checkCampiInseriti() {
        //check bitmap
        //indirizzo
        //segni particolari
        //nome
        return false;
    }
}

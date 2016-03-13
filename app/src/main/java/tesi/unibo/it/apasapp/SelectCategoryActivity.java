package tesi.unibo.it.apasapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import tesi.unibo.it.apasapp.model.Persona;

/**
 * Created by Volpi on 15/02/2016.
 */
public class SelectCategoryActivity extends AppCompatActivity {

    Button btnRifugioAnimaliApas, btnSmarrimenti, btnAdozioniPrivati, btnModificaDatiPersona;
    Persona p;
    TextView txtViewBenvenuto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_category);

        btnModificaDatiPersona = (Button)findViewById(R.id.btnModificaDatiPersona);
        txtViewBenvenuto = (TextView)findViewById(R.id.txtBenvenuto);

        Intent intent = getIntent();
        if(intent.hasExtra("persona")) { // sono loggato
            p = (Persona)getIntent().getExtras().getSerializable("persona");
            txtViewBenvenuto.setText("Benvenuto: " + p.getPrivilegio() + " " + p.getCognome() + " " + p.getNome());

            btnModificaDatiPersona.setVisibility(View.VISIBLE);
            btnModificaDatiPersona.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(SelectCategoryActivity.this, ModificaDatiPersona.class);
                    intent.putExtra("persona", p);
                    startActivity(intent);

                }
            });
            //mostra bottone modifica i miei dati



        }

        btnRifugioAnimaliApas = (Button)findViewById(R.id.btnRifugioAPASCatergory);
        btnRifugioAnimaliApas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        btnSmarrimenti = (Button)findViewById(R.id.btnSmarrimentiCategory);
        btnSmarrimenti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectCategoryActivity.this, SmarrimentiMainActivity.class);
                startActivity(intent);

            }
        });


        btnAdozioniPrivati = (Button)findViewById(R.id.btnAdozioneAnimaliCategory);
        btnAdozioniPrivati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });












    }
}

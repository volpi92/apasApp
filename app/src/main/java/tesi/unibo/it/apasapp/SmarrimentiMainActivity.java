package tesi.unibo.it.apasapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Volpi on 15/02/2016.
 */
public class SmarrimentiMainActivity extends AppCompatActivity {

    Button btnGotoInserisciAnnuncioAnimaleSmarrito, btnGoToVisualizzaAnimaliSmarriti, btnGoToVisualizzaIMieiAnimaliSmarriti;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.smarrimenti_main_layout);

        btnGotoInserisciAnnuncioAnimaleSmarrito = (Button)findViewById(R.id.btnGoToInserisciAnnuncioAnimaleSmarrito);
        btnGoToVisualizzaAnimaliSmarriti = (Button)findViewById(R.id.btnGoToVisualizzaAnimaliSmarriti);
        btnGoToVisualizzaIMieiAnimaliSmarriti = (Button)findViewById(R.id.btnGoToVisualizzaIMieiAnimaliSmarriti);


        btnGotoInserisciAnnuncioAnimaleSmarrito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //vai all'activity aggiungi animale

                Intent intent = new Intent(SmarrimentiMainActivity.this, InserisciAnimaleSmarritoActivity.class);
                startActivity(intent);

            }
        });

        btnGoToVisualizzaAnimaliSmarriti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //vai all'activity visualizza animali (che contiene tab e gestirà i fragments
            }
        });

        btnGoToVisualizzaIMieiAnimaliSmarriti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //vai all'activity contenete i miei animali smarriti
            }
        });

    }

}

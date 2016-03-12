package tesi.unibo.it.apasapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Volpi on 15/02/2016.
 */
public class SelectCategoryActivity extends AppCompatActivity {

    Button btnRifugioAnimaliApas, btnSmarrimenti, btnAdozioniPrivati;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_category);


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

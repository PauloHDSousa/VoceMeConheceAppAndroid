package com.paulohdsousa.vocemeconhece;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class SucessoActivity  extends AppCompatActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sucesso);

        TextView etSucesso = (TextView)findViewById(R.id.tvSucesso);

        Bundle b = getIntent().getExtras();
        int acertos = 0;
        int perguntas = 0;
        if(b != null) {
            acertos = b.getInt("acertos");
            perguntas = b.getInt("perguntas");

            etSucesso.setText("Você acertou " + acertos + " de " + perguntas);
        }
    }
}

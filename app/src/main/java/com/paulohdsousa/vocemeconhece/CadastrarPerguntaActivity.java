package com.paulohdsousa.vocemeconhece;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.paulohdsousa.vocemeconhece.database.DatabaseHelper;
import com.paulohdsousa.vocemeconhece.dominio.entidades.Pergunta;
import com.paulohdsousa.vocemeconhece.dominio.entidades.Resposta;
import com.paulohdsousa.vocemeconhece.dominio.repositorio.PerguntaRepositorio;
import com.paulohdsousa.vocemeconhece.dominio.repositorio.RespostaRepositorio;

import java.util.List;

public class CadastrarPerguntaActivity extends  BaseActivity {

    ImageButton btnSalvar;
    SQLiteDatabase conexao;
    DatabaseHelper databaseHelper;
    EditText edtPergunta;
    EditText edtAlternativa1;
    EditText edtAlternativa2;
    EditText edtAlternativa3;
    RadioGroup rgAlternativas;
    PerguntaRepositorio perguntaRepositorio;
    RespostaRepositorio respostaRepositorio;


    @Override
    int getContentViewId() {
        return R.layout.activity_cadastrar_pergunta;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.navigation_perguntas;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MobileAds.initialize(this);

        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        btnSalvar = (ImageButton) findViewById(R.id.btnSalvar);
        edtPergunta = (EditText) findViewById(R.id.edtPergunta);
        edtAlternativa1 = (EditText) findViewById(R.id.edtAlternativa1);
        edtAlternativa2 = (EditText) findViewById(R.id.edtAlternativa2);
        edtAlternativa3 = (EditText) findViewById(R.id.edtAlternativa3);
        rgAlternativas = (RadioGroup) findViewById(R.id.rgAlternativas);


        btnSalvar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                try {
                    databaseHelper = new DatabaseHelper(CadastrarPerguntaActivity.this);

                    conexao = databaseHelper.getWritableDatabase();

                    perguntaRepositorio = new PerguntaRepositorio(conexao);
                    respostaRepositorio = new RespostaRepositorio(conexao);


                    String textoPergunta = edtPergunta.getText().toString();

                    Pergunta pergunta = new Pergunta();
                    pergunta.Pergunta = textoPergunta ;

                    if(textoPergunta.isEmpty()){
                        Toast.makeText(CadastrarPerguntaActivity.this, "Digite um texto para sua pergunta", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    long idPergunta = perguntaRepositorio.Inserir(pergunta);

                    if (idPergunta != -1){

                        int radioButtonID = rgAlternativas.getCheckedRadioButtonId();

                        String tAlternativa1 = edtAlternativa1.getText().toString();
                        String tAlternativa2 = edtAlternativa2.getText().toString();
                        String tAlternativa3 = edtAlternativa3.getText().toString();

                        if(tAlternativa1.isEmpty() || tAlternativa2.isEmpty() || tAlternativa3.isEmpty() || radioButtonID == -1){
                            Toast.makeText(CadastrarPerguntaActivity.this, "Preencha todas as alternativas e escolha uma correta antes de continuar", Toast.LENGTH_LONG).show();
                            return;
                        }

                        Resposta r1 = new Resposta();
                        r1.Resposta = tAlternativa1;
                        r1.IdPergunta = (int)idPergunta;
                        r1.RespostaCorreta = radioButtonID == R.id.rbalternativa1;
                        respostaRepositorio.Inserir(r1);

                        Resposta r2 = new Resposta();
                        r2.Resposta = tAlternativa2;
                        r2.IdPergunta = (int)idPergunta;
                        r2.RespostaCorreta = radioButtonID == R.id.rbalternativa2;
                        respostaRepositorio.Inserir(r2);

                        Resposta r3 = new Resposta();
                        r3.Resposta = tAlternativa3;
                        r3.IdPergunta = (int)idPergunta;
                        r3.RespostaCorreta = radioButtonID == R.id.rbalternativa3;
                        respostaRepositorio.Inserir(r3);

                        Toast.makeText(CadastrarPerguntaActivity.this, "Pergunta cadastrada com sucesso", Toast.LENGTH_SHORT).show();
                        conexao.close();
                        finish();
                    }else{
                        Toast.makeText(CadastrarPerguntaActivity.this, "Erro ao inserir pergunta", Toast.LENGTH_SHORT).show();
                    }

                } catch (SQLException e){
                    Toast.makeText(CadastrarPerguntaActivity.this, "Erro", Toast.LENGTH_SHORT).show();
                }
                finally {
                    conexao.close();
                }
            }
        });
    }
}

package com.paulohdsousa.vocemeconhece;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.paulohdsousa.vocemeconhece.database.DatabaseHelper;
import com.paulohdsousa.vocemeconhece.dominio.entidades.Pergunta;
import com.paulohdsousa.vocemeconhece.dominio.entidades.Ranking;
import com.paulohdsousa.vocemeconhece.dominio.entidades.Resposta;
import com.paulohdsousa.vocemeconhece.dominio.repositorio.PerguntaRepositorio;
import com.paulohdsousa.vocemeconhece.dominio.repositorio.RankingRepositorio;
import com.paulohdsousa.vocemeconhece.dominio.repositorio.RespostaRepositorio;

import java.util.List;

public class JogarActivity extends AppCompatActivity {

    ImageButton btnNext;
    SQLiteDatabase conexao;
    DatabaseHelper databaseHelper;
    TextView tvPergunta;
    TextView tvAlternativa1;
    TextView tvAlternativa2;
    TextView tvAlternativa3;
    RadioGroup rgAlternativas;
    PerguntaRepositorio perguntaRepositorio;
    RespostaRepositorio respostaRepositorio;
    RankingRepositorio rankingRepositorio;
    String nomeJogador;

    int perguntaAtual;
    int respostasCorretas = 0;
    int respostaCorreta = 0;
    List<Pergunta> perguntas;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jogar);


        MobileAds.initialize(this);

        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final EditText input = new EditText(this);
        builder.setView(input);
        builder.setMessage("Preencha seu nome")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        nomeJogador = input.getText().toString();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();


        btnNext = (ImageButton) findViewById(R.id.btnNext);
        tvPergunta = (TextView) findViewById(R.id.tvPergunta);
        tvAlternativa1 = (TextView) findViewById(R.id.tvAlternativa1);
        tvAlternativa2 = (TextView) findViewById(R.id.tvAlternativa2);
        tvAlternativa3 = (TextView) findViewById(R.id.tvAlternativa3);
        rgAlternativas = (RadioGroup) findViewById(R.id.rgAlternativas);

        try {
            databaseHelper = new DatabaseHelper(this);

            conexao = databaseHelper.getWritableDatabase();

            perguntaRepositorio = new PerguntaRepositorio(conexao);
            rankingRepositorio  = new RankingRepositorio(conexao);
            respostaRepositorio = new RespostaRepositorio(conexao);

            perguntas = perguntaRepositorio.Buscar();
            Pergunta p = perguntas.get(perguntaAtual);

            if (!perguntas.isEmpty()){
                tvPergunta.setText(p.Pergunta);
                List<Resposta> respostas = respostaRepositorio.Buscar(p.IdPergunta);

                tvAlternativa1.setText("1 - " + respostas.get(0).Resposta);
                tvAlternativa2.setText("2 - " +respostas.get(1).Resposta);
                tvAlternativa3.setText("3 - " +respostas.get(2).Resposta);

                if(respostas.get(0).RespostaCorreta)
                    respostaCorreta = R.id.rbalternativa1;

                if(respostas.get(1).RespostaCorreta)
                    respostaCorreta = R.id.rbalternativa2;

                if(respostas.get(2).RespostaCorreta)
                    respostaCorreta = R.id.rbalternativa3;

            }else{
                Toast.makeText(this, "Nenhum registro encontrado", Toast.LENGTH_SHORT).show();
            }

        } catch (SQLException e){
            Toast.makeText(this, "Erro", Toast.LENGTH_SHORT).show();
        }


        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int i = rgAlternativas.getCheckedRadioButtonId();

                if(i == -1){
                    Toast.makeText(JogarActivity.this, "Escolha uma alternativa", Toast.LENGTH_SHORT).show();
                    return;
                }

               if (respostaCorreta == rgAlternativas.getCheckedRadioButtonId())
                   respostasCorretas++;

                perguntaAtual++;
                rgAlternativas.clearCheck();
                if(perguntaAtual ==  perguntas.size()){
                    Toast.makeText(JogarActivity.this, "Você acertou " + respostasCorretas + " perguntas", Toast.LENGTH_SHORT).show();
                    Ranking r = new Ranking();
                    r.Jogador = nomeJogador;
                    r.QuantidadeAcertos = respostasCorretas;
                    r.QuantidadeRespondida = perguntas.size();
                    r.PorcentagemAcertos  =  (int)((respostasCorretas * 100.0f) / perguntas.size());
                    rankingRepositorio.Inserir(r);

                    alert_Sucesso();
                    return;
                }

                Pergunta p = perguntas.get(perguntaAtual);

                List<Resposta> respostas = respostaRepositorio.Buscar(p.IdPergunta);

                if(respostas.isEmpty()){
                    perguntaAtual ++;
                    p = perguntas.get(perguntaAtual);

                    respostas = respostaRepositorio.Buscar(p.IdPergunta);
                }

                if (!perguntas.isEmpty()) {
                    tvPergunta.setText(p.Pergunta);
                    tvAlternativa1.setText(respostas.get(0).Resposta);
                    tvAlternativa2.setText(respostas.get(1).Resposta);
                    tvAlternativa3.setText(respostas.get(2).Resposta);

                    if(respostas.get(0).RespostaCorreta)
                        respostaCorreta = R.id.rbalternativa1;

                    if(respostas.get(1).RespostaCorreta)
                        respostaCorreta = R.id.rbalternativa2;

                    if(respostas.get(2).RespostaCorreta)
                        respostaCorreta = R.id.rbalternativa3;
                }
            }
        });
    }

    public void alert_Sucesso() {
        final Dialog dialog = new Dialog(JogarActivity.this);
        dialog.setContentView(R.layout.activity_sucesso);
        dialog.setTitle("Ranking");

        TextView text = (TextView) dialog.findViewById(R.id.tvSucesso);
        text.setText("Você acertou " + respostasCorretas + " de " + perguntas.size());

        ImageButton dialogButton = (ImageButton) dialog.findViewById(R.id.btnOk);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                finish();
            }
        });
        dialog.show();
    }
}

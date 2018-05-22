package com.paulohdsousa.vocemeconhece;

import android.content.DialogInterface;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.paulohdsousa.vocemeconhece.database.DatabaseHelper;
import com.paulohdsousa.vocemeconhece.dominio.entidades.Pergunta;
import com.paulohdsousa.vocemeconhece.dominio.entidades.Resposta;
import com.paulohdsousa.vocemeconhece.dominio.repositorio.PerguntaRepositorio;
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
    String nomeJogador;

    int perguntaAtual;
    int respostasCorretas = 0;
    int respostaCorreta = 0;
    List<Pergunta> perguntas;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jogar);


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final EditText input = new EditText(this);
        builder.setView(input);
        builder.setMessage("Preencha seu nome")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        nomeJogador = input.getText().toString();

                        Toast.makeText(JogarActivity.this, nomeJogador, Toast.LENGTH_SHORT).show();
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

            Toast.makeText(this, "Sucesso", Toast.LENGTH_SHORT).show();

            perguntaRepositorio = new PerguntaRepositorio(conexao);

            respostaRepositorio = new RespostaRepositorio(conexao);

            perguntas = perguntaRepositorio.Buscar();
            Pergunta p = perguntas.get(perguntaAtual);




            if (!perguntas.isEmpty()){
                tvPergunta.setText(p.Pergunta);
                List<Resposta> respostas = respostaRepositorio.Buscar(p.IdPergunta);
                tvAlternativa1.setText(respostas.get(0).Resposta);

                tvAlternativa2.setText(respostas.get(1).Resposta);
                tvAlternativa3.setText(respostas.get(2).Resposta);

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

               if (respostaCorreta == rgAlternativas.getCheckedRadioButtonId())
                   respostasCorretas++;

                perguntaAtual++;
                rgAlternativas.clearCheck();
                if(perguntaAtual == perguntas.size()){
                    Toast.makeText(JogarActivity.this, "Você acertou " + respostasCorretas + " perguntas", Toast.LENGTH_SHORT).show();

                    return;
                }

                Pergunta p = perguntas.get(perguntaAtual);

                List<Resposta> respostas = respostaRepositorio.Buscar(p.IdPergunta);


                if (!perguntas.isEmpty()) {
                    tvPergunta.setText(p.Pergunta);
                    tvAlternativa1.setText(respostas.get(0).Resposta);
                    tvAlternativa2.setText(respostas.get(1).Resposta);
                    tvAlternativa3.setText(respostas.get(2).Resposta);
                }
            }
        });
    }
}

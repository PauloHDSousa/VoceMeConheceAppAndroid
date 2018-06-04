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

                    Pergunta pergunta = new Pergunta();
                    pergunta.Pergunta = edtPergunta.getText().toString();



                    long idPergunta = perguntaRepositorio.Inserir(pergunta);

                    if (idPergunta != -1){

                        int radioButtonID = rgAlternativas.getCheckedRadioButtonId();

                        Resposta r1 = new Resposta();
                        r1.Resposta = edtAlternativa1.getText().toString();
                        r1.IdPergunta = (int)idPergunta;
                        r1.RespostaCorreta = radioButtonID == R.id.rbalternativa1;
                        respostaRepositorio.Inserir(r1);

                        Resposta r2 = new Resposta();
                        r2.Resposta = edtAlternativa2.getText().toString();
                        r2.IdPergunta = (int)idPergunta;
                        r2.RespostaCorreta = radioButtonID == R.id.rbalternativa2;
                        respostaRepositorio.Inserir(r2);

                        Resposta r3 = new Resposta();
                        r3.Resposta = edtAlternativa3.getText().toString();
                        r3.IdPergunta = (int)idPergunta;
                        r3.RespostaCorreta = radioButtonID == R.id.rbalternativa3;
                        respostaRepositorio.Inserir(r3);



                        Toast.makeText(CadastrarPerguntaActivity.this, "Perguntas cadastradas com sucesso", Toast.LENGTH_SHORT).show();
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

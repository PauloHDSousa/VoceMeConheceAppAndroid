package com.paulohdsousa.vocemeconhece;

import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.paulohdsousa.vocemeconhece.database.DatabaseHelper;
import com.paulohdsousa.vocemeconhece.dominio.entidades.Pergunta;
import com.paulohdsousa.vocemeconhece.dominio.repositorio.PerguntaRepositorio;

import java.util.List;

public class PerguntasActivity extends BaseActivity {

    ListView listView;
    SQLiteDatabase conexao;
    DatabaseHelper databaseHelper;
    CustomArrayAdapter dataSource;
    ImageButton btnAdd;

    @Override
    int getContentViewId() {
        return R.layout.activity_perguntas;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.navigation_perguntas;
    }

    @Override
    public void onResume() {
        super.onResume();

            try {
                databaseHelper = new DatabaseHelper(this);

                conexao = databaseHelper.getWritableDatabase();

                PerguntaRepositorio perguntaRepositorio = new PerguntaRepositorio(conexao);

                List<Pergunta> perguntas = perguntaRepositorio.Buscar();

                if (!perguntas.isEmpty()){
                    dataSource = new CustomArrayAdapter(this, perguntas);
                    listView.setAdapter(dataSource);
                }else{
                    Toast.makeText(this, "Nenhum pergunta cadastrada", Toast.LENGTH_LONG).show();
                }

            } catch (SQLException e){
                Toast.makeText(this, "Erro", Toast.LENGTH_SHORT).show();
            }
            finally {
                conexao.close();
            }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MobileAds.initialize(this);

        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        listView = (ListView)  findViewById(R.id.listViewPerguntas);
        btnAdd = (ImageButton) findViewById(R.id.btnAdicionarPergunta);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(PerguntasActivity.this, CadastrarPerguntaActivity.class);
                PerguntasActivity.this.startActivity(myIntent);
            }
        });
    }
}
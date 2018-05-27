package com.paulohdsousa.vocemeconhece;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.paulohdsousa.vocemeconhece.database.DatabaseHelper;
import com.paulohdsousa.vocemeconhece.dominio.entidades.Pergunta;
import com.paulohdsousa.vocemeconhece.dominio.entidades.Ranking;
import com.paulohdsousa.vocemeconhece.dominio.entidades.Resposta;
import com.paulohdsousa.vocemeconhece.dominio.repositorio.PerguntaRepositorio;
import com.paulohdsousa.vocemeconhece.dominio.repositorio.RankingRepositorio;
import com.paulohdsousa.vocemeconhece.dominio.repositorio.RespostaRepositorio;

import java.util.List;

public class RankingActivity extends BaseActivity {

    RankingActivity rankingActivity;

    ListView listView;
    SQLiteDatabase conexao;
    DatabaseHelper databaseHelper;
    CustomRankingArrayAdapter dataSource;

    @Override
    int getContentViewId() {
        return R.layout.activity_ranking;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.navigation_ranking;
    }

    @Override
    public void onResume() {
        super.onResume();


        try {
            databaseHelper = new DatabaseHelper(this);

            conexao = databaseHelper.getWritableDatabase();

            Toast.makeText(this, "Sucesso", Toast.LENGTH_SHORT).show();

            RankingRepositorio rankingRepositorio = new RankingRepositorio(conexao);

            List<Ranking> ranking = rankingRepositorio.Buscar();

            if (!ranking.isEmpty()) {
                dataSource = new CustomRankingArrayAdapter(this, ranking);
                listView.setAdapter(dataSource);
            } else {
                Toast.makeText(this, "Nenhum registro encontrado", Toast.LENGTH_SHORT).show();
            }

        } catch (SQLException e) {
            Toast.makeText(this, "Erro", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        listView = (ListView) findViewById(R.id.listViewRanking);

    }
}
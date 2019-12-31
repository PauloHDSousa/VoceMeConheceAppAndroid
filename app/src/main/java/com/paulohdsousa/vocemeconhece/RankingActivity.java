package com.paulohdsousa.vocemeconhece;

import android.app.Dialog;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.paulohdsousa.vocemeconhece.database.DatabaseHelper;
import com.paulohdsousa.vocemeconhece.dominio.entidades.Ranking;
import com.paulohdsousa.vocemeconhece.dominio.repositorio.RankingRepositorio;

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

            RankingRepositorio rankingRepositorio = new RankingRepositorio(conexao);

            List<Ranking> ranking = rankingRepositorio.Buscar();

            if (!ranking.isEmpty()) {
                dataSource = new CustomRankingArrayAdapter(this, ranking);
                listView.setAdapter(dataSource);
            } else {
                Toast.makeText(this, "Nenhum jogo registrado", Toast.LENGTH_LONG).show();
            }

        } catch (SQLException e) {
            Toast.makeText(this, "Erro", Toast.LENGTH_SHORT).show();
        }
        finally {
            conexao.close();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        listView = (ListView) findViewById(R.id.listViewRanking);
        ImageButton imgButton = (ImageButton) findViewById(R.id.btnQuestion);
        imgButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                alertHowItWorks();
            }
        });
    }

    public void alertHowItWorks() {
        final Dialog dialog = new Dialog(RankingActivity.this);
        dialog.setContentView(R.layout.activity_como_funciona_ranking);
        dialog.setTitle("Como funciona o Ranking");


       ImageButton dialogButton = (ImageButton) dialog.findViewById(R.id.btnOk);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
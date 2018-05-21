package com.paulohdsousa.vocemeconhece;

import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.paulohdsousa.vocemeconhece.database.DatabaseHelper;
import com.paulohdsousa.vocemeconhece.dominio.entidades.Pergunta;
import com.paulohdsousa.vocemeconhece.dominio.repositorio.PerguntaRepositorio;

import java.util.List;

public class RankingActivity extends BaseActivity {



    @Override
    int getContentViewId() {
        return R.layout.activity_ranking;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.navigation_ranking;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }
}
package com.paulohdsousa.vocemeconhece;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageButton;

import com.paulohdsousa.vocemeconhece.database.DatabaseHelper;
import com.paulohdsousa.vocemeconhece.dominio.repositorio.PerguntaRepositorio;

public class    MainActivity extends BaseActivity {


    SQLiteDatabase conexao;
    DatabaseHelper databaseHelper;
    PerguntaRepositorio perguntaRepositorio;
    ImageButton btnJogar, btnRate, btnEmail;
    ImageButton btnSobre;

    int qtdPerguntasMinimas = 5;
    @Override
    int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.navigation_home;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        btnJogar= (ImageButton)findViewById(R.id.btnJogar);
        databaseHelper = new DatabaseHelper(MainActivity.this);
        conexao = databaseHelper.getWritableDatabase();
        perguntaRepositorio = new PerguntaRepositorio(conexao);
        btnRate = (ImageButton)findViewById(R.id.btnRate);
        btnEmail = (ImageButton)findViewById(R.id.btnEmail);

        btnRate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String str ="https://play.google.com/store/apps/details?id=com.paulohdsousa.vocemeconhece";
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(str)));
            }
        });

        btnEmail.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);

                /* Fill it with Data */
                emailIntent.setType("plain/text");
                emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"paulo.ti.sousa@gmail.com"});
                emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Você me conhece? - Críticas, Dúvidas, Elogios e Sugestões");
                emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Gostariz de dizer que ");

                /* Send it off to the Activity-Chooser */
                MainActivity.this.startActivity(Intent.createChooser(emailIntent, "Enviar email"));
            }
        });

        btnJogar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int quantidadePerguntas = perguntaRepositorio.Buscar().size();
                if(quantidadePerguntas < qtdPerguntasMinimas) {

                    AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                    alertDialog.setTitle("Aviso");
                    alertDialog.setMessage("Você precisa cadastrar no mínimo " + qtdPerguntasMinimas + " perguntas");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    Intent myIntent = new Intent(MainActivity.this, PerguntasActivity.class);
                                    MainActivity.this.startActivity(myIntent);
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                    return;
                }


                    Intent myIntent = new Intent(MainActivity.this, JogarActivity.class);
                    MainActivity.this.startActivity(myIntent);

            }
        });

    }
}

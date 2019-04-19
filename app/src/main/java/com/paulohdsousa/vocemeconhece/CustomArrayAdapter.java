package com.paulohdsousa.vocemeconhece;


import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.paulohdsousa.vocemeconhece.database.DatabaseHelper;
import com.paulohdsousa.vocemeconhece.dominio.entidades.Pergunta;
import com.paulohdsousa.vocemeconhece.dominio.repositorio.PerguntaRepositorio;
import com.paulohdsousa.vocemeconhece.dominio.repositorio.RespostaRepositorio;

import java.util.List;

public class CustomArrayAdapter extends ArrayAdapter<Pergunta> {
    private final Context context;
    private final List<Pergunta> perguntas;

    SQLiteDatabase conexao;
    DatabaseHelper databaseHelper;

    public CustomArrayAdapter(Context context,  List<Pergunta> perguntas) {
        super(context, R.layout.list_perguntas, perguntas);
        this.context = context;
        this.perguntas = perguntas;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.list_perguntas, parent, false);

        ImageButton btnDelete = (ImageButton) rowView.findViewById(R.id.btnDelete);
        TextView textView = (TextView) rowView.findViewById(R.id.nomeCliente);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        position = position;

        textView.setText(perguntas.get(position).Pergunta);

        final int finalPosition = position;
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    databaseHelper = new DatabaseHelper(rowView.getContext());
                    conexao = databaseHelper.getWritableDatabase();

                    int idPergunta = perguntas.get(finalPosition).IdPergunta;

                    PerguntaRepositorio perguntaRepositorio = new PerguntaRepositorio(conexao);
                    RespostaRepositorio respostaRepositorio = new RespostaRepositorio(conexao);

                    perguntaRepositorio.Excluir(idPergunta);
                    respostaRepositorio.Excluir(idPergunta);

                    remove(finalPosition);
                    notifyDataSetChanged();

                    Toast.makeText(rowView.getContext(), "Pergunta removida", Toast.LENGTH_SHORT).show();


                } catch (SQLException e){
                    Toast.makeText(rowView.getContext(), "Erro" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                    finally {
                    conexao.close();
                }
            }
        });


        return rowView;
    }

    public void remove(int position){
        perguntas.remove(position);
    }
}
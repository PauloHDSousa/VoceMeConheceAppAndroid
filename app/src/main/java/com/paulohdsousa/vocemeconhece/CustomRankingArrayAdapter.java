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
import com.paulohdsousa.vocemeconhece.dominio.entidades.Ranking;
import com.paulohdsousa.vocemeconhece.dominio.repositorio.PerguntaRepositorio;
import com.paulohdsousa.vocemeconhece.dominio.repositorio.RespostaRepositorio;

import org.w3c.dom.Text;

import java.util.List;

public class CustomRankingArrayAdapter extends ArrayAdapter<Ranking> {
    private final Context context;
    private final List<Ranking> perguntas;

    SQLiteDatabase conexao;
    DatabaseHelper databaseHelper;

    public CustomRankingArrayAdapter(Context context,  List<Ranking> perguntas) {
        super(context, R.layout.list_ranking, perguntas);
        this.context = context;
        this.perguntas = perguntas;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.list_ranking, parent, false);

        TextView tvJogador = (TextView) rowView.findViewById(R.id.tvJogador);
        TextView tvAcertos = (TextView) rowView.findViewById(R.id.tvAcertos);
        TextView tvPosicao = (TextView) rowView.findViewById(R.id.tvPosicao);

        TextView tvPerguntas = (TextView) rowView.findViewById(R.id.tvPerguntas);
        TextView tvPorcentagemAcertos = (TextView) rowView.findViewById(R.id.tvPorcentagemAcertos);


        ImageView imageView = (ImageView) rowView.findViewById(R.id.imgTrophy);
        position = position;

        if(position == 1)
            imageView.setImageResource(R.drawable.silver);

        if(position == 2)
            imageView.setImageResource(R.drawable.bronze);

        if(position > 2) {
            imageView.setVisibility(View.GONE);
            tvPosicao.setVisibility(View.VISIBLE);
        }
        tvPosicao.setText((position + 1)  +"º");

        Ranking r = perguntas.get(position);

        tvJogador.setText(r .Jogador);
        tvAcertos.setText(String.valueOf(r .QuantidadeAcertos));
        tvPerguntas.setText(String.valueOf(r .QuantidadeRespondida));
        tvPorcentagemAcertos.setText(String.valueOf(r .PorcentagemAcertos));

        return rowView;
    }

    public void remove(int position){
        perguntas.remove(position);
    }
}
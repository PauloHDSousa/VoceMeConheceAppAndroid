package com.paulohdsousa.vocemeconhece.dominio.repositorio;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.paulohdsousa.vocemeconhece.dominio.entidades.Ranking;
import com.paulohdsousa.vocemeconhece.dominio.entidades.Resposta;

import java.util.ArrayList;
import java.util.List;

public class RankingRepositorio  {
    private SQLiteDatabase conexao;

    public  RankingRepositorio(SQLiteDatabase conexao){
        this.conexao = conexao;
    }


    public long Inserir(Ranking ranking){

        ContentValues cv = new ContentValues();
        cv.put("Jogador", ranking.Jogador);
        cv.put("QuantidadeAcertos", ranking.QuantidadeAcertos);
        cv.put("QuantidadeRespondida", ranking.QuantidadeRespondida);
        cv.put("PorcentagemAcertos", ranking.PorcentagemAcertos);

        return conexao.insertOrThrow("Ranking", null, cv);
    }

    public void Excluir(int idPergunta){

        String[] parametros = new String[1];
        parametros[0] = String.valueOf(idPergunta);

        conexao.delete("Resposta", "idPergunta = ?",parametros);
    }

    public List<Ranking> Buscar(){
        List<Ranking> respostas = new ArrayList<Ranking>();

        String sql =  "SELECT Jogador, QuantidadeAcertos, QuantidadeRespondida, PorcentagemAcertos FROM Ranking";

        Cursor resultado =  conexao.rawQuery(sql,null);

        if(resultado.getCount() == 0)
            return respostas;

        resultado.moveToFirst();

        do{
            Ranking r = new Ranking();
            r.Jogador =  resultado.getString(resultado.getColumnIndexOrThrow("Jogador"));
            r.QuantidadeRespondida =    resultado.getInt(resultado.getColumnIndexOrThrow("QuantidadeRespondida"));
            r.QuantidadeAcertos =    resultado.getInt(resultado.getColumnIndexOrThrow("QuantidadeAcertos"));
            r.PorcentagemAcertos =    resultado.getInt(resultado.getColumnIndexOrThrow("PorcentagemAcertos"));
            respostas.add(r);

        } while(resultado.moveToNext());

        return  respostas;
    }
}

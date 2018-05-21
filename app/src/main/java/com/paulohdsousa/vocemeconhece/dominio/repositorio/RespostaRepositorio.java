package com.paulohdsousa.vocemeconhece.dominio.repositorio;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.paulohdsousa.vocemeconhece.dominio.entidades.Pergunta;
import com.paulohdsousa.vocemeconhece.dominio.entidades.Resposta;

import java.util.ArrayList;
import java.util.List;

public class RespostaRepositorio {
    private SQLiteDatabase conexao;

    public  RespostaRepositorio(SQLiteDatabase conexao){
        this.conexao = conexao;
    }


    public long Inserir(Resposta resposta){

        ContentValues cv = new ContentValues();
        cv.put("IdPergunta", resposta.IdPergunta);
        cv.put("Resposta", resposta.Resposta);
        cv.put("RespostaCorreta", resposta.RespostaCorreta ? 1 : 0);

        return conexao.insertOrThrow("Resposta", null, cv);
    }

    public void Excluir(int idPergunta){

        String[] parametros = new String[1];
        parametros[0] = String.valueOf(idPergunta);

        conexao.delete("Resposta", "idPergunta = ?",parametros);
    }

    public void Alterar(Resposta resposta){

        ContentValues cv = new ContentValues();
        cv.put("Resposta", resposta.Resposta);
        cv.put("RespostaCorreta",  resposta.RespostaCorreta ? 1 : 0);

        String[] parametros = new String[1];
        parametros[0] = String.valueOf(resposta.IdResposta);

        conexao.update("Resposta", cv,"IdResposta = ?", parametros);
    }

    public List<Resposta> Buscar(){
        List<Resposta> respostas = new ArrayList<Resposta>();

        String sql =  "SELECT Resposta,RespostaCorreta FROM Resposta";

        Cursor resultado =  conexao.rawQuery(sql,null);

        if(resultado.getCount() == 0)
            return respostas;

        resultado.moveToFirst();

        do{
            Resposta r = new Resposta();
            r.IdResposta =  resultado.getInt(resultado.getColumnIndexOrThrow("IdPergunta"));
            r.Resposta =    resultado.getString(resultado.getColumnIndexOrThrow("Pergunta"));
            r.RespostaCorreta =    resultado.getInt(resultado.getColumnIndexOrThrow("Pergunta")) == 1 ? true : false ;
            respostas.add(r);

        } while(resultado.moveToNext());

        return  respostas;
    }

    public List<Resposta> Buscar(int idPergunta){
        List<Resposta> respostas = new ArrayList<Resposta>();

        String sql =  "SELECT Resposta, RespostaCorreta,IdPergunta FROM Resposta WHERE  IdPergunta = ?";
        String[] parametros = new String[1];
        parametros[0] = String.valueOf(idPergunta);

        Cursor resultado =  conexao.rawQuery(sql,parametros);

        if(resultado.getCount() == 0)
            return respostas;

        resultado.moveToFirst();

        do{
            Resposta r = new Resposta();
            r.IdResposta =  resultado.getInt(resultado.getColumnIndexOrThrow("IdPergunta"));
            r.Resposta   =    resultado.getString(resultado.getColumnIndexOrThrow("Resposta"));
            r.RespostaCorreta =    resultado.getInt(resultado.getColumnIndexOrThrow("RespostaCorreta")) == 1 ? true : false ;
            respostas.add(r);

        } while(resultado.moveToNext());

        return  respostas;
    }
}

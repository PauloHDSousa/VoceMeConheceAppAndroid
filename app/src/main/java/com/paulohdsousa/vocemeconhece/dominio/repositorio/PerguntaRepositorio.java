package com.paulohdsousa.vocemeconhece.dominio.repositorio;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.paulohdsousa.vocemeconhece.dominio.entidades.Pergunta;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class PerguntaRepositorio {

    private SQLiteDatabase conexao;

    public  PerguntaRepositorio(SQLiteDatabase conexao){
        this.conexao = conexao;
    }


    public long Inserir(Pergunta pergunta){

        ContentValues cv = new ContentValues();
        cv.put("Pergunta", pergunta.Pergunta);

        return conexao.insertOrThrow("Pergunta", null, cv);
    }

    public void Excluir(int idPergunta){

        String[] parametros = new String[1];
        parametros[0] = String.valueOf(idPergunta);

        conexao.delete("Pergunta", "IdPergunta = ?",parametros);
    }

    public void Alterar(Pergunta pergunta){

        ContentValues cv = new ContentValues();
        cv.put("Pergunta", pergunta.Pergunta);

        String[] parametros = new String[1];
        parametros[0] = String.valueOf(pergunta.IdPergunta);

        conexao.update("Pergunta", cv,"IdPergunta = ?", parametros);
    }

    public List<Pergunta> Buscar(){
        List<Pergunta> perguntas = new ArrayList<Pergunta>();

        String sql =  "SELECT IdPergunta,Pergunta FROM Pergunta";

        Cursor resultado =  conexao.rawQuery(sql,null);

        if(resultado.getCount() == 0)
            return perguntas;

        resultado.moveToFirst();

        do{
            Pergunta p = new Pergunta();
            p.IdPergunta =  resultado.getInt(resultado.getColumnIndexOrThrow("IdPergunta"));
            p.Pergunta =    resultado.getString(resultado.getColumnIndexOrThrow("Pergunta"));

            perguntas.add(p);

        } while(resultado.moveToNext());

        return  perguntas;
    }

    public Pergunta Buscar(int idPergunta){
        Pergunta pergunta = new Pergunta();

        String sql =  "SELECT IdPergunta,Pergunta FROM Pergunta WHERE  IdPergunta = ?";

        String[] parametros = new String[1];
        parametros[0] = String.valueOf(idPergunta);

        Cursor resultado =  conexao.rawQuery(sql,parametros);

        if(resultado.getCount() == 0)
            return  null;

        resultado.moveToFirst();

        pergunta.IdPergunta = resultado.getInt(resultado.getColumnIndexOrThrow("IdPergunta"));
        pergunta.Pergunta = resultado.getString(resultado.getColumnIndexOrThrow("Pergunta"));

        return  pergunta;
    }
}

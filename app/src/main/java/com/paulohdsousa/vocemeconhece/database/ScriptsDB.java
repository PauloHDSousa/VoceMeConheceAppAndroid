package com.paulohdsousa.vocemeconhece.database;

public class ScriptsDB {
    public  static String getCreateTablePergunta() {
        StringBuilder sql = new StringBuilder();
        sql.append("CREATE TABLE IF NOT EXISTS Pergunta ( ");
        sql.append("IdPergunta	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, ");
        sql.append("Pergunta TEXT NOT NULL )");
        return sql.toString();
    }

    public  static String getCreateTableResposta() {
        StringBuilder sql = new StringBuilder();
        sql.append("CREATE TABLE IF NOT EXISTS Resposta ( ");
        sql.append("IdResposta INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " );
        sql.append("IdPergunta INTEGER NOT NULL, ");
        sql.append("Resposta TEXT NOT NULL, ");
        sql.append("RespostaCorreta INTEGER NOT NULL, ");
        sql.append("FOREIGN KEY(IdPergunta) REFERENCES  Pergunta(IdPergunta) )");

        return sql.toString();
    }

    public  static String getCreateTableRanking() {
        StringBuilder sql = new StringBuilder();
        sql.append("CREATE TABLE IF NOT EXISTS Ranking ( ");
        sql.append("IdRanking INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " );
        sql.append("Jogador TEXT NOT NULL, ");
        sql.append("QuantidadeAcertos INTEGER NOT NULL, ");
        sql.append("QuantidadeRespondida INTEGER NOT NULL, ");
        sql.append("PorcentagemAcertos INTEGER NOT NULL");

        return sql.toString();
    }

}



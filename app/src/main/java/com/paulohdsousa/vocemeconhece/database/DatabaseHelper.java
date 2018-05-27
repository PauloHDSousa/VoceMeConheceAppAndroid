package com.paulohdsousa.vocemeconhece.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper  extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, "vocemeconhece.db", null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ScriptsDB.getCreateTableRanking());
        db.execSQL(ScriptsDB.getCreateTablePergunta());
        db.execSQL(ScriptsDB.getCreateTableResposta());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
}

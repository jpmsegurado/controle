package com.autzon.controledeestoque.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by JP on 26/05/2015.
 */
public class DbHelper extends SQLiteOpenHelper {

    private StringBuilder sql;

    public DbHelper(Context context) {
        super(context, Contract.nome_banco, null, Contract.versao);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        sql = new StringBuilder();
        sql.append("CREATE TABLE IF NOT EXISTS transacao(");
        sql.append("_id INTEGER PRIMARY KEY AUTOINCREMENT,");
        sql.append("tipo int(1),");
        sql.append("qtd int(5),");
        sql.append("id_produto int(11),");
        sql.append("data_hora TIMESTAMP ");
        sql.append(");");

        db.execSQL(sql.toString());

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }
}

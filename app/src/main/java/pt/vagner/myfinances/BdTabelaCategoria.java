package pt.vagner.myfinances;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class BdTabelaCategoria implements BaseColumns {
    public static final String NOME_TABELA = "categorias";
    public static final String CAMPO_DESCRICAO = "descricao";


    public SQLiteDatabase db;
    public BdTabelaCategoria(SQLiteDatabase db) {
        this.db = db;
    }


    public void criar() {
        db.execSQL(
                        "CREATE TABLE " + NOME_TABELA + "(" +
                        _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        CAMPO_DESCRICAO + " TEXT NOT NULL" +
                        ")"
        );

    }
}

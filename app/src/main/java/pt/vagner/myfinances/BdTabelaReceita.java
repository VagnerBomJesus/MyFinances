package pt.vagner.myfinances;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class BdTabelaReceita implements BaseColumns {

    public static final String NOME_TABELA = "receita";
    public static final String CAMPO_DESCRICAO = "descricao";
    public static final String CAMPO_VALOR = "valor";
    public static final String CAMPO_CATEGORIA = "categoria";

    public SQLiteDatabase db;
    public BdTabelaReceita(SQLiteDatabase db) {
        this.db = db;
    }

    //criação da tabela
    public void criar() {

        db.execSQL(
                "CREATE TABLE " + NOME_TABELA + "(" +
                        _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        CAMPO_DESCRICAO + " TEXT NOT NULL," +
                        CAMPO_VALOR + " INTEGER NOT NULL," +
                        CAMPO_CATEGORIA + " INTEGER NOT NULL," +
                        "FOREIGN KEY (" + CAMPO_CATEGORIA + ") REFERENCES " + BdTabelaCategoria.NOME_TABELA + "(" + BdTabelaCategoria._ID + ")" +
                        ")"
        );



    }
}

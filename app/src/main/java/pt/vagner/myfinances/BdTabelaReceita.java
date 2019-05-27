package pt.vagner.myfinances;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class BdTabelaReceita implements BaseColumns {

    public static final String TABLE_NAME = "TipoReceita";
    public static final String _ID = "id_receita";
    public static final String CATEGORIA_RECEITA = "categoria";

    public SQLiteDatabase db;
    public BdTabelaReceita(SQLiteDatabase db) {
        this.db = db;
    }

    //criação da tabela
    public void criar() {

        db.execSQL(
                "CREATE TABLE " + TABLE_NAME + " (" +
                        _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        CATEGORIA_RECEITA + " TEXT NOT NULL)"
        );



    }
}

package pt.vagner.myfinances;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class BdTabelaDespesa implements BaseColumns {

    public static final String TABLE_NAME = "TipoDespesa";
    public static final String _ID = "id_despesa";
    public static final String CATEGORIA_DESPESAS = "categoria";

    public SQLiteDatabase db;
    public BdTabelaDespesa(SQLiteDatabase db) {
        this.db = db;
    }

    //criação da tabela
    public void criar() {

        db.execSQL(
                "CREATE TABLE " + TABLE_NAME + " (" +
                        _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        CATEGORIA_DESPESAS + " TEXT NOT NULL)"
        );

    }
}

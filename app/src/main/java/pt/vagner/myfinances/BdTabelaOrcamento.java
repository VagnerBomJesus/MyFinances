package pt.vagner.myfinances;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class BdTabelaOrcamento implements BaseColumns {
    //public static final String _ID = "id_orcamento";
    public static final String VALOR = "valor";
    public static final String TABLE_NAME = "Orcamento";


    public SQLiteDatabase db;
    public BdTabelaOrcamento(SQLiteDatabase db) {
        this.db = db;
    }

    //criação da tabela
    public void criar() {

        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                VALOR + " REAL NOT NULL" +
                ")"
        );

    }
}

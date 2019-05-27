package pt.vagner.myfinances;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class BdTabelaReceita implements BaseColumns {

    public SQLiteDatabase db;
    public BdTabelaReceita(SQLiteDatabase db) {
        this.db = db;
    }


    public void criar() {

    }
}

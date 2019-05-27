package pt.vagner.myfinances;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class BdTabelaOrcamento implements BaseColumns {



    public SQLiteDatabase db;
    public BdTabelaOrcamento(SQLiteDatabase db) {
        this.db = db;
    }


}

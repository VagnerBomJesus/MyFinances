package pt.vagner.myfinances;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class BdTabelaDespesa implements BaseColumns {

    public SQLiteDatabase db;
    public BdTabelaDespesa(SQLiteDatabase db) {
        this.db = db;
    }

    public void criar() {
    }
}

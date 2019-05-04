package pt.vagner.myfinances;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class BdTableTipeDespesa implements BaseColumns {
    private final SQLiteDatabase db;

    public BdTableTipeDespesa(SQLiteDatabase db) {
        this.db = db;
    }

    public void cria() {
    }
}

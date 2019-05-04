package pt.vagner.myfinances;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class BdTableTipoReceita implements BaseColumns {
    private final SQLiteDatabase db;

    public BdTableTipoReceita(SQLiteDatabase db) {
        this.db = db;
    }

    public void cria() {
    }
}

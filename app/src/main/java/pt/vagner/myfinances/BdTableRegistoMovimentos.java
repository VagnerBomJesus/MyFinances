package pt.vagner.myfinances;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class BdTableRegistoMovimentos implements BaseColumns {
    private final SQLiteDatabase db;

    public BdTableRegistoMovimentos(SQLiteDatabase db) {
        this.db = db;
    }

    public void cria() {
    }
}

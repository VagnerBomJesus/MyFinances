package pt.vagner.myfinances;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class BdTabelaDespesa implements BaseColumns {

    public BdTabelaDespesa db;
    public BdTabelaDespesa(BdTabelaDespesa db) {
        this.db = db;
    }

}

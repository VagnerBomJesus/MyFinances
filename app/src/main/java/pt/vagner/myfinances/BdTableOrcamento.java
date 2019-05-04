package pt.vagner.myfinances;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class BdTableOrcamento implements BaseColumns {
    private SQLiteDatabase db;

    public BdTableOrcamento(SQLiteDatabase db){
        this.db = db;
    }


    public void cria() {
    }
}

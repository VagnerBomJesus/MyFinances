package pt.vagner.myfinances;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class BdTableTipeDespesa implements BaseColumns {
    public static final String NAME_TABELA = "TipoDespesa";
    public static final String _ID = "id_despesa";
    public static final String CATEGORIA_DESPESAS = "categoria";



    private final SQLiteDatabase db;

    public BdTableTipeDespesa(SQLiteDatabase db) {
        this.db = db;
    }

    //criação da tabela
    public void cria() {

            db.execSQL(
                    "CREATE TABLE " + NAME_TABELA + " (" +
                            _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                            CATEGORIA_DESPESAS + " TEXT NOT NULL)"
            );

    }
}

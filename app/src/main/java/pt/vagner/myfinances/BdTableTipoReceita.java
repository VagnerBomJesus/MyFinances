package pt.vagner.myfinances;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class BdTableTipoReceita implements BaseColumns {
    public static final String TABLE_NAME = "TipoReceita";
    public static final String _ID = "id_receita";
    public static final String CATEGORIA_RECEITA = "categoria";


    private final SQLiteDatabase db;

    //Construtor
    public BdTableTipoReceita(SQLiteDatabase db) {
        this.db = db;
    }
    //criação da tabela
    public void cria() {
        db.execSQL(
                "CREATE TABLE " + TABLE_NAME + " (" +
                        _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        CATEGORIA_RECEITA + " TEXT NOT NULL)"
        );
    }
}

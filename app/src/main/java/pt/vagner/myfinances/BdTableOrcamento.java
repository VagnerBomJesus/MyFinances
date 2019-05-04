package pt.vagner.myfinances;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class BdTableOrcamento implements BaseColumns {
    public static final String _ID = "id_orcamento";
    private static final String Nome_Tabela ="Orcamento";
    private static final String VALOR = "valor" ;
    private SQLiteDatabase db;

    public BdTableOrcamento(SQLiteDatabase db){
        this.db = db;
    }

    //Criação da Tabela
    public void cria() {

        db.execSQL(
                "CREATE TABLE " + Nome_Tabela + "(" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                VALOR + " REAL NOT NULL" +
                ")"
        );

    }
}

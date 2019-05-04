package pt.vagner.myfinances;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class BdTableRegistoMovimentos implements BaseColumns {
    public static final String NOME_TALA ="RegistoMovimetos";
    public static final String _ID = "id_movimento";
    public static final String DIA = "dia";
    public static final String MES = "mes";
    public static final String ANO = "ano";
    public static final String RECEITADESPESA = "receitadespesa";
    public static final String DESGINACAO = "desginacao";
    public static final String VALOR = "valor";



    private final SQLiteDatabase db;
    //Construtor
    public BdTableRegistoMovimentos(SQLiteDatabase db) {
        this.db = db;
    }
    //Criação da tabela
    public void cria() {
        db.execSQL(
           "CREATE TABLE "+NOME_TALA+" "+
                    "("+
                   _ID+" TEXT PRIMARY KEY, "+
                   DIA+" INTEGER NOT NULL, "+
                   MES+" INTEGER NOT NULL, "+
                   ANO+" INTEGER NOT NULL, "+
                   RECEITADESPESA+" TEXT NOT NULL, "+
                   DESGINACAO+" TEXT, "+
                   VALOR+" REAL NOT NULL" +
                   ")"
        );
    }
}

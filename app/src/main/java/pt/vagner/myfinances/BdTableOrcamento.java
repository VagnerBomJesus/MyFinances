package pt.vagner.myfinances;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class BdTableOrcamento implements BaseColumns {
    public static final String _ID = "id_orcamento";

    public static final String NOME_TABELA ="Orcamento";
    public static final String VALOR = "valor";
    private SQLiteDatabase db;

    public BdTableOrcamento(SQLiteDatabase db){
        this.db = db;
    }

    //Criação da Tabela
    public void cria() {

        db.execSQL(
                "CREATE TABLE " + NOME_TABELA + "(" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                VALOR + " REAL NOT NULL" +
                ")"
        );

    }

    //create
    public long insert(ContentValues values){
        return db.insert(NOME_TABELA, null, values);
    }

    //update
    public int update(ContentValues values, String whereClause, String[] whereArgs){
        return db.update(NOME_TABELA,values,whereClause,whereArgs);
    }

    //delete
    public int delete(String whereClause, String[] whereArgs){
        return db.delete(NOME_TABELA,whereClause,whereArgs);
    }

    //read
    public Cursor query (String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy){
        return db.query(NOME_TABELA, columns, selection, selectionArgs, groupBy, having, orderBy);
    }

}

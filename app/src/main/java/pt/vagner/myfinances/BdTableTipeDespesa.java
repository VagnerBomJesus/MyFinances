package pt.vagner.myfinances;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class BdTableTipeDespesa implements BaseColumns {
    public static final String NOME_TABELA = "TipoDespesa";
    public static final String _ID = "id_despesa";
    public static final String CATEGORIA_DESPESAS = "categoria";



    private final SQLiteDatabase db;

    public BdTableTipeDespesa(SQLiteDatabase db) {
        this.db = db;
    }

    //criação da tabela
    public void cria() {

            db.execSQL(
                    "CREATE TABLE " + NOME_TABELA + " (" +
                            _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                            CATEGORIA_DESPESAS + " TEXT NOT NULL)"
            );

    }

    //insert
    public long insert(ContentValues values) {
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

    //CRUD
    public static ContentValues getContentValues(TipoDespesa tipoDespesa){
        ContentValues values = new ContentValues();
        values.put(CATEGORIA_DESPESAS, tipoDespesa.getCategoria());

        return values;
    }

    public static TipoDespesa getCurrentTipoDespesaFromCursor(Cursor cursor){
        final int posId = cursor.getColumnIndex(_ID);
        final int posCatDes = cursor.getColumnIndex(CATEGORIA_DESPESAS);

        TipoDespesa tipoDespesa = new TipoDespesa();

        tipoDespesa.setId_despesa(cursor.getInt(posId));
        tipoDespesa.setCategoria(cursor.getString(posCatDes));

        return tipoDespesa;
    }
}

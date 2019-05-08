package pt.vagner.myfinances;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class BdTableTipoReceita implements BaseColumns {
    public static final String NOME_TABELA = "TipoReceita";
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
                "CREATE TABLE " + NOME_TABELA + " (" +
                        _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        CATEGORIA_RECEITA + " TEXT NOT NULL)"
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
    public static ContentValues getContentValues(TipoReceita tipoReceita){
        ContentValues values = new ContentValues();
        values.put(CATEGORIA_RECEITA, tipoReceita.getCategoria());

        return values;
    }

    public static TipoReceita getCurrentTipoReceitaFromCursor(Cursor cursor){
        final int posId = cursor.getColumnIndex(_ID);
        final int posCatRec = cursor.getColumnIndex(CATEGORIA_RECEITA);

        TipoReceita tipoReceita = new TipoReceita();

        tipoReceita.setId_receita(cursor.getInt(posId));
        tipoReceita.setCategoria(cursor.getString(posCatRec));

        return tipoReceita;
    }
}

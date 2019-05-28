package pt.vagner.myfinances;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.text.TextUtils;
import android.util.Log;

public class BdTabelaReceita implements BaseColumns {
    public static final String NOME_TABELA = "receita";

    public static final String ALIAS_NOME_CATEGORIA = "nome_categ";

    public static final String CAMPO_DESCRICAO_RECEITA = "descricaoReceita";
    public static final String CAMPO_VALOR = "valor";
    public static final String CAMPO_CATEGORIA = "categoria";
    public static final String CAMPO_NOME_CATEGORIA = BdTabelaCategoria.NOME_TABELA + "." + BdTabelaCategoria.CAMPO_DESCRICAO + " AS " + ALIAS_NOME_CATEGORIA; // tabela de categorias (só de leitura)

    public static final String[] TODAS_COLUNAS = new String[] { NOME_TABELA + "." + _ID, CAMPO_DESCRICAO_RECEITA, CAMPO_VALOR, CAMPO_CATEGORIA, CAMPO_NOME_CATEGORIA };

    private SQLiteDatabase db;

    public BdTabelaReceita(SQLiteDatabase db) {
        this.db = db;
    }

    public void cria() {
        db.execSQL(
                "CREATE TABLE " + NOME_TABELA + "(" +
                        _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        CAMPO_DESCRICAO_RECEITA + " TEXT NOT NULL," +
                        CAMPO_VALOR + " INTEGER NOT NULL," +
                        CAMPO_CATEGORIA + " INTEGER NOT NULL," +
                        "FOREIGN KEY (" + CAMPO_CATEGORIA + ") REFERENCES " + BdTabelaCategoria.NOME_TABELA + "(" + BdTabelaCategoria._ID + ")" +
                        ")"
        );
    }

    public Cursor query(String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        String colunasSelect = TextUtils.join(",", columns);

        String sql = "SELECT " + colunasSelect + " FROM " +
                NOME_TABELA + " INNER JOIN " + BdTabelaCategoria.NOME_TABELA + " WHERE " + NOME_TABELA + "." + CAMPO_CATEGORIA + "=" + BdTabelaCategoria.NOME_TABELA + "." + BdTabelaCategoria._ID
                ;

        if (selection != null) {
            sql += " AND " + selection;
        }

        Log.d("Tabela Tipo Recita", "query: " + sql);

        return db.rawQuery(sql, selectionArgs);
    }

    public long insert(ContentValues values) {
        return db.insert(NOME_TABELA, null, values);
    }

    public int update(ContentValues values, String whereClause, String [] whereArgs) {
        return db.update(NOME_TABELA, values, whereClause, whereArgs);
    }

    public int delete(String whereClause, String[] whereArgs) {
        return db.delete(NOME_TABELA, whereClause, whereArgs);
    }
}

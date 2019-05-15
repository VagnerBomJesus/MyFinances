package pt.vagner.myfinances;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class BdTableRegistoMovimentos implements BaseColumns {
    public static final String NOME_TABELA ="RegistoMovimetos";
    public static final String _ID = "id_movimento";
    public static final String DIA = "dia";
    public static final String MES = "mes";
    public static final String ANO = "ano";
    public static final String RECEITADESPESA = "receitadespesa";
    public static final String DESGINACAO = "desginacao";
    public static final String VALOR = "valor";
    public static final String FK_ID_RECEITA = "id_Receita";
    public static final String FK_ID_DESPESA = "id_Despesa";


    public static final String[] ALL_COLUMNS = new String[]{_ID, DIA, MES, ANO, RECEITADESPESA, DESGINACAO, VALOR, FK_ID_RECEITA, FK_ID_DESPESA};
    public static final String[] INSERT_RECEITA_COLUMNS = new String[]{_ID, DIA, MES, ANO, RECEITADESPESA, DESGINACAO, VALOR, FK_ID_RECEITA};
    public static final String[] INSERT_DESPESA_COLUMNS = new String[]{_ID, DIA, MES, ANO, RECEITADESPESA, DESGINACAO, VALOR, FK_ID_DESPESA};
    public static final String[] VALOR_COLUMN = new String[]{VALOR};


    private final SQLiteDatabase db;

    //Construtor
    public BdTableRegistoMovimentos(SQLiteDatabase db) {
        this.db = db;
    }

    //Criação da tabela
    public void cria() {
        db.execSQL(
                "CREATE TABLE "+NOME_TABELA+" " +
                        "("+
                        _ID+" TEXT PRIMARY KEY, "+
                        DIA+" INTEGER NOT NULL, "+
                        MES+" INTEGER NOT NULL, "+
                        ANO+" INTEGER NOT NULL, "+
                        RECEITADESPESA+" TEXT NOT NULL, "+
                        DESGINACAO+" TEXT, "+
                        VALOR+" REAL NOT NULL, "+
                        FK_ID_RECEITA+" INTEGER REFERENCES "+BdTableTipoReceita.NOME_TABELA+" ("+BdTableTipoReceita._ID+"), "+
                        FK_ID_DESPESA+" INTEGER REFERENCES "+BdTableTipoDespesa.NOME_TABELA+" ("+BdTableTipoDespesa._ID+")" + ")"
        );
    }



    //insert
    public long insert (ContentValues values){
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
    public static ContentValues getContentValues (RegistoMovimentos registoMovimentos){
        ContentValues values = new ContentValues();
        values.put(_ID, registoMovimentos.getId_movimento());
        values.put(DIA, registoMovimentos.getDia());
        values.put(MES, registoMovimentos.getMes());
        values.put(ANO, registoMovimentos.getAno());
        values.put(RECEITADESPESA, registoMovimentos.getReceitadespesa());
        values.put(DESGINACAO, registoMovimentos.getDesignacao());
        values.put(VALOR, registoMovimentos.getValor());
        values.put(FK_ID_RECEITA, registoMovimentos.getTiporeceita());
        values.put(FK_ID_DESPESA, registoMovimentos.getTipodespesa());

        return values;
    }

    public static RegistoMovimentos getCurrentRegistoMovimentoDespesaFromCursor(Cursor cursor){
        final int posId = cursor.getColumnIndex(_ID);
        final int posDia = cursor.getColumnIndex(DIA);
        final int posMes = cursor.getColumnIndex(MES);
        final int posAno = cursor.getColumnIndex(ANO);
        final int posRecDes = cursor.getColumnIndex(RECEITADESPESA);
        final int posDesig = cursor.getColumnIndex(DESGINACAO);
        final int posValor = cursor.getColumnIndex(VALOR);
        final int posIdDes = cursor.getColumnIndex(FK_ID_DESPESA);


        RegistoMovimentos registoMovimentos = new RegistoMovimentos();

        registoMovimentos.setId_movimento(cursor.getString(posId));
        registoMovimentos.setDia(cursor.getInt(posDia));
        registoMovimentos.setMes(cursor.getInt(posMes));
        registoMovimentos.setAno(cursor.getInt(posAno));
        registoMovimentos.setReceitadespesa(cursor.getString(posRecDes));
        registoMovimentos.setDesignacao(cursor.getString(posDesig));
        registoMovimentos.setValor(cursor.getDouble(posValor));
        registoMovimentos.setTipodespesa(cursor.getInt(posIdDes));

        return registoMovimentos;
    }

    public static RegistoMovimentos getCurrentRegistoMovimentoReceitaFromCursor(Cursor cursor){
        final int posId = cursor.getColumnIndex(_ID);
        final int posDia = cursor.getColumnIndex(DIA);
        final int posMes = cursor.getColumnIndex(MES);
        final int posAno = cursor.getColumnIndex(ANO);
        final int posRecDes = cursor.getColumnIndex(RECEITADESPESA);
        final int posDesig = cursor.getColumnIndex(DESGINACAO);
        final int posValor = cursor.getColumnIndex(VALOR);
        final int posIdRec = cursor.getColumnIndex(FK_ID_RECEITA);

        RegistoMovimentos registoMovimentos = new RegistoMovimentos();

        registoMovimentos.setId_movimento(cursor.getString(posId));
        registoMovimentos.setDia(cursor.getInt(posDia));
        registoMovimentos.setMes(cursor.getInt(posMes));
        registoMovimentos.setAno(cursor.getInt(posAno));
        registoMovimentos.setReceitadespesa(cursor.getString(posRecDes));
        registoMovimentos.setDesignacao(cursor.getString(posDesig));
        registoMovimentos.setValor(cursor.getDouble(posValor));
        registoMovimentos.setTiporeceita(cursor.getInt(posIdRec));

        return registoMovimentos;
    }
}

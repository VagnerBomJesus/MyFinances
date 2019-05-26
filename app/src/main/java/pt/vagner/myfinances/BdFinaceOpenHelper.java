package pt.vagner.myfinances;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.LinkedList;
import java.util.List;

public class BdFinaceOpenHelper extends SQLiteOpenHelper {

    public static final String NOME_BASE_DADOS = "finances.db";
    public static final int VERSAO_BASE_DADOS = 1;

    /**
     * Create a helper object to create, open, and/or manage a database.
     * This method always returns very quickly.  The database is not actually
     * created or opened until one of {@link #getWritableDatabase} or
     * {@link #getReadableDatabase} is called.
     *
     * @param context to use for locating paths to the the database
     *
     */
    public BdFinaceOpenHelper(Context context) {
        super(context, NOME_BASE_DADOS, null, VERSAO_BASE_DADOS);
    }

    /**
     * Called when the database is created for the first time. This is where the
     * creation of tables and the initial population of the tables should happen.
     *
     * @param db The database.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        //Criação da Tabela Orçamento
        new BdTableOrcamento(db).cria();

        //Criação da Tabela Registo de Movimento
        new BdTableRegistoMovimentos(db).cria();

        //Criação da Tabela Tipo de Receita
        new BdTableTipoReceita(db).cria();

        //Criação da Tabela Tipo de Despesa
        new BdTableTipoDespesa(db).cria();
    }

    /**
     * Called when the database needs to be upgraded. The implementation
     * should use this method to drop tables, add tables, or do anything else it
     * needs to upgrade to the new schema version.
     *
     * <p>
     * The SQLite ALTER TABLE documentation can be found
     * <a href="http://sqlite.org/lang_altertable.html">here</a>. If you add new columns
     * you can use ALTER TABLE to insert them into a live table. If you rename or remove columns
     * you can use ALTER TABLE to rename the old table, then create the new table and then
     * populate the new table with the contents of the old table.
     * </p><p>
     * This method executes within a transaction.  If an exception is thrown, all changes
     * will automatically be rolled back.
     * </p>
     *
     * @param db         The database.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public List<RegistoMovimentos> getListRegistoMovimentos(){ //lista com todos os registos

        String query = "SELECT * FROM "+BdTableRegistoMovimentos.NOME_TABELA;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        List<RegistoMovimentos> registosLinkedList = new LinkedList<>();
        RegistoMovimentos registoMovimentos;

        if (cursor.moveToFirst()){
            do{
                registoMovimentos = new RegistoMovimentos();

                registoMovimentos.setId_movimento(cursor.getString(cursor.getColumnIndex(BdTableRegistoMovimentos._ID)));
                registoMovimentos.setDia(cursor.getInt(cursor.getColumnIndex(BdTableRegistoMovimentos.DIA)));
                registoMovimentos.setMes(cursor.getInt(cursor.getColumnIndex(BdTableRegistoMovimentos.MES)));
                registoMovimentos.setAno(cursor.getInt(cursor.getColumnIndex(BdTableRegistoMovimentos.ANO)));
                registoMovimentos.setReceitadespesa(cursor.getString(cursor.getColumnIndex(BdTableRegistoMovimentos.RECEITADESPESA)));
                registoMovimentos.setDesignacao(cursor.getString(cursor.getColumnIndex(BdTableRegistoMovimentos.DESGINACAO)));
                registoMovimentos.setValor(cursor.getDouble(cursor.getColumnIndex(BdTableRegistoMovimentos.VALOR)));
                registoMovimentos.setTiporeceita(cursor.getInt(cursor.getColumnIndex(BdTableRegistoMovimentos.FK_ID_RECEITA)));
                registoMovimentos.setTipodespesa(cursor.getInt(cursor.getColumnIndex(BdTableRegistoMovimentos.FK_ID_DESPESA)));

                registosLinkedList.add(registoMovimentos);
            }while(cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return registosLinkedList;
    }

    public List<RegistoMovimentos> getListRegistoMovimentosAno(int ano){ //lista com os registo por ano

        String query = "SELECT * FROM "+BdTableRegistoMovimentos.NOME_TABELA+" WHERE "+BdTableRegistoMovimentos.ANO+"=?";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, new String[]{Integer.toString(ano)});

        List<RegistoMovimentos> registosLinkedList = new LinkedList<>();
        RegistoMovimentos registoMovimentos;

        if (cursor.moveToFirst()){
            do{
                registoMovimentos = new RegistoMovimentos();

                registoMovimentos.setId_movimento(cursor.getString(cursor.getColumnIndex(BdTableRegistoMovimentos._ID)));
                registoMovimentos.setDia(cursor.getInt(cursor.getColumnIndex(BdTableRegistoMovimentos.DIA)));
                registoMovimentos.setMes(cursor.getInt(cursor.getColumnIndex(BdTableRegistoMovimentos.MES)));
                registoMovimentos.setAno(cursor.getInt(cursor.getColumnIndex(BdTableRegistoMovimentos.ANO)));
                registoMovimentos.setReceitadespesa(cursor.getString(cursor.getColumnIndex(BdTableRegistoMovimentos.RECEITADESPESA)));
                registoMovimentos.setDesignacao(cursor.getString(cursor.getColumnIndex(BdTableRegistoMovimentos.DESGINACAO)));
                registoMovimentos.setValor(cursor.getDouble(cursor.getColumnIndex(BdTableRegistoMovimentos.VALOR)));
                registoMovimentos.setTiporeceita(cursor.getInt(cursor.getColumnIndex(BdTableRegistoMovimentos.FK_ID_RECEITA)));
                registoMovimentos.setTipodespesa(cursor.getInt(cursor.getColumnIndex(BdTableRegistoMovimentos.FK_ID_DESPESA)));

                registosLinkedList.add(registoMovimentos);
            }while(cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return registosLinkedList;
    }

    public List<RegistoMovimentos> getListRegistoMovimentosMes(int mes, int ano){ //lista com os registos por mes e ano

        String query = "SELECT * FROM "+BdTableRegistoMovimentos.NOME_TABELA+" WHERE "+BdTableRegistoMovimentos.ANO+"=?"+" AND "+BdTableRegistoMovimentos.MES+"=?";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, new String[]{Integer.toString(ano),Integer.toString(mes)});

        List<RegistoMovimentos> registosLinkedList = new LinkedList<>();
        RegistoMovimentos registoMovimentos;

        if (cursor.moveToFirst()){
            do{
                registoMovimentos = new RegistoMovimentos();

                registoMovimentos.setId_movimento(cursor.getString(cursor.getColumnIndex(BdTableRegistoMovimentos._ID)));
                registoMovimentos.setDia(cursor.getInt(cursor.getColumnIndex(BdTableRegistoMovimentos.DIA)));
                registoMovimentos.setMes(cursor.getInt(cursor.getColumnIndex(BdTableRegistoMovimentos.MES)));
                registoMovimentos.setAno(cursor.getInt(cursor.getColumnIndex(BdTableRegistoMovimentos.ANO)));
                registoMovimentos.setReceitadespesa(cursor.getString(cursor.getColumnIndex(BdTableRegistoMovimentos.RECEITADESPESA)));
                registoMovimentos.setDesignacao(cursor.getString(cursor.getColumnIndex(BdTableRegistoMovimentos.DESGINACAO)));
                registoMovimentos.setValor(cursor.getDouble(cursor.getColumnIndex(BdTableRegistoMovimentos.VALOR)));
                registoMovimentos.setTiporeceita(cursor.getInt(cursor.getColumnIndex(BdTableRegistoMovimentos.FK_ID_RECEITA)));
                registoMovimentos.setTipodespesa(cursor.getInt(cursor.getColumnIndex(BdTableRegistoMovimentos.FK_ID_DESPESA)));

                registosLinkedList.add(registoMovimentos);
            }while(cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return registosLinkedList;
    }

    public List<RegistoMovimentos> getListRegistoMovimentosDia(int dia, int mes, int ano){ //lista com os registos por dia, mes e ano

        String query = "SELECT * FROM "+BdTableRegistoMovimentos.NOME_TABELA+" WHERE "+BdTableRegistoMovimentos.ANO+"=? AND "+BdTableRegistoMovimentos.MES+"=? AND "+BdTableRegistoMovimentos.DIA+"=?";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, new String[]{Integer.toString(ano),Integer.toString(mes),Integer.toString(dia)});

        List<RegistoMovimentos> registosLinkedList = new LinkedList<>();
        RegistoMovimentos registoMovimentos;

        if (cursor.moveToFirst()){
            do{
                registoMovimentos = new RegistoMovimentos();

                registoMovimentos.setId_movimento(cursor.getString(cursor.getColumnIndex(BdTableRegistoMovimentos._ID)));
                registoMovimentos.setDia(cursor.getInt(cursor.getColumnIndex(BdTableRegistoMovimentos.DIA)));
                registoMovimentos.setMes(cursor.getInt(cursor.getColumnIndex(BdTableRegistoMovimentos.MES)));
                registoMovimentos.setAno(cursor.getInt(cursor.getColumnIndex(BdTableRegistoMovimentos.ANO)));
                registoMovimentos.setReceitadespesa(cursor.getString(cursor.getColumnIndex(BdTableRegistoMovimentos.RECEITADESPESA)));
                registoMovimentos.setDesignacao(cursor.getString(cursor.getColumnIndex(BdTableRegistoMovimentos.DESGINACAO)));
                registoMovimentos.setValor(cursor.getDouble(cursor.getColumnIndex(BdTableRegistoMovimentos.VALOR)));
                registoMovimentos.setTiporeceita(cursor.getInt(cursor.getColumnIndex(BdTableRegistoMovimentos.FK_ID_RECEITA)));
                registoMovimentos.setTipodespesa(cursor.getInt(cursor.getColumnIndex(BdTableRegistoMovimentos.FK_ID_DESPESA)));

                registosLinkedList.add(registoMovimentos);
            }while(cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return registosLinkedList;
    }

    public RegistoMovimentos getRegistoFromRecycler(String id){ //devolve um objeoto do tipo RegistoMovimentos
        String query = "SELECT * FROM "+BdTableRegistoMovimentos.NOME_TABELA+" WHERE "+BdTableRegistoMovimentos._ID+"=?";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, new String[]{id});

        RegistoMovimentos registoMovimentos = new RegistoMovimentos();
        if (cursor.getCount() > 0){
            cursor.moveToFirst();
            registoMovimentos.setDia(cursor.getInt(cursor.getColumnIndex(BdTableRegistoMovimentos.DIA)));
            registoMovimentos.setMes(cursor.getInt(cursor.getColumnIndex(BdTableRegistoMovimentos.MES)));
            registoMovimentos.setAno(cursor.getInt(cursor.getColumnIndex(BdTableRegistoMovimentos.ANO)));
            registoMovimentos.setReceitadespesa(cursor.getString(cursor.getColumnIndex(BdTableRegistoMovimentos.RECEITADESPESA)));
            registoMovimentos.setDesignacao(cursor.getString(cursor.getColumnIndex(BdTableRegistoMovimentos.DESGINACAO)));
            registoMovimentos.setValor(cursor.getDouble(cursor.getColumnIndex(BdTableRegistoMovimentos.VALOR)));
            registoMovimentos.setTiporeceita(cursor.getInt(cursor.getColumnIndex(BdTableRegistoMovimentos.FK_ID_RECEITA)));
            registoMovimentos.setTipodespesa(cursor.getInt(cursor.getColumnIndex(BdTableRegistoMovimentos.FK_ID_DESPESA)));
        }

        cursor.close();
        db.close();

        return registoMovimentos;
    }


    public String getTipoDespesaByID(int id){ //devolve tipo despesa dado um ID
        String query = "SELECT "+BdTableTipoDespesa.CATEGORIA_DESPESAS+" FROM "+BdTableTipoDespesa.NOME_TABELA+" WHERE "+BdTableTipoDespesa._ID+"=?";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,new String[]{Integer.toString(id)});

        String categoria = "";
        if (cursor.moveToFirst()){
            categoria = cursor.getString(cursor.getColumnIndex(BdTableTipoDespesa.CATEGORIA_DESPESAS));
        }

        cursor.close();
        db.close();

        return categoria;
    }

    public String getTipoReceitaByID(int id){ //devolve tipo receita por ID
        String query = "SELECT "+BdTableTipoReceita.CATEGORIA_RECEITA+" FROM "+BdTableTipoReceita.NOME_TABELA+" WHERE "+BdTableTipoReceita._ID+"=?";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,new String[]{Integer.toString(id)});

        String categoria = "";
        if (cursor.moveToFirst()){
            categoria = cursor.getString(cursor.getColumnIndex(BdTableTipoReceita.CATEGORIA_RECEITA));
        }

        cursor.close();
        db.close();

        return categoria;
    }

    public String checkReceitaDespesa(String id){ //devolve receita/despesa
        //Leitura
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT "+BdTableRegistoMovimentos.RECEITADESPESA+" FROM "+BdTableRegistoMovimentos.NOME_TABELA+" WHERE "+BdTableRegistoMovimentos._ID+" =?";

        Cursor cursor = db.rawQuery(query, new String[]{id});

        String tipo = "";
        if (cursor.getCount() > 0){
            cursor.moveToFirst();
            tipo = cursor.getString(cursor.getColumnIndex(BdTableRegistoMovimentos.RECEITADESPESA));
        }

        cursor.close();
        db.close();

        return tipo;
    }

}

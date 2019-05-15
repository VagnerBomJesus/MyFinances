package pt.vagner.myfinances;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.InstrumentationRegistry.getContext;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class BdFinanceTest {
    @Before
    public void apagaBaseDados() {
        getAppContext().deleteDatabase(BdFinaceOpenHelper.NOME_BASE_DADOS);
    }

    @Test
    public void openBdFinanceTest() {
        // Context of the app under test.
        Context appContext = getAppContext();

        BdFinaceOpenHelper openHelper = new BdFinaceOpenHelper(appContext);

        SQLiteDatabase db = openHelper.getReadableDatabase();

        assertTrue(db.isOpen());
    }

    private Context getAppContext() {
        return InstrumentationRegistry.getTargetContext();
    }


    @Test
    public void OrcamentoCRUDTest() {

        //Abrir BD
        BdFinaceOpenHelper openHelper = new BdFinaceOpenHelper(getAppContext());
        //Op. escrita
        SQLiteDatabase db = openHelper.getWritableDatabase();

        BdTableOrcamento tableOrcamento = new BdTableOrcamento(db);

        //Insert/Create (C)RUD
        Orcamento orcamento = new Orcamento();
        orcamento.setValor(60.9);
       // orcamento.setValor(700.0);
       // orcamento.setValor(800.0);


        long id = tableOrcamento.insert(BdTableOrcamento.getContentValues(orcamento));
        assertNotEquals(-1,id);

        //query/Read C/R)UD
        orcamento = PrimeiroOrcamento(tableOrcamento, 60.9, id);

        //update CR(U)D
        orcamento.setValor(200.0);

        int rowsAffected = tableOrcamento.update(
                BdTableOrcamento.getContentValues(orcamento),
                BdTableOrcamento._ID + "=?",
                new String[]{Long.toString(id)}
        );
        //"Erro ao atualizar orçamento",
        assertEquals(1,rowsAffected);

        //delete CRU(D)
        rowsAffected = tableOrcamento.delete(
                BdTableOrcamento._ID+"=?",
                new String[]{Long.toString(id)}
        );
        //"Erro ao eliminar orçamento",
        assertEquals(1,rowsAffected);

        Cursor cursor = tableOrcamento.query(BdTableOrcamento.ALL_COLUMNS, null, null, null, null, null);
        //"Registos de orçamentos encontrados depois de eliminados..."
        assertEquals(0,cursor.getCount());

    }

    @Test
    public void RegistoMovimentosCRUDTest(){
        //Abrir BD
        BdFinaceOpenHelper openHelper = new BdFinaceOpenHelper(getAppContext());

        //Op. escrita
        SQLiteDatabase db = openHelper.getWritableDatabase();

        BdTableTipoDespesa tableTipoDespesa = new BdTableTipoDespesa(db);
        BdTableRegistoMovimentos tableRegistoMovimentos = new BdTableRegistoMovimentos(db);

        TipoDespesa tipoDespesa = new TipoDespesa();
        tipoDespesa.setCategoria("Alimentação");

        long idDespesa = tableTipoDespesa.insert(BdTableTipoDespesa.getContentValues(tipoDespesa));

        //Insert/Create (C)RUD
        RegistoMovimentos registoMovimentos = new RegistoMovimentos();
        registoMovimentos.setId_movimento("0011100110011");
        registoMovimentos.setDia(8);
        registoMovimentos.setMes(6);
        registoMovimentos.setAno(2019);
        registoMovimentos.setReceitadespesa("Despesa");
        registoMovimentos.setDesignacao("Almoço cantina");
        registoMovimentos.setValor(10.99);
        registoMovimentos.setTipodespesa((int)idDespesa);

        tableRegistoMovimentos.insert(BdTableRegistoMovimentos.getContentValues(registoMovimentos));
        assertNotEquals(-1,1);
    }

    @Test
    public void TipoDespesaCRUDTest(){
        //Abrir BD
        BdFinaceOpenHelper openHelper = new BdFinaceOpenHelper(getAppContext());

        //Op. escrita
        SQLiteDatabase db = openHelper.getWritableDatabase();

        BdTableTipoDespesa tableTipoDespesa = new BdTableTipoDespesa(db);

        TipoDespesa tipoDespesa = new TipoDespesa();
        tipoDespesa.setCategoria("Compras");

        //Insert/Create (C)RUD
        long id = tableTipoDespesa.insert(BdTableTipoDespesa.getContentValues(tipoDespesa));
        //Erro ao inserir categoria
        assertNotEquals(-1,id); //Se der -1 é porque não foi possível inserir o registo

        //query/Read C/R)UD
        tipoDespesa = PrimeiroTipoDespesa(tableTipoDespesa,"Compras",id);

        //update CR(U)D
        tipoDespesa.setCategoria("Alimentação");

        int rowsAffected = tableTipoDespesa.update(
                BdTableTipoDespesa.getContentValues(tipoDespesa),
                BdTableTipoDespesa._ID + "=?",
                new String[]{Long.toString(id)}
        );
        //Erro ao atualizar categoria
        assertEquals(1,rowsAffected);

        //delete CRU(D)
        rowsAffected = tableTipoDespesa.delete(
                BdTableTipoDespesa._ID+"=?",
                new String[]{Long.toString(id)}
        );
        //Erro ao eliminar categoria
        assertEquals(1,rowsAffected);


        Cursor cursor = tableTipoDespesa.query(BdTableTipoDespesa.ALL_COLUMNS, null, null, null, null, null);
        //Categorias encontradas depois de eliminadas...
        assertEquals(0,cursor.getCount());
    }


    @Test
    public void TipoReceitaCRUDTest(){
        //Abrir BD
        BdFinaceOpenHelper openHelper = new BdFinaceOpenHelper(getAppContext());

        //Op. escrita
        SQLiteDatabase db = openHelper.getWritableDatabase();
        BdTableTipoReceita tableTipoReceita = new BdTableTipoReceita(db);

        TipoReceita tipoReceita = new TipoReceita();
        tipoReceita.setCategoria("Vencimento");

        //Insert/Create (C)RUD
        long id = tableTipoReceita.insert(BdTableTipoReceita.getContentValues(tipoReceita));
        assertNotEquals(-1,id);

    }

    private Orcamento PrimeiroOrcamento(BdTableOrcamento tableOrcamento, double expectedValue, long expectedId) {
        Cursor cursor = tableOrcamento.query(BdTableOrcamento.ALL_COLUMNS, null, null, null, null, null);
        //Verificar se está a ler orçamento
        assertEquals(1,cursor.getCount()); //caso não devolva 1 linha, dá erro

        //Obter o primeiro registo de orçamento
        //Erro ao ler o primeiro registo de orçamento
        assertTrue(cursor.moveToNext());

        Orcamento orcamento = BdTableOrcamento.getCurrentOrcamentoFromCursor(cursor);
        //verficra se o Valor do orçamento incorreto
        assertEquals(expectedValue, orcamento.getValor(), 0.001);
        //Verfificar se Id do orçamento está incorreto
        assertEquals(expectedId, orcamento.getId_orcamento());

        return orcamento;
    }

    private TipoDespesa PrimeiroTipoDespesa(BdTableTipoDespesa tableTipoDespesa, String expectedName, long expectedId) {
        Cursor cursor = tableTipoDespesa.query(BdTableTipoDespesa.ALL_COLUMNS, null, null, null, null, null);
        //Erro ao ler tipo receita
        assertEquals(1,cursor.getCount()); //caso não devolva 1 linha, dá erro

        //Obter a primeira categoria de receitas
        //Erro ao ler a categoria da receita
        assertTrue(cursor.moveToNext());

        TipoDespesa tipoDespesa = BdTableTipoDespesa.getCurrentTipoDespesaFromCursor(cursor);
        //Nome da categoria incorreta
        assertEquals(expectedName, tipoDespesa.getCategoria());
        //Id da categoria incorreto
        assertEquals(expectedId,tipoDespesa.getId_despesa());

        return tipoDespesa;
    }
}

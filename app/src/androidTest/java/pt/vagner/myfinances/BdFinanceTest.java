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
        orcamento.setValor(60.0);


        long id = tableOrcamento.insert(BdTableOrcamento.getContentValues(orcamento));
        assertNotEquals(-1,id);

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
        assertNotEquals(-1,id);
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
}

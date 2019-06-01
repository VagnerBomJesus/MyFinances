package pt.vagner.myfinances;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class BdFinanceTest {
    @Before
    public void apagaBaseDados() {
        getAppContext().deleteDatabase(BdFinancesOpenHelper.NOME_BASE_DADOS);
    }

    @Test
    public void criaBdFinance() {
        // Context of the app under test.
        Context appContext = getAppContext();

        BdFinancesOpenHelper openHelper = new BdFinancesOpenHelper(appContext);

        SQLiteDatabase db = openHelper.getReadableDatabase();

        assertTrue(db.isOpen());
    }

    private Context getAppContext() {
        return InstrumentationRegistry.getTargetContext();
    }

    @Test
    public void BdTipoDespesatest() {
        BdFinancesOpenHelper openHelper = new BdFinancesOpenHelper(getAppContext());
        SQLiteDatabase db = openHelper.getWritableDatabase();

        BdTableCategorias tabelaCategorias = new BdTableCategorias(db);

        // Teste read categorias (cRud)
        Cursor cursorCategorias = getCategorias(tabelaCategorias);
        assertEquals(0, cursorCategorias.getCount());

        // Teste create/read categorias (CRud)
        String nome = "Alimentação";
        long idAlimentacao = criaCategoria(tabelaCategorias, nome);

        cursorCategorias = getCategorias(tabelaCategorias);
        assertEquals(1, cursorCategorias.getCount());

        Categoria categoria = getCategoriaComID(cursorCategorias, idAlimentacao);

        assertEquals(nome, categoria.getDescricao());

        nome = "Saúde";
        long idSaude = criaCategoria(tabelaCategorias, nome);

        cursorCategorias = getCategorias(tabelaCategorias);
        assertEquals(2, cursorCategorias.getCount());

        categoria = getCategoriaComID(cursorCategorias, idSaude);

        assertEquals(nome, categoria.getDescricao());

        // Teste Update/Read categorias (cRUd)
        nome = "Inposto / Faturas";
        categoria.setDescricao(nome);

        int registosAlterados = tabelaCategorias.update(categoria.getContentValues(), BdTableCategorias._ID + "=?", new String[]{String.valueOf(idSaude)});

        assertEquals(1, registosAlterados);

        cursorCategorias = getCategorias(tabelaCategorias);
        categoria = getCategoriaComID(cursorCategorias, idSaude);

        assertEquals(nome, categoria.getDescricao());

        // Teste Create/Delete/Read categorias (CRuD)
        long id = criaCategoria(tabelaCategorias, "TESTE");
        cursorCategorias = getCategorias(tabelaCategorias);
        assertEquals(3, cursorCategorias.getCount());

        tabelaCategorias.delete(BdTableCategorias._ID + "=?", new String[]{String.valueOf(id)});
        cursorCategorias = getCategorias(tabelaCategorias);
        assertEquals(2, cursorCategorias.getCount());

        getCategoriaComID(cursorCategorias, idAlimentacao);
        getCategoriaComID(cursorCategorias, idSaude);

        BdTabelaTipoDespesa tabelaTipoDespesa = new BdTabelaTipoDespesa(db);

        // Teste read Despesa (cRud)
        Cursor cursorTipoDespesa = getTipoDspesa(tabelaTipoDespesa);
        assertEquals(0, cursorTipoDespesa.getCount());

        // Teste create/read categorias (CRud)
        String descricaoDespesa = "Arroz";
        double valor = 199;

        id = criaTipoDespesa(tabelaTipoDespesa, descricaoDespesa, valor, idAlimentacao);
        cursorTipoDespesa = getTipoDspesa(tabelaTipoDespesa);
        assertEquals(1, cursorTipoDespesa.getCount());

        TipoDespesa tipoDespesa = getTipoDespesaComID(cursorTipoDespesa, id);
        assertEquals(descricaoDespesa, tipoDespesa.getDescricaoDespesa());
        assertEquals(valor, tipoDespesa.getValor());
        assertEquals(idAlimentacao, tipoDespesa.getCategoria());

        descricaoDespesa = "Galpe";
        valor = 1009.9;
        id = criaTipoDespesa(tabelaTipoDespesa, descricaoDespesa, valor, idSaude);
        cursorTipoDespesa = getTipoDspesa(tabelaTipoDespesa);
        assertEquals(2, cursorTipoDespesa.getCount());

        tipoDespesa = getTipoDespesaComID(cursorTipoDespesa, id);
        assertEquals(descricaoDespesa, tipoDespesa.getDescricaoDespesa());
        assertEquals(valor, tipoDespesa.getValor());
        assertEquals(idSaude, tipoDespesa.getCategoria());

        id = criaTipoDespesa(tabelaTipoDespesa, "Teste", 1, idAlimentacao);
        cursorTipoDespesa = getTipoDspesa(tabelaTipoDespesa);
        assertEquals(3, cursorTipoDespesa.getCount());

        // Teste read/update Despesa (cRUd)
        tipoDespesa = getTipoDespesaComID(cursorTipoDespesa, id);
        descricaoDespesa = "smas";
        valor = 700.9;

        tipoDespesa.setDescricaoDespesa(descricaoDespesa);
        tipoDespesa.setValor(valor);
        tipoDespesa.setCategoria(idSaude);

        tabelaTipoDespesa.update(tipoDespesa.getContentValues(), BdTabelaTipoDespesa._ID + "=?", new String[]{String.valueOf(id)});

        cursorTipoDespesa = getTipoDspesa(tabelaTipoDespesa);

        tipoDespesa = getTipoDespesaComID(cursorTipoDespesa, id);
        assertEquals(descricaoDespesa, tipoDespesa.getDescricaoDespesa());
        assertEquals(valor, tipoDespesa.getValor());
        assertEquals(idSaude, tipoDespesa.getCategoria());

        // Teste read/delete Despesa (cRuD)
        tabelaTipoDespesa.delete(BdTabelaTipoDespesa._ID + "=?", new String[]{String.valueOf(id)});
        cursorTipoDespesa = getTipoDspesa(tabelaTipoDespesa);
        assertEquals(2, cursorTipoDespesa.getCount());
    }

    private long criaCategoria(BdTableCategorias tabelaCategorias, String nome) {
        Categoria categoria = new Categoria();
        categoria.setDescricao(nome);

        long id = tabelaCategorias.insert(categoria.getContentValues());
        assertNotEquals(-1, id);

        return id;
    }

    private Cursor getCategorias(BdTableCategorias tabelaCategorias) {
        return tabelaCategorias.query(BdTableCategorias.TODAS_COLUNAS, null, null, null, null, null);
    }

    private Categoria getCategoriaComID(Cursor cursor, long id) {
        Categoria categoria = null;

        while (cursor.moveToNext()) {
            categoria = Categoria.fromCursor(cursor);

            if (categoria.getId() == id) {
                break;
            }
        }

        assertNotNull(categoria);

        return categoria;
    }

    private long criaTipoDespesa(BdTabelaTipoDespesa tabelaTipoDespesa, String descricaoDespesa, double valor, long categoria) {
        TipoDespesa tipoDespesa = new TipoDespesa();

        tipoDespesa.setDescricaoDespesa(descricaoDespesa);
        tipoDespesa.setValor(valor);
        tipoDespesa.setCategoria(categoria);

        long id = tabelaTipoDespesa.insert(tipoDespesa.getContentValues());
        assertNotEquals(-1, id);

        return id;
    }

    private Cursor getTipoDspesa(BdTabelaTipoDespesa tabelaTipoDespesa) {
        return tabelaTipoDespesa.query(BdTabelaTipoDespesa.TODAS_COLUNAS, null, null, null, null, null);
    }

    private TipoDespesa getTipoDespesaComID(Cursor cursor, long id) {
        TipoDespesa tipoDespesa = null;

        while (cursor.moveToNext()) {
            tipoDespesa = TipoDespesa.fromCursor(cursor);

            if (tipoDespesa.getId() == id) {
                break;
            }
        }

        assertNotNull(tipoDespesa);

        return tipoDespesa;
    }
}
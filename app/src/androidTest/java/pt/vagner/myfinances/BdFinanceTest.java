package pt.vagner.myfinances;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

import static junit.framework.TestCase.assertEquals;

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

        BdTabelaCategoria tabelaCategorias = new BdTabelaCategoria(db);

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

        int registosAlterados = tabelaCategorias.update(categoria.getContentValues(), BdTabelaCategoria._ID + "=?", new String[]{String.valueOf(idSaude)});

        assertEquals(1, registosAlterados);

        cursorCategorias = getCategorias(tabelaCategorias);
        categoria = getCategoriaComID(cursorCategorias, idSaude);

        assertEquals(nome, categoria.getDescricao());

        // Teste Create/Delete/Read categorias (CRuD)
        long id = criaCategoria(tabelaCategorias, "TESTE");
        cursorCategorias = getCategorias(tabelaCategorias);
        assertEquals(3, cursorCategorias.getCount());

        tabelaCategorias.delete(BdTabelaCategoria._ID + "=?", new String[]{String.valueOf(id)});
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
        valor = 1009;
        id = criaTipoDespesa(tabelaTipoDespesa, descricaoDespesa, valor, idSaude);
        cursorTipoDespesa = getTipoDspesa(tabelaTipoDespesa);
        assertEquals(2, cursorTipoDespesa.getCount());

        tipoDespesa = getTipoDespesaComID(cursorTipoDespesa, id);
        assertEquals(descricaoDespesa, tipoDespesa.getDescricaoDespesa());
        //assertEquals(valor, tipoDespesa.getValor());
        assertEquals(idSaude, tipoDespesa.getCategoria());

        id = criaTipoDespesa(tabelaTipoDespesa, "Teste", 1, idAlimentacao);
        cursorTipoDespesa = getTipoDspesa(tabelaTipoDespesa);
        assertEquals(3, cursorTipoDespesa.getCount());

        // Teste read/update Despesa (cRUd)
        tipoDespesa = getTipoDespesaComID(cursorTipoDespesa, id);
        descricaoDespesa = "smas";
        valor = 700;

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
    /************************************************************************/
    @Test
    public void BdTipoReceitaTest() {
        BdFinancesOpenHelper openHelper = new BdFinancesOpenHelper(getAppContext());
        SQLiteDatabase db = openHelper.getWritableDatabase();

        BdTabelaCategoria tabelaCategorias = new BdTabelaCategoria(db);

        // Teste read categorias (cRud)
        Cursor cursorCategorias = getCategorias(tabelaCategorias);
        Assert.assertEquals(0, cursorCategorias.getCount());

        // Teste create/read categorias (CRud)
        String nome = "Depósito";
        long idDeposito = criaCategoria(tabelaCategorias, nome);

        cursorCategorias = getCategorias(tabelaCategorias);
        Assert.assertEquals(1, cursorCategorias.getCount());

        Categoria categoria = getCategoriaComID(cursorCategorias, idDeposito);

        Assert.assertEquals(nome, categoria.getDescricao());

        nome = "Economias";
        long idEconomias = criaCategoria(tabelaCategorias, nome);

        cursorCategorias = getCategorias(tabelaCategorias);
        Assert.assertEquals(2, cursorCategorias.getCount());

        categoria = getCategoriaComID(cursorCategorias, idEconomias);

        Assert.assertEquals(nome, categoria.getDescricao());

        // Teste Update/Read categorias (cRUd)
        nome = "Transporte";
        categoria.setDescricao(nome);

        int registosAlterados = tabelaCategorias.update(categoria.getContentValues(), BdTabelaCategoria._ID + "=?", new String[]{String.valueOf(idEconomias)});

        Assert.assertEquals(1, registosAlterados);

        cursorCategorias = getCategorias(tabelaCategorias);
        categoria = getCategoriaComID(cursorCategorias, idEconomias);

        Assert.assertEquals(nome, categoria.getDescricao());

        // Teste Create/Delete/Read categorias (CRuD)
        long id = criaCategoria(tabelaCategorias, "TESTE");
        cursorCategorias = getCategorias(tabelaCategorias);
        Assert.assertEquals(3, cursorCategorias.getCount());

        tabelaCategorias.delete(BdTabelaCategoria._ID + "=?", new String[]{String.valueOf(id)});
        cursorCategorias = getCategorias(tabelaCategorias);
        Assert.assertEquals(2, cursorCategorias.getCount());

        getCategoriaComID(cursorCategorias, idDeposito);
        getCategoriaComID(cursorCategorias, idEconomias);

        BdTabelaTipoReceita bdTabelaTipoReceita = new BdTabelaTipoReceita(db);

        // Teste read Receita (cRud)
        Cursor cursorTipoReceita = getTipoReceita(bdTabelaTipoReceita);
        Assert.assertEquals(0, cursorTipoReceita.getCount());

        // Teste create/read categorias (CRud)
        String descricaoReceita = "Manutenção";
        double valor = 23.9;

        id = criaTipoReceita(bdTabelaTipoReceita, descricaoReceita, valor, idDeposito);
        cursorTipoReceita = getTipoReceita(bdTabelaTipoReceita);
        Assert.assertEquals(1, cursorTipoReceita.getCount());

        TipoReceita tipoReceita = getTipoReceitaComID(cursorTipoReceita, id);
        Assert.assertEquals(descricaoReceita, tipoReceita.getDescricaoReceita());
        Assert.assertEquals(valor, tipoReceita.getValor());
        Assert.assertEquals(idDeposito, tipoReceita.getCategoria());

        descricaoReceita = "Bateria";
        valor = 100.9;
        id = criaTipoReceita(bdTabelaTipoReceita, descricaoReceita, valor, idEconomias);
        cursorTipoReceita = getTipoReceita(bdTabelaTipoReceita);
        Assert.assertEquals(2, cursorTipoReceita.getCount());

        tipoReceita = getTipoReceitaComID(cursorTipoReceita, id);
        Assert.assertEquals(descricaoReceita, tipoReceita.getDescricaoReceita());
        Assert.assertEquals(valor, tipoReceita.getValor());
        Assert.assertEquals(idEconomias, tipoReceita.getCategoria());

        id = criaTipoReceita(bdTabelaTipoReceita, "Teste", 1, idDeposito);
        cursorTipoReceita = getTipoReceita(bdTabelaTipoReceita);
        Assert.assertEquals(3, cursorTipoReceita.getCount());

        // Teste read/update Receita (cRUd)
        tipoReceita = getTipoReceitaComID(cursorTipoReceita, id);
        descricaoReceita = "Pintura";
        valor = 700.9;

        tipoReceita.setDescricaoReceita(descricaoReceita);
        tipoReceita.setValor(valor);
        tipoReceita.setCategoria(idEconomias);

        bdTabelaTipoReceita.update(tipoReceita.getContentValues(), BdTabelaTipoReceita._ID + "=?", new String[]{String.valueOf(id)});

        cursorTipoReceita = getTipoReceita(bdTabelaTipoReceita);

        tipoReceita = getTipoReceitaComID(cursorTipoReceita, id);
        Assert.assertEquals(descricaoReceita, tipoReceita.getDescricaoReceita());
        Assert.assertEquals(valor, tipoReceita.getValor());
        Assert.assertEquals(idEconomias, tipoReceita.getCategoria());

        // Teste read/delete Receita (cRuD)
        bdTabelaTipoReceita.delete(BdTabelaTipoReceita._ID + "=?", new String[]{String.valueOf(id)});
        cursorTipoReceita = getTipoReceita(bdTabelaTipoReceita);
        Assert.assertEquals(2, cursorTipoReceita.getCount());
    }

    private long criaCategoria(BdTabelaCategoria tabelaCategorias, String nome) {
        Categoria categoria = new Categoria();
        categoria.setDescricao(nome);

        long id = tabelaCategorias.insert(categoria.getContentValues());
        assertNotEquals(-1, id);

        return id;
    }

    private Cursor getCategorias(BdTabelaCategoria tabelaCategorias) {
        return tabelaCategorias.query(BdTabelaCategoria.TODAS_COLUNAS, null, null, null, null, null);
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

    private long criaTipoReceita(BdTabelaTipoReceita tabelaTipoReceita, String descricaoReceita, double valor, long categoria) {
        TipoReceita tipoReceita = new TipoReceita();

        tipoReceita.setDescricaoReceita(descricaoReceita);
        tipoReceita.setValor(valor);
        tipoReceita.setCategoria(categoria);

        long id = tabelaTipoReceita.insert(tipoReceita.getContentValues());
        assertNotEquals(-1, id);

        return id;
    }

    private Cursor getTipoReceita(BdTabelaTipoReceita tabelaTipoReceita) {
        return tabelaTipoReceita.query(BdTabelaTipoReceita.TODAS_COLUNAS, null, null, null, null, null);
    }

    private TipoReceita getTipoReceitaComID(Cursor cursor, long id) {
        TipoReceita tipoReceita = null;

        while (cursor.moveToNext()) {
            tipoReceita = TipoReceita.fromCursor(cursor);

            if (tipoReceita.getId() == id) {
                break;
            }
        }

        assertNotNull(tipoReceita);

        return tipoReceita;
    }
}

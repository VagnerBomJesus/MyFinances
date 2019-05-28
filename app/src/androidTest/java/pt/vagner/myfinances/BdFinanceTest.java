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
        getAppContext().deleteDatabase(BdMyFinanceOpenHelper.NOME_BASE_DADOS);
    }

    @Test
    public void criaBdFinances() {
        // Context of the app under test.
        Context appContext = getAppContext();

        BdMyFinanceOpenHelper openHelper = new BdMyFinanceOpenHelper(appContext);

        SQLiteDatabase db = openHelper.getReadableDatabase();

        assertTrue(db.isOpen());
    }

    private Context getAppContext() {
        return InstrumentationRegistry.getTargetContext();
    }

    @Test
    public void testCRUD() {
        BdMyFinanceOpenHelper openHelper = new BdMyFinanceOpenHelper(getAppContext());
        SQLiteDatabase db = openHelper.getWritableDatabase();

        BdTabelaCategoria tabelaCategorias = new BdTabelaCategoria(db);

        // Teste read categorias (cRud)
        Cursor cursorCategorias = getCategorias(tabelaCategorias);
        assertEquals(0, cursorCategorias.getCount());

        // Teste create/read categorias (CRud)
        String nome = "Vencimento";
        long idVencimento = criaCategoria(tabelaCategorias, nome);

        cursorCategorias = getCategorias(tabelaCategorias);
        assertEquals(1, cursorCategorias.getCount());

        Categoria categoria = getCategoriaComID(cursorCategorias, idVencimento);

        assertEquals(nome, categoria.getDescricao());

        nome = "Depósito";
        long idDeposito = criaCategoria(tabelaCategorias, nome);

        cursorCategorias = getCategorias(tabelaCategorias);
        assertEquals(2, cursorCategorias.getCount());

        categoria = getCategoriaComID(cursorCategorias, idDeposito);

        assertEquals(nome, categoria.getDescricao());

        // Teste Update/Read categorias (cRUd)
        nome = "Economias / Depósito";
        categoria.setDescricao(nome);

        int registosAlterados = tabelaCategorias.update(categoria.getContentValues(), BdTabelaCategoria._ID + "=?", new String[]{String.valueOf(idDeposito)});

        assertEquals(1, registosAlterados);

        cursorCategorias = getCategorias(tabelaCategorias);
        categoria = getCategoriaComID(cursorCategorias, idDeposito);

        assertEquals(nome, categoria.getDescricao());

        // Teste Create/Delete/Read categorias (CRuD)
        long id = criaCategoria(tabelaCategorias, "TESTE");
        cursorCategorias = getCategorias(tabelaCategorias);
        assertEquals(3, cursorCategorias.getCount());

        tabelaCategorias.delete(BdTabelaCategoria._ID + "=?", new String[]{String.valueOf(id)});
        cursorCategorias = getCategorias(tabelaCategorias);
        assertEquals(2, cursorCategorias.getCount());

        getCategoriaComID(cursorCategorias, idVencimento);
        getCategoriaComID(cursorCategorias, idDeposito);

        BdTabelaReceita bdTabelaReceita = new BdTabelaReceita(db);

        // Teste read TipoReceita (cRud)
        Cursor cursorReceita = getReceita(bdTabelaReceita);
        assertEquals(0, cursorReceita.getCount());

        // Teste create/read categorias (CRud)
        String descricaoReceita = "Meus Negosios";
        int valor = 203;

        id = criaReceita(bdTabelaReceita, descricaoReceita, valor, idVencimento);
        cursorReceita = getReceita(bdTabelaReceita);
        assertEquals(1, cursorReceita.getCount());

        TipoReceita tipoReceita = getTipoReceitaComID(cursorReceita, id);
        assertEquals(descricaoReceita, tipoReceita.getDescricaoReceita());
        assertEquals(valor, tipoReceita.getValor());
        assertEquals(idVencimento, tipoReceita.getCategoria());

        descricaoReceita = "Ofertas";
        valor = 100;
        id = criaReceita(bdTabelaReceita, descricaoReceita, valor, idDeposito);
        cursorReceita = getReceita(bdTabelaReceita);
        assertEquals(2, cursorReceita.getCount());

        tipoReceita = getTipoReceitaComID(cursorReceita, id);
        assertEquals(descricaoReceita, tipoReceita.getDescricaoReceita());
        assertEquals(valor, tipoReceita.getValor());
        assertEquals(idDeposito, tipoReceita.getCategoria());

        id = criaReceita(bdTabelaReceita, "Teste", 1, idVencimento);
        cursorReceita = getReceita(bdTabelaReceita);
        assertEquals(3, cursorReceita.getCount());

        // Teste read/update TipoReceita (cRUd)
        tipoReceita = getTipoReceitaComID(cursorReceita, id);
        descricaoReceita = "Doacoes";
        valor = 700;

        tipoReceita.setDescricaoReceita(descricaoReceita);
        tipoReceita.setValor(valor);
        tipoReceita.setCategoria(idDeposito);

        bdTabelaReceita.update(tipoReceita.getContentValues(), BdTabelaReceita._ID + "=?", new String[]{String.valueOf(id)});

        cursorReceita = getReceita(bdTabelaReceita);

        tipoReceita = getTipoReceitaComID(cursorReceita, id);
        assertEquals(descricaoReceita, tipoReceita.getDescricaoReceita());
        assertEquals(valor, tipoReceita.getValor());
        assertEquals(idDeposito, tipoReceita.getCategoria());

        // Teste read/delete TipoReceita (cRuD)
        bdTabelaReceita.delete(BdTabelaReceita._ID + "=?", new String[]{String.valueOf(id)});
        cursorReceita = getReceita(bdTabelaReceita);
        assertEquals(2, cursorReceita.getCount());
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

    private long criaReceita(BdTabelaReceita tabelaReceita, String dedescricaoReceita, int valor, long categoria) {
        TipoReceita tipoReceita = new TipoReceita();

        tipoReceita.setDescricaoReceita(dedescricaoReceita);
        tipoReceita.setValor(valor);
        tipoReceita.setCategoria(categoria);

        long id = tabelaReceita.insert(tipoReceita.getContentValues());
        assertNotEquals(-1, id);

        return id;
    }

    private Cursor getReceita(BdTabelaReceita tabelaReceita) {
        return tabelaReceita.query(BdTabelaReceita.TODAS_COLUNAS, null, null, null, null, null);
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

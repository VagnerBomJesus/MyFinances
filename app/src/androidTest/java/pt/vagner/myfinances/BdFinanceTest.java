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

        SQLiteDatabase db = openHelper.getReadableDatabase(); //Db para leitura

        assertTrue(db.isOpen()); //devolve a mensagem se não conseguir criar/abrir a Base de Dados
       // db.close();
    }

    private Context getAppContext() {
        return InstrumentationRegistry.getTargetContext();
    }


    @Test
    public void OrcamentoCRUDTest() {
        //Abrir BD
        BdFinaceOpenHelper openHelper = new BdFinaceOpenHelper(getContext());
        //Op. escrita
        SQLiteDatabase db = openHelper.getWritableDatabase();

        BdTableOrcamento tableOrcamento = new BdTableOrcamento(db);

        Orcamento orcamento = new Orcamento();
        orcamento.setValor(60.0);

        //Insert/Create (C)RUD
        long id = tableOrcamento.insert(BdTableOrcamento.getContentValues(orcamento));
        assertNotEquals(-1,id); //Se der -1 é porque não foi possível inserir o registo

        /*long id = tableOrcamento.insert(orcamento.getContentValues());
        assertNotEquals(-1, id);*/

    }
}

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
public class BdMyFinanceTest {
    @Before
    public void apagaBaseDados() {
        getAppContext().deleteDatabase(BdMyFinanceOpenHelper.NOME_BASE_DADOS);
    }

    @Test
    public void criarBdFinance() {
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
    public void OrcamentoCRUDTest() {


    }


}
package pt.vagner.myfinances;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import static pt.vagner.myfinances.BdTabelaCategoria.CAMPO_DESCRICAO;

public class EditDespesa extends AppCompatActivity implements DialogFragmentCategoria.ExampleDialogListener, LoaderManager.LoaderCallbacks<Cursor> {

    private static final int ID_CURSO_LOADER_CATEGORIAS = 0;

    private EditText editTextDescricao;
    private Spinner spinnerCategorias;
    private EditText editTextValor;

    private TipoDespesa tipoDespesa = null;

    private boolean categoriasCarregadas = false;
    private boolean categoriaAtualizada = false;

    private Uri enderecoFinanceEditar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_despesas);
       // Toolbar toolbar = findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editTextDescricao = (EditText) findViewById(R.id.editTextDesignacaoDespesa);
        spinnerCategorias = (Spinner) findViewById(R.id.spinnerCategoriaDespesa);
        editTextValor = (EditText) findViewById(R.id.editTextValorDespesa);

        getSupportLoaderManager().initLoader(ID_CURSO_LOADER_CATEGORIAS, null, this);


        Intent intent = getIntent();

        long idFinance = intent.getLongExtra(ListarTodoTipoDespesaActivity.ID_TIPO_DESPESA, -1);

        if (idFinance == -1) {
            Toast.makeText(this, "Erro: não foi possível ler o tipoDespesa", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        enderecoFinanceEditar = Uri.withAppendedPath(FinanceContentProvider.ENDERECO_TIPO_DESPESA, String.valueOf(idFinance));

        Cursor cursor = getContentResolver().query(enderecoFinanceEditar, BdTabelaTipoDespesa.TODAS_COLUNAS, null, null, null);

        if (!cursor.moveToNext()) {
            Toast.makeText(this, "Erro: não foi possível ler o tipoDespesa", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        tipoDespesa = TipoDespesa.fromCursor(cursor);

        editTextDescricao.setText(tipoDespesa.getDescricaoDespesa());
        editTextValor.setText(String.valueOf(tipoDespesa.getValor()));

        actualizaCategoriaSelecionada();
    }
    @Override
    protected void onResume() {
        getSupportLoaderManager().restartLoader(ID_CURSO_LOADER_CATEGORIAS, null, this);

        super.onResume();
    }
    private void actualizaCategoriaSelecionada() {
        if (!categoriasCarregadas) return;
        if (categoriaAtualizada) return;

        for (int i = 0; i < spinnerCategorias.getCount(); i++) {
            if (spinnerCategorias.getItemIdAtPosition(i) == tipoDespesa.getCategoria()) {
                spinnerCategorias.setSelection(i);
                break;
            }
        }

        categoriaAtualizada = true;
    }



    private void mostraCategoriasSpinner(Cursor cursorCategorias) {
        SimpleCursorAdapter adaptadorCategorias = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_1,
                cursorCategorias,
                new String[]{BdTabelaCategoria.CAMPO_DESCRICAO},
                new int[]{android.R.id.text1}
        );
        spinnerCategorias.setAdapter(adaptadorCategorias);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();



        return super.onOptionsItemSelected(item);
    }


    public void cancel(View view){
        finish();
    }
    public void updateDespesaDb(View view) {//button inserir vai a base de dados
        EditText editTextDesignacaoDespesa = (EditText) findViewById(R.id.editTextDesignacaoDespesa);
        String designacao = editTextDesignacaoDespesa.getText().toString();

        EditText editTextValorDespesa = (EditText) findViewById(R.id.editTextValorDespesa);
        String mensagem = editTextValorDespesa.getText().toString();


        if (designacao.trim().length() == 0) {
            editTextDesignacaoDespesa.setError(getString(R.string.erro_editTextDesignacaoReceita));
            editTextDesignacaoDespesa.requestFocus();
            return;
        }
        double valor = 0;

        try {
            valor = Double.parseDouble(editTextValorDespesa.getText().toString());
        } catch (NumberFormatException e) {
            editTextValorDespesa.setError(getString(R.string.erro_smsValor));
            editTextValorDespesa.requestFocus();
            return;
        }

        if (valor == 0) {
            editTextValorDespesa.setError(getString(R.string.insira_um_valor_maior_que_0));
            editTextValorDespesa.requestFocus();
            return;
        }
        long idCategoria = spinnerCategorias.getSelectedItemId();


        // AtualoiazarDespesaDb os dados
        tipoDespesa.setDescricaoDespesa(designacao);
        tipoDespesa.setCategoria(idCategoria);
        tipoDespesa.setValor(valor);

        try {
            getContentResolver().update(enderecoFinanceEditar, tipoDespesa.getContentValues(), null, null);

            Toast.makeText(this, getString(R.string.livro_guardado_sucesso), Toast.LENGTH_LONG).show();
            finish();
        } catch (Exception e) {
            Snackbar.make(
                    editTextDescricao,
                    getString(R.string.erro_guardar_livro),
                    Snackbar.LENGTH_LONG)
                    .show();

            e.printStackTrace();
        }


    }

    /**
     * Instantiate and return a new Loader for the given ID.
     *
     * <p>This will always be called from the process's main thread.
     *
     * @param id   The ID whose loader is to be created.
     * @param args Any arguments supplied by the caller.
     * @return Return a new Loader instance that is ready to start loading.
     */
    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        androidx.loader.content.CursorLoader cursorLoader = new androidx.loader.content.CursorLoader(this, FinanceContentProvider.ENDERECO_CATEGORIAS, BdTabelaCategoria.TODAS_COLUNAS, null, null, BdTabelaCategoria.CAMPO_DESCRICAO
        );

        return cursorLoader;
    }

    /**
     * Called when a previously created loader has finished its load.  Note
     * that normally an application is <em>not</em> allowed to commit fragment
     * transactions while in this call, since it can happen after an
     * activity's state is saved.  See {@link FragmentManager#beginTransaction()
     * FragmentManager.openTransaction()} for further discussion on this.
     *
     * <p>This function is guaranteed to be called prior to the release of
     * the last data that was supplied for this Loader.  At this point
     * you should remove all use of the old data (since it will be released
     * soon), but should not do your own release of the data since its Loader
     * owns it and will take care of that.  The Loader will take care of
     * management of its data so you don't have to.  In particular:
     *
     * <ul>
     * <li> <p>The Loader will monitor for changes to the data, and report
     * them to you through new calls here.  You should not monitor the
     * data yourself.  For example, if the data is a {@link Cursor}
     * and you place it in a {@link CursorAdapter}, use
     * the {@link CursorAdapter#CursorAdapter(Context,
     * Cursor, int)} constructor <em>without</em> passing
     * in either {@link CursorAdapter#FLAG_AUTO_REQUERY}
     * or {@link CursorAdapter#FLAG_REGISTER_CONTENT_OBSERVER}
     * (that is, use 0 for the flags argument).  This prevents the CursorAdapter
     * from doing its own observing of the Cursor, which is not needed since
     * when a change happens you will get a new Cursor throw another call
     * here.
     * <li> The Loader will release the data once it knows the application
     * is no longer using it.  For example, if the data is
     * a {@link Cursor} from a {@link CursorLoader},
     * you should not call close() on it yourself.  If the Cursor is being placed in a
     * {@link CursorAdapter}, you should use the
     * {@link CursorAdapter#swapCursor(Cursor)}
     * method so that the old Cursor is not closed.
     * </ul>
     *
     * <p>This will always be called from the process's main thread.
     *
     * @param loader The Loader that has finished.
     * @param data   The data generated by the Loader.
     */
    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        mostraCategoriasSpinner(data);
        categoriasCarregadas = true;
        actualizaCategoriaSelecionada();
    }

    /**
     * Called when a previously created loader is being reset, and thus
     * making its data unavailable.  The application should at this point
     * remove any references it has to the Loader's data.
     *
     * <p>This will always be called from the process's main thread.
     *
     * @param loader The Loader that is being reset.
     */
    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        categoriasCarregadas = false;
        categoriaAtualizada = false;
        mostraCategoriasSpinner(null);
    }

    public void addCategoriaEditDespesa(View view) {
        DialogFragmentCategoria dialogFragmentCategoria = new DialogFragmentCategoria();
        dialogFragmentCategoria.show(getSupportFragmentManager(), "DialogFragmentDespesas");
    }

    @Override
    public void setTexts(String categoria) {

        try {
            if (checkCategoriaDespesa(categoria) != -1){ //Se devolver um id != -1 é porque já exite uma categoria com o nome que vamos inserir
                Toast.makeText(EditDespesa.this,R.string.cate_ja_exist,Toast.LENGTH_LONG).show();
                return;
            }
            insertCategoria(categoria);
            Toast.makeText(EditDespesa.this,R.string.sms_cat_inserida_success,Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(EditDespesa.this,R.string.sms_error_inserir_cat_db,Toast.LENGTH_LONG).show();
        }
        atualizarSpinner(); //Atualizar spinner

    }
    private void insertCategoria(String categoria) {
        //Abrir BD
        Categoria addcategoria = new Categoria();
        addcategoria.setDescricao(categoria);


        try {
            getContentResolver().insert(FinanceContentProvider.ENDERECO_CATEGORIAS, addcategoria.getContentValues());
        } catch (Exception e) {
            Snackbar.make(
                    editTextDescricao,
                    getString(R.string.erro_inserir_registo_bd),
                    Snackbar.LENGTH_LONG)
                    .show();

            e.printStackTrace();
        }
    }
    public int checkCategoriaDespesa(String categoria){
        //Abrir a BD
        BdFinancesOpenHelper OpenHelper = new BdFinancesOpenHelper(getApplicationContext());
        //Leitura
        SQLiteDatabase db = OpenHelper.getReadableDatabase();

        String query = "SELECT "+ BdTabelaCategoria._ID+" FROM "+ BdTabelaCategoria.NOME_TABELA+" WHERE "+ CAMPO_DESCRICAO+" =?";
        Cursor cursor = db.rawQuery(query,new String[]{categoria});

        int id = -1;

        if (cursor.getCount() > 0){
            cursor.moveToFirst();
            id = cursor.getInt(cursor.getColumnIndex(BdTabelaCategoria._ID));
        }

        cursor.close();
        db.close();
        return id;
    }

    private void atualizarSpinner(){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getCategoriasFrom());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategorias.setAdapter(adapter);
    }
    private ArrayList<String> getCategoriasFrom(){
        //Abrir BD
        BdFinancesOpenHelper OpenHelper = new BdFinancesOpenHelper(getApplicationContext());
        //Leitura
        SQLiteDatabase db = OpenHelper.getReadableDatabase();

        BdTabelaCategoria bdTabelaCategoria = new BdTabelaCategoria(db);

        Cursor cursor = bdTabelaCategoria.query(BdTabelaCategoria.COLUNAS, null, null, null, null, BdTabelaCategoria._ID);

        ArrayList<String> list = NovaDespesa.getCategoriasFrom(cursor);

        cursor.close();
        db.close();
        return list;
    }
    public static ArrayList<String> getCategoriasFrom(Cursor cursor){ //list catte
        final int posCatDes = cursor.getColumnIndex(CAMPO_DESCRICAO);

        ArrayList<String> list = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getString(posCatDes));
            }while (cursor.moveToNext());
        }

        return list;
    }
}

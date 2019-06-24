package pt.vagner.myfinances;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import static pt.vagner.myfinances.BdTabelaCategoria.CAMPO_DESCRICAO;


public class GerirCategorias extends AppCompatActivity implements DialogFragmentCategoria.ExampleDialogListener, LoaderManager.LoaderCallbacks<Cursor> {


    private static final int ID_CURSO_LOADER_CATEGORIA = 0;
    public static final String ID_CATEGORIA = "ID_CATEGORIA";

    private RecyclerView recyclerViewCategoria;
    private AdaptadorFinanceCategoria adaptadorFinanceCategoria;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gerir_categorias);



        getSupportLoaderManager().initLoader(ID_CURSO_LOADER_CATEGORIA, null, this);

        recyclerViewCategoria = (RecyclerView) findViewById(R.id.recyclerViewCategoria);
        adaptadorFinanceCategoria = new AdaptadorFinanceCategoria(this);
        recyclerViewCategoria.setAdapter(adaptadorFinanceCategoria);
        recyclerViewCategoria.setLayoutManager(new LinearLayoutManager(this));

    }
    @Override
    protected void onResume() {
        getSupportLoaderManager().restartLoader(ID_CURSO_LOADER_CATEGORIA, null, this);

        super.onResume();
    }
    public void atualizaOpcoesMenu() {
        Categoria categoria = adaptadorFinanceCategoria.getCategoriaSelecionado();

        boolean mostraAlterarEliminar = (categoria != null);



        menu.findItem(R.id.action_Eliminar).setVisible(mostraAlterarEliminar);
    }
    private Menu menu;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_categira, menu);

        this.menu = menu;

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if(id == R.id.action_Eliminar){
            Intent i = new Intent(this, EliminarCategoriaActivity.class);
           i.putExtra(ID_CATEGORIA, adaptadorFinanceCategoria.getCategoriaSelecionado().getId());
            startActivity(i);
            return true;
        }
        if(id == R.id.action_InserirCAtegoria){
            DialogFragmentCategoria dialogFragmentCategoria = new DialogFragmentCategoria();
            dialogFragmentCategoria.show(getSupportFragmentManager(), "DialogFragmentCategoria");
        }

        return super.onOptionsItemSelected(item);
    }


    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        CursorLoader cursorLoader = new CursorLoader(this, FinanceContentProvider.ENDERECO_CATEGORIAS, BdTabelaCategoria.TODAS_COLUNAS, null, null, BdTabelaCategoria.CAMPO_DESCRICAO
        );

        return cursorLoader;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        adaptadorFinanceCategoria.setCursor(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        adaptadorFinanceCategoria.setCursor(null);
    }

    private void insertCategoria(String categoria) {
        //Abrir BD
        Categoria addcategoria = new Categoria();
        addcategoria.setDescricao(categoria);


        try {
            getContentResolver().insert(FinanceContentProvider.ENDERECO_CATEGORIAS, addcategoria.getContentValues());
            addcategoria.setDescricao(categoria);
        } catch (Exception e) {
            Snackbar.make(
                    recyclerViewCategoria,
                    getString(R.string.erro_inserir_registo_bd),
                    Snackbar.LENGTH_LONG)
                    .show();

            e.printStackTrace();
        }
    }
    @Override
    public void setTexts(String categoria) { //Ação do botão "addCategoriaDespesa"

        try {
            if (checkCategoria(categoria) != -1){ //Se devolver um id != -1 é porque já exite uma categoria com o nome que vamos inserir
                Toast.makeText(GerirCategorias.this,R.string.cate_ja_exist,Toast.LENGTH_LONG).show();
                return;
            }
            insertCategoria(categoria);
            goToMainActivity();
            Toast.makeText(GerirCategorias.this,R.string.sms_cat_inserida_success,Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(GerirCategorias.this,R.string.sms_error_inserir_cat_db,Toast.LENGTH_LONG).show();
        }


    }
    public int checkCategoria(String categoria){
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
    private void goToMainActivity(){
        Intent i = new Intent(this, GerirCategorias.class);
        startActivity(i);
    }

}

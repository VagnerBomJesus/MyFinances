package pt.vagner.myfinances;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuItem;

public class ListarTodoTipoReceitaActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

        private static final int ID_CURSOR_LOADER_RECEITA = 0;
        public static  final String ID_RECEITA = "ID_RECEITA";

        private RecyclerView recyclerViewReceita;
        private AdaptadorFinanceTipoReceita adaptadorFinanceTipoReceita;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_listar_todo_tipo_receita);
            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            getSupportLoaderManager().initLoader(ID_CURSOR_LOADER_RECEITA,null, this);

            recyclerViewReceita = (RecyclerView) findViewById(R.id.recyclerViewReceita);
            adaptadorFinanceTipoReceita = new AdaptadorFinanceTipoReceita(this);
            recyclerViewReceita.setAdapter(adaptadorFinanceTipoReceita);
            recyclerViewReceita.setLayoutManager(new LinearLayoutManager(this));

        }
        @Override
        protected void onResume(){
            getSupportLoaderManager().initLoader(ID_CURSOR_LOADER_RECEITA,null, this);
            super.onResume();
        }

    private Menu menu;

    public void atualizaOpcoesMenu() {
        TipoReceita tipoReceita = adaptadorFinanceTipoReceita.getReceitaSelecionado();

        boolean mostraAlterarEliminar = (tipoReceita != null);


        menu.findItem(R.id.action_EditarReceita).setVisible(mostraAlterarEliminar);
        menu.findItem(R.id.action_Eliminar).setVisible(mostraAlterarEliminar);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_receita, menu);

        this.menu = menu;

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_IserirReceita){
            Intent i = new Intent (this, NovaReceita.class);
            startActivity(i);
            return true;
        }
        if (id == R.id.action_EditarReceita) {//com sua ação Editar
            Intent i = new Intent(this, EditReceita.class);
            i.putExtra(ID_RECEITA, adaptadorFinanceTipoReceita.getReceitaSelecionado().getId());
            startActivity(i);
            return true;
        }
        if(id == R.id.action_Eliminar){
            Intent i = new Intent(this, EliminarReceita.class);
            i.putExtra(ID_RECEITA, adaptadorFinanceTipoReceita.getReceitaSelecionado().getId());
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


        @NonNull
        @Override
        public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
            CursorLoader cursorLoader = new CursorLoader(this, FinanceContentProvider.ENDERECO_TIPO_RECEITA, BdTabelaTipoReceita.TODAS_COLUNAS, null, null, BdTabelaTipoReceita.CAMPO_DESCRIACO_RECEITA
            );

            return cursorLoader;
        }

        @Override
        public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
            adaptadorFinanceTipoReceita.setCursor(data);
        }

        @Override
        public void onLoaderReset(@NonNull Loader<Cursor> loader) {
            adaptadorFinanceTipoReceita.setCursor(null);
        }
    }

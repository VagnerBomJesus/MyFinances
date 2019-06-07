package pt.vagner.myfinances;


import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class GerirCategorias extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {


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
}

package pt.vagner.myfinances;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;
import java.util.Date;

import static pt.vagner.myfinances.BdTabelaCategoria.CAMPO_DESCRICAO;

public class NovaReceita extends AppCompatActivity implements DatePickerDialog.OnDateSetListener,DialogFragmentCategoria.ExampleDialogListener, LoaderManager.LoaderCallbacks<Cursor>{

    private MenuItem item;
    private TextView textViewSelectedDate;
    private TextView textViewData;
    private static  final int ID_CURSO_LOADER_CATEGORIAS = 0;

    private EditText editTextDesignacaoReceita;
    private Spinner spinnerCategoriaRceita;
    private EditText editTextValorReceita;
    private EditText textViewSelectedDateDespesa;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_receita);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        /************************Construção dos objetos***************************/

        getSupportLoaderManager().initLoader(ID_CURSO_LOADER_CATEGORIAS, null, this);


        /************************Construção dos objetos***************************/
        editTextDesignacaoReceita = (EditText) findViewById(R.id.editTextDesignacaoReceita);
        spinnerCategoriaRceita = (Spinner) findViewById(R.id.spinnerCategoriaReceita);
        editTextValorReceita = (EditText) findViewById(R.id.editTextValorReceita);

        setDefaultDateToTextView(); ///visualizar data atual
    }

    @Override
    protected void onResume() {
        getSupportLoaderManager().restartLoader(ID_CURSO_LOADER_CATEGORIAS, null, this);

        super.onResume();
    }
    private void mostraCategoriasSpinner(Cursor cursorCategorias) {
        SimpleCursorAdapter adaptadorCategorias = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_1,
                cursorCategorias,
                new String[]{CAMPO_DESCRICAO},
                new int[]{android.R.id.text1}
        );
        spinnerCategoriaRceita.setAdapter(adaptadorCategorias);
    }
    public void inserirReceitaDb(View view) {
        EditText editTextValorReceita = (EditText) findViewById(R.id.editTextValorReceita);
        String mensagem = editTextValorReceita.getText().toString();

        EditText editTextDesignacaoReceita = (EditText) findViewById(R.id.editTextDesignacaoReceita);
        String designacao = editTextDesignacaoReceita.getText().toString();


        if (designacao.trim().length() == 0) {
            editTextDesignacaoReceita.setError(getString(R.string.erro_editTextDesignacaoReceita));
            editTextDesignacaoReceita.requestFocus();
            return;
        }
        double valor = 0;

        try {
            valor = Double.parseDouble(editTextValorReceita.getText().toString());
        } catch (NumberFormatException e) {
            editTextValorReceita.setError(getString(R.string.erro_smsValor));
            editTextValorReceita.requestFocus();
            return;
        }

        if (valor == 0) {
            editTextValorReceita.setError(getString(R.string.insira_um_valor_maior_que_0));
            editTextValorReceita.requestFocus();
            return;
        }
        Date data = new Date();
        long idCategoria = spinnerCategoriaRceita.getSelectedItemId();


       /* Intent intent = new Intent(this, ListarTodoTipoDespesaActivity.class);

        intent.putExtra(DefinicoesApp.Designacao, designacao);

        startActivity(intent);

        intent.putExtra(DefinicoesApp.MENSAGEM, mensagem);
        intent.putExtra(DefinicoesApp.DATA, data);

        startActivity(intent);*/

        TipoReceita tipoReceita = new TipoReceita();
        tipoReceita.setDescricaoReceita(designacao);
        tipoReceita.setCategoria(idCategoria);
        tipoReceita.setValor((int) valor);

        try {
            getContentResolver().insert(FinanceContentProvider.ENDERECO_TIPO_RECEITA, tipoReceita.getContentValues());

            Toast.makeText(this, getString(R.string.registo_inserido_success), Toast.LENGTH_SHORT).show();
            finish();
        } catch (Exception e) {
            Snackbar.make(
                    editTextDesignacaoReceita,
                    getString(R.string.erro_inserir_registo_bd),
                    Snackbar.LENGTH_LONG)
                    .show();

            e.printStackTrace();
        }
        Intent intent = new Intent(this, ListarTodoTipoReceitaActivity.class);

        intent.putExtra(DefinicoesApp.Designacao, designacao);

        intent.putExtra(DefinicoesApp.MENSAGEM, mensagem);

        startActivity(intent);
    }

    /**
     * @return dia atual
     */
    private int getCurrentDay(){
        Calendar c = Calendar.getInstance();
        int dia = c.get(Calendar.DAY_OF_MONTH);
        return dia;
    }

    /**
     * @return mes atual
     */
    private int getCurrentMonth(){
        Calendar c = Calendar.getInstance();
        int month = c.get(Calendar.MONTH)+1;
        return month;
    }

    /**
     * @return ano atual
     */
    private int getCurrentYear(){
        Calendar c = Calendar.getInstance();
        int ano = c.get(Calendar.YEAR);
        return ano;
    }
    /**
     * coloca na textview da data a data atual dd/mm/yyyy
     */
    private void setDefaultDateToTextView(){
        TextView textViewDate = (TextView) findViewById(R.id.textViewSelectedDate);

        int dia = getCurrentDay();
        int mes = getCurrentMonth();
        int ano = getCurrentYear();

        textViewDate.setText(""+dia+"/"+mes+"/"+ano);
    }

    public void cancel(View view) { //Botão "cancelar"
        finish();
    }



    public void definirDataReceita(View view) { //Botão "definir data"
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        //Código que obtém a data selecionada pelo utilizador
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        month++;

        textViewSelectedDate.setText("" + dayOfMonth + "/" + month + "/" + year);
        //isClicked = true;

    }
    public void addCategoria(View view) { //Botão "+"
        DialogFragmentCategoria dialogFragmentCategoria = new DialogFragmentCategoria();
        dialogFragmentCategoria.show(getSupportFragmentManager(), "DialogFragmentCategoria");
    }

    @Override
    public void setTexts(String categoria) { //Ação do botão Inserir Categoria

        try {

            Toast.makeText(NovaReceita.this, R.string.sms_cat_inserida_success,Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(NovaReceita.this, R.string.sms_error_inserir_cat_db,Toast.LENGTH_LONG).show();
        }


    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        androidx.loader.content.CursorLoader cursorLoader = new androidx.loader.content.CursorLoader(this, FinanceContentProvider.ENDERECO_CATEGORIAS, BdTabelaCategoria.TODAS_COLUNAS, null, null, CAMPO_DESCRICAO
        );

        return cursorLoader;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        mostraCategoriasSpinner(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        mostraCategoriasSpinner(null);
    }
}

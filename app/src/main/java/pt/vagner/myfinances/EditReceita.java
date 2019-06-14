package pt.vagner.myfinances;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cursoradapter.widget.SimpleCursorAdapter;
import androidx.fragment.app.DialogFragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;
import java.util.Date;

public class EditReceita extends AppCompatActivity implements DatePickerDialog.OnDateSetListener,DialogFragmentCategoria.ExampleDialogListener , LoaderManager.LoaderCallbacks<Cursor> {

    private EditText editTextValorReceita;
    private Spinner spinnerCategoria;
    private EditText editTextDescricao;
    private TextView textViewSelectedDate;
    private TextView textViewData;

    private static final int CATEGORIES_CURSOR_LOADER_ID = 0;
    private TipoReceita tipoReceita = null;

    private boolean categoriasCarregadas = false;
    private boolean categoriaAtualizada = false;

    private Uri enderecoReceitaEditar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_receita);


        editTextDescricao = (EditText) findViewById(R.id.editTextDesignacaoReceita);
        spinnerCategoria = (Spinner) findViewById(R.id.spinnerCategoria);
        editTextValorReceita = (EditText) findViewById(R.id.editTextValorReceita);
       // textViewSelectedDate = (TextView) findViewById(R.id.textViewSelectedDate);
        //textViewData= (TextView) findViewById(R.id.textViewData);

        getSupportLoaderManager().initLoader(CATEGORIES_CURSOR_LOADER_ID, null, this);

        Intent intent = getIntent();

        long idReceita = intent.getLongExtra(ListarTodoTipoReceitaActivity.ID_RECEITA, -1);

        if(idReceita == -1){
            Toast.makeText(this, "Erro: não foi possiveli ler o registo", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        enderecoReceitaEditar = Uri.withAppendedPath(FinanceContentProvider.ENDERECO_TIPO_RECEITA, String.valueOf(idReceita));

        Cursor cursor = getContentResolver().query(enderecoReceitaEditar, BdTabelaTipoReceita.TODAS_COLUNAS, null, null, null);
        if (!cursor.moveToNext()){
            Toast.makeText(this, "Erro: não foi possivel ler o registo", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        tipoReceita = TipoReceita.fromCursor(cursor);
        editTextDescricao.setText(tipoReceita.getDescricaoReceita());
        editTextValorReceita.setText(String.valueOf(tipoReceita.getValor()));



        actualizaCategoriaSelecionada();
       // setDefaultDateToTextView();///visualizar data atual
    }

    private void actualizaCategoriaSelecionada() {
        if (!categoriasCarregadas) return;
        if (categoriaAtualizada) return;

        for (int i = 0; i < spinnerCategoria.getCount(); i++) {
            if (spinnerCategoria.getItemIdAtPosition(i) == tipoReceita.getCategoria()) {
                spinnerCategoria.setSelection(i);
                break;
            }
        }

        categoriaAtualizada = true;
    }

    @Override
    protected void onResume() {
        getSupportLoaderManager().restartLoader(CATEGORIES_CURSOR_LOADER_ID, null, this);

        super.onResume();
    }
    private void mostraCategoriasSpinner(Cursor cursorCategorias) {
        SimpleCursorAdapter adaptadorCategorias1 = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_1,
                cursorCategorias,
                new String[]{BdTabelaCategoria.CAMPO_DESCRICAO},
                new int[]{android.R.id.text1}
        );
        spinnerCategoria.setAdapter(adaptadorCategorias1);

    }
    public void atualizarReceita(View view) {
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
        long idCategoria = spinnerCategoria.getSelectedItemId();

        //Guaradara dados
        tipoReceita.setDescricaoReceita(designacao);
        tipoReceita.setCategoria(idCategoria);
        tipoReceita.setValor(valor);

        try {
            getContentResolver().update(enderecoReceitaEditar, tipoReceita.getContentValues(),
            null, null);

            Toast.makeText(this, "Registo Guaradado com Sucesso", Toast.LENGTH_LONG).show();
            finish();

        }catch (Exception e ){
            Snackbar.make(
                    editTextDesignacaoReceita,"Erro: não foi possível guaradar o registo",
                    Snackbar.LENGTH_LONG
            ).show();
            e.printStackTrace();
        }

        Intent intent = new Intent(this, ListarTodoTipoReceitaActivity.class);

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
  /*  private void setDefaultDateToTextView(){
        TextView textViewDate = (TextView) findViewById(R.id.textViewSelectedDate);

        int dia = getCurrentDay();
        int mes = getCurrentMonth();
        int ano = getCurrentYear();

        textViewDate.setText(""+dia+"/"+mes+"/"+ano);
    }*/

    public void cancel(View view) { //Botão "cancelar"
        finish();
    }



    public void definirDataEditReceita(View view) { //Botão "definir data"
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

    }
    public void addCategoriaEditReceita(View view) { //Botão "+"
        DialogFragmentCategoria dialogFragmentCategoria = new DialogFragmentCategoria();
        dialogFragmentCategoria.show(getSupportFragmentManager(), "DialogFragmentCategoria");
    }

    @Override
    public void setTexts(String categoria) { //Ação do botão Inserir Categoria

        try {

            Toast.makeText(EditReceita.this, R.string.sms_cat_inserida_success,Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(EditReceita.this, R.string.sms_error_inserir_cat_db,Toast.LENGTH_LONG).show();
        }


    }


    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        androidx.loader.content.CursorLoader cursorLoader = new androidx.loader.content.CursorLoader(this, FinanceContentProvider.ENDERECO_CATEGORIAS, BdTabelaCategoria.TODAS_COLUNAS, null, null, BdTabelaCategoria.CAMPO_DESCRICAO
        );

        return cursorLoader;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        mostraCategoriasSpinner(data);
        categoriasCarregadas = true;
        actualizaCategoriaSelecionada();
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        categoriasCarregadas = false;
        categoriaAtualizada = false;
        mostraCategoriasSpinner(null);
    }
}

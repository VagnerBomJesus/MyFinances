package pt.vagner.myfinances;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
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

import java.util.ArrayList;
import java.util.Calendar;

import static pt.vagner.myfinances.BdTableCategorias.CAMPO_DESCRICAO;

public class NovaDespesa extends AppCompatActivity implements  DatePickerDialog.OnDateSetListener, DialogFragmentCategoria.ExampleDialogListener, LoaderManager.LoaderCallbacks<Cursor> {

    private static  final int ID_CURSO_LOADER_CATEGORIAS = 0;

    private EditText editTextDesignacaoDespesa;
    private Spinner spinnerCategoriaDespesa;
    private EditText editTextValorDespesa;
    private EditText textViewSelectedDateDespesa;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_despesa);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportLoaderManager().initLoader(ID_CURSO_LOADER_CATEGORIAS, null, this);


        /************************Construção dos objetos***************************/
        editTextDesignacaoDespesa = (EditText) findViewById(R.id.editTextDesignacaoDespesa);
        spinnerCategoriaDespesa = (Spinner) findViewById(R.id.spinnerCategoriaDespesa);
        editTextValorDespesa = (EditText) findViewById(R.id.editTextValorDespesa);

        setDefaultDateToTextView(); //data atual na textview data
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
        spinnerCategoriaDespesa.setAdapter(adaptadorCategorias);
    }

    public void inserirDespesaDb(View view) {//button inserir vai a base de dados
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

        long idCategoria = spinnerCategoriaDespesa.getSelectedItemId();

    /*
        Intent intent = new Intent(this, ListarTodosMainActivity.class);

        intent.putExtra(DefinicoesApp.Designacao, designacao);

        startActivity(intent);

        intent.putExtra(DefinicoesApp.MENSAGEM, mensagem);

        startActivity(intent);
    */
        TipoDespesa tipoDespesa = new TipoDespesa();
        tipoDespesa.setDescricaoDespesa(designacao);
        tipoDespesa.setCategoria(idCategoria);
        tipoDespesa.setValor(valor);

        try {
            getContentResolver().insert(FinanceContentProvider.ENDERECO_TIPO_DESPESA, tipoDespesa.getContentValues());

            Toast.makeText(this, getString(R.string.registo_inserido_success), Toast.LENGTH_SHORT).show();
            finish();
        } catch (Exception e) {
            Snackbar.make(
                    editTextDesignacaoDespesa,
                    getString(R.string.erro_inserir_registo_bd),
                    Snackbar.LENGTH_LONG)
                    .show();

            e.printStackTrace();
        }
        Intent intent = new Intent(this, ListarTodosMainActivity.class);

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
        TextView textViewDate = (TextView) findViewById(R.id.textViewSelectedDateDespesja);

        int dia = getCurrentDay();
        int mes = getCurrentMonth();
        int ano = getCurrentYear();

        textViewDate.setText(""+dia+"/"+mes+"/"+ano);
    }



    public void cancel(View view) { //Botão "cancelar"
        finish();
    }
    public void definirDataDespesa(View view) { //Botão "definir data"
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

        textViewSelectedDateDespesa.setText(""+dayOfMonth+"/"+month+"/"+year);

    }
    public void addCategoriaDespesa(View view) { //Botão adicionar categoria Despesa
        DialogFragmentCategoria dialogFragmentCategoria = new DialogFragmentCategoria();
        dialogFragmentCategoria.show(getSupportFragmentManager(), "DialogFragmentDespesas");
    }







    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        androidx.loader.content.CursorLoader cursorLoader = new androidx.loader.content.CursorLoader(this, FinanceContentProvider.ENDERECO_CATEGORIAS, BdTableCategorias.TODAS_COLUNAS, null, null, CAMPO_DESCRICAO
        );

        return cursorLoader;    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        mostraCategoriasSpinner(data);

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        mostraCategoriasSpinner(null);
    }

    private void insertCategoria(String categoria) {
        //Abrir BD
        Categoria addcategoria = new Categoria();
        addcategoria.setDescricao(categoria);


        try {
            getContentResolver().insert(FinanceContentProvider.ENDERECO_CATEGORIAS, addcategoria.getContentValues());
        } catch (Exception e) {
            Snackbar.make(
                    editTextDesignacaoDespesa,
                    getString(R.string.erro_inserir_registo_bd),
                    Snackbar.LENGTH_LONG)
                    .show();

            e.printStackTrace();
        }
    }
    @Override
    public void setTexts(String categoria) { //Ação do botão "addCategoriaDespesa"

        try {
            if (checkCategoriaDespesa(categoria) != -1){ //Se devolver um id != -1 é porque já exite uma categoria com o nome que vamos inserir
                Toast.makeText(NovaDespesa.this,R.string.cate_ja_exist,Toast.LENGTH_LONG).show();
                return;
            }
            insertCategoria(categoria);
            Toast.makeText(NovaDespesa.this,R.string.sms_cat_inserida_success,Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(NovaDespesa.this,R.string.sms_error_inserir_cat_db,Toast.LENGTH_LONG).show();
        }
       atualizarSpinner(); //Atualizar spinner

    }
    public int checkCategoriaDespesa(String categoria){
        //Abrir a BD
        BdFinancesOpenHelper OpenHelper = new BdFinancesOpenHelper(getApplicationContext());
        //Leitura
        SQLiteDatabase db = OpenHelper.getReadableDatabase();

        String query = "SELECT "+BdTableCategorias._ID+" FROM "+BdTableCategorias.NOME_TABELA+" WHERE "+ CAMPO_DESCRICAO+" =?";
        Cursor cursor = db.rawQuery(query,new String[]{categoria});

        int id = -1;

        if (cursor.getCount() > 0){
            cursor.moveToFirst();
            id = cursor.getInt(cursor.getColumnIndex(BdTableCategorias._ID));
        }

        cursor.close();
        db.close();
        return id;
    }

    private void atualizarSpinner(){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getCategoriasFrom());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategoriaDespesa.setAdapter(adapter);
    }
    private ArrayList<String> getCategoriasFrom(){
        //Abrir BD
        BdFinancesOpenHelper OpenHelper = new BdFinancesOpenHelper(getApplicationContext());
        //Leitura
        SQLiteDatabase db = OpenHelper.getReadableDatabase();

        BdTableCategorias bdTabelaCategoria = new BdTableCategorias(db);

        Cursor cursor = bdTabelaCategoria.query(BdTableCategorias.COLUNAS, null, null, null, null, BdTableCategorias._ID);

        ArrayList<String> list = NovaDespesa.getCategoriasFrom(cursor);

        cursor.close();
        db.close();
        return list;
    }
    public static ArrayList<String> getCategoriasFrom(Cursor cursor){ //lista de cat. despesa
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

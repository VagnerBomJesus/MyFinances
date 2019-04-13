package pt.vagner.myfinances;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import java.util.Calendar;

public class NovaDespesa extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    private static Boolean isClicked = false;
    private EditText editTextDesignacaoDespesa;
    private TextView textViewSelectedDateDespesa;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_despesa);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /************************Construção dos objetos***************************/

        editTextDesignacaoDespesa = (EditText) findViewById(R.id.editTextDesignacaoDespesa);
        textViewSelectedDateDespesa = (TextView) findViewById(R.id.textViewSelectedDateDespesa);

        setDefaultDateToTextView(); //data atual na textview data
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
        //Se o botão "definirData" não foi clicado
        if (!isClicked){
            new ListarTodos();
        }


        Intent intent = new Intent(this, ListarTodos.class);

        intent.putExtra(DefinicoesApp.Designacao, designacao);

        startActivity(intent);

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
        TextView textViewDate = (TextView) findViewById(R.id.textViewSelectedDateDespesa);

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

        isClicked = true;
    }


}

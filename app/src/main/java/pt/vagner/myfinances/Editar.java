package pt.vagner.myfinances;

import android.content.Intent;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class Editar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setDefaultDateToTextView();///visualizar data atual
    }
    public void guardar(View view){


        EditText textCategoria = (EditText) findViewById(R.id.textCategoria);
        String mensagem = textCategoria.getText().toString();

        EditText editEditeDesinacao = (EditText) findViewById(R.id.editEditeDesinacao);
        String designacao = editEditeDesinacao.getText().toString();

        EditText textInputEditValor = (EditText) findViewById(R.id.textInputEditValor);
        String Categoria = textInputEditValor.getText().toString();

        if (mensagem.trim().length() == 0) {
            textCategoria.setError(getString(R.string.erro_editTextDesignacaoReceita));
            textCategoria.requestFocus();
            return;
        }
        if (designacao.trim().length() == 0) {
            editEditeDesinacao.setError(getString(R.string.erro_editTextDesignacaoReceita));
            editEditeDesinacao.requestFocus();
            return;
        }
        double valor = 0;

        try {
            valor = Double.parseDouble(textInputEditValor.getText().toString());
        } catch (NumberFormatException e) {
            textInputEditValor.setError(getString(R.string.erro_smsValor));
            textInputEditValor.requestFocus();
            return;
        }

        if (valor == 0) {
            textInputEditValor.setError(getString(R.string.insira_um_valor_maior_que_0));
            textInputEditValor.requestFocus();
            return;
        }

        Intent intent = new Intent(this, MainActivity.class);

        intent.putExtra(DefinicoesApp.Designacao, designacao);

        startActivity(intent);

        intent.putExtra(DefinicoesApp.MENSAGEM, mensagem);
        startActivity(intent);

        intent.putExtra(DefinicoesApp.Designacao, Categoria);

        startActivity(intent);
        Toast.makeText(getApplicationContext(),"Guardaro Com sucesso",Toast.LENGTH_LONG).show();

    }

    public void cancel(View view) { // todo: Botão "cancelar"
        finish();
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
}

package pt.vagner.myfinances;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class NovaReceita extends AppCompatActivity {

    private MenuItem item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_receita);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        setDefaultDateToTextView();///visualizar data atual
    }
    public void inserirReceita(View view) {
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
        TextView textViewDate = (TextView) findViewById(R.id.textViewSelectedDate);

        int dia = getCurrentDay();
        int mes = getCurrentMonth();
        int ano = getCurrentYear();

        textViewDate.setText(""+dia+"/"+mes+"/"+ano);
    }

    public void cancel(View view) { //Botão "cancelar"
        finish();
    }
}

package pt.vagner.myfinances;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.EditText;

public class NovaDespesa extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_despesa);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    public void inserirDespesaDb(View view) {
        EditText editTextDesignacaoDespesa = (EditText) findViewById(R.id.editTextDesignacaoDespesa);
        String mensagem = editTextDesignacaoDespesa.getText().toString();

        EditText editTextValorDespesa = (EditText) findViewById(R.id.editTextValorDespesa);
        String designacao = editTextValorDespesa.getText().toString();


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

        Intent intent = new Intent(this, ListarTodos.class);

        intent.putExtra(DefinicoesApp.Designacao, designacao);

        startActivity(intent);

        intent.putExtra(DefinicoesApp.MENSAGEM, mensagem);

        startActivity(intent);
    }
    public void cancel(View view) { //Botão "cancelar"
        finish();
    }

}

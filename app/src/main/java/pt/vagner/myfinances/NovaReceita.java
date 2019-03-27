package pt.vagner.myfinances;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.EditText;

public class NovaReceita extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_receita);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    public void inserirReceita(View view) {
        EditText editTextValorReceita = (EditText) findViewById(R.id.editTextValorReceita);
        String mensagem = editTextValorReceita.getText().toString();

        if (mensagem.trim().length() == 0) {
            editTextValorReceita.setError(getString(R.string.erro_smsValor));
            editTextValorReceita.requestFocus();
            return;
        }
        if(mensagem.trim().length()==0){

        }

        Intent intent = new Intent(this, ListarTodos.class);

        intent.putExtra(DefinicoesApp.MENSAGEM, mensagem);

        startActivity(intent);
    }
    public void cancel(View view) { //Botão "cancelar"

        finish();
    }
}

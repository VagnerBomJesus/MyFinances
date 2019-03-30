package pt.vagner.myfinances;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ListarTodos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_todos);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mostraDesignacao();
        mostraMensagem();
        sendMessage();

    }
    public void sendMessage() {

        Toast.makeText(this,R.string.registo_inserido_success,
                Toast.LENGTH_LONG).show();
    }
    private void mostraMensagem() {
        Intent intent = getIntent();
        String mensagem = intent.getStringExtra(DefinicoesApp.MENSAGEM);
        TextView textViewValor = (TextView) findViewById(R.id.textViewValor);
        textViewValor.setText(mensagem);
    }
    private void mostraDesignacao() {
        Intent intent = getIntent();
        String designacao = intent.getStringExtra(DefinicoesApp.Designacao);
        TextView textViewDesignacao = (TextView) findViewById(R.id.textViewDesignacao);
        textViewDesignacao.setText(designacao);
    }
}

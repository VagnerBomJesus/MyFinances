package pt.vagner.myfinances;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.TextView;

public class ListarTodos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_todos);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mostraMensagem();
    }
    private void mostraMensagem() {
        Intent intent = getIntent();

        String mensagem = intent.getStringExtra(DefinicoesApp.MENSAGEM);

        TextView textViewValor = (TextView) findViewById(R.id.textViewValor);

        textViewValor.setText(mensagem);
    }
}

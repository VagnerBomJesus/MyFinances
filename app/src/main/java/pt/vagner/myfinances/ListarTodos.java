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

import java.util.Calendar;
import java.util.Date;

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

        int dia = getCurrentDay();
        int mes = getCurrentMonth();
        int ano = getCurrentYear();


        Intent intent = getIntent();

        String mensagem = intent.getStringExtra(DefinicoesApp.MENSAGEM);
        Date data = (Date) intent.getSerializableExtra(DefinicoesApp.DATA);

        TextView textViewValor = (TextView) findViewById(R.id.textViewValor);
        TextView textViewData = (TextView) findViewById(R.id.textViewData);


        textViewData.setText(""+dia+"/"+mes+"/"+ano);
        textViewValor.setText(mensagem+"€");
    }

    private void mostraDesignacao() {
        Intent intent = getIntent();
        String designacao = intent.getStringExtra(DefinicoesApp.Designacao);
        TextView textViewDesignacao = (TextView) findViewById(R.id.textViewDesignacao);
        textViewDesignacao.setText(designacao);
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
}

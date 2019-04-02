package pt.vagner.myfinances;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {//definiçao
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Toast.makeText(getApplicationContext(),"Clicastes em Definiçoes",Toast.LENGTH_SHORT).show();
            return true;
        }
        /***if (id == R.id.action_ListarDia) {//com sua ação Listar por dia
            Intent i = new Intent(this, ListarDia.class);
            Toast.makeText(this,R.string.action_ListarDia,
                    Toast.LENGTH_LONG).show();
            startActivity(i);
            return true;
        }
        if (id == R.id.action_ListarMes) {//com sua ação Listar por Mes
            Intent i = new Intent(this, ListarMes.class);
            Toast.makeText(this,R.string.action_ListarMes,
                    Toast.LENGTH_LONG).show();
            startActivity(i);
            return true;
        }
        if (id == R.id.action_ListarAno) {//com sua ação Listar por Ano
            Intent i = new Intent(this, ListarAno.class);
            Toast.makeText(this,R.string.action_ListarAno,
                    Toast.LENGTH_LONG).show();
            startActivity(i);
            return true;
        }
        if (id == R.id.action_ListarTodos) {//com sua ação Listar Todos
            Intent i = new Intent(this, ListarTodos.class);
            Toast.makeText(this,R.string.Onclik_ListarTodos,
                    Toast.LENGTH_LONG).show();
            startActivity(i);
            return true;
        }**/

        if (id == R.id.action_sobre) {//com sua ação Sobre
            Intent i = new Intent(this, sobre.class);
            startActivity(i);
            return true;
        }


        //Onclik in Terminar
        if (id == R.id.action_terminar) {//com sua ação finich
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void novaReceita(View view) { //Botão "NOVA RECEITA"
        Intent i = new Intent(this, NovaReceita.class);
        startActivity(i);
    }

    public void novaDespesa(View view) { //Botão "NOVA DESPESA"
        Intent i = new Intent(this, NovaDespesa.class);
        startActivity(i);
    }
}

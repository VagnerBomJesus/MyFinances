package pt.vagner.myfinances;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
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
            novaReceita121();
            return true;
        }
        if (id == R.id.action_Editar) {//com sua ação Editar
            Intent i = new Intent(this, Editar.class);
            startActivity(i);
            return true;
        }
        if (id == R.id.action_Eliminar) {//com sua ação Eliminar
            Intent i = new Intent(this, Eliminar.class);
            startActivity(i);
            return true;
        }

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
    public void novaReceita121 (){
        AlertDialog.Builder msBox = new AlertDialog.Builder(this);
        msBox.setTitle("Excluindo.....");
        msBox.setIcon(android.R.drawable.ic_menu_delete);
        msBox.setMessage("Tens certeza do que estas a fazer?...");
        msBox.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "Clicou em sim",Toast.LENGTH_LONG).show();
            }
        });
        msBox.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this,"Clicou em não", Toast.LENGTH_LONG).show();
            }
        });
        msBox.show();
    }
}

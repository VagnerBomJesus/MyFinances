package pt.vagner.myfinances;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.View;

import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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

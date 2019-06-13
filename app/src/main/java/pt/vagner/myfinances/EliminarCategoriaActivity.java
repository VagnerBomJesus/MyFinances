package pt.vagner.myfinances;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class EliminarCategoriaActivity extends AppCompatActivity {

    private Uri enderecoCategoriaApagar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_categoria);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView textViewCategoriaEliminar = (TextView) findViewById(R.id.textViewCategoriaEliminar);

        Intent intent = getIntent();
        long idCategoria =  intent.getLongExtra(GerirCategorias.ID_CATEGORIA,-1);
        if(idCategoria == -1){
            Toast.makeText(this, "Erro: não foi possivel apagar o registo", Toast.LENGTH_LONG).show();
            finish();
            return;
        }
        enderecoCategoriaApagar = Uri.withAppendedPath(FinanceContentProvider.ENDERECO_CATEGORIAS, String.valueOf(idCategoria));
        Cursor cursor = getContentResolver().query(enderecoCategoriaApagar, BdTabelaCategoria.TODAS_COLUNAS, null, null, null);

        if(!cursor.moveToNext()){
            Toast.makeText(this, "Erro: não foi possivel apagar o registo", Toast.LENGTH_LONG).show();
            finish();
            return;
        }
        Categoria categoria = Categoria.fromCursor(cursor);

        textViewCategoriaEliminar.setText(categoria.getDescricao());
    }

    private  void cancelar(View view){
        finish();
    }
    public void eliminareConfirmado(){
        int resitoApagados = getContentResolver().delete(enderecoCategoriaApagar, null, null);

        Toast.makeText(this,R.string.registo_eliminado_success,
                Toast.LENGTH_LONG).show();

        Intent intent = new Intent(this, GerirCategorias.class);
        startActivity(intent);
    }
    private void PerguntaQuerEliminar(int recursodescricao, String mensagem) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setTitle(recursodescricao);
        alertDialogBuilder.setMessage(mensagem);

        alertDialogBuilder.setPositiveButton(
                getString(R.string.sim),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        eliminareConfirmado();
                    }
                }
        );

        alertDialogBuilder.setNegativeButton(
                getString(R.string.nao1),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }
        );

        alertDialogBuilder.show();
    }


    public void eliminar(View view) {
        String mensagem = getString(R.string.alertaEliminar) + "                "
                + getString(R.string.naorecopera);


        PerguntaQuerEliminar(R.string.elminarRegisto, mensagem);
    }
}

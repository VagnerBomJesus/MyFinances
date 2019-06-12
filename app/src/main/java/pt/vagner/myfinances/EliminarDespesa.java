package pt.vagner.myfinances;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class EliminarDespesa extends AppCompatActivity {

    private Uri enderecoFinaceEliminada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar);
        //Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        TextView textViewDescricao = (TextView) findViewById(R.id.textViewDescricaoReceita);
        TextView textViewCategoria = (TextView) findViewById(R.id.textViewDescricaoCategoria);
        TextView textViewValor = (TextView) findViewById(R.id.textViewValor);

        Intent intent = getIntent();
        long idTipoDespesa = intent.getLongExtra(ListarTodoTipoDespesaActivity.ID_TIPO_DESPESA, -1);
        if (idTipoDespesa == -1) {
            Toast.makeText(this, getString(R.string.nao_e_possimve_eliminar), Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        enderecoFinaceEliminada = Uri.withAppendedPath(FinanceContentProvider.ENDERECO_TIPO_DESPESA, String.valueOf(idTipoDespesa));

        Cursor cursor = getContentResolver().query(enderecoFinaceEliminada, BdTabelaTipoDespesa.TODAS_COLUNAS, null, null, null);

        if (!cursor.moveToNext()) {
            Toast.makeText(this, getString(R.string.nao_e_possimve_eliminar), Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        TipoDespesa tipoDespesa = TipoDespesa.fromCursor(cursor);

        textViewDescricao.setText(tipoDespesa.getDescricaoDespesa());
        textViewCategoria.setText(tipoDespesa.getNomeCategoria());
        textViewValor.setText(String.valueOf(tipoDespesa.getValor()));
    }







    public void Eliminar(View view){//Botão EliminarDespesa
        String mensagem = getString(R.string.alertaEliminar) + "                "
                + getString(R.string.naorecopera);


        PerguntaQuerEliminar(R.string.elminarRegisto, mensagem);


    }
    public void cancel(View view) { //Botão "cancelar"
        finish();
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
    public void eliminareConfirmado(){
        int resitoApagados = getContentResolver().delete(enderecoFinaceEliminada, null, null);

        Toast.makeText(this,R.string.registo_eliminado_success,
                Toast.LENGTH_LONG).show();

        Intent intent = new Intent(this, ListarTodoTipoDespesaActivity.class);
        startActivity(intent);
    }
}

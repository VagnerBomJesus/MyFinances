package pt.vagner.myfinances;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class EliminarReceita extends AppCompatActivity {
    private Uri enderecoReceitaApagar;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar);

        TextView textViewDescricaoReceita = (TextView) findViewById(R.id.textViewDescricaoReceita);
        TextView textViewCategoria = (TextView) findViewById(R.id.textViewDescricaoCategoria);
        TextView textViewValor = (TextView) findViewById(R.id.textViewValor);

        Intent intent = getIntent();
        long idReceita = intent.getLongExtra(ListarTodoTipoReceitaActivity.ID_RECEITA,-1);
        if(idReceita == -1){
            Toast.makeText(this, "Erro: não foi possivel apagar o registo", Toast.LENGTH_LONG).show();
            finish();
            return;
        }
        enderecoReceitaApagar = Uri.withAppendedPath(FinanceContentProvider.ENDERECO_TIPO_RECEITA, String.valueOf(idReceita));

        Cursor cursor = getContentResolver().query(enderecoReceitaApagar, BdTabelaTipoReceita.TODAS_COLUNAS, null, null, null);
        if (!cursor.moveToNext()){
            Toast.makeText(this, "Erro: não foi possivel apagar o registo", Toast.LENGTH_LONG).show();
           finish();
           return;
        }
        TipoReceita tipoReceita = TipoReceita.fromCursor(cursor);

        textViewDescricaoReceita.setText(tipoReceita.getDescricaoReceita());
        textViewCategoria.setText(tipoReceita.getNomeCategoria());
        textViewValor.setText(String.valueOf(tipoReceita.getValor()));

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
        int resitoApagados = getContentResolver().delete(enderecoReceitaApagar, null, null);

        Toast.makeText(this,R.string.registo_eliminado_success,
                Toast.LENGTH_LONG).show();

        Intent intent = new Intent(this, ListarTodoTipoReceitaActivity.class);
        startActivity(intent);
    }
}

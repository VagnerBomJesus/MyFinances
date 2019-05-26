package pt.vagner.myfinances;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class ListarTodos extends AppCompatActivity {
    /******************Variáveis**********************/

    private RegistoMovimentosAdapter adapter;
    private RecyclerView recyclerViewRegistos;
    private BdFinaceOpenHelper bdFinaceOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_todos);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /********************Construção dos objetos***********************/
        bdFinaceOpenHelper = new BdFinaceOpenHelper(this);
        recyclerViewRegistos = (RecyclerView) findViewById(R.id.recyclerViewListarTodos);

        initComponents();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initComponents();
    }

    /************************************Buttons actions*******************************************/

    /**
     * Take care of popping the fragment back stack or finishing the activity
     * as appropriate.
     */
    @Override
    public void onBackPressed() {
        finish();
    }

    /*********************************Funtions and Methods******************************************/

    /**
     * Inicializa todos os componentes
     */
    private void initComponents() {
        adapter = new RegistoMovimentosAdapter(bdFinaceOpenHelper.getListRegistoMovimentos(), this, recyclerViewRegistos);

        //verificar se há registos
        if (bdFinaceOpenHelper.getListRegistoMovimentos().size() == 0) {
            Toast.makeText(this, R.string.nao_foram_encontrados_registos_todos, Toast.LENGTH_LONG).show();
        }

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewRegistos.setLayoutManager(layoutManager);
        recyclerViewRegistos.setItemAnimator(new DefaultItemAnimator());
        recyclerViewRegistos.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerViewRegistos.setAdapter(adapter);

    }

}

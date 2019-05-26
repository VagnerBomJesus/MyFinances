package pt.vagner.myfinances;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RegistoMovimentosAdapter extends RecyclerView.Adapter<RegistoMovimentosAdapter.RegistoViewHolder> {

    private List<RegistoMovimentos> registoMovimentosList;
    private Context context;
    private RecyclerView recyclerView;
    private Cursor cursor = null;
    private BdFinaceOpenHelper bdFinaceOpenHelper;



    /**
     *
     * @param parent
     * @param viewType
     * @return layout de um elemento da recycler view
     */
    @Override
    public RegistoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_view_listar_todos, parent, false);
        return new RegistoViewHolder(itemView);
    }


    /**
     * @param holder
     * @param position
     *
     */
    @Override
    public void onBindViewHolder(final RegistoViewHolder holder, final int position) {
        //get element from dataset at this position
        //cursor.moveToPosition(position);
        final RegistoMovimentos registoMovimentos = registoMovimentosList.get(position);
        holder.setRegistoMovimento(registoMovimentos);

        //listen to single view click
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//click no item

            }
        });


    }



    //NÃO USADO
    public void setCursor(Cursor cursor) {
        if (this.cursor != cursor) {
            this.cursor = cursor;
            notifyDataSetChanged();
        }
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return registoMovimentosList.size();
        //if (cursor == null) return 0;
        //return cursor.getCount();
    }
    private static View.OnClickListener viewHolderLivroSelecionado = null;
    public class RegistoViewHolder extends RecyclerView.ViewHolder { //representa um item dentro da recycler view

        public TextView textViewDesignacao;
        public TextView textViewData;
        public TextView textViewCategoriaDespesa;
        public TextView textViewCategoriaReceita;
        public TextView textViewTipo;
        public TextView textViewValor;

        /**
         * @param itemView
         *
         * construção dos objetos
         */
        public RegistoViewHolder(View itemView) {
            super(itemView);
            textViewDesignacao = (TextView) itemView.findViewById(R.id.textViewDesignacao);
            textViewData = (TextView) itemView.findViewById(R.id.textViewData);
            textViewCategoriaDespesa = (TextView) itemView.findViewById(R.id.textViewCategoriaDespesa);
            textViewCategoriaReceita = (TextView) itemView.findViewById(R.id.textViewCategoriaReceita);
            textViewTipo = (TextView) itemView.findViewById(R.id.textViewTipo);
            textViewValor = (TextView) itemView.findViewById(R.id.textViewValor);

            itemView.setOnClickListener((View.OnClickListener) this);
        }

        BdFinaceOpenHelper bdFinaceOpenHelper = new BdFinaceOpenHelper(context);

        /**
         * @param registoMovimento
         *
         * conteudo de um registo na recycler view
         */
        public void setRegistoMovimento(RegistoMovimentos registoMovimento) {
            int idDespesa = registoMovimento.getTipodespesa();
            int idReceita = registoMovimento.getTiporeceita();
            String catDespesa = bdFinaceOpenHelper.getTipoDespesaByID(idDespesa);
            String catReceita = bdFinaceOpenHelper.getTipoReceitaByID(idReceita);

            if (registoMovimento.getDesignacao().equals("")) { //caso a designacao não exista, colocar a categoria
                if(!catDespesa.equals("")) {
                    textViewDesignacao.setText("" + catDespesa);
                }else {
                    textViewDesignacao.setText("" + catReceita);
                }
            }else{
                textViewDesignacao.setText("" + registoMovimento.getDesignacao());
            }

            textViewCategoriaDespesa.setText(""+catDespesa);
            textViewCategoriaReceita.setText(""+catReceita);
            textViewData.setText(""+registoMovimento.getDia()+"/"+registoMovimento.getMes()+"/"+registoMovimento.getAno());

            if (registoMovimento.getReceitadespesa().equals("Despesa")){
                textViewTipo.setTextColor(Color.parseColor("#ff384c"));
            }else{
                textViewTipo.setTextColor(Color.parseColor("#93D67C"));
            }
            textViewTipo.setText(""+registoMovimento.getReceitadespesa());

            if(registoMovimento.getReceitadespesa().equals("Despesa")) {
                textViewValor.setText("-" +String.format("%.2f",registoMovimento.getValor()) + "€");
                textViewValor.setTextColor(Color.parseColor(String.valueOf(R.color.ColorVert)));
            }else{
                textViewValor.setText(""+String.format("%.2f",registoMovimento.getValor())+"€");
                textViewValor.setTextColor(Color.parseColor(String.valueOf(R.color.ColorRed)));
            }
        }

    }

    public RegistoMovimentosAdapter(List<RegistoMovimentos> myDataset, Context context, RecyclerView recyclerView) {
        this.registoMovimentosList = myDataset;
        this.context = context;
        this.recyclerView = recyclerView;
    }


}

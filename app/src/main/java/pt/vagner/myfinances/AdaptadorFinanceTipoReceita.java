package pt.vagner.myfinances;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdaptadorFinanceTipoReceita extends RecyclerView.Adapter<AdaptadorFinanceTipoReceita.ViewHolderFinanceR> {

    private Cursor cursor;
    private Context context;

    public AdaptadorFinanceTipoReceita(Context context) {
        this.context = context;
    }

    public void setCursor(Cursor cursor) {
        if (this.cursor != cursor) {
            this.cursor = cursor;
            notifyDataSetChanged();
        }
    }


    @NonNull
    @Override
    public AdaptadorFinanceTipoReceita.ViewHolderFinanceR onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemFinaceReceita = LayoutInflater.from(context).inflate(R.layout.layout_view_listar_todos_r, parent, false);

        return new ViewHolderFinanceR(itemFinaceReceita);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorFinanceTipoReceita.ViewHolderFinanceR holder, int position) {
        cursor.moveToPosition(position);
        TipoReceita tipoReceita = TipoReceita.fromCursor(cursor);
        holder.setTipoReceita(tipoReceita);
    }

    @Override
    public int getItemCount() {
        if(cursor == null)return 0;

        return cursor.getCount();
    }


    public TipoReceita getLivroSelecionado() {
        if (viewHolderLivroSelecionado == null) return null;

        return viewHolderLivroSelecionado.tipoReceita;
    }

    private static ViewHolderFinanceR viewHolderLivroSelecionado = null;

    public class ViewHolderFinanceR extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView textViewDesignacaoReceita;
        private TextView textViewCategoriaReceita;
        private TextView textViewValor;

        private TipoReceita tipoReceita;

        public ViewHolderFinanceR(@NonNull View itemView) {
            super(itemView);

            textViewDesignacaoReceita = (TextView)itemView.findViewById(R.id.textViewCategoria);
            textViewCategoriaReceita =  (TextView)itemView.findViewById(R.id.textViewCategoriaReceita);
            textViewValor =  (TextView)itemView.findViewById(R.id.textViewValor);

            itemView.setOnClickListener(this);
        }

        public void setTipoReceita(TipoReceita tipoReceita) {
            this.tipoReceita = tipoReceita;

            textViewDesignacaoReceita.setText(tipoReceita.getDescricaoReceita());
            textViewValor.setText(String.format("%.2f",tipoReceita.getValor()) + "€");
            textViewCategoriaReceita.setText(tipoReceita.getNomeCategoria());
        }

        @Override
        public void onClick(View v) {
            if (viewHolderLivroSelecionado != null) {
                viewHolderLivroSelecionado.desSeleciona();
            }

            viewHolderLivroSelecionado = this;

            //((ListarTodoTipoReceitaActivity) context).atualizaOpcoesMenu();

            seleciona();
        }

        private void desSeleciona() {
            itemView.setBackgroundResource(android.R.color.white);
        }

        private void seleciona() {
            itemView.setBackgroundResource(R.color.colorItemSelecionado);
        }
    }
}

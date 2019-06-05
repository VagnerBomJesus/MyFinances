package pt.vagner.myfinances;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdaptadorFinanceTipoReceita extends RecyclerView.Adapter<AdaptadorFinanceTipoReceita.ViewHolderFinance> {

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
    public ViewHolderFinance onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemFinance = LayoutInflater.from(context).inflate(R.layout.layout_view_listar_todos, parent, false);

        return new ViewHolderFinance(itemFinance);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderFinance holder, int position) {
        cursor.moveToPosition(position);
        TipoReceita tipoReceita = TipoReceita.fromCursor(cursor);
        holder.setTipoReceita(tipoReceita);
    }

    @Override
    public int getItemCount() {
        if (cursor == null) return 0;

        return cursor.getCount();
    }

    public TipoReceita getFinacesSelecionado() {
        if (viewHolderFinanceSelecionado == null) return null;

        return viewHolderFinanceSelecionado.tipoReceita;
    }

    private static ViewHolderFinance viewHolderFinanceSelecionado = null;


    public class ViewHolderFinance extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView textViewDesignacao;
        private TextView textViewCategoriaDespesa;
        private TextView textViewValor;

        private TipoReceita tipoReceita;


        public ViewHolderFinance(@NonNull View itemView) {
            super(itemView);

            textViewDesignacao = (TextView)itemView.findViewById(R.id.textViewDesignacao);
            textViewCategoriaDespesa =  (TextView)itemView.findViewById(R.id.textViewCategoriaDespesa);
            textViewValor =  (TextView)itemView.findViewById(R.id.textViewValor);

            itemView.setOnClickListener(this);
        }

        public void setTipoReceita(TipoReceita tipoReceita) {
            this.tipoReceita = tipoReceita;

            textViewDesignacao.setText(tipoReceita.getDescricaoReceita());
            textViewValor.setText("" +String.format("%.2f",tipoReceita.getValor()) + "€");
            //textViewValor.setTextColor(Color.parseColor(String.valueOf(R.color.ColorRed)));
            textViewCategoriaDespesa.setText(tipoReceita.getNomeCategoria());
        }

        @Override
        public void onClick(View v) {
            if (viewHolderFinanceSelecionado != null) {
                viewHolderFinanceSelecionado.desSeleciona();
            }

            viewHolderFinanceSelecionado = this;

            ((ListarTodosMainActivity) context).atualizaOpcoesMenu();

            seleciona();        }

        private void desSeleciona() {
            itemView.setBackgroundResource(android.R.color.white);
        }

        private void seleciona() {
            itemView.setBackgroundResource(R.color.colorItemSelecionado);
        }
    }
}

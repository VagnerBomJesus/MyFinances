package pt.vagner.myfinances;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static java.lang.Double.valueOf;

public class AdaptadorFinances extends RecyclerView.Adapter<AdaptadorFinances.ViewHolderFinance> {
    private Cursor cursor;
    private Context context;

    public AdaptadorFinances(Context context) {
        this.context = context;
    }

    public void setCursor(Cursor cursor) {
        if (this.cursor != cursor) {
            this.cursor = cursor;
            notifyDataSetChanged();
        }
    }
    /**
     * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
     * an item.
     * <p>
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     * <p>
     * The new ViewHolder will be used to display items of the adapter using
     * {@link #onBindViewHolder(ViewHolder, int, List)}. Since it will be re-used to display
     * different items in the data set, it is a good idea to cache references to sub views of
     * the View to avoid unnecessary {@link View#findViewById(int)} calls.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     * @see #getItemViewType(int)
     * @see #onBindViewHolder(ViewHolder, int)
     */
    @NonNull
    @Override
    public ViewHolderFinance onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemFinance = LayoutInflater.from(context).inflate(R.layout.layout_view_listar_todos, parent, false);

        return new ViewHolderFinance(itemFinance);
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the {@link ViewHolder#itemView} to reflect the item at the given
     * position.
     * <p>
     * Note that unlike {@link ListView}, RecyclerView will not call this method
     * again if the position of the item changes in the data set unless the item itself is
     * invalidated or the new position cannot be determined. For this reason, you should only
     * use the <code>position</code> parameter while acquiring the related data item inside
     * this method and should not keep a copy of it. If you need the position of an item later
     * on (e.g. in a click listener), use {@link ViewHolder#getAdapterPosition()} which will
     * have the updated adapter position.
     * <p>
     * Override {@link #onBindViewHolder(ViewHolder, int, List)} instead if Adapter can
     * handle efficient partial bind.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolderFinance holder, int position) {
        cursor.moveToPosition(position);
        TipoDespesa tipoDespesa = TipoDespesa.fromCursor(cursor);
        holder.setTipoDespesa(tipoDespesa);
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        if (cursor == null) return 0;

        return cursor.getCount();
    }

    public TipoDespesa getFinacesSelecionado() {
        if (viewHolderFinanceSelecionado == null) return null;

        return viewHolderFinanceSelecionado.tipoDespesa;
    }

    private static ViewHolderFinance viewHolderFinanceSelecionado = null;

    public class ViewHolderFinance extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView textViewDesignacao;
        private TextView textViewCategoriaDespesa;
        private TextView textViewValor;

        private TipoDespesa tipoDespesa;

        public ViewHolderFinance(@NonNull View itemView) {
            super(itemView);

            textViewDesignacao = (TextView)itemView.findViewById(R.id.textViewDesignacao);
            textViewCategoriaDespesa =  (TextView)itemView.findViewById(R.id.textViewCategoriaDespesa);
            textViewValor =  (TextView)itemView.findViewById(R.id.textViewValor);

            itemView.setOnClickListener(this);
        }

        public void setTipoDespesa(TipoDespesa tipoDespesa) {
            this.tipoDespesa = tipoDespesa;

            textViewDesignacao.setText(tipoDespesa.getDescricaoDespesa());
            textViewValor.setText("-" +String.format("%.2f",tipoDespesa.getValor()) + "€");
            //textViewValor.setTextColor(Color.parseColor(String.valueOf(R.color.ColorRed)));
            textViewCategoriaDespesa.setText(tipoDespesa.getNomeCategoria());
        }

        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        @Override
        public void onClick(View v) {
            if (viewHolderFinanceSelecionado != null) {
                viewHolderFinanceSelecionado.desSeleciona();
            }

            viewHolderFinanceSelecionado = this;

            ((ListarTodosMainActivity) context).atualizaOpcoesMenu();

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

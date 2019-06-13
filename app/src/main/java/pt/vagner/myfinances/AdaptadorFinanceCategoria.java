package pt.vagner.myfinances;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdaptadorFinanceCategoria extends RecyclerView.Adapter<AdaptadorFinanceCategoria.ViewHolderFinance> {
    private Cursor cursor;
    private Context context;

    public AdaptadorFinanceCategoria(Context context) {
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
        View itemCategoria = LayoutInflater.from(context).inflate(R.layout.intem_categoria, parent, false);
        return new ViewHolderFinance(itemCategoria);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorFinanceCategoria.ViewHolderFinance holder, int position) {
        cursor.moveToPosition(position);
        Categoria categoria = Categoria.fromCursor(cursor);
        holder.setCategoria(categoria);
    }

    @Override
    public int getItemCount() {
        if (cursor == null) return 0;

        return cursor.getCount();
    }
    public Categoria getCategoriaSelecionado() {
        if (viewHolderLivroSelecionado == null) return null;

        return viewHolderLivroSelecionado.categoria;
    }

    private static ViewHolderFinance viewHolderLivroSelecionado = null;

    public class ViewHolderFinance extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView textViewCategoria;

        private  Categoria categoria;

        public ViewHolderFinance(@NonNull View itemView) {
            super(itemView);
            textViewCategoria = (TextView) itemView.findViewById(R.id.textViewDescricaoCategoria);

            itemView.setOnClickListener(this);
        }
        public  void setCategoria (Categoria categoria){
            this.categoria = categoria;
            textViewCategoria.setText(categoria.getDescricao());
        }

        @Override
        public void onClick(View v) {
            if (viewHolderLivroSelecionado != null) {
                viewHolderLivroSelecionado.desSeleciona();
            }

            viewHolderLivroSelecionado = this;

            ((GerirCategorias) context).atualizaOpcoesMenu();

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

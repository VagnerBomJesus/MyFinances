package pt.vagner.myfinances;

import android.content.ContentValues;
import android.database.Cursor;

public class TipoReceita {
    private long id;
    private String descricaoReceita;
    private int valor;
    private long categoria;
    private String nomeCategoria1; // Campo "externo"

    public String getNomeCategoria1() {
        return nomeCategoria1;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescricaoReceita() {
        return descricaoReceita;
    }

    public void setDescricaoReceita(String descricaoReceita) {
        this.descricaoReceita = descricaoReceita;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public long getCategoria() {
        return categoria;
    }

    public void setCategoria(long categoria) {
        this.categoria = categoria;
    }

    public ContentValues getContentValues() {
        ContentValues valores = new ContentValues();

        valores.put(BdTabelaReceita.CAMPO_DESCRICAO_RECEITA, descricaoReceita);
        valores.put(BdTabelaReceita.CAMPO_VALOR, valor);
        valores.put(BdTabelaReceita.CAMPO_CATEGORIA, categoria);

        return valores;
    }

    public static TipoReceita fromCursor(Cursor cursor) {
        long id = cursor.getLong(
                cursor.getColumnIndex(BdTabelaReceita._ID)
        );

        String titulo = cursor.getString(
                cursor.getColumnIndex(BdTabelaReceita.CAMPO_DESCRICAO_RECEITA)
        );

        int pagina = cursor.getInt(
                cursor.getColumnIndex(BdTabelaReceita.CAMPO_VALOR)
        );

        long categoria = cursor.getLong(
                cursor.getColumnIndex(BdTabelaReceita.CAMPO_CATEGORIA)
        );

        String nomeCategoria = cursor.getString(
                cursor.getColumnIndex(BdTabelaReceita.ALIAS_NOME_CATEGORIA)
        );

        TipoReceita tipoReceita = new TipoReceita();

        tipoReceita.setId(id);
        tipoReceita.setDescricaoReceita(titulo);
        tipoReceita.setValor(pagina);
        tipoReceita.setCategoria(categoria);
        tipoReceita.nomeCategoria1 = nomeCategoria;

        return tipoReceita;
    }
}

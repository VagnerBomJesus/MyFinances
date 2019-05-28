package pt.vagner.myfinances;

import android.content.ContentValues;
import android.database.Cursor;

public class TipoDespesa {
    private long id;
    private String descricaoDespesa;
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

    public String getDescricaoDespesa() {
        return descricaoDespesa;
    }

    public void setDescricaoDespesa(String descricaoDespesa) {
        this.descricaoDespesa = descricaoDespesa;
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

        valores.put(BdTabelaReceita.CAMPO_DESCRICAO_RECEITA, descricaoDespesa);
        valores.put(BdTabelaReceita.CAMPO_VALOR, valor);
        valores.put(BdTabelaReceita.CAMPO_CATEGORIA, categoria);

        return valores;
    }

    public static TipoDespesa fromCursor(Cursor cursor) {
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

        String nomeCategoria1 = cursor.getString(
                cursor.getColumnIndex(BdTabelaReceita.ALIAS_NOME_CATEGORIA)
        );

        TipoDespesa tipoDespesa = new TipoDespesa();

        tipoDespesa.setId(id);
        tipoDespesa.setDescricaoDespesa(titulo);
        tipoDespesa.setValor(pagina);
        tipoDespesa.setCategoria(categoria);
        tipoDespesa.nomeCategoria1 = nomeCategoria1;

        return tipoDespesa;
    }
}

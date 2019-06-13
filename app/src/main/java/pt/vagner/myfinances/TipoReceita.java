package pt.vagner.myfinances;

import android.content.ContentValues;
import android.database.Cursor;

public class TipoReceita {
    private long id;
    private String descricaoReceita;
    private double valor;
    private long categoria;
    private String nomeCategoria; // Campo "externo"

    public String getNomeCategoria() {
        return nomeCategoria;
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

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
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

        valores.put(BdTabelaTipoReceita.CAMPO_DESCRIACO_RECEITA, descricaoReceita);
        valores.put(BdTabelaTipoReceita.CAMPO_VALOR, valor);
        valores.put(BdTabelaTipoReceita.CAMPO_CATEGORIA, categoria);

        return valores;
    }

    public static TipoReceita fromCursor(Cursor cursor) {
        long id = cursor.getLong(
                cursor.getColumnIndex(BdTabelaTipoReceita._ID)
        );

        String descricaoReceita = cursor.getString(
                cursor.getColumnIndex(BdTabelaTipoReceita.CAMPO_DESCRIACO_RECEITA)
        );

        double valor = cursor.getInt(
                cursor.getColumnIndex(BdTabelaTipoReceita.CAMPO_VALOR)
        );

        long categoria = cursor.getLong(
                cursor.getColumnIndex(BdTabelaTipoReceita.CAMPO_CATEGORIA)
        );

        String nomeCategoria = cursor.getString(
                cursor.getColumnIndex(BdTabelaTipoReceita.ALIAS_NOME_CATEGORIA)
        );

        TipoReceita tipoReceita = new TipoReceita();

        tipoReceita.setId(id);
        tipoReceita.setDescricaoReceita(descricaoReceita);
        tipoReceita.setValor(valor);
        tipoReceita.setCategoria(categoria);
        tipoReceita.nomeCategoria = nomeCategoria;

        return tipoReceita;
    }
}

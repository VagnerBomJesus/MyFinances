package pt.vagner.myfinances;

import android.content.ContentValues;
import android.database.Cursor;

public class TipoDespesa {
    private long id;
    private String descricaoDespesa;
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

    public String getDescricaoDespesa() {
        return descricaoDespesa;
    }

    public void setDescricaoDespesa(String descricaoDespesa) {
        this.descricaoDespesa = descricaoDespesa;
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

        valores.put(BdTabelaTipoDespesa.CAMPO_DESCRICAO_DESPESA, descricaoDespesa);
        valores.put(BdTabelaTipoDespesa.CAMPO_VALOR, valor);
        valores.put(BdTabelaTipoDespesa.CAMPO_CATEGORIA, categoria);

        return valores;
    }

    public static TipoDespesa fromCursor(Cursor cursor) {
        long id = cursor.getLong(
                cursor.getColumnIndex(BdTabelaTipoDespesa._ID)
        );

        String descricaoDespesa = cursor.getString(
                cursor.getColumnIndex(BdTabelaTipoDespesa.CAMPO_DESCRICAO_DESPESA)
        );

        int valor = cursor.getInt(
                cursor.getColumnIndex(BdTabelaTipoDespesa.CAMPO_VALOR)
        );

        long categoria = cursor.getLong(
                cursor.getColumnIndex(BdTabelaTipoDespesa.CAMPO_CATEGORIA)
        );

        String nomeCategoria = cursor.getString(
                cursor.getColumnIndex(BdTabelaTipoDespesa.ALIAS_NOME_CATEGORIA)
        );

        TipoDespesa tipoDespesa = new TipoDespesa();

        tipoDespesa.setId(id);
        tipoDespesa.setDescricaoDespesa(descricaoDespesa);
        tipoDespesa.setValor(valor);
        tipoDespesa.setCategoria(categoria);
        tipoDespesa.nomeCategoria = nomeCategoria;

        return tipoDespesa;
    }
}

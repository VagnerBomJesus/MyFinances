package pt.vagner.myfinances;

import android.content.ContentValues;
import android.database.Cursor;

public class TipoDespesa {
        private long id;
        private String descricao;
        private double valor;
        private long categoria;

        private String nomeCategoria;//Campo ""externo""


        public String getNomeCategoria() {
            return nomeCategoria;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getDescricao() {
            return descricao;
        }

        public void setDescricao(String descricao) {
            this.descricao = descricao;
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

            valores.put(BdTabelaReceita.CAMPO_DESCRICAO, descricao);
            valores.put(BdTabelaReceita.CAMPO_VALOR, valor);
            valores.put(BdTabelaReceita.CAMPO_CATEGORIA, categoria);

            return valores;
        }

        ////CRUD
        public TipoDespesa fromCursor(Cursor cursor) {
            long id = cursor.getLong(
                    cursor.getColumnIndex(BdTabelaReceita._ID)
            );

            String descricao = cursor.getString(
                    cursor.getColumnIndex(BdTabelaReceita.CAMPO_DESCRICAO)
            );

            int valor = cursor.getInt(
                    cursor.getColumnIndex(BdTabelaReceita.CAMPO_VALOR)
            );

            long categoria = cursor.getLong(
                    cursor.getColumnIndex(BdTabelaReceita.CAMPO_CATEGORIA)
            );

            String nomeCategoria = cursor.getString(
                    cursor.getColumnIndex(BdTabelaReceita.ALIAS_NOME_CATEGORIA)
            );

            TipoDespesa tipoReceita = new TipoDespesa();

            tipoReceita.setId(id);
            tipoReceita.setDescricao(descricao);
            tipoReceita.setValor(valor);
            tipoReceita.setCategoria(categoria);
            tipoReceita.nomeCategoria = nomeCategoria;

            return tipoReceita;
        }
}

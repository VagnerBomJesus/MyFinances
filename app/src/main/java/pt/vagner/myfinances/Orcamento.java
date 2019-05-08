package pt.vagner.myfinances;


import android.content.ContentValues;
import android.database.Cursor;

public class Orcamento {
   private long id_orcamento;
   private double valor;

    //Construtores
    public Orcamento(){}

    public Orcamento(long id_orcamento, double valor) {
        this.id_orcamento = id_orcamento;
        this.valor = valor;
    }

    //Setters and Getters
    public long getId_orcamento() {
        return id_orcamento;
    }

    public void setId_orcamento(long id_orcamento) {
        this.id_orcamento = id_orcamento;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }


    //CRUD
    public ContentValues getContentVslues (){
        ContentValues valores = new ContentValues();
        valores.put(BdTableOrcamento.VALOR, valor);
        return valores;

    }

    public static Orcamento getCurrentOrcamentoFromCursor(Cursor cursor){
        long id_orcamento = cursor.getLong(
                cursor.getColumnIndex(BdTableOrcamento._ID)
        );

        final int posValor = cursor.getInt(
                cursor.getColumnIndex(BdTableOrcamento.VALOR)
        );

        Orcamento orcamento = new Orcamento();

        orcamento.setId_orcamento(id_orcamento);
        orcamento.setValor(posValor);

        return orcamento;
    }

}

package pt.vagner.myfinances;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class GerirCategorias extends AppCompatActivity {
    public static boolean clickedButtonCatReceita = false;
    public static boolean clickButtonCatDespesa = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gerir_categorias);
    }
}

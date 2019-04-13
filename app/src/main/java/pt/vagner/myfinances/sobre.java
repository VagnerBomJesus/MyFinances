package pt.vagner.myfinances;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class sobre extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sobre);
    }

    public void sendEmail(View view) {
        Intent i = new Intent();
        i.setAction(Intent.ACTION_SEND);
        i.putExtra(Intent.EXTRA_TEXT, "A sua mensagem de texto...");
        i.putExtra(Intent.EXTRA_EMAIL, "vagneripg@gmail.com");
        i.putExtra(Intent.EXTRA_SUBJECT, "Suporte MyFinances");
        i.setType("text/plain");
        startActivity(i);
    }
}

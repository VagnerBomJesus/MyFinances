package pt.vagner.myfinances;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.github.clans.fab.FloatingActionMenu;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

public class sobre extends AppCompatActivity {
    FloatingActionMenu actionMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sobre);
        Toolbar toolbar = findViewById(R.id.toolbar);
        actionMenu=findViewById(R.id.menuFotoante);
        actionMenu.setClosedOnTouchOutside(true);
        setSupportActionBar(toolbar);

    }
    public void sendEmail(View view) {
        Intent i = new Intent();
        i.setAction(Intent.ACTION_SEND);
        i.putExtra(Intent.EXTRA_TEXT, "A sua mensagem de texto...");
        i.putExtra(Intent.EXTRA_EMAIL, "vagneripg@gmail.com");
        i.putExtra(Intent.EXTRA_SUBJECT, "Suporte Finanças");
        i.setType("text/plain");
        startActivity(i);
    }
    public void facebookIntent(View view){
        String url = "https://www.facebook.com/profile.php?id=100007017550232";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

}

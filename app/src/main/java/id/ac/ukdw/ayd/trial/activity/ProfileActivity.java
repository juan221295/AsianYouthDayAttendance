package id.ac.ukdw.ayd.trial.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import id.ac.ukdw.ayd.trial.MyPreferenceManager;
import id.ac.ukdw.ayd.trial.R;

public class ProfileActivity extends AppCompatActivity {
    TextView txtNama;
    TextView txtEmail;
    TextView txtNoHp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        txtNama = (TextView) findViewById(R.id.txtProfilNamaPanitia);
        txtEmail = (TextView) findViewById(R.id.txtEmailPanitia);
        txtNoHp = (TextView) findViewById(R.id.txtNoHpPanitia);
        MyPreferenceManager pm = new MyPreferenceManager(getApplicationContext());
        String nama = pm.getPanitia().getNamaDepan() + " " + pm.getPanitia().getNamaBelakang();
        txtNama.setText(": " + nama);
        txtNoHp.setText(": " + pm.getPanitia().getNoHpIndonesia());
        txtEmail.setText(": " + pm.getPanitia().getEmail());


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            new MyPreferenceManager(getApplicationContext()).clear();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return true;
        }else if(id == R.id.profile_panitia){
//            Toast.makeText(this, "profile", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}

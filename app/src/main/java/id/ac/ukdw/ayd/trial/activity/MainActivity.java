package id.ac.ukdw.ayd.trial.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import id.ac.ukdw.ayd.trial.MyPreferenceManager;
import id.ac.ukdw.ayd.trial.R;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class MainActivity extends AppCompatActivity{
    Button btnScan;
    TextView txtNamaPanitia;
    Spinner spnAcara;
    Spinner spn_mg_id;
    Spinner spn_member_type;
    Spinner spn_did_id;
    Spinner spn_acco;
    Spinner spn_bus_no;
    RadioButton rb_live_in;
    int acara = 0;
    RadioButton rb_event;
    RadioButton rb_daily;
//    int daily_type;

    private ZXingScannerView mScannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(new MyPreferenceManager(getApplicationContext()).getPanitia() == null){
            launchLoginActivity();
        }
        setContentView(R.layout.activity_main);
        setLayout();



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

            startActivity(new Intent(this, ProfileActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setLayout(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        rb_event = (RadioButton) findViewById(R.id.rb_event);
        rb_daily = (RadioButton) findViewById(R.id.rb_daily);
        rb_live_in = (RadioButton) findViewById(R.id.rb_live_in);
        rb_event.setChecked(true);

        rb_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rb_event.isChecked()){
                    spn_mg_id.setEnabled(true);
                    spnAcara.setEnabled(true);
                    spn_member_type.setEnabled(false);
                    rb_daily.setChecked(false);

                    rb_live_in.setChecked(false);
                    spn_acco.setEnabled(false);
                    spn_bus_no.setEnabled(false);

                }
            }
        });

        rb_daily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rb_daily.isChecked()){
                    spn_mg_id.setEnabled(false);
                    spnAcara.setEnabled(false);
                    spn_member_type.setEnabled(true);
                    rb_event.setChecked(false);

                    rb_live_in.setChecked(false);
                    spn_bus_no.setEnabled(false);
                    spn_acco.setEnabled(false);
                }
            }
        });

        rb_live_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rb_live_in.isChecked()){
                    spn_mg_id.setEnabled(false);
                    spnAcara.setEnabled(false);
                    spn_member_type.setEnabled(false);
                    rb_event.setChecked(false);

                    rb_daily.setChecked(false);
                    spn_bus_no.setEnabled(true);
                    spn_acco.setEnabled(true);
                }
            }
        });

        spn_mg_id = (Spinner) findViewById(R.id.spn_mg_id);
        spn_member_type = (Spinner) findViewById(R.id.spn_member_type);
        spn_member_type.setEnabled(false);


        spn_acco = (Spinner) findViewById(R.id.spn_type_acco);
        spn_bus_no = (Spinner) findViewById(R.id.spn_bus_no);
        spn_bus_no.setEnabled(false);
        spn_acco.setEnabled(false);
        List<String> spinnerArray =  new ArrayList<String>();
        for (int i=1; i<50; i++){
            if(i < 10){
                spinnerArray.add("ACMBUS" + "0"+ Integer.toString(i));
            }else{
                spinnerArray.add("ACMBUS" + Integer.toString(i));
            }
        }
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArray );

        spn_bus_no.setAdapter(spinnerArrayAdapter);

        spnAcara = (Spinner) findViewById(R.id.spnAcara);

//        spn_member_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                switch (position){
//                    case 0 :
//                        spn_did_id.setEnabled(false);
//                        daily_type = 1;
//                        break;
//                    case 1 :
//                        spn_did_id.setEnabled(true);
//                        daily_type = 2;
//                        break;
//                    default :
//                        break;
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//        spnAcara.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                acara = position;
//                switch (position){
//                    case 0:
//                        Toast.makeText(getApplicationContext(), "acara 1", Toast.LENGTH_SHORT).show();
//                        break;
//                    case 1:
//                        Toast.makeText(getApplicationContext(), "acara 2", Toast.LENGTH_SHORT).show();
//                        break;
//                    case 2:
//                        Toast.makeText(getApplicationContext(), "acara 3", Toast.LENGTH_SHORT).show();
//                        break;
//                    default:
//                        break;
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });




        btnScan = (Button) findViewById(R.id.btnScan);
        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ScanActivity.class);
                if(rb_event.isChecked()){
                    i.putExtra("type", 1);
                    i.putExtra("event", spnAcara.getSelectedItem().toString());
                    i.putExtra("mg_id", spn_mg_id.getSelectedItem().toString());
                    i.putExtra("member_type", "");
                    i.putExtra("did_id", "");
                    i.putExtra("acco_type", "");
                    i.putExtra("bus_no", "");

//                    i.putExtra("dailyType", -1);
                }else if(rb_daily.isChecked()){
                    i.putExtra("type", 2);
                    i.putExtra("member_type", spn_member_type.getSelectedItem().toString());
                    i.putExtra("event", "");
                    i.putExtra("mg_id", "");
                    i.putExtra("did_id", "");
                    i.putExtra("acco_type", "");
                    i.putExtra("bus_no", "");

//                    i.putExtra("dailyType", daily_type);
                }else if(rb_live_in.isChecked()){
                    i.putExtra("type", 3);
                    i.putExtra("member_type", "");
                    i.putExtra("event", "");
                    i.putExtra("mg_id", "");
//                    i.putExtra("did_id", spn_did_id.getSelectedItem().toString());
                    i.putExtra("did_id", "");

                    i.putExtra("acco_type", spn_acco.getSelectedItem().toString());
                    i.putExtra("bus_no", spn_bus_no.getSelectedItem().toString());
                }
                startActivity(i);

            }
        });
        txtNamaPanitia = (TextView) findViewById(R.id.txtNamaPanitia);
        MyPreferenceManager pm = new MyPreferenceManager(getApplicationContext());
        String nama = pm.getPanitia().getNamaDepan() + " " + pm.getPanitia().getNamaBelakang();
        txtNamaPanitia.setText(nama);
    }

    private void launchLoginActivity() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}

package id.ac.ukdw.ayd.trial.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import id.ac.ukdw.ayd.trial.Endpoints;
import id.ac.ukdw.ayd.trial.MyPreferenceManager;
import id.ac.ukdw.ayd.trial.Panitia;
import id.ac.ukdw.ayd.trial.R;

public class LoginActivity extends AppCompatActivity {

    EditText txtCommitteeId;
    EditText txtPassword;
    Button btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txtCommitteeId = (EditText) findViewById(R.id.txtCommitteeId);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

    }

    public void login(){
        final String committeeId = txtCommitteeId.getText().toString();
//        Toast.makeText(LoginActivity.this, email, Toast.LENGTH_SHORT).show();

        final String password = txtPassword.getText().toString();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Endpoints.LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);

                    if(object.getBoolean("error") == false){
                        JSONObject panitiaObj = object.getJSONObject("panitia");
                        Panitia panitia = new Panitia();
                        panitia.setKodePanitia(panitiaObj.getString("kode_panitia"));
                        panitia.setNamaDepan(panitiaObj.getString("nama_depan"));
                        panitia.setNamaBelakang(panitiaObj.getString("nama_belakang"));
                        panitia.setEmail(panitiaObj.getString("email"));
                        panitia.setNoHpIndonesia(panitiaObj.getString("no_hp_indonesia"));
                        new MyPreferenceManager(getApplicationContext()).storePanitia(panitia);

                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();


                    }else{
                        Toast.makeText(LoginActivity.this, "User Tidak Ditemukan atau Tidak Diberikan Akses", Toast.LENGTH_LONG).show();
                        //kalo ga ada user nya
                    }
                } catch (JSONException e) {
                    e.printStackTrace();


                    Toast.makeText(LoginActivity.this, "Koneksi Error, silahkan coba lagi", Toast.LENGTH_SHORT).show();


                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this, "Koneksi Error, silahkan coba lagi", Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("committee_id", committeeId);
                params.put("password", password);
//                Log.e(TAG, "params: " + params.toString());
//                return super.getParams();
                return params;
            }
        };

        Volley.newRequestQueue(this).add(stringRequest);


    }
}

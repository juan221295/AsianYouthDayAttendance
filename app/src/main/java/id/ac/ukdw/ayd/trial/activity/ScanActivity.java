package id.ac.ukdw.ayd.trial.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.Result;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import id.ac.ukdw.ayd.trial.Endpoints;
import id.ac.ukdw.ayd.trial.R;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler{

    int acara = 0;
    int type = 1;
    String mg_id;
    String event;
    String member_type;
    ZXingScannerView mScannerView;
    String namaDepan ="";
    String namaBelakang ="";
//    int daily_type;
    String participant_id="";
    String did_id;
    String bus_no;
    String acco_type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        if(extras !=null){
            acara = extras.getInt("acara");
            type = extras.getInt("type");
            mg_id = extras.getString("mg_id");
            event = extras.getString("event");
            member_type = extras.getString("member_type");
//            daily_type = extras.getInt("dailyType");
            did_id = extras.getString("did_id");
            bus_no = extras.getString("bus_no");
            acco_type = extras.getString("acco_type");

        }else{
            Toast.makeText(this, "error ga ada acara", Toast.LENGTH_SHORT).show();
        }
        setContentView(R.layout.activity_scan);
//        setContentView(mScannerView);
        startScan();
    }

    @Override
    public void handleResult(Result result) {
//        String finalResult = "";
//        final String hasil = result.getText();
        sign(result.getText());

    }

    private void startScan(){
        mScannerView = new ZXingScannerView(this);
        setContentView(mScannerView);
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();

    }

    private void sign(String qrCode){
        final AlertDialog.Builder builder = new AlertDialog.Builder(ScanActivity.this);
        builder.setTitle("Scan Result");
        builder.setPositiveButton("SCAN", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mScannerView.resumeCameraPreview(ScanActivity.this);
            }
        });
        builder.setNeutralButton("BACK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onBackPressed();
                mScannerView.stopCamera();

            }
        });

        if(type == 1){
            String url = Endpoints.SIGN_EVENT + "qrcode=" + qrCode + "&mg_id=" + mg_id + "&event=" + event;
//        Toast.makeText(ScanActivity.this, "qrcode=" + qrCode + "&acara=" + Integer.toString(acara), Toast.LENGTH_SHORT).show();

            StringRequest stringRequest = new StringRequest(
                    Request.Method.GET,
                    url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject object = new JSONObject(response);
                                if(object.getBoolean("error") == false){
                                    JSONObject pesertaObj = object.getJSONObject("peserta");
//                                stringArrayList.add("nama depan: " + pesertaObj.getString("nama_depan"));
//                                stringArrayList.add("nama belakang: " + pesertaObj.getString("nama_belakang"));
                                    namaDepan = "nama depan: " + pesertaObj.getString("nama_depan");
                                    participant_id = pesertaObj.getString("participant_id");
                                    namaBelakang = "nama belakang: " + pesertaObj.getString("nama_belakang");
                                    if(object.getBoolean("isSign")){
                                        builder.setMessage("Peserta ini sudah presensi");
                                        AlertDialog alert1 = builder.create();
                                        alert1.show();
                                    }else{
                                        builder.setMessage("Sukses!!" + "\n" + namaDepan + "\n" + namaBelakang + "\n" + participant_id);
                                        AlertDialog alert1 = builder.create();
                                        alert1.show();

                                    }
                                }else{
//                                Toast.makeText(ScanActivity.this, "Gada user", Toast.LENGTH_SHORT).show();
                                    //kalo ga ada user nya
                                    builder.setMessage("Gagal!!" + "\n" +"Tidak ada peserta di acara ini");
                                    AlertDialog alert1 = builder.create();
                                    alert1.show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(ScanActivity.this, "Koneksi Error", Toast.LENGTH_SHORT).show();
//                                Toast.makeText(ScanActivity.this, response, Toast.LENGTH_SHORT).show();
//                                builder.setMessage("Error koneksi");
//                                AlertDialog alert1 = builder.create();
//                                alert1.show();
//                            stringArrayList.add("Error Connection");

                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(ScanActivity.this, "Koneksi Error", Toast.LENGTH_SHORT).show();
//                            builder.setMessage("Error volley");
//                            AlertDialog alert1 = builder.create();
//                            alert1.show();
//                        stringArrayList.add("Error Connection");
                        }
                    }
            ){

            };
            Volley.newRequestQueue(this).add(stringRequest);

        }else if(type == 2){
            String url = Endpoints.SIGN_DAILY + "qrcode=" + qrCode + "&member_type=" + member_type;
//        Toast.makeText(ScanActivity.this, "qrcode=" + qrCode + "&acara=" + Integer.toString(acara), Toast.LENGTH_SHORT).show();

            StringRequest stringRequest = new StringRequest(
                    Request.Method.GET,
                    url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject object = new JSONObject(response);
                                if(object.getBoolean("error") == false){
                                    JSONObject participantObj = null;
                                    JSONObject committeeObj = null;

                                    if(!object.isNull("participant")){
                                        participantObj = object.getJSONObject("participant");

                                    }else if(!object.isNull("committee")){
                                        committeeObj = object.getJSONObject("committee");

                                    }

                                    if(participantObj != null){
                                        namaDepan = "nama depan: " + participantObj.getString("first_name");
                                        namaBelakang = "nama belakang: " + participantObj.getString("last_name");
                                        participant_id = participantObj.getString("participant_id");
                                    }else if(committeeObj != null){
                                        namaDepan = "nama depan: " + committeeObj.getString("c_first_name");
                                        namaBelakang = "nama belakang: " + committeeObj.getString("c_last_name");
                                        participant_id = committeeObj.getString("committee_id");

                                    }
                                    builder.setMessage("Sukses!!" + "\n" + namaDepan + "\n" + namaBelakang + "\n" + participant_id);
                                    AlertDialog alert1 = builder.create();
                                    alert1.show();
//                                stringArrayList.add("nama depan: " + pesertaObj.getString("nama_depan"));
//                                stringArrayList.add("nama belakang: " + pesertaObj.getString("nama_belakang"));

                                }else{
//                                Toast.makeText(ScanActivity.this, "Gada user", Toast.LENGTH_SHORT).show();
                                    //kalo ga ada user nya
                                    builder.setMessage("Gagal!!" + "\n" +"Tidak ada panitia peserta yang terdaftar");
                                    AlertDialog alert1 = builder.create();
                                    alert1.show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(ScanActivity.this, "Koneksi Error", Toast.LENGTH_SHORT).show();
//                                Toast.makeText(ScanActivity.this, response, Toast.LENGTH_SHORT).show();
//                                builder.setMessage("Error koneksi");
//                                AlertDialog alert1 = builder.create();
//                                alert1.show();
//                            stringArrayList.add("Error Connection");

                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(ScanActivity.this, "Koneksi Error", Toast.LENGTH_SHORT).show();
//                            builder.setMessage("Error volley");
//                            AlertDialog alert1 = builder.create();
//                            alert1.show();
//                        stringArrayList.add("Error Connection");
                        }
                    }
            ){

            };
            Volley.newRequestQueue(this).add(stringRequest);

        }else if(type == 3){
            String url = Endpoints.SIGN_ACCO + "qrcode=" + qrCode +"&acco_type=" + acco_type + "&bus_no=" + bus_no ;
            StringRequest stringRequest = new StringRequest(
                    Request.Method.GET,
                    url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject object = new JSONObject(response);
                                if(object.getBoolean("error") == false){
                                    JSONObject participantObj = object.getJSONObject("peserta");
//                                        JSONObject committeeObj = null;
                                    participant_id = participantObj.getString("participant_id");
                                    namaDepan = "nama depan: " + participantObj.getString("nama_depan");
                                    namaBelakang = "nama belakang: " + participantObj.getString("nama_belakang");
                                    builder.setMessage("Sukses!!" + "\n" + namaDepan + "\n" + namaBelakang + "\n" + participant_id);
                                    AlertDialog alert1 = builder.create();
                                    alert1.show();
//                                stringArrayList.add("nama depan: " + pesertaObj.getString("nama_depan"));
//                                stringArrayList.add("nama belakang: " + pesertaObj.getString("nama_belakang"));

                                }else{
//                                Toast.makeText(ScanActivity.this, "Gada user", Toast.LENGTH_SHORT).show();
                                    //kalo ga ada user nya
                                    builder.setMessage("Gagal!!" + "\n" +"Tidak ada peserta terdaftar di did ini");
                                    AlertDialog alert1 = builder.create();
                                    alert1.show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(ScanActivity.this, "Koneksi Error", Toast.LENGTH_SHORT).show();
//                                Toast.makeText(ScanActivity.this, response, Toast.LENGTH_SHORT).show();
//                                builder.setMessage("Error koneksi");
//                                AlertDialog alert1 = builder.create();
//                                alert1.show();
//                            stringArrayList.add("Error Connection");

                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(ScanActivity.this, "Koneksi Error", Toast.LENGTH_SHORT).show();
//                            builder.setMessage("Error volley");
//                            AlertDialog alert1 = builder.create();
//                            alert1.show();
//                        stringArrayList.add("Error Connection");
                        }
                    }
            ){

            };
            Volley.newRequestQueue(this).add(stringRequest);
        }


//        return stringArrayList;
    }

    @Override
    public void onBackPressed() {
        mScannerView.stopCamera();
        super.onBackPressed();

    }
}

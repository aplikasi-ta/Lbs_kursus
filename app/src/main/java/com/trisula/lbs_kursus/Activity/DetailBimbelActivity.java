package com.trisula.lbs_kursus.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.trisula.lbs_kursus.KonekDB;
import com.trisula.lbs_kursus.R;
import com.trisula.lbs_kursus.RequestHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class DetailBimbelActivity extends AppCompatActivity {
String id;
ProgressDialog loading;
TextView txtNama,txtKontak,txtAlamat,txtMatpel,txtJadwal,txtBiaya;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_bimbel);
        final Intent intent = getIntent();
        id = intent.getStringExtra("id_bimbel");
        getData(id);

        txtNama = (TextView) findViewById(R.id.user_profile_name);
        txtKontak = (TextView) findViewById(R.id.user_profile_short_bio);
        txtAlamat = (TextView) findViewById(R.id.txtAlamatKursus);
        txtMatpel = (TextView) findViewById(R.id.txtMatpel);
        txtJadwal = (TextView) findViewById(R.id.txtJadwal);
        txtBiaya = (TextView) findViewById(R.id.txtBiaya);
    }

    private void getData(final String Id_user){

        loading = ProgressDialog.show(this,"Please wait...","Fetching...",false,false);

        String url = KonekDB.API_DETAIL_LOKASI+Id_user;

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
                showDetal(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(DetailBimbelActivity.this,error.getMessage().toString(),Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    void showDetal(String json){
        JSONObject jsonObject = null;
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray("result");

            for (int i = 0; i < result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);
                String id_bimbel = jo.getString("id_bimbel");
                String nama_lembaga = jo.getString("nama_lembaga");
                String alamat = jo.getString("alamat");
                String kontak = jo.getString("kontak");
                String matpel = jo.getString("matpel");
                String jadwal = jo.getString("jadwal");
                String biaya = jo.getString("biaya");

                txtNama.setText(nama_lembaga);
                txtKontak.setText(kontak);
                txtAlamat.setText(alamat);
                txtMatpel.setText(matpel);
                txtJadwal.setText(jadwal);
                txtBiaya.setText(biaya);

            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getApplication(),"Data Salah "+e,Toast.LENGTH_LONG).show();
        }
    }



}

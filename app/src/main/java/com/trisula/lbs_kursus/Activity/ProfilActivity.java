package com.trisula.lbs_kursus.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.trisula.lbs_kursus.Kelas.SharedVariabel;
import com.trisula.lbs_kursus.KonekDB;
import com.trisula.lbs_kursus.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ProfilActivity extends AppCompatActivity {
ProgressDialog loading;
TextView txtNama,txtKontak,txtMail,txtAlamat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        getData(LoginActivity.etEmail.getText().toString().trim());

        txtNama = (TextView) findViewById(R.id.user_profile_name);
        txtMail = (TextView) findViewById(R.id.txtEmail);
        txtKontak = (TextView) findViewById(R.id.txtTelp);
        txtAlamat = (TextView) findViewById(R.id.txtAlamatuser);
    }

    private void getData(final String Id_user){

        loading = ProgressDialog.show(this,"Please wait...","Fetching...",false,false);

        String url = KonekDB.API_DETAIL_USER+Id_user;

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
                        Toast.makeText(ProfilActivity.this,error.getMessage().toString(),Toast.LENGTH_LONG).show();
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
                String id_user = jo.getString("id_user");
                String nama_lengkap = jo.getString("nama_lengkap");
                String email = jo.getString("email");
                String alamat = jo.getString("alamat");
                String no_telp = jo.getString("no_telp");

                txtNama.setText(nama_lengkap);
                txtMail.setText(email);
                txtAlamat.setText(alamat);
                txtKontak.setText(no_telp);

            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getApplication(),"Data Salah "+e,Toast.LENGTH_LONG).show();
        }
    }

}

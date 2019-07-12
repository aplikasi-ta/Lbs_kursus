package com.trisula.lbs_kursus.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.trisula.lbs_kursus.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {
EditText etNama,etNotelp,etMail,etPass,etAlamat;
Button btnRegis;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        etNama = (EditText) findViewById(R.id.txtNama);
        etNotelp = (EditText) findViewById(R.id.txtTelp);
        etMail = (EditText) findViewById(R.id.txtMail);
        etPass = (EditText) findViewById(R.id.txtPass);
        etAlamat = (EditText) findViewById(R.id.txtAlamat);
        btnRegis = (Button) findViewById(R.id.btnReg);

        btnRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                simpanPengguna(etNama.getText().toString().trim(),etNotelp.getText().toString().trim(),
                        etMail.getText().toString().trim(),etPass.getText().toString().trim(),etAlamat.getText().toString().trim());
            }
        });

    }


    public void simpanPengguna(final String Nama, final String Telp, final String Email, final String Pass, final String Alamat){
        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("nama", Nama));
                nameValuePairs.add(new BasicNameValuePair("no_telp", Telp));
                nameValuePairs.add(new BasicNameValuePair("email", Email));
                nameValuePairs.add(new BasicNameValuePair("pass", Pass));
                nameValuePairs.add(new BasicNameValuePair("alamat", Alamat));

                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(
                            "https://esri.000webhostapp.com/api_lbs/regis.php");
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                    HttpResponse response = httpClient.execute(httpPost);

                    HttpEntity entity = response.getEntity();

                } catch (ClientProtocolException e) {
                    e.printStackTrace();

                } catch (IOException e) {
                    e.printStackTrace();

                }
                return "success";
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                if(result.equalsIgnoreCase("success")){
                    Toast.makeText(getApplication(),"Data tersimpan",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplication(),"Terjadi kesalahan Sistem",Toast.LENGTH_LONG).show();
                }
            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(Nama, Telp, Email, Pass, Alamat);
    }

}

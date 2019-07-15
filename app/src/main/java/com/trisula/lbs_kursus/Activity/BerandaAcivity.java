package com.trisula.lbs_kursus.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.trisula.lbs_kursus.R;

public class BerandaAcivity extends AppCompatActivity {
ImageButton btn_cek;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beranda_acivity);
        btn_cek = (ImageButton) findViewById(R.id.btn_cek);

        btn_cek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BerandaAcivity.this,MapsActivity.class);
                startActivity(intent);
            }
        });

    }
}

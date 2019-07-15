package com.trisula.lbs_kursus.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.trisula.lbs_kursus.R;

public class BerandaAcivity extends AppCompatActivity {
Button btnMenu1, btnMenu2,btnMenu3,btnMenu4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beranda_acivity);
        btnMenu1 = (Button) findViewById(R.id.btnmenu1);
        btnMenu2 = (Button) findViewById(R.id.btnmenu2);
        btnMenu3 = (Button) findViewById(R.id.btnmenu3);
        btnMenu4 = (Button) findViewById(R.id.btnmenu4);

        btnMenu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BerandaAcivity.this,MapsActivity.class);
                startActivity(intent);
            }
        });

    }
}

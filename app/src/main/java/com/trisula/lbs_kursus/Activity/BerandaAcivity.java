package com.trisula.lbs_kursus.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.trisula.lbs_kursus.R;

public class BerandaAcivity extends AppCompatActivity {
Button btnMenu1, btnMenu2,btnMenu3,btnMenu4,btnMenu5;
NiftyDialogBuilder dialogs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beranda_acivity);
        btnMenu1 = (Button) findViewById(R.id.btnmenu1);
        btnMenu2 = (Button) findViewById(R.id.btnmenu2);
        btnMenu3 = (Button) findViewById(R.id.btnmenu3);
        btnMenu4 = (Button) findViewById(R.id.btnmenu4);
        dialogs = NiftyDialogBuilder.getInstance(this);

        btnMenu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BerandaAcivity.this,MapsActivity.class);
                startActivity(intent);
            }
        });

        btnMenu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BerandaAcivity.this, ProfilActivity.class);
                startActivity(intent);
            }
        });

        btnMenu3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent = new Intent(BerandaAcivity.this, BantuanActivity.class);
               startActivity(intent);
            }
        });

        btnMenu4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        BerandaAcivity.this);

                alertDialogBuilder.setTitle("Keluar dari aplikasi ?");
                alertDialogBuilder
                        .setMessage("Klik Ya untuk keluar!")
                        .setIcon(R.mipmap.ic_launcher)
                        .setCancelable(false)
                        .setPositiveButton("Ya",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                Intent intent = new Intent(BerandaAcivity.this, LoginActivity.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("Tidak",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                // membuat alert dialog dari builder
                AlertDialog alertDialog = alertDialogBuilder.create();

                // menampilkan alert dialog
                alertDialog.show();
            }

        });
    }
}

package com.trisula.lbs_kursus.Activity;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.trisula.lbs_kursus.KonekDB;
import com.trisula.lbs_kursus.R;
import com.trisula.lbs_kursus.RequestHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class MapsActivity extends FragmentActivity{

    private GoogleMap mMap;
    GoogleMap googleMap;
    SupportMapFragment mapFragment;
    Location location;
    LocationManager locationManager;
    ProgressDialog loading;
    String JSON_STRING;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        if ((ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)) {

        }
        location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
        getJSON();
    }


    private void getJSON(){
        class GetJSON extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(MapsActivity.this,"Mengambil Data","Mohon Tunggu...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                showMarker(s);
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(KonekDB.API_LOKASI);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }


    void showMarker(String json){
        JSONObject jsonObject = null;
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray("result");

            for (int i = 0; i < result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);
                String nama_lembaga = jo.getString("nama_lembaga");
                String alamat = jo.getString("alamat");
                Double lati = jo.getDouble("kor_lati");
                Double longi = jo.getDouble("kor_longi");

                Location_based_service(nama_lembaga,alamat,lati,longi);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getApplication(),"Data Salah "+e,Toast.LENGTH_LONG).show();
        }
    }


    void Location_based_service(final String Nama_lembaga, final String Alamat, final Double Lati_c, final Double Longi_c){
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-5.397140, 105.266789), 12.0f));

                Criteria criteria = new Criteria();
                if ((ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                        ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)) {
                    mMap.setMyLocationEnabled(true);
                }

                Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
                if (location!=null){
                    LatLng MyLocation = new LatLng(location.getLatitude(), location.getLongitude());

                    LatLng ubl = new LatLng(Lati_c, Longi_c);
                    MarkerOptions marker = null;
                    marker = new MarkerOptions()
                            .position(ubl)
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.teacher));

                    double earthRadius = 3958.75; // in miles, change to 6371 for kilometer output
                    double dLat = Math.toRadians(location.getLatitude()-Lati_c);
                    double dLng = Math.toRadians(location.getLongitude()-Longi_c);
                    double sindLat = Math.sin(dLat / 2);
                    double sindLng = Math.sin(dLng / 2);
                    double a = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)
                            * Math.cos(Math.toRadians(Lati_c)) * Math.cos(Math.toRadians(location.getLatitude()));
                    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
                    double dist = earthRadius * c;

                    DecimalFormat df = new DecimalFormat("#.#");
                    Double jarak = Double.parseDouble(df.format(dist));

                    if(jarak >= 2){
                        Log.d("Jarak ",String.valueOf(jarak));
                    }else{
                        mMap.addMarker(marker.title(Nama_lembaga).snippet(Alamat+" - "+jarak));
                        mMap.getUiSettings().setZoomControlsEnabled(true);
                        mMap.getUiSettings().setZoomGesturesEnabled(true);
                        mMap.addMarker(new MarkerOptions().position(MyLocation).title("Posisi Anda").snippet(""+location.getLatitude()));
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(MyLocation,13));
                    }

                }else{
                    Toast.makeText(getApplication(), "Lokasi Tidak Di temukan", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


}

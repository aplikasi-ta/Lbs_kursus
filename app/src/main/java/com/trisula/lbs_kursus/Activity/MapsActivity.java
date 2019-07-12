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
import android.os.Bundle;
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
import com.trisula.lbs_kursus.R;

public class MapsActivity extends FragmentActivity{

    private GoogleMap mMap;
    GoogleMap googleMap;
    SupportMapFragment mapFragment;
    Location location;
    LocationManager locationManager;
    ProgressDialog loading;

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
        Location_based_service();
    }


    void Location_based_service(){
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-5.397140, 105.266789), 12.0f));

                LatLng ubl = new LatLng(-5.397140, 105.266792);
                MarkerOptions marker = null;
                marker = new MarkerOptions()
                        .position(ubl)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.teacher));
                mMap.addMarker(marker);
                mMap.getUiSettings().setZoomControlsEnabled(true);
                mMap.getUiSettings().setZoomGesturesEnabled(true);

                Criteria criteria = new Criteria();
                if ((ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                        ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)) {
                    mMap.setMyLocationEnabled(true);
                }

                Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
                if (location!=null){
                    LatLng MyLocation = new LatLng(location.getLatitude(), location.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(MyLocation).title("Posisi Anda").snippet(""+location.getLatitude()));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(MyLocation,13));
                }else{
                    Toast.makeText(getApplication(), "Lokasi Tidak Di temukan", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


}

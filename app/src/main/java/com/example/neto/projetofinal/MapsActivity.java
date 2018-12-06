package com.example.neto.projetofinal;

import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.neto.activitys.R;
import com.example.neto.projetofinal.bancodedados.evento.Evento;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Evento evento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        this.evento = (Evento)getIntent().getSerializableExtra("EVENTO");

        setTitle(evento.getNomeLocal());

    }

    @Override
    public boolean onSupportNavigateUp() {
        super.onBackPressed();
        return true;
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        Geocoder geoCoder = new Geocoder(this,Locale.getDefault());

        try {
            List<Address> addresses =
                    geoCoder.getFromLocationName(evento.getNomeLocal(), 1);

            if(addresses.size()>0){
                LatLng local = new LatLng(addresses.get(0).getLatitude(),addresses.get(0).getLongitude());

                mMap.addMarker(new MarkerOptions().position(local).title(evento.getComplemento()));
                //mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(local,15));
                //mMap.animateCamera(CameraUpdateFactory.zoomTo(10),2000,null);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(local,17));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

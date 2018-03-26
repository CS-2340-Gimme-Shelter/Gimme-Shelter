package com.example.hkamath.gimmeshelterapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.hkamath.gimmeshelterapp.model.Shelter;
import com.example.hkamath.gimmeshelterapp.model.ShelterHandler;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class ShelterMap extends AppCompatActivity implements OnMapReadyCallback {

    private List<Shelter> shelters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Retrieve the content view that renders the map.
        setContentView(R.layout.activity_shelter_map);
        // Get the SupportMapFragment and request notification
        // when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Check to see if shelters was passed in the intent
        Object[] ins = (Object[]) getIntent().getSerializableExtra("shelters");
        // If not just use all shelters
        if (ins == null) {
            shelters = ShelterHandler.getShelters();
        } else {
            shelters = new ArrayList<Shelter>();
            for (Object o: ins) {
                shelters.add((Shelter) o);
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        LatLng first = null;
        for (Shelter shelter: shelters) {
            LatLng shelt = new LatLng(shelter.getLatitude(), shelter.getLongitude());
            googleMap.addMarker(new MarkerOptions().position(shelt)
                    .snippet(shelter.getUniqueKey() + ""));
            Log.d("Map", shelter.getUniqueKey() + "");
            if (first == null) {
                first = shelt;
            }
        }

        if (first != null) {
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(first));
        }
        googleMap.moveCamera(CameraUpdateFactory.zoomTo(10));

        googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

            // Use default InfoWindow frame
            @Override
            public View getInfoWindow(Marker arg0) {
                return null;
            }

            // Defines the contents of the InfoWindow
            @Override
            public View getInfoContents(Marker marker) {

                // Getting view from the layout file info_window_layout
                View v = getLayoutInflater().inflate(R.layout.shelter_map_icon, null);

                TextView mapName = (TextView) v.findViewById(R.id.map_icon_name);
                TextView mapPhone = (TextView) v.findViewById(R.id.map_icon_phone);
                TextView mapAddress = (TextView) v.findViewById(R.id.map_icon_address);

                Shelter shelter = ShelterHandler.getShelterById(Long.parseLong(marker.getSnippet()));

                mapName.setText(shelter.getShelterName());
                mapPhone.setText(shelter.getPhoneNumber());
                mapAddress.setText(shelter.getAddress());

                // Returning the view containing InfoWindow contents
                return v;

            }
        });

        googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Shelter shelter = ShelterHandler.getShelterById(Long.parseLong(marker.getSnippet()));

                Intent intent = new Intent(ShelterMap.this, ShelterDetails.class);
                // Eek this is not great
                intent.putExtra("Shelter", ShelterHandler.getShelters().indexOf(shelter));
                startActivity(intent);
            }
        });
    }
}

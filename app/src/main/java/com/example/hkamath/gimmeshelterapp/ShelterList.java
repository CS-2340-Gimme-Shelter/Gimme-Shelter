package com.example.hkamath.gimmeshelterapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.hkamath.gimmeshelterapp.model.Shelter;
import com.example.hkamath.gimmeshelterapp.model.ShelterHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ShelterList extends AppCompatActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelter_list);

        List<Shelter> shelters;
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

        listView = findViewById(R.id.shelter_list);
        listView.setAdapter(new ArrayAdapter<Shelter>(this, R.layout.shelter_list_item, shelters) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                // Get the data item for this position
                Shelter shelter = getItem(position);
                // Check if an existing view is being reused, otherwise inflate the view
                if (convertView == null) {
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.shelter_list_item, parent, false);
                }
                // Lookup view for data population
                TextView name = (TextView) convertView.findViewById(R.id.shelter_list_item_name);
                TextView restriction = (TextView) convertView.findViewById(R.id.shelter_list_item_constraint);
                TextView address = (TextView) convertView.findViewById(R.id.shelter_list_item_address);
                // Populate the data into the template view using the data object
                name.setText(shelter.getShelterName());
                restriction.setText(getString(R.string.shelter_item_restiction_label) + " " + shelter.getRestrictionsString());
                address.setText(getString(R.string.shelter_item_address_label) + " " + shelter.getAddress());

                // Return the completed view to render on screen
                return convertView;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(ShelterList.this, ShelterDetails.class);
                intent.putExtra("Shelter", position);
                startActivity(intent);
            }
        });
    }
}

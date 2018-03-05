package com.example.hkamath.gimmeshelterapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.hkamath.gimmeshelterapp.model.Shelter;

public class ShelterDetails extends AppCompatActivity {

    private TextView nameField;
    private TextView capacityField;
    private TextView phoneField;
    private TextView restrictionField;
    private TextView notesField;
    private TextView addressFields;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelter_details);

        nameField = findViewById(R.id.shelter_name_text);
        capacityField = findViewById(R.id.shelter_capacity_text);
        phoneField = findViewById(R.id.shelter_phone_number_text);
        restrictionField = findViewById(R.id.shelter_restrictions_text);
        notesField = findViewById(R.id.shelter_special_notes_text);
        addressFields = findViewById(R.id.shelter_address_text);

        int position = (Integer) getIntent().getSerializableExtra("Shelter");
        Shelter shelter = Shelter.getShelters().get(position);

        nameField.setText(shelter.getShelterName());
        capacityField.setText(shelter.getCapacity() + "");
        phoneField.setText(shelter.getPhoneNumber());
        restrictionField.setText(shelter.getRestrictions());
        notesField.setText(shelter.getSpecialNotes());
        addressFields.setText(shelter.getAddress()
        );
    }
}

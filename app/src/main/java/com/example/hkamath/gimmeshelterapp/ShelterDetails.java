package com.example.hkamath.gimmeshelterapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hkamath.gimmeshelterapp.model.APIUtil;
import com.example.hkamath.gimmeshelterapp.model.Shelter;
import com.example.hkamath.gimmeshelterapp.model.ShelterHandler;
import com.example.hkamath.gimmeshelterapp.model.User;

public class ShelterDetails extends AppCompatActivity {

    private TextView nameField;
    private TextView capacityField;
    private TextView phoneField;
    private TextView restrictionField;
    private TextView notesField;
    private TextView addressFields;

    private LinearLayout pickerLayout;
    private NumberPicker bedNumberPicker;
    private Button reserveBedsButton;

    private LinearLayout removerLayout;
    private TextView bedCount;
    private Button removeBed;

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

        pickerLayout = findViewById(R.id.shelter_claim_linear);
        bedNumberPicker = findViewById(R.id.shelter_bed_number);
        reserveBedsButton = findViewById(R.id.shelter_reserve_bed);

        removerLayout = findViewById(R.id.shelter_remove_linear);
        bedCount = findViewById(R.id.shelter_num_beds);
        removeBed = findViewById(R.id.shelter_remove_bed);

        int position = (Integer) getIntent().getSerializableExtra("Shelter");
        Shelter shelter = ShelterHandler.getShelters().get(position);

        // Set text fields to correct Strings
        nameField.setText(shelter.getShelterName());
        capacityField.setText(shelter.getCapacity() + "");
        phoneField.setText(shelter.getPhoneNumber());
        restrictionField.setText(shelter.getRestrictionsString());
        notesField.setText(shelter.getSpecialNotes());
        addressFields.setText(shelter.getAddress());

        // Setup bedNumberPicker

        User user = User.getCurrentUser();
        if (shelter.getVisitors().containsKey(user.getFirebaseUser().getUid()) && shelter.getVisitors().get(user.getFirebaseUser().getUid()) != 0
                ) {
            setupRemover(shelter.getUniqueKey());
        } else {
            setupPicker(shelter.getUniqueKey());
        }
    }

    private void setupRemover(long shelterId) {
        pickerLayout.setVisibility(View.GONE);
        removerLayout.setVisibility(View.VISIBLE);
        User user = User.getCurrentUser();
        Shelter shelter = ShelterHandler.getShelterById(shelterId);

        bedCount.setText(getString(R.string.num_beds) + shelter.getVisitors().get(user.getFirebaseUser().getUid()) + " beds reserved.");
        removeBed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = User.getCurrentUser();

                Log.d("Shelter", "Remove");
                Shelter shelter = ShelterHandler.getShelterById(shelterId);
                APIUtil.removeShelterGuest(shelter, user);
                setupPicker(shelterId);
            }
        });
    }

    public void setupPicker(long shelterId) {
        removerLayout.setVisibility(View.GONE);
        pickerLayout.setVisibility(View.VISIBLE);
        Shelter shelter = ShelterHandler.getShelterById(shelterId);

        int max = (int) shelter.getCapacity() - shelter.getVisitors().values().stream().mapToInt(Number::intValue).sum();
        bedNumberPicker.setMaxValue(max);
        bedNumberPicker.setMinValue(max > 1 ? 1 : max);
        reserveBedsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = User.getCurrentUser();

                if (user.getBedRequestedShelter() >= 0) {
                    Toast.makeText(ShelterDetails.this, "You already have beds selected at another shelter.", Toast.LENGTH_LONG).show();
                    return;
                }

                Shelter shelter = ShelterHandler.getShelterById(shelterId);
                shelter.getVisitors().put(user.getFirebaseUser().getUid(), bedNumberPicker.getValue());
                boolean success = APIUtil.giveShelterGuest(shelter, user, bedNumberPicker.getValue());
                if (!success) {
                    Toast.makeText(ShelterDetails.this, "That many beds are not available", Toast.LENGTH_SHORT).show();
                } else {
                    setupRemover(shelterId);
                }
            }
        });
    }
}

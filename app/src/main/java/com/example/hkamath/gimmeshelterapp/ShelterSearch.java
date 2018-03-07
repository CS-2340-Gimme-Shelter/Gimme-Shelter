package com.example.hkamath.gimmeshelterapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hkamath.gimmeshelterapp.model.Shelter;
import com.example.hkamath.gimmeshelterapp.model.ShelterGender;
import com.example.hkamath.gimmeshelterapp.model.ShelterHandler;
import com.example.hkamath.gimmeshelterapp.model.ShelterRestriction;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ShelterSearch extends AppCompatActivity {

    private Spinner genderSpinner;
    private Spinner restrictionSpinner;
    private Button restrictionButton;
    private LinearLayout restrictionList;
    private TextView genderView;
    private Button search;

    private TextView nameInput;

    private List<ShelterRestriction> restrictions = new ArrayList<ShelterRestriction>();
    private ShelterRestriction currentRestriction;
    private ShelterGender currentGender = ShelterGender.UNRESTRICTED;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelter_search);
        setTitle(R.string.search_title);

        nameInput = findViewById(R.id.search_name);
        genderSpinner = findViewById(R.id.search_gender_spinner);
        restrictionSpinner = findViewById(R.id.search_restriction_spinner);
        restrictionButton = findViewById(R.id.search_add_restriction_button);
        restrictionList = findViewById(R.id.search_specs_list);
        search = findViewById(R.id.search_run_search);

        // Setup gender spinner
        genderSpinner.setAdapter(new ArrayAdapter<ShelterGender>(this,
                android.R.layout.simple_list_item_1, ShelterGender.values()));
        genderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                Log.d("Boop", adapterView.getItemAtPosition(position).toString());
                currentGender = (ShelterGender) adapterView.getItemAtPosition(position);
                if (genderView == null) {
                    genderView = new TextView(ShelterSearch.this);
                    restrictionList.addView(genderView);
                }

                genderView.setText(getString(R.string.select_gender) + " " + currentGender.toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // Setup restriction spinner
        restrictionSpinner.setAdapter(new ArrayAdapter<ShelterRestriction>(this,
                android.R.layout.simple_list_item_1, ShelterRestriction.values()));
        restrictionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                Log.d("Boop", adapterView.getItemAtPosition(position).toString());
                currentRestriction = (ShelterRestriction) adapterView.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        
        // Setup restriction button
        restrictionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (restrictions.contains(currentRestriction)) {
                    Toast.makeText(ShelterSearch.this, R.string.restriction_already_added, Toast.LENGTH_SHORT).show();
                } else if (currentRestriction == ShelterRestriction.NONE) {
                    restrictions.clear();
                    restrictionList.removeAllViews();
                    restrictionList.addView(genderView);
                    Toast.makeText(ShelterSearch.this, getString(R.string.removing_restrictions), Toast.LENGTH_SHORT).show();
                } else {
                    restrictions.add(currentRestriction);
                    TextView newView = new TextView(ShelterSearch.this);
                    newView.setText(currentRestriction.toString());
                    restrictionList.addView(newView);
                }
            }
        });

        // On search clicked
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShelterSearch.this, ShelterList.class);
                ShelterRestriction[] restricts = new ShelterRestriction[0];
                intent.putExtra("shelters",
                        ShelterHandler.getOrderedShelterList(currentGender, nameInput.getText().toString(), restrictions.toArray(restricts)));
                startActivity(intent);
            }
        });
    }
}

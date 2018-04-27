package com.example.hkamath.gimmeshelterapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.hkamath.gimmeshelterapp.model.APIUtil;
import com.example.hkamath.gimmeshelterapp.model.Shelter;
import com.example.hkamath.gimmeshelterapp.model.ShelterFetchCallback;
import com.example.hkamath.gimmeshelterapp.model.User;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class HomePage extends AppCompatActivity implements ShelterFetchCallback {

    private Button logoutButton;
    private Button shelterListButton;
    private Button shelterSearchButton;
    private Button mapButton;
    private Button banButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        setTitle("Hello " + User.getCurrentUser().getFirstName());

        // Locate the buttons in activity_main.xml
        logoutButton = (Button) findViewById(R.id.logout_button);
        shelterListButton = (Button) findViewById(R.id.shelter_list_button);
        mapButton = (Button) findViewById(R.id.shelter_map_button);
        shelterSearchButton = (Button) findViewById(R.id.shelter_search_button);
        banButton = (Button) findViewById(R.id.ban_button);

        APIUtil.GrabSheltersTask sheltersTask = new APIUtil.GrabSheltersTask();
        sheltersTask.fetch(this);

        if (User.getCurrentUser().isAdmin()) {
            banButton.setVisibility(View.VISIBLE);
        }

        banButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(HomePage.this, BanPage.class);
                startActivity(myIntent);
            }
        });

        // Capture button clicks
        logoutButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                FirebaseAuth.getInstance().signOut();

                // Go back to login screen
                Intent myIntent = new Intent(HomePage.this,
                        MainPage.class);
                startActivity(myIntent);
            }
        });

        shelterListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Switch to List View
                Intent myIntent = new Intent(HomePage.this,
                        ShelterList.class);
                startActivity(myIntent);
            }
        });

        shelterSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(HomePage.this,
                        ShelterSearch.class);
                startActivity(myIntent);
            }
        });

        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Switch to List View
                Toast.makeText(HomePage.this, "Not implemented yet.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onFail() {
        Toast.makeText(this, R.string.shelters_didnt_load, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void sheltersFetched(List<Shelter> shelters) {
        Log.d("Shelters", shelters.toString());
    }
}

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

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        setTitle("Hello " + User.getCurrentUser().getFirstName());

        // Locate the button in activity_main.xml
        button = (Button) findViewById(R.id.logout_button);

        APIUtil.GrabSheltersTask sheltersTask = new APIUtil.GrabSheltersTask();
        sheltersTask.fetch(this);

        // Capture button clicks
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                FirebaseAuth.getInstance().signOut();

                // Start NewActivity.class
                Intent myIntent = new Intent(HomePage.this,
                        MainPage.class);
                startActivity(myIntent);
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

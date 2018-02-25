package com.example.hkamath.gimmeshelterapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.hkamath.gimmeshelterapp.model.User;

public class HomePage extends AppCompatActivity {

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        setTitle("Hello " + User.getCurrentUser().getFirstName());

        // Locate the button in activity_main.xml
        button = (Button) findViewById(R.id.logout_button);

        // Capture button clicks
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                // Start NewActivity.class
                Intent myIntent = new Intent(HomePage.this,
                        MainPage.class);
                startActivity(myIntent);
            }
        });
    }
}

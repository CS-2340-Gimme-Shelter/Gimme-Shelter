package com.example.hkamath.gimmeshelterapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainPage extends AppCompatActivity {

    private Button loginButton;
    private Button registerButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from activity_main.xml
        setContentView(R.layout.activity_main_page);

        // Locate the login button in activity_main.xml
        loginButton = (Button) findViewById(R.id.login_button);

        // Capture login button clicks
        loginButton.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {

                // Start NewActivity.class
                Intent myIntent = new Intent(MainPage.this,
                        LoginScreen.class);
                startActivity(myIntent);
            }
        });

        // Locate the register button in activity_main.xml
        registerButton = (Button) findViewById(R.id.register_button);

        // Capture register button clicks
        registerButton.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {

                // Start NewActivity.class
                Intent myIntent = new Intent(MainPage.this,
                        RegistrationScreen.class);
                startActivity(myIntent);
            }
        });
    }
}

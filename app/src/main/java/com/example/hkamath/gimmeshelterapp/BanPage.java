package com.example.hkamath.gimmeshelterapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.hkamath.gimmeshelterapp.model.APIUtil;

public class BanPage extends AppCompatActivity {

    EditText banEditText;
    Button banUser;
    Button unBanUser;
    APIUtil.BanUserTask banUserTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ban_page);

        banEditText = (EditText) findViewById(R.id.ban_edit_text);
        banUser = (Button) findViewById(R.id.ban_user_button);
        unBanUser = (Button) findViewById(R.id.unban_user_button);



        banUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (banEditText.getText().toString() != "") {
                    banUserTask = new APIUtil.BanUserTask(banEditText.getText().toString());
                    banUserTask.banUser(true);
                    finish();
                }
            }
        });

        unBanUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (banEditText.getText().toString() != "") {
                    banUserTask = new APIUtil.BanUserTask(banEditText.getText().toString());
                    banUserTask.banUser(false);
                    finish();
                }
            }
        });

    }
}

package com.curryware.ws1infoviewer;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

public class UserDetailActivity extends AppCompatActivity {

    Toolbar settings_toolbar;
    TextView textView_User_Name;
    TextView textView_User_Full_Name;
    TextView textView_User_Domain;
    TextView textView_User_ID;
    TextView textView_User_UPN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

        settings_toolbar = findViewById(R.id.settings_toolbar);
        textView_User_Name = findViewById(R.id.detail_user_name);
        textView_User_Full_Name = findViewById(R.id.textViewFullName);
        textView_User_Domain = findViewById(R.id.textViewUserDomain);
        textView_User_ID = findViewById(R.id.textViewUserID);
        textView_User_UPN = findViewById(R.id.textViewUPN);

        Intent intent = getIntent();
        String userName = intent.getStringExtra("USER_NAME");
        String userFullName = intent.getStringExtra("USER_FULL_NAME");
        String userDomain = intent.getStringExtra("USER_DOMAIN");
        String userID = intent.getStringExtra("USER_ID");
        String userUPN = intent.getStringExtra("USER_UPN");

        textView_User_Name.setText(userName);
        textView_User_Full_Name.setText(userFullName);
        textView_User_Domain.setText(userDomain);
        textView_User_ID.setText(userID);
        textView_User_UPN.setText(userUPN);

        setSupportActionBar(settings_toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}

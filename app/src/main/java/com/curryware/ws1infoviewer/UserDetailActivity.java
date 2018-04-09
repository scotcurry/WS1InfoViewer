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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

        settings_toolbar = findViewById(R.id.settings_toolbar);
        textView_User_Name = findViewById(R.id.detail_user_name);

        Intent intent = getIntent();
        String userName = intent.getStringExtra("USER_NAME");
        textView_User_Name.setText(userName);

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

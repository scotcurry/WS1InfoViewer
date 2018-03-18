package com.curryware.ws1infoviewer;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;


public class SettingsActivity extends AppCompatActivity {

    final static String TAG = SettingsActivity.class.getSimpleName();

    Toolbar settings_toolbar;
    boolean changes_not_saved;
    Spinner tenant_domain;
    FloatingActionButton save_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        settings_toolbar = findViewById(R.id.settings_toolbar);
        tenant_domain = findViewById(R.id.spinnerTenantDomain);
        save_button = findViewById(R.id.floating_button_save);

        changes_not_saved = false;
        // For this code take a look at https://developer.android.com/training/implementing-navigation/nav-drawer.html
        setSupportActionBar(settings_toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Floating Button Clicked");
            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.tenant_options, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tenant_domain.setAdapter(adapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}

package com.curryware.ws1infoviewer;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import android.widget.Spinner;
import android.widget.TextView;


public class SettingsActivity extends AppCompatActivity {

    final static String TAG = SettingsActivity.class.getSimpleName();

    Toolbar settings_toolbar;
    Spinner tenant_domain;
    TextView appVersionText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        appVersionText = findViewById(R.id.textViewAppVersion);

        getApplictionVersion();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void getApplictionVersion() {

        String versionName = "Not Found";
        try {
            PackageManager packageManager = getApplicationContext().getPackageManager();
            String packageName = getApplicationContext().getPackageName();
            PackageInfo packageInfo = packageManager.getPackageInfo(packageName, 0);
            versionName = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        String versionText = getString(R.string.version_string);
        versionText = versionText + versionName;
        appVersionText.setText(versionText);
    }
}

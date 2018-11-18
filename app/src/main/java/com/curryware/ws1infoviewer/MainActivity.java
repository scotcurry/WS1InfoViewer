package com.curryware.ws1infoviewer;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.RestrictionsManager;
import android.content.SharedPreferences;
import android.os.UserManager;
import androidx.annotation.NonNull;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.crittercism.app.Crittercism;
import com.google.gson.Gson;

import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity {

    private Activity activity;
    final static String TAG = MainActivity.class.getSimpleName();

    final static MediaType mediaTypeJSON = MediaType.parse("application/json; charset=utf-8");
    final static String heathCheckEndpoint = "SAAS/API/1.0/REST/system/health";
    final static String loginEndpoint = "SAAS/API/1.0/REST/auth/system/login";
    final static String PREF_ADMIN_USERNAME = "adminUserName";
    final static String PREF_ADMIN_PASSWORD = "adminPassword";
    final static String PREF_TENANT_NAME = "ws1TenantName";
    final static String AUTH_TOKEN_TO_PASS = "com.curryware.ws1infoviewer.AUTH_TOKEN";
    final static String TENANT_URL_TO_PASS = "com.curryware.ws1infoviewer.TENANT_URL";
    final static String TENANT_APP_CONFIG = "tenant_name";
    final static String BUILD_CONST = "Build";

    EditText tenantNameEdit;
    EditText adminUserNameEdit;
    EditText adminPasswordEdit;
    TextView healthCheckConnection;
    Button connectButton;
    Button listUsersButton;
    DrawerLayout navDrawer;
    NavigationView navView;
    Toolbar toolbar;
    Spinner tenant_domain;

    OkHttpClient client;
    String CURRENT_AUTH_TOKEN;
    String TENANT_URL;

    BroadcastReceiver restrictionsReceiver;
    AppSettings appSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity = this;

        tenantNameEdit = findViewById(R.id.ws1_tenant);
        adminUserNameEdit = findViewById(R.id.admin_user_name);
        adminPasswordEdit = findViewById(R.id.admin_password);
        tenant_domain = findViewById(R.id.spinnerTenantDomain);
        navDrawer = findViewById(R.id.drawer_layout);
        navView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        Crittercism.initialize(getApplicationContext(), "e7898e1d9aa64cdf9ade31584a8163ca00555300");

        // For this code take a look at https://developer.android.com/training/implementing-navigation/nav-drawer.html
        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;
        final androidx.appcompat.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_hamburger_menu);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.tenant_options, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tenant_domain.setAdapter(adapter);

        connectButton = findViewById(R.id.buttonConnect);
        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "Calling handleConnection");
                handleConnectionButton();
            }
        });

        listUsersButton = findViewById(R.id.buttonListUsers);
        listUsersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TENANT_URL = tenantNameEdit.getText().toString() + "." + tenant_domain.getSelectedItem().toString();
                Intent intent = new Intent(activity, ListUserActivity.class);
                intent.putExtra(AUTH_TOKEN_TO_PASS, CURRENT_AUTH_TOKEN);
                intent.putExtra(TENANT_URL_TO_PASS, TENANT_URL);
                startActivity(intent);
            }
        });

        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                navDrawer.closeDrawers();
                switch (item.getItemId()) {
                    case R.id.nav_settings:
                        Log.i(TAG, "Clicked on Settings");
                        Intent intent = new Intent(activity, SettingsActivity.class);
                        startActivity(intent);
                        break;
                }
                return true;
            }
        });

        Crittercism.leaveBreadcrumb("On create breadcrumb!");
        getAppRestrictions();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                navDrawer.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {

        super.onStart();
        registerRestrictionsReceiver();

        appSettings = getSavedPreferences();
        if (appSettings == null) {
            Crittercism.leaveBreadcrumb("Don't have settings");
        }
    }

    @Override
    protected void onResume() {

        super.onResume();
        getAppRestrictions();
    }

    @Override
    protected void onStop() {

        super.onStop();
        unregisterReceiver(restrictionsReceiver);
    }

    void getSystemHealthJSON() {

        Crittercism.leaveBreadcrumb("Calling getSystemHealth");
        client = HttpHelpers.getInstance();

        String healthCheckURI = tenantNameEdit.getText() + "." + tenant_domain.getSelectedItem().toString();
        HttpUrl url = HttpHelpers.getFormattedURL(healthCheckURI, heathCheckEndpoint);

        Request request = new Request.Builder()
                .url(url)
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                Log.i(TAG, "Call Failed: " + e.getMessage());
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        healthCheckConnection.setVisibility(View.VISIBLE);
                        healthCheckConnection.setText(getString(R.string.connection_failed));
                        healthCheckConnection.setTextColor(ContextCompat.getColor(activity, R.color.error_red));

                        Toast.makeText(activity, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    Log.i(TAG, response.body().string());
                    throw new IOException("Unexpected Code: " + response);
                } else {
                    Log.i(TAG, "Call Succeeded: " + response.toString());
                    final ResponseBody body = response.body();
                    Gson gson = new Gson();
                    final SystemHealth sysHealth = gson.fromJson(body.string(), SystemHealth.class);

                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String buildVersion = sysHealth.getBuildVersion();
                            int startOfBuild = buildVersion.indexOf(BUILD_CONST);
                            buildVersion = buildVersion.substring(0, startOfBuild - 1);
                            Log.i(TAG, "Build Version: " + buildVersion);

                            Snackbar
                                    .make(findViewById(R.id.containerLayout), "Connected - Version: " + buildVersion, Snackbar.LENGTH_LONG)
                                    .show();
                        }
                    });
                    getAuthToken();
                }
            }
        });
    }

    void getAuthToken() {

        LoginCredentials loginCreds = new LoginCredentials();
        loginCreds.setUsername(adminUserNameEdit.getText().toString());
        loginCreds.setPassword(adminPasswordEdit.getText().toString());
        loginCreds.setIssueToken("true");

        final Gson gson = new Gson();
        String loginJSON = gson.toJson(loginCreds);
        Log.i(TAG, loginJSON);
        RequestBody body = RequestBody.create(mediaTypeJSON, loginJSON);

        String vidmDomain = tenantNameEdit.getText() + "." + tenant_domain.getSelectedItem().toString();
        HttpUrl loginURI = HttpHelpers.getFormattedURL(vidmDomain, loginEndpoint);
        Request request = new Request.Builder()
                .url(loginURI)
                .post(body)
                .addHeader("Accept", "application/json")
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                Log.e(TAG, "Error attempting to login: " + e.getMessage());
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(activity, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i(TAG, "Login call returned");
                if (!response.isSuccessful()) {
                    final String toastString = response.body().string();
                    Log.i(TAG, "Error: " + toastString);
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(activity, toastString, Toast.LENGTH_LONG).show();
                        }
                    });
                    throw new IOException("Unexpected Code: " + response);
                } else {
                    LoginResponse loginResponse = gson.fromJson(response.body().string(), LoginResponse.class);
                    Log.i(TAG, "Session Token: " + loginResponse.getSessionToken());
                    CURRENT_AUTH_TOKEN = loginResponse.getSessionToken();

                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            listUsersButton.setEnabled(true);
                            handleSaveSettings();
                        }
                    });
                }
            }
        });
    }

    private void handleSaveSettings() {

        SharedPreferences sharedPrefs = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();

        String adminUserName = adminUserNameEdit.getText().toString();
        String adminPassword = adminPasswordEdit.getText().toString();

        byte[] userNameBytes = adminUserName.getBytes();
        byte[] userPasswordBytes = adminPassword.getBytes();
        String tenantNameString = tenantNameEdit.getText().toString();

        if (adminUserName.length() > 0)
            editor.putString(PREF_ADMIN_USERNAME, new String(userNameBytes));
        else
            Toast.makeText(this, "No Admin Username", Toast.LENGTH_SHORT).show();

        if (adminPassword.length() > 0)
            editor.putString(PREF_ADMIN_PASSWORD, new String(userPasswordBytes));
        else
            Toast.makeText(this, "No Admin Username", Toast.LENGTH_SHORT).show();

        if (tenantNameString.length() > 0)
            editor.putString(PREF_TENANT_NAME, tenantNameString);
        else
            Toast.makeText(this, "No TenantName Username", Toast.LENGTH_SHORT).show();

        editor.apply();
    }

    private AppSettings getSavedPreferences() {

        SharedPreferences sharedPrefs = getPreferences(Context.MODE_PRIVATE);

        String adminUserName = sharedPrefs.getString(PREF_ADMIN_USERNAME, "");
        String adminPassword = sharedPrefs.getString(PREF_ADMIN_PASSWORD, "");
        String ws1TenantName = sharedPrefs.getString(PREF_TENANT_NAME, "");

        if (adminUserName.length() > 0)
            adminUserNameEdit.setText(adminUserName);

        if (adminPassword.length() > 0)
            adminPasswordEdit.setText(adminPassword);

        if (ws1TenantName.length() > 0) {
            tenantNameEdit.setText(ws1TenantName);
        }

        AppSettings appSettingsToReturn = new AppSettings();
        appSettingsToReturn.setUsername(adminUserName);
        appSettingsToReturn.setPassword(adminPassword);

        return appSettingsToReturn;
    }

    private void handleConnectionButton() {

        if (validateInput()) {
            Log.i(TAG, "Calling getSystemHealthJSON");
            getSystemHealthJSON();
        }
    }

    private boolean validateInput() {

        boolean dataValidated = true;
        if (tenantNameEdit.getText().toString().length() == 0) {
            Toast.makeText(this, getString(R.string.invalid_tenant_information), Toast.LENGTH_LONG).show();
            dataValidated = false;
        }

        if (adminUserNameEdit.getText().toString().length() == 0) {
            Toast.makeText(this, getString(R.string.invalid_user_name), Toast.LENGTH_LONG).show();
            dataValidated = false;
        }

        if (adminPasswordEdit.getText().toString().length() == 0) {
            Toast.makeText(this, getString(R.string.invalid_user_password), Toast.LENGTH_LONG).show();
            dataValidated = false;
        }

        return dataValidated;
    }


    private void getAppRestrictions() {

        Log.d(TAG, "Check to see if app is managed and has App Config");
        RestrictionsManager restrictionsManager = (RestrictionsManager) this.getSystemService(Context.RESTRICTIONS_SERVICE);

        Bundle appRestrictions = restrictionsManager.getApplicationRestrictions();
        // We got something, let's try to use it.
        if (!appRestrictions.isEmpty()) {
            // Need to research why keys would be pending
            if (!appRestrictions.getBoolean(UserManager.KEY_RESTRICTIONS_PENDING)) {
                String tenantFromAppConfig = appRestrictions.getString(TENANT_APP_CONFIG);
                Log.d(TAG, "Got a tenant from app config: " + tenantFromAppConfig);
                tenantNameEdit.setText(tenantFromAppConfig);
            } else {
                Toast.makeText(this, R.string.restrictions_pending_block_user, Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }

    private void registerRestrictionsReceiver() {
        IntentFilter restrictionsFilter = new IntentFilter(Intent.ACTION_APPLICATION_RESTRICTIONS_CHANGED);

        restrictionsReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d(TAG, "Got new app config information");
                getAppRestrictions();
            }
        };

        registerReceiver(restrictionsReceiver, restrictionsFilter);
    }
}

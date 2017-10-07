package com.curryware.ws1infoviewer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
    final static String ws1Domain = ".vmwareidentity.com";
    final static String heathCheckEndpoint = "SAAS/API/1.0/REST/system/health";
    final static String loginEndpoint = "SAAS/API/1.0/REST/auth/system/login";
    final static String PREF_ADMIN_USERNAME = "adminUserName";
    final static String PREF_ADMIN_PASSWORD = "adminPassword";
    final static String PREF_TENANT_NAME = "ws1TenantName";
    final static String AUTH_TOKEN_TO_PASS = "com.curryware.ws1infoviewer.AUTH_TOKEN";
    final static String TENANT_URL_TO_PASS = "com.curryware.ws1infoviewer.TENANT_URL";

    EditText tenantNameEdit;
    EditText adminUserNameEdit;
    EditText adminPasswordEdit;
    TextView completeTenantString;
    TextView healthCheckConnection;
    TextView buildNumberTextView;
    TextView authTokenTextView;
    Button connectButton;
    Button listUsersButton;

    OkHttpClient client;
    String VIDM_DOMAIN;
    String CURRENT_AUTH_TOKEN;
    String TENANT_URL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity = this;

        tenantNameEdit = findViewById(R.id.editTextTenantName);
        adminUserNameEdit = findViewById(R.id.editTextAdminUserName);
        adminPasswordEdit = findViewById(R.id.editTextAdminPassword);
        completeTenantString = findViewById(R.id.textViewCompleteTenant);
        healthCheckConnection = findViewById(R.id.textViewHealthCheckConnectionSuccessful);
        buildNumberTextView = findViewById(R.id.textViewBuildNumber);
        authTokenTextView = findViewById(R.id.textViewAuthTokenMessage);

        VIDM_DOMAIN = getString(R.string.vidm_domain);

        tenantNameEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                completeTenantString.setText("https://" + charSequence.toString() + ws1Domain);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

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
                TENANT_URL = tenantNameEdit.getText().toString();
                Intent intent = new Intent(activity, ListUserActivity.class);
                intent.putExtra(AUTH_TOKEN_TO_PASS, CURRENT_AUTH_TOKEN);
                intent.putExtra(TENANT_URL_TO_PASS, TENANT_URL);
                startActivity(intent);
            }
        });

        getSavedPreferences();
    }

    void getSystemHealthJSON() {

        client = HttpHelpers.getInstance();
        HttpUrl url = HttpHelpers.getFormattedURL(tenantNameEdit.getText().toString(),  heathCheckEndpoint);

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
                            healthCheckConnection.setVisibility(View.VISIBLE);
                            healthCheckConnection.setTextColor(ContextCompat.getColor(activity, R.color.colorAccent));
                            String buildVersion =  sysHealth.getBuildVersion();
                            Log.i(TAG, "Build Version: " + buildVersion);

                            int buildNumberOffset = buildVersion.lastIndexOf("Build");
                            String buildNumber = buildVersion.substring(0, buildNumberOffset);
                            buildNumber = getString(R.string.version_string) + " " + buildNumber;

                            buildNumberTextView.setVisibility(View.VISIBLE);
                            buildNumberTextView.setText(buildNumber);
                            buildNumberTextView.setTextColor(ContextCompat.getColor(activity, R.color.colorAccent));
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

        Request request = new Request.Builder()
                .url(HttpHelpers.getFormattedURL(tenantNameEdit.getText().toString(), loginEndpoint))
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
                        authTokenTextView.setVisibility(View.VISIBLE);
                        authTokenTextView.setText(getString(R.string.auth_token_failed));
                        authTokenTextView.setTextColor(ContextCompat.getColor(activity, R.color.error_red));

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
                            authTokenTextView.setVisibility(View.VISIBLE);
                            authTokenTextView.setTextColor(ContextCompat.getColor(activity, R.color.error_red));
                            authTokenTextView.setText(toastString);
                            Toast.makeText(activity, toastString, Toast.LENGTH_LONG).show();
                        }
                    });
                    throw new IOException("Unexpected Code: " + response);
                } else {
                    // ResponseBody body = response.body();
                    LoginResponse loginResponse = gson.fromJson(response.body().string(), LoginResponse.class);
                    Log.i(TAG, "Session Token: " + loginResponse.getSessionToken());
                    CURRENT_AUTH_TOKEN = loginResponse.getSessionToken();

                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            authTokenTextView.setVisibility(View.VISIBLE);
                            authTokenTextView.setTextColor(ContextCompat.getColor(activity, R.color.colorAccent));
                            authTokenTextView.setText(getString(R.string.auth_token_success));
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

    private void getSavedPreferences() {

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
            completeTenantString.setText("https://" + ws1TenantName + VIDM_DOMAIN);
        }
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
}

package com.curryware.ws1infoviewer;

import android.app.Activity;
import android.content.Context;
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

    /*  These values are for debug only */
    final static String adminUserName = "local_admin";
    final static String adminPassword = "AirWatch1";
    final static String ws1Tenant = "curryware2.vmwareidentity.com";

    final static MediaType mediaTypeJSON = MediaType.parse("application/json; charset=utf-8");
    final static String ws1Domain = ".vmwareidentity.com";
    final static String heathCheckEndpoint = "SAAS/API/1.0/REST/system/health";
    final static String loginEndpoint = "SAAS/API/1.0/REST/auth/system/login";
    final static String PREF_ADMIN_USERNAME = "adminUserName";
    final static String PREF_ADMIN_PASSWORD = "amdinPassword";
    final static String PREF_TENANT_NAME = "ws1TenantName";
    OkHttpClient client;

    EditText tenantNameEdit;
    EditText adminUserNameEdit;
    EditText adminPasswordEdit;
    TextView completeTenantString;
    TextView healthCheckConnection;
    Button saveButton;
    Button connectButton;

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

        saveButton = findViewById(R.id.buttonSave);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               handleSaveSettings();
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

        getSavedPreferences();
    }

    void getSystemHealthJSON() {
        client = new OkHttpClient();

        HttpUrl url = getFormattedURL(heathCheckEndpoint);
        Request request = new Request.Builder()
                .url(url)
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i(TAG, "Call Failed: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
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
                        }
                    });
                }
            }
        });
    }

    void getAuthToken() {

        // String adminJSON = "{\"username\":\"local_admin\", \"password\":\"AirWatch1\", \"issueToken\":\"true\"}";

        LoginCredentials loginCreds = new LoginCredentials();
        loginCreds.setUsername(adminUserName);
        loginCreds.setPassword(adminPassword);
        loginCreds.setIssueToken("true");

        final Gson gson = new Gson();
        String loginJSON = gson.toJson(loginCreds);
        Log.i(TAG, loginJSON);
        RequestBody body = RequestBody.create(mediaTypeJSON, loginJSON);

        Request request = new Request.Builder()
                .url(getFormattedURL(loginEndpoint))
                .post(body)
                .addHeader("Accept", "application/json")
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "Error attempting to login: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i(TAG, "Login call returned");
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected Code: " + response);
                } else {
                    ResponseBody body = response.body();
                    LoginResponse loginResponse = gson.fromJson(response.body().string(), LoginResponse.class);
                    Log.i(TAG, "Session Token: " + loginResponse.getSessionToken());
                }
            }
        });
    }

    private HttpUrl getFormattedURL(String urlEndpoint) {

        return new HttpUrl.Builder()
                .scheme("https")
                .host(ws1Tenant)
                .addEncodedPathSegments(urlEndpoint)
                .build();
    }

    private void handleSaveSettings() {

        SharedPreferences sharedPrefs = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();

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
            completeTenantString.setText("https://" + ws1TenantName + ".vmwareidentity.com");
        }
    }

    private void handleConnectionButton() {

        Log.i(TAG, "Calling getSystemHealthJSON");
        getSystemHealthJSON();
    }

    private void handleHealthCheckSuccessful() {

        healthCheckConnection.setVisibility(View.VISIBLE);
        healthCheckConnection.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
    }
}

package com.curryware.ws1infoviewer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ListUserActivity extends AppCompatActivity {

    final static String TAG = ListUserActivity.class.getSimpleName();
    final static String userEndpointURL = "SAAS/jersey/manager/api/scim/Users";

    OkHttpClient client;
    String RAW_OAUTH_TOKEN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_user);

        Intent intent = getIntent();
        RAW_OAUTH_TOKEN = intent.getStringExtra(MainActivity.AUTH_TOKEN_TO_PASS);
        String tenantURL = intent.getStringExtra(MainActivity.TENANT_URL_TO_PASS);
        Log.d(TAG, "Logging Information Provided to Intent");
        Log.d(TAG, "Tenant URL: " + tenantURL);
        Log.d(TAG, "OAuth Token: " + RAW_OAUTH_TOKEN);

        getAllUsers(tenantURL, RAW_OAUTH_TOKEN);
    }

    private void getAllUsers(String tenantURL, String oAuthToken) {

        Log.d(TAG, "Calling getAllUsers");
        client = HttpHelpers.getInstance();
        HttpUrl url = HttpHelpers.getFormattedURL(tenantURL, userEndpointURL);

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "HZN " + oAuthToken)
                .build();

        Log.d(TAG, request.toString());
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i(TAG, "Error Getting Users: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    Log.i(TAG, "Bad Response: " + String.valueOf(response.code()));
                } else {
                    Log.d(TAG, response.message());
                }
            }
        });
    }
}

package com.curryware.ws1infoviewer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    final static String TAG = MainActivity.class.getSimpleName();

    /*  These values are for debug only */
    final static String adminUserName = "local_admin";
    final static String adminPassword = "AirWatch1";
    final MediaType mediaType = MediaType.parse("application/json; charset=utf-8");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSystemHealthJSON();
    }

    void getSystemHealthJSON() {
        OkHttpClient client = new OkHttpClient();
        HttpUrl url = new HttpUrl.Builder()
                .scheme("https")
                .host("curryware2.vmwareidentity.com")
                .addEncodedPathSegment("SAAS/API/1.0/REST/system/health")
                .build();

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
                Log.i(TAG, "Call Succeeded: " + response.toString());
                getAuthToken();
            }
        });
    }

        void getAuthToken() {

            String adminJSON = "{\"username\":\"local_admin\", \"password\":\"AirWatch1\", \"issueToken\":\"true\"}";
            RequestBody body = RequestBody.create(mediaType, adminJSON);

            LoginCredentials loginCreds = new LoginCredentials();
            loginCreds.setUsername(adminUserName);
            loginCreds.setPassword(adminPassword);
            loginCreds.setIssueToken("true");

            Gson gson = new Gson();
            String loginJSON = gson.toJson(loginCreds);
            Log.i(TAG, loginJSON);
    }
}

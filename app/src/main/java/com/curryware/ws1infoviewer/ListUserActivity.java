package com.curryware.ws1infoviewer;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class ListUserActivity extends AppCompatActivity {

    final static String TAG = ListUserActivity.class.getSimpleName();
    final static String userEndpointURL = "SAAS/jersey/manager/api/scim/Users";

    String RAW_OAUTH_TOKEN;
    LinearLayoutManager linearLayoutManager;
    RecyclerView recyclerView;
    RecyclerViewAdapterUsers recyclerViewAdapterUsers;

    List<RecyclerViewUser> ws1Users;

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

        recyclerView = findViewById(R.id.recyclerViewUsers);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new RecyclerViewItemDecorationUsers(48));

        getAllUsers(tenantURL, RAW_OAUTH_TOKEN);
    }

    private void getAllUsers(String tenantURL, String oAuthToken) {

        Log.d(TAG, "Calling getAllUsers");
        OkHttpClient client = HttpHelpers.getInstance();
        HttpUrl url = HttpHelpers.getFormattedURL(tenantURL, userEndpointURL);

        final Gson gson = new Gson();
        String fullAuthToken = "HZN " + oAuthToken;
        Log.d(TAG, "Authorization Token: " + fullAuthToken);
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", fullAuthToken)
                .addHeader("Content-Type", "application/json")
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
                    ResponseBody responseBody = response.body();
                    String userResponseBody =  responseBody.string();
                    final GetUserResponse getAllResponses = gson.fromJson(userResponseBody, GetUserResponse.class);
                    Log.d(TAG, userResponseBody);

                    ListUserActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ws1Users = getAllUsersFromJSON(getAllResponses);
                            recyclerViewAdapterUsers = new RecyclerViewAdapterUsers(ListUserActivity.this, ws1Users);
                            recyclerView.setAdapter(recyclerViewAdapterUsers);
                        }
                    });
                }
            }
        });
    }

    private List<RecyclerViewUser> getAllUsersFromJSON(GetUserResponse allResponses) {

        List<RecyclerViewUser> allUsers = new ArrayList<>();

        List<UserResource> userResources = allResponses.getResources();
        String jsonUserName;
        String jsonEmailAddress;
        for (int counter = 0; counter < userResources.size(); counter++) {
            jsonUserName = userResources.get(counter).getUserName();
            jsonEmailAddress = userResources.get(counter).getEmails().get(0).getValue();


            int imageId = R.drawable.ic_user_image;
            List<UserRole> allRoles = userResources.get(counter).getRoles();
            for (int roleCounter = 0; roleCounter < allRoles.size(); roleCounter++) {
                UserRole thisRole = allRoles.get(roleCounter);
                if (thisRole.getDisplay().compareTo("Administrator") == 0) {
                    imageId = R.drawable.ic_admin_user;
                }
            }
            allUsers.add(new RecyclerViewUser(jsonUserName, jsonEmailAddress, imageId));
        }

        return allUsers;
    }
}

package com.curryware.ws1infoviewer;

import android.util.Log;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;


public class HttpHelpers {

    static final String TAG = HttpHelpers.class.getSimpleName();
    static String VIDM_DOMAIN = ".vmwareidentity.com";

    public static HttpUrl getFormattedURL(String tenantName, String urlEndpoint) {

        Log.d(TAG, "Tenant Name: " + tenantName);

        return new HttpUrl.Builder()
                .scheme("https")
                .host(tenantName)
                .addEncodedPathSegments(urlEndpoint)
                .build();
    }

    private static OkHttpClient instance = null;
    public static OkHttpClient getInstance() {
        if (instance == null) {
            instance = new OkHttpClient();
        }
        return instance;
    }
}

package com.example.antosh.myapplication;

import android.support.annotation.NonNull;
import android.util.Log;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;

public class JSONParser {

    static final String Tag = "ExLog";

    /********
     * URLS
     *******/
    private static final String MAIN_URL = "https://randomuser.me/api";

    /**
     * Response
     */

    private static Response response;
    /**
     * Get Data From WEB
     *
     * @return JSON Object
     */


    public static JSONObject getDataFromWeb() {
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(MAIN_URL)
                    .build();
            response = client.newCall(request).execute();
            return new JSONObject(response.body().string());
        } catch (@NonNull IOException | JSONException e) {
            Log.i("ExLog",e.getLocalizedMessage());

        }
        return null;
    }
}

package com.epicwednesday.android.createuser;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by mjohnson on 1/31/18.
 */

public class CreateMemberWebPull {
    private String mName;
    private String mPassword;
    private String results;
    private static final  String TAG = "CreateMemberWebPull";
    private static final String url = "http://10.0.2.2:8000/membercreate/";
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    OkHttpClient mOkHttpClient = new OkHttpClient();
    public void nameAndPass(String name, String pass){
        setName(name);
        setPassword(pass);
    }
    void createUser(){
        JSONObject json = new JSONObject();
        try {
            json.put("username", getName());
            json.put("password",getPassword());
        }catch (JSONException jse){
            Log.e(TAG, "createUser: ",jse );
        }
        Log.d(TAG, "createUser: "+json.toString());
        RequestBody requestBody = RequestBody.create(JSON,json.toString() );
        Request request = new Request.Builder().url(url).post(requestBody)
                .build();
        try (Response response = mOkHttpClient.newCall(request).execute()){
            setResults(response.body().string());
        }catch (IOException ioe){
            Log.e(TAG, "createUser: ",ioe );
        }

    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public String getResults() {
        return results;
    }

    public void setResults(String results) {
        this.results = results;
    }
}
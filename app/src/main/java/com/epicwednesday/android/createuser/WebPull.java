package com.epicwednesday.android.createuser;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by mjohnson on 1/13/18.
 */

public class WebPull {
   private static final String TAG = "WebPull";
   private static final  String API_ADDRESS = "http://10.0.2.2:8000/members/users/";
   private String mEmail;
   private String mPassword;
   private JSONArray mJsonArray;


   public WebPull(String email, String password){
       Log.d(TAG, "WebPull: ");
       setEmail(email);
       setPassword(password);
   }
    public byte[] getUrlBytes(String urlSpec) throws IOException {
        Log.d(TAG, "getUrlBytes: ");
        URL url = new URL(urlSpec);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();

        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException(connection.getResponseMessage() +
                        ": with " + urlSpec);
            }

            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }
            out.close();
            return out.toByteArray();
        } finally {
            connection.disconnect();
        }
    }


    public String getUrlString(String urlSpec) throws IOException {
        Log.d(TAG, "getUrlString: ");
        return new String(getUrlBytes(urlSpec));
    }


    public void validateEmail() {
        try {
            Log.d(TAG, "fetchItems: ");
            String url = Uri.parse(API_ADDRESS)
                    .buildUpon()
                    .build().toString();
            setJsonString( getUrlString(url));
            Log.d(TAG, "fetchItems: "+getJsonString());
            Log.i(TAG, "Received JSON: " + mJsonArray.toString());
        } catch (IOException ioe) {
            Log.e(TAG, "Failed to fetch items", ioe);
        }
    }


    public Boolean emailAlreadyExists(){
       Boolean bool = Boolean.valueOf(Boolean.FALSE);
       try {
            for (int i = 0; i < mJsonArray.length();i++) {
                JSONObject jsonObject = mJsonArray.getJSONObject(i);
                Log.d(TAG, "emailAlreadyExists: "+jsonObject.getString("email"));
              if ((jsonObject.getString("email")).equals(getEmail())){
                  Log.d(TAG, "emailAlready exists if statement true ");
                  bool = Boolean.valueOf(Boolean.TRUE);
              }
            }
       }catch (JSONException jse){
           Log.e(TAG, "emailAlreadyExists: ",jse );
       }
           return bool;
    }


    public String getEmail() {
        return mEmail;
    }

    private void setEmail(String email) {
        mEmail = email;
    }

    public String getPassword() {
        return mPassword;
    }

    private void setPassword(String password) {
        mPassword = password;
    }

    public JSONArray getJsonString() {
        return mJsonArray;
    }

    private void setJsonString(String jsonString) {
        Log.d(TAG, "setJsonString: "+jsonString);
       try {
           mJsonArray = new JSONArray(jsonString);
           Log.d(TAG, "setJsonString: "+ mJsonArray.length());
       }catch (JSONException jse){
           Log.e(TAG, "setJsonString: ",jse );
       }
    }
}

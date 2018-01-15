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
 * API_ADDRESS has to be changed before production
 * THis class checks to see if email is in database
 *     0.API_ADDRESS has to be changed to match appropriate api ADDRESS
 *     1. has to be initialized with the email
 *     2. After Initialization call validateEmail()
 *     3. call emailALreadyExists to
 *
 */

public class EmailValidationWebPull {
   private static final String TAG = "EmailValidationWebPull";
   private static final  String API_ADDRESS = "http://10.0.2.2:8000/members/users/";
   private String mEmail;
   private Boolean mEmailValidBoolean = true;
   private JSONArray mJsonArray;


   public EmailValidationWebPull(String email){
       Log.d(TAG, "EmailValidationWebPull: ");
       setEmail(email);
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
            setEmailiSValidBoolean();
        } catch (IOException ioe) {
            Log.e(TAG, "Failed to fetch items", ioe);
        }
    }





    public String getEmail() {
        return mEmail;
    }

    private void setEmail(String email) {
        mEmail = email;
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

    public Boolean getEmailIsValidBoolean() {
        return mEmailValidBoolean;
    }

    private void setEmailiSValidBoolean() {
        try {
            for (int i = 0; i < mJsonArray.length();i++) {
                JSONObject jsonObject = mJsonArray.getJSONObject(i);
                Log.d(TAG, "emailAlreadyExists: "+jsonObject.getString("email"));
                if ((jsonObject.getString("email")).equals(getEmail())){
                    Log.d(TAG, "emailAlready exists if statement true ");
                    mEmailValidBoolean = false;
                }
            }
        }catch (JSONException jse){
            Log.e(TAG, "setEMailValidBoolean: ",jse );
        }

    }
}

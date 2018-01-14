package com.epicwednesday.android.createuser;

import android.net.Uri;
import android.util.Log;

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
   private static final  String API_ADDRESS = "http://127.0.0.1:8000/members/users/";
   private String mEmail;
   private String mPassword;
   private String mJsonString;

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

    public void fetchItems() {
        try {
            Log.d(TAG, "fetchItems: ");
            String url = Uri.parse("http://10.0.2.2:8000/members/")
                    .buildUpon()
                    .build().toString();
            setJsonString( getUrlString(url));
            Log.d(TAG, "fetchItems: "+getJsonString());
            Log.i(TAG, "Received JSON: " + mJsonString);
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

    public String getPassword() {
        return mPassword;
    }

    private void setPassword(String password) {
        mPassword = password;
    }

    public String getJsonString() {
        return mJsonString;
    }

    private void setJsonString(String jsonString) {
        mJsonString = jsonString;
    }
}

package com.wolff.wolfffrest1c.rest;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.wolff.wolfffrest1c.Const.BASE_URL;


/**
 * Created by wolff on 13.02.2017.
 */

public class RESTInvoker {
    SharedPreferences sp;
    public RESTInvoker(){}

    public HttpURLConnection getConnection(Context context,URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        sp = PreferenceManager.getDefaultSharedPreferences(context);
        //String authString = LOGIN + ":" + PASSWORD;
        Log.e("LOGIN = ",""+sp.getString("serverLogin","wolf"));
        Log.e("PASS = ",""+sp.getString("serverPassword","1"));
        String authString = sp.getString("serverLogin","wolf")+ ":" + sp.getString("serverPassword","1");
        String authStringEnc;
        authStringEnc = new String(Base64.encode(authString.getBytes(),0));
        urlConnection.setRequestProperty("Authorization", "Basic " + authStringEnc);
        return urlConnection;
    }
    public String getBaseUrl(Context context){
        sp = PreferenceManager.getDefaultSharedPreferences(context);
       // String lUrl = "http://"+sp.getString("serverName","13.10.12.10")+"/"+sp.getString("baseName","v83_zadacha")+BASE_URL;
        String lUrl = "http://"+"13.10.12.10"+"/"+"v83_zadacha"+BASE_URL;
        //String lUrl = "http://"+"13.10.12.11"+"/"+"v83_zadacha"+BASE_URL;
        Log.e("getBaseUrl = ",""+lUrl);
        return lUrl;
    }
}

package com.wolff.wolfffrest1c.rest;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.wolff.wolfffrest1c.Const.BASE_URL;

/**
 * Created by wolff on 22.02.2017.
 */

public class GetData {

    public String getDataFromServer(String catalog) {
        RESTInvoker restInvoker = new RESTInvoker();
        StringBuilder sb = new StringBuilder();
        try {
            URL url = new URL(BASE_URL + catalog+"?$format=json");
            HttpURLConnection urlConnection = restInvoker.getConnection(url);
            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            reader.close();
            Log.e("GET",""+sb.toString());
            return sb.toString();
        } catch (Exception e) {
            Log.e("ERROR","WRONG PASS");
            //throw new RuntimeException(e);
            return "";
        }
    }



}

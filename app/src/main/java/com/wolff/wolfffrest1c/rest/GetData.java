package com.wolff.wolfffrest1c.rest;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static com.wolff.wolfffrest1c.Const.BASE_URL;
import static com.wolff.wolfffrest1c.Const.READ_TIMEOUT;
import static com.wolff.wolfffrest1c.Const.CONNECT_TIMEOUT;

/**
 * Created by wolff on 22.02.2017.
 */

public class GetData {

    public String getDataFromServer(Context context, String catalog) throws IOException {
        RESTInvoker restInvoker = new RESTInvoker();
        StringBuilder sb = new StringBuilder();
            URL url = new URL(restInvoker.getBaseUrl(context) + catalog+"?$format=json");
            HttpURLConnection urlConnection = restInvoker.getConnection(context,url);
            urlConnection.setReadTimeout(READ_TIMEOUT);
            urlConnection.setConnectTimeout(CONNECT_TIMEOUT);

        BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            reader.close();
            urlConnection.disconnect();
            //Log.e("GET",""+sb.toString());
            return sb.toString();
     }



}

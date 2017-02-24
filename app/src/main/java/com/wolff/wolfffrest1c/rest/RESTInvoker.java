package com.wolff.wolfffrest1c.rest;

import android.util.Base64;
import android.util.Log;

import org.apache.commons.io.IOUtils;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import static com.wolff.wolfffrest1c.Const.LOGIN;
import static com.wolff.wolfffrest1c.Const.PASSWORD;


/**
 * Created by wolff on 13.02.2017.
 */

public class RESTInvoker {
    RESTInvoker(){}

    public HttpURLConnection getConnection(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        String authString = LOGIN + ":" + PASSWORD;
        String authStringEnc;
        authStringEnc = new String(Base64.encode(authString.getBytes(),0));
        urlConnection.setRequestProperty("Authorization", "Basic " + authStringEnc);
        return urlConnection;
    }
}

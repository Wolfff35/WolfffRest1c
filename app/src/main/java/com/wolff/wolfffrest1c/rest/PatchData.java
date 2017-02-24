package com.wolff.wolfffrest1c.rest;

import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static com.wolff.wolfffrest1c.Const.BASE_URL;

/**
 * Created by wolff on 23.02.2017.
 */

public class PatchData {
         public String patchDataOnServer(String data,String catalog,String guid,String dataVersion){
            int responseCode=0;
            HttpURLConnection conn=null;
            RESTInvoker restInvoker = new RESTInvoker();
            try {
                URL url = new URL(BASE_URL+catalog+"(guid'"+guid+"')?$format=json");
                conn = restInvoker.getConnection(url);
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("PATCH");
                conn.setDoOutput(true);
                conn.setDoInput(true);
                conn.setRequestProperty("If-Match", ""+dataVersion);
                conn.setRequestProperty("Content-Length", "" + Integer.toString(data.getBytes().length));

                OutputStream os= new BufferedOutputStream(conn.getOutputStream());
                os.write(data.getBytes());
                os.flush();
                conn.connect();

                responseCode= conn.getResponseCode();
                 if ((responseCode >= 200)&&(responseCode<=203)) {
                    Log.e("SUCCESS","code= "+responseCode);
                }else if (responseCode==412) {
                     //Данные изменились в процессе
                     Log.e("SERVER_ERROR","code== "+responseCode);
                 }else{
                    Log.e("SERVER_ERROR","code== "+responseCode);
                }
            } catch (MalformedURLException e) {
                //Log.e("!!!!!!"," 2 ");
            } catch (IOException e) {
                //Log.e("!!!!!!"," 3==== "+e.getLocalizedMessage()+"\n"+e.getMessage()+"\n"+e.fillInStackTrace().toString());
            }finally {

                conn.disconnect();

            }

            return  Integer.toString(responseCode);
        }


    }



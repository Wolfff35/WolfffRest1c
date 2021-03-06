package com.wolff.wolfffrest1c.rest;

import android.content.Context;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static com.wolff.wolfffrest1c.Const.BASE_URL;
import static com.wolff.wolfffrest1c.Const.CONNECT_TIMEOUT;
import static com.wolff.wolfffrest1c.Const.READ_TIMEOUT;

/**
 * Created by wolff on 23.02.2017.
 */

public class PatchData {
         public String patchDataOnServer(Context context, String data, String catalog, String guid, String dataVersion){
            int responseCode=0;
            HttpURLConnection conn=null;
            RESTInvoker restInvoker = new RESTInvoker();
            Log.e("DATA = ",""+data);
             try {
                URL url = new URL(restInvoker.getBaseUrl(context)+catalog+"(guid'"+guid+"')?$format=json");
                conn = restInvoker.getConnection(context,url);
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECT_TIMEOUT);
                conn.setRequestMethod("PATCH");
                conn.setDoOutput(true);
                conn.setDoInput(true);
                //conn.setRequestProperty("If-Match", ""+dataVersion);
                conn.setRequestProperty("Content-Length", "" + Integer.toString(data.getBytes().length));

                OutputStream os= new BufferedOutputStream(conn.getOutputStream());
                os.write(data.getBytes());
                os.flush();
                conn.connect();

                responseCode= conn.getResponseCode();
                 if ((responseCode >= 200)&&(responseCode<=203)) {
                   // Log.e("SUCCESS","code= "+responseCode);
                }else if (responseCode==412) {
                     //Данные изменились в процессе
                   //  Log.e("SERVER_ERROR","code== "+responseCode);
                 }else{
                   // Log.e("SERVER_ERROR","code== "+responseCode);
                }
            } catch (MalformedURLException e) {
               // Log.e("!!!!!!"," 2 ");
            } catch (IOException e) {
               // Log.e("!!!!!!"," 3==== "+e.getLocalizedMessage());
            }finally {

                conn.disconnect();

            }

            return  Integer.toString(responseCode);
        }


    }


